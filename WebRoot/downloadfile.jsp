<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<html>
	<head>
		<title>文件下载</title>
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
						<a style="color: white" href="uploadfile.jsp">上传文件</a>&nbsp;&nbsp;
					</td>
				</tr>
				<tr height="350">
					<td width="22%" align="center" valign="top" bgcolor="#F7FAF6"
						style="padding-top: 8">
						<div
							style="background: #177B6C; width: 94%; height: 25; color: white">
							<b>注意事项：</b>
						</div>
					</td>
					<td align="center" valign="top" bgcolor="white">
						<table border="0" width="95%" cellpadding="5" cellspacing="1"
							bgcolor="#999999" style="word-break: break-all; margin-top: 20">
							<tr height="25" align="center" class="listhead">
								<td width="35%">
									文件名称
								</td>
								<td width="25%">
									文件大小
								</td>
								<td>
									文件描述
								</td>
								<td width="8%">
									下载
								</td>
							</tr>
							<c:if test="${!empty files}">
								<c:forEach var="file" varStatus="fvs" items="${files}">
									<c:if test="${fvs.index%2==0}">
										<c:set var="bgc" value="#F5F5F5" />
									</c:if>
									<c:if test="${fvs.index%2!=0}">
										<c:set var="bgc" value="white" />
									</c:if>
									<tr bgcolor="${bgc}">
										<td>
											${file.fileName}
										</td>
										<td>
											${file.fileSize} 字节
										</td>
										<td>
											${file.fileInfoForShow}
										</td>
										<td align="center">
											<a href="downloadrun?downname=${file.saveName}">下载</a>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty files}">
								<tr height="50" bgcolor="white" align="center">
									<td colspan="4">
										没有文件！
									</td>
								</tr>
							</c:if>
						</table>
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