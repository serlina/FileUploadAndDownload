package com.duanchao.file.yxq.filexload;

import java.io.FileOutputStream;

public class File {
	private FileXLoad parent; // 设置该属性主要是为了在File类实例中访问FileXLoad实例中的filecontent_b属性值
	private String fieldName; // 文件字段名
	private String filePath; // 文件路径，包括文件名称
	private String fileName; // 文件名称
	private String fileExt; // 文件扩展名，包含“.”字符
	private String fileType; // 文件类型
	private int fileSize; // 文件大小
	private int start; // 文件内容的起始位置
	private int end; // 文件内容的结束位置
	private boolean available; // 是否为有效文件的标识

	public File() {
		parent = null;
		filePath = "";
		fileName = "";
		fileExt = "";
		fileType = "";
		fileSize = 0;
		start = 0;
		end = 0;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileExt() {
		return fileExt;
	}

	public String getFileType() {
		return fileType;
	}

	public int getFileSize() {
		return fileSize;
	}

	protected int getStart() {
		return start;
	}

	protected int getEnd() {
		return end;
	}

	public boolean isAvailable() {
		return available;
	}

	protected void setParent(FileXLoad parent) {
		this.parent = parent;
	}

	protected void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	protected void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	protected void setFileName(String fileName) {
		this.fileName = fileName;
	}

	protected void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	protected void setFileType(String fileType) {
		this.fileType = fileType;
	}

	protected void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	protected void setStart(int start) {
		this.start = start;
	}

	protected void setEnd(int end) {
		this.end = end;
	}

	protected void setAvailable(boolean available) {
		this.available = available;
	}

	/**
	 * 保存文件到磁盘中
	 * 
	 * @param filedir
	 *            :保存到磁盘上的路径
	 * @param savename：保存到磁盘上的文件名
	 * @return
	 */
	public boolean saveFileToDisk(String filedir, String savename) {
		boolean mark = false;
		FileOutputStream fos = null; // 文件输出流
		try {
			java.io.File upfile = new java.io.File(filedir, savename);
			fos = new FileOutputStream(upfile);
			fos.write(parent.filecontent_b, start, (end - start) + 1);
			fos.close();
			mark = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mark;
	}
}
