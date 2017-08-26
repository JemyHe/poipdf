package controller;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import module.Contract;
import module.Developer;
import module.Project;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping( "/main" )
public class MainController{

    //doxc模板
    private static final String SIMPLE_PARAM_TEMPLATE_PATH = "F:\\poi\\poipdf\\src\\main\\resources\\ReportWithVelocity.docx";

    //doclist模板
    private static final String LIST_PARAM_TEMPLATE_PATH = "F:\\poi\\poipdf\\src\\main\\resources\\Convert2PDFWithList.docx";

    //合同模板
    private static final String CONTRACT_TEMPLATE_PATH = "F:\\poi\\poipdf\\src\\main\\resources\\Contract.docx";

    //report存放路径
    private static final String REPORT_PATH = "F:\\poi\\poipdf\\src\\main\\resources\\report";

    //convert存放路径
    private static final String CONVERT_PATH = "F:\\poi\\poipdf\\src\\main\\resources\\convert";

    @RequestMapping(value = "/download", method = RequestMethod.GET )
    public String getDownloadPage(){
        return "downloadpage";
    }

    /**
     * 下载简单填值docx模板
     * @param
     * @return
     */
    @RequestMapping("/template/docx")
    public void templateDocx(HttpServletResponse response){
        downloadTemplate(response,MainController.SIMPLE_PARAM_TEMPLATE_PATH);
    }

    /**
     * 填值docx模板并输出
     * @param
     * @return
     */
    @RequestMapping("/report/docx")
    public void reportDocx(HttpServletResponse response){
        String report = report(MainController.SIMPLE_PARAM_TEMPLATE_PATH, 0);
        downloadTemplate(response,report);
    }

    /**
     * 填值docx模板并输出为pdf
     * @param
     * @return
     */
    @RequestMapping("/convert/docx")
    public void convertDocx(HttpServletResponse response){
        String convert = convert(MainController.SIMPLE_PARAM_TEMPLATE_PATH, 0 ,null);
        downloadTemplate(response,convert);
    }

    /**
     * 下载list填值docx模板
     * @param response
     */
    @RequestMapping( "/template/docxlist" )
    public void templateDocxList(HttpServletResponse response){
        downloadTemplate(response,MainController.LIST_PARAM_TEMPLATE_PATH);
    }

    /**
     * 填值docxlist模板并输出
     * @param
     * @return
     */
    @RequestMapping("/report/docxlist")
    public void reportDocxlist(HttpServletResponse response){
        String report = report(MainController.LIST_PARAM_TEMPLATE_PATH, 1);
        downloadTemplate(response,report);
    }

    /**
     * 填值docxlist模板并输出为pdf
     * @param
     * @return
     */
    @RequestMapping("/convert/docxlist")
    public void convertDocxlist(HttpServletResponse response){
        String convert = convert(MainController.LIST_PARAM_TEMPLATE_PATH, 1,null);
        downloadTemplate(response,convert);
    }

    /**
     * 下载合同模板
     * @param response
     */
    @RequestMapping( "/template/contract" )
    public void templateTemplate(HttpServletResponse response){
        downloadTemplate(response,MainController.CONTRACT_TEMPLATE_PATH);
    }

    /**
     * 下载合同pdf
     * @param response
     */
    @RequestMapping( "/convert/contract" )
    public void convertContract(HttpServletResponse response,Contract contract){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String now = sdf.format(new Date());
        contract.setNow(now);
        String convert = convert(MainController.CONTRACT_TEMPLATE_PATH, 2 , contract);
        downloadTemplate(response,convert);
    }



    /**
     * 填值输出
     * @param filePath
     * @param type  简单填值：0   list填值：1
     */
    private String report(String filePath,int type){
        try {
            File docx = new File(filePath);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(new FileInputStream(docx) , TemplateEngineKind.Velocity);

            IContext context = report.createContext();
            Project project = new Project("EmployeeList");
            context.put("project", project);

            //list填值
            if(type==1){
                initParams(context,report);
            }

            String outPath = MainController.REPORT_PATH + File.separator + "Result.docx";
            OutputStream out = new FileOutputStream(new File(outPath));
            report.process(context, out);
            return outPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XDocReportException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * docx2PDF
     * @param filePath
     * @param type
     * @return
     */
    private String convert(String filePath, int type, Contract contract){

        try {
            File docx = new File(filePath);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(new FileInputStream(docx) , TemplateEngineKind.Velocity);


            // 2) Create context Java model
            IContext context = report.createContext();
            Project project = new Project("EmployeeList");
            context.put("project", project);

            if(type==1){
                //list填值
                initParams(context,report);
            }

            //合同填值输出
            String pdfname = "Result.pdf";
            if(contract!=null){
                context.put("contract",contract);
                pdfname = "Contract.pdf";
            }

            String outPath = MainController.CONVERT_PATH + File.separator + pdfname;
            OutputStream out = new FileOutputStream(new File(outPath));
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
            report.convert(context, options, out);
            return outPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XDocReportException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 组装list参数
     * @param context
     * @param report
     */
    private void initParams(IContext context,IXDocReport report){
        List<Developer> list = new ArrayList<Developer>();
        list.add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com"));
        list.add(new Developer("Leclercq", "Pascal", "pascal.leclercq@gmail.com"));
        context.put("developer",list);
        FieldsMetadata metadata = new FieldsMetadata();
        metadata.addFieldAsList("developer.firstName");
        metadata.addFieldAsList("developer.lastName");
        metadata.addFieldAsList("developer.email");
        report.setFieldsMetadata(metadata);
    }

    /**
     * 下载
     * @param response
     * @param filePath
     */
    private void downloadTemplate(HttpServletResponse response,String filePath){
        try {
            File docx = new File(filePath);
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(docx));
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="+docx.getName());
            OutputStream out = response.getOutputStream();
            while ((len = br.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            br.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
