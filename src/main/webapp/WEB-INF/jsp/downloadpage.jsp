<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>demo</title>
</head>
<body>
    <h2>docx模板简单参数填值</h2>
    <a href="/main/template/docx" target="_blank">下载docx模板</a>
    <a href="/main/report/docx">下载docx结果</a>
    <a href="/main/convert/docx">下载pdf结果</a>
    <br/>

    <h2>docx模板list参数填值</h2>
    <a href="/main/template/docxlist" target="_blank">下载docx模板</a>
    <a href="/main/report/docxlist">下载docx结果</a>
    <a href="/main/convert/docxlist">下载pdf结果</a>
    <br/>

    <h2>生成合同pdf</h2>
    <a href="/main/template/contract" target="_blank">下载合同模板</a>
    <form action="/main/convert/contract" method="post">
        姓名:<input name="name"></br>
        联系方式:<input name="phone"/></br>
        身份证号:<input name="idcard" /></br>
        家庭地址:<input name="address" /></br>
        <button type="submit">下载结果</button>
    </form>
    <br/>
</body>
</html>