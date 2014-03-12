package com.duanchao.file.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duanchao.file.dao.FileDao;
import com.duanchao.file.toolsbean.StringHandler;
import com.duanchao.file.valuebean.FileBean;
import com.duanchao.file.yxq.filexload.File;
import com.duanchao.file.yxq.filexload.FileXLoad;
import com.duanchao.file.yxq.filexload.Parameters;

@SuppressWarnings("serial")
public class MyFileServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 获取请求方式
		String servletPath = request.getServletPath();
		if ("/upload".equals(servletPath))
			upload(request, response);
		else if ("/downloadview".equals(servletPath))
			downloadview(request, response);
		else if ("/downloadrun".equals(servletPath))
			downloadrun(request, response);
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void upload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "";
		int maxlen = 1024 * 1024 * 50;  //文件的最大为 50MB
		String filedir = getServletContext().getRealPath("/files"); // 文件保存到磁盘上的路径
		System.out.println("文件保存到磁盘上的路径filedir--" + filedir);
		FileXLoad myxload = new FileXLoad(); // 实例化FileXLoad
		myxload.init(request, response, filedir);
		myxload.setMaxlen(maxlen); // 设置文件最大的大小
		boolean start = myxload.upload(); // 调用文件上传的方法

		if (start) {
			List files = myxload.getFiles();
			//判断文件是否存在
			if (files != null && files.size() != 0) {
				Date date = new Date();
				FileDao fileDao = new FileDao();
				Parameters params = myxload.getParams();
				for (int i = 0; i < files.size(); i++) {
					File file = (File) files.get(i);
					//判断文件是否有效
					if (file.isAvailable()) {
						String filepath = file.getFilePath(); // 获取文件路径
						String fileinfo = params.getFieldValue("fileinfo"
								+ (i + 1)); // 获取文件描述
						
						// 获取文件保存到数据库的名字
						// file.getFileExt():文件扩展名，包含"."字符
						String savename = StringHandler.getSerial(date, i)
								+ file.getFileExt();
						System.out.println(savename + "---savename---"
								+ file.getFileExt());
						String filename = file.getFileName(); // 获取上传文件的名称
						String filetype = file.getFileType(); // 获取上传文件的类型
						int filesize = file.getFileSize(); // 获取上传文件的大小
						String uptime = StringHandler.timeTostr(date); // 获取文件上传的时间

						FileBean filebean = new FileBean();
						filebean.setFilePath(filepath);
						filebean.setFileName(filename);
						filebean.setSaveName(savename);
						filebean.setFileType(filetype);
						filebean.setFileSize(filesize);
						filebean.setFileInfo(fileinfo);
						filebean.setUptime(uptime);

						int k = fileDao.addFileInfo(filebean); // 保存文件信息到数据库中
						if (k <= 0)
							message += "● 文件 <b><font color='red'>"
									+ file.getFilePath()
									+ "</font></b> 上传失败！<br>";
						else {
							//filedir：保存到磁盘上的路径
							//savename：保存到磁盘上的文件名
							boolean savetodisk = file.saveFileToDisk(filedir,
									savename); // 保存文件到磁盘中
							if (savetodisk)
								message += "√ 文件 <b><font color='red'>"
										+ file.getFilePath()
										+ "</font></b> 上传成功！<br>";
							else
								message += "× 文件 <b><font color='red'>"
										+ file.getFilePath()
										+ "</font></b> 上传失败！<br>";
						}
					} else
						message += "● 文件 <b><font color='red'>"
								+ file.getFilePath()
								+ "</font></b> 无效或大小为0！<br>";
				}
				fileDao.closed();
			} else
				message += "● 请至少选择一个要上传的文件，或您选择的文件大小为0！<br>";
		} else
			message = myxload.getMessage();

		message += "<br>>> <a href='uploadfile.jsp'>返回</a>";
		message += "<br>>> <a href='index.jsp'>主页</a>";

		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		rd.forward(request, response);
	}

	/**
	 * 显示所有的文件信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void downloadview(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		FileDao fileDao = new FileDao();
		try {
			List filelist = fileDao.getFileList();
			request.setAttribute("filelist", filelist); // 保存所有的文件下载信息
		} catch (SQLException e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request
				.getRequestDispatcher("/downloadfile.jsp");
		rd.forward(request, response);
	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void downloadrun(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		FileBean file = null;
		try {
			String filedir = getServletContext().getRealPath("/files"); // 获取文件所在的磁盘路径
			System.out.println(filedir + "---获取文件所在的磁盘路径");
			String downfilename = request.getParameter("downname"); // 获取保存在磁盘上的文件名
			System.out.println(downfilename + "---downfilename");

			FileDao fileDao = new FileDao();
			file = fileDao.getFileSingle(downfilename);
			fileDao.closed();
			if (file != null) {
				String filename = new String(file.getFileName().getBytes(
						"gb2312"), "ISO-8859-1"); // 该行代码用来解决下载的文件名是乱码
				FileXLoad myxload = new FileXLoad();
				myxload.init(request, response, filedir);
				myxload.download(downfilename, filename, file.getFileType());
			}
		} catch (Exception e) {
			e.printStackTrace();
			String message = "× 下载失败！文件 <b><font color='red'>"
					+ file.getFileName() + "</font></b> 不存在或已经被删除！<br>";
			message += "<a href='javascript:window.history.go(-1)'>返回</a>";
			request.setAttribute("message", message);
			RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
			rd.forward(request, response);
		}
	}
}
