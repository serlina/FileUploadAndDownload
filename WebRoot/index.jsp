<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<html>
	<head>
		<title>文件下载和下载</title>
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
				<tr>
					<td width="50%" style="padding-left: 306; padding-top: 28">
						<a href="uploadfile.jsp"><img src="images/up.jpg"
								style="border: 0">
						</a>
					</td>
					<td width="50%" style="padding-left: 31; padding-top: 28">
						<a href="downloadview"><img src="images/down.jpg"
								style="border: 0">
						</a>
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