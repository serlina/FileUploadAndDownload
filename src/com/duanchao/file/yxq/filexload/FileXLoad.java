package com.duanchao.file.yxq.filexload;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileXLoad {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Parameters params;

	private String message;
	protected byte[] filecontent_b;
	private int maxlen;
	private int current_pos;
	private String filedir;

	public FileXLoad() {
		message = "";
		maxlen = 0;
		current_pos = 0;
		filedir = "";
		params = new Parameters();
	}

	public void setMaxlen(int maxlen) {
		this.maxlen = maxlen;
	}

	public void init(HttpServletRequest request, HttpServletResponse response,
			String filedir) {
		this.request = request;
		this.response = response;
		this.filedir = filedir;
	}

	/**
	 * 文件上传的方法
	 * 
	 * @return
	 */
	public boolean upload() {
		/** 定义用来保存上传文件的目录 */
		java.io.File dir = new java.io.File(filedir);
		if (!dir.exists())
			dir.mkdir();

		boolean mark = false;
		String contentType = request.getContentType(); // 获取文件格式
		System.out.println(contentType + "----获取文件格式contentType");
		if (contentType.indexOf("multipart/form-data", 0) != -1) {
			int len = request.getContentLength(); // 文件的大小
			if (len > maxlen)
				message = "● 上传的文件总长度最大极限为50兆!";
			else {
				/** 将文件表单内容读到字节数组filecontent_b中 */
				try {
					DataInputStream in = new DataInputStream(request
							.getInputStream());
					filecontent_b = new byte[len];
					int readbyte = 0;
					int totalreadbyte = 0;
					while (totalreadbyte < len) {
						readbyte = in.read(filecontent_b, totalreadbyte, len);
						totalreadbyte += readbyte;
					}
					in.close();
					mark = true;
				} catch (IOException e) {
					e.printStackTrace();
				}

				/** 将文件表单内容写到临时文件temp.txt中。在实际开发中不需要生成该文件，本段代码是为了方便读者查看通过文件表单提交的数据的格式 */
				try {
					java.io.File tempfile = new java.io.File(filedir,
							"temp.txt");
					FileOutputStream tempfile_stream = new FileOutputStream(
							tempfile);
					tempfile_stream.write(filecontent_b);
					tempfile_stream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else
			message = "● 上传的文件类型不是 multipart/form-data 格式！<br>";
		return mark;
	}

	@SuppressWarnings("unchecked")
	public List getFiles() {
		List files = new ArrayList();
		int index = 0;
		while (current_pos < filecontent_b.length) {
			File single = getFile(index);
			if (single != null)
				files.add(single);
			index++;
		}
		return files;
	}

	private File getFile(int index) {
		File file = null;

		// 获取分界字符串
		String bound = getBound();

		if (!bound.substring(bound.length() - 2).equals("--")) {
			// 获取内容描述段
			String dataHeader = getDateHeader();

			// 计算要获取内容的开始位置和结束位置:pos[0]为开始位置，pos[1]为结束位置
			int[] pos = getDataContentSegment(bound);

			// 获取字段名称
			String fieldName = getFieldNameValue(dataHeader, "name=");

			if (dataHeader.indexOf("filename=") > 0) {
				file = new File();
				// 获取上传的文件全名
				String filePath = getFieldNameValue(dataHeader, "filename=");

				// 获取上传的文件实际名称
				String fileName = getFileName(filePath);

				// 获取文件后缀名
				String fileExt = getFileExt(fileName);

				// 获取文件类型
				String fileType = getFileType(dataHeader);

				// 获取文件大小
				int fileSize = (pos[1] - pos[0]) + 1;
				if (fileSize <= 0) {
					file.setAvailable(false);
					file.setFilePath(filePath);
				} else {
					file.setAvailable(true);
					file.setParent(this);
					file.setFieldName(fieldName);
					file.setFilePath(filePath);
					file.setFileName(fileName);
					file.setFileExt(fileExt);
					file.setFileType(fileType);
					file.setFileSize(fileSize);
					file.setStart(pos[0]);
					file.setEnd(pos[1]);
				}
			} else { // 获取其他字段内容
				String fieldNameContent = new String(filecontent_b, pos[0],
						(pos[1] - pos[0]) + 1);
				params.setFields(fieldName, fieldNameContent);
			}
		} else
			current_pos = filecontent_b.length + 1;
		return file;
	}

	/** @功能：获取分界字符串 */
	private String getBound() {
		String bound = "";
		for (; filecontent_b[current_pos] != 13; current_pos++)
			bound += (char) filecontent_b[current_pos];
		current_pos += 2;
		return bound;
	}

	/** @功能：获取内容描述段 */
	private String getDateHeader() {
		String dateHeader = "";

		int start = current_pos;
		int end = 0;
		for (; !(filecontent_b[current_pos] == 13 && filecontent_b[current_pos + 2] == 13); current_pos++)
			;
		end = current_pos - 1;
		current_pos += 4;

		dateHeader = new String(filecontent_b, start, (end - start) + 1);
		return dateHeader;
	}

	/** @功能：获取字段名称 */
	private String getFieldNameValue(String dataHeader, String fieldName) {
		int start = dataHeader.indexOf(fieldName) + fieldName.length() + 1;
		int end = dataHeader.indexOf("\"", start);
		String value = dataHeader.substring(start, end);
		return value;
	}

	/** @功能：获取文件名 */
	private String getFileName(String filePath) {
		String filename = "";
		int start = filePath.lastIndexOf("\\");
		if (start == -1)
			start = filePath.lastIndexOf("/");
		if (start > 0)
			filename = filePath.substring(start + 1);
		else
			filename = filePath;
		return filename;
	}

	/** @功能：获取文件后缀名 */
	private String getFileExt(String fileName) {
		String fileext = "";
		if (fileName != null && !fileName.equals("")) {
			int start = fileName.lastIndexOf(".");
			if (start > 0)
				fileext = fileName.substring(start);
		}
		return fileext;
	}

	/** @功能：获取文件类型 */
	private String getFileType(String dataHeader) {
		int start = dataHeader.indexOf("Content-Type: ") + 14;
		String filetype = dataHeader.substring(start);
		return filetype;
	}

	/** @功能：计算要获取内容(字节数组形式)的起始位置和结束位置 */
	private int[] getDataContentSegment(String bound) {
		int[] pos = new int[2];
		pos[0] = current_pos; // 要获取内容的起始位置
		pos[1] = getDataContentEnd(bound); // 要获取内容的结束位置
		return pos;
	}

	/** @功能：获取文件内容的结束位置 */
	private int getDataContentEnd(String bound) {
		int end = -1;
		boolean found = false;
		int key = 0;
		byte[] bound_b = bound.getBytes();
		while (!found && current_pos < filecontent_b.length) {
			if (filecontent_b[current_pos] == bound_b[key]) {
				if (key == bound_b.length - 1)
					found = true;
				else {
					key++;
					current_pos++;
				}
			} else {
				key = 0;
				current_pos++;
			}
		}
		if (found) {
			current_pos = current_pos - (bound_b.length - 1);
			end = current_pos - 3;
		}
		return end;
	}

	public String getMessage() {
		return this.message;
	}

	public Parameters getParams() {
		return this.params;
	}

	/** @功能：下载文件 */
	public void download(String downfilename, String filename, String filetype)
			throws IOException {
		java.io.File file = new java.io.File(filedir, downfilename);
		long len = file.length();
		byte a[] = new byte[600];

		FileInputStream fin = new FileInputStream(file);
		OutputStream outs = response.getOutputStream();

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filename);
		response.setContentType(filetype);
		response.setHeader("Content_Lenght", String.valueOf(len));

		int read = 0;
		while ((read = fin.read(a)) != -1) {
			outs.write(a, 0, read);
		}
		outs.close();
		fin.close();
	}
}