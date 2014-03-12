<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<html>
	<head>
		<title>文件信息</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	<body bgcolor="#E1E1E1">
		<c:set var="files" value="${requestScope.filelist}" />
		<center>
			<table border="1" bordercolor="#BFD3E1" cellpadding="0"
				cellspacing="0" rules="cols">
				<tr>
					<td colspan="2">
						<img src="images/top.jpg" width="833">
					</td>
				<tr>
				<tr height="30" bgcolor="black">
					<td colspan="2" style="text-indent: 20" align="right">
						<a style="color: white" href="index.jsp">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a style="color: white" href="uploadfile.jsp">下载上传</a>&nbsp;&nbsp;
						<a style="color: white" href="downloadview">下载文件</a>&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="listhead" height="25">
					<td>
						文件上传
					</td>
				</tr>
				<tr bgcolor="#F5F5F5">
					<td align="center">
						${requestScope.message}
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<img src="images/end.jpg" width="833">
					</td>
				<tr>
			</table>
		</center>
	</body>
</html>