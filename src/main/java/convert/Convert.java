package convert;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import module.Developer;
import module.Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 *  读取docx模板，填充值后，转换为pdf文档
 */
public class Convert {

    public static void convert(){
        try {
            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            InputStream in = ClassLoader.getSystemResourceAsStream("ReportWithVelocity.docx");
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            // 2) Create context Java model
            IContext context = report.createContext();
            Project project = new Project("XDocReport");
            context.put("project", project);

            // 3) Generate report by merging Java model with the Docx
            OutputStream out = new FileOutputStream(new File("ReportWithVelocity_Out.pdf"));
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
            report.convert(context, options, out);
            System.out.println("测试git");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XDocReportException e) {
            e.printStackTrace();
        }
    }

    public static void convertWithList(){
        try {
            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            InputStream in = ClassLoader.getSystemResourceAsStream("Convert2PDFWithList.docx");
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            // 2) Create context Java model
            IContext context = report.createContext();
            Project project = new Project("EmployeeList");
            List<Developer> list = new ArrayList<Developer>();
            list.add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com"));
            list.add(new Developer("Leclercq", "Pascal", "pascal.leclercq@gmail.com"));
            project.setList(list);
            //context可以根据需要put多个键值对，也可以存放map，这里只put了一个
            context.put("project", project);


            // 3) Create fields metadata to manage lazy loop (#forech velocity) for table row.
            //如果docx文档中没有#foreach，自动创建的情况下才需要以下代码
            //如果docx文档中有遍历标签#foreach，则不需要以下代码
            //如果遍历的数据要存放在docx表格中，最好使用以下方式处理表格的样式
            /*context.put("developer",list);
            FieldsMetadata metadata = new FieldsMetadata();
            metadata.addFieldAsList("developer.firstName");
            metadata.addFieldAsList("developer.lastName");
            metadata.addFieldAsList("developer.email");
            report.setFieldsMetadata(metadata);*/

            // 4) Generate report by merging Java model with the Docx
            OutputStream out = new FileOutputStream(new File("ReportWithVelocity_Out.pdf"));
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
            report.convert(context, options, out);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XDocReportException e) {
            e.printStackTrace();
        }
    }
}
