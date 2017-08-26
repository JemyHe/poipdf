package report;


/**
 * Created by Administrator on 2017/8/23.
 */
public class ReportClass {

    public static void main(String[] args) {

        //为docx模板填值并输出
        ReportWithVelocity.report();

        //使用缓存为docx模板填值并输出
        //ReportWithVelocity.reportWithCache();
    }
}
