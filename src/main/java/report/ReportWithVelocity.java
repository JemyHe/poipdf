package report;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ClassPathImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import module.Project;


import java.io.*;

/**
 * Created by Administrator on 2017/8/23.
 * XdocReport基於docx格式，doc格式不允許
 *
 * 項目只做了docx的報告demo，如果需要pptx或者其他格式的報告
 * 請參照wiki：
 * https://github.com/opensagres/xdocreport/wiki/Reporting
 */
public class ReportWithVelocity {

    /**
     * 通過velocity模板引擎 將 $project.name 的值從實體類中讀取出來
     * 并輸出成docx文檔。
     * 注：
     * 如果需要使用FreeMarker模板引擎，
     * 在docx文檔中使用${project.name}格式
     */
    public static void report(){
        try {
            // 1) Load Docx file by filling Velocity template engine and cache it to the registry
            //讀取模板
            InputStream in = ClassLoader.getSystemResourceAsStream("ReportWithVelocity.docx");
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
            //如果需要使用FreeMarker模板引擎，則使用下一句代碼，並且在docx文檔中使用${project.name}格式
            //IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

            // 2) Create context Java model
            //填值；需要填充多个键值对或者list，请参照Convert.convertWithList()
            IContext context = report.createContext();
            Project project = new Project("XDocReport");
            context.put("project", project);

            // 3) Generate report by merging Java model with the Docx
            //輸出docx
            OutputStream out = new FileOutputStream(new File("ReportWithVelocity_Out.docx"));
            report.process(context, out);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XDocReportException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一次讀取docx模板時，可以指定一個cacheId進行緩存，運行結果結果如下
     *  Report processed in 1040 ms
     *  Report processed in 65 ms
     *  利用緩存讀取docx模板更加快速
     */
    public static void reportWithCache(){
        try {
            //緩存鍵
            String reportId = "cacheA";

            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            InputStream in = ClassLoader.getSystemResourceAsStream("ReportWithVelocity.docx");
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, reportId, TemplateEngineKind.Velocity);

            // 2) Create context Java model
            IContext context = report.createContext();
            Project project = new Project("XDocReport");
            context.put("project", project);

            // 3) Generate report by merging Java model with the Docxs
            OutputStream out = new FileOutputStream(new File("ReportWithVelocity_Out.docx"));
            long start = System.currentTimeMillis();
            report.process(context, out);
            System.out.println("Report processed in " + (System.currentTimeMillis() - start) + " ms");

            // 4) Regenerate report 從緩存中取cacheA對應的report
            IXDocReport report2 = XDocReportRegistry.getRegistry().getReport(reportId);
            out = new FileOutputStream(new File("ReportWithVelocity_Out.docx"));
            start = System.currentTimeMillis();
            report2.process(context, out);
            System.out.println("Report processed in " + (System.currentTimeMillis() - start) + " ms");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XDocReportException e) {
            e.printStackTrace();
        }
    }
}
