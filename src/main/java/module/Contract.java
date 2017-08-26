package module;

/**
 * Created by Administrator on 2017/8/25.
 */
public class Contract {

    private String name;

    private String phone;

    private String idcard;

    private String address;

    private String money = "17600.00";

    private String moneyText = "壹万柒仟陆百元整";

    private String month = "6";

    private String payback = "0.29";

    private String paybackMonth = "7个月";

    private String paybackPermonth = "1215.28";

    private String totalMonth = "24个月";

    private String now;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneyText() {
        return moneyText;
    }

    public void setMoneyText(String moneyText) {
        this.moneyText = moneyText;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPayback() {
        return payback;
    }

    public void setPayback(String payback) {
        this.payback = payback;
    }

    public String getPaybackMonth() {
        return paybackMonth;
    }

    public void setPaybackMonth(String paybackMonth) {
        this.paybackMonth = paybackMonth;
    }

    public String getPaybackPermonth() {
        return paybackPermonth;
    }

    public void setPaybackPermonth(String paybackPermonth) {
        this.paybackPermonth = paybackPermonth;
    }

    public String getTotalMonth() {
        return totalMonth;
    }

    public void setTotalMonth(String totalMonth) {
        this.totalMonth = totalMonth;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public Contract() {
    }

    public Contract(String name, String phone, String idcard, String address, String now) {
        this.name = name;
        this.phone = phone;
        this.idcard = idcard;
        this.address = address;
        this.now = now;
    }
}
