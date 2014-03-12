package com.duanchao.file.valuebean;

import com.duanchao.file.toolsbean.StringHandler;

public class FileBean {
	private int 	id;
	private String 	filePath;				//文件路径
	private String 	saveName;				//文件保存到磁盘中用到的名称
	private String 	fileName;				//文件的真实名称
	private String 	fileType;				//文件类型
	private int 	fileSize;				//文件大小
	private String 	fileInfo;				//文件描述
	private String 	uptime;					//上传时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileInfo() {
		return fileInfo;
	}
	public String getFileInfoForShow() {
		return StringHandler.changehtml(fileInfo);
	}
	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
}
