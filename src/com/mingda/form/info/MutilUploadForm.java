package com.mingda.form.info;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class MutilUploadForm extends ActionForm {
	private static final long serialVersionUID = 1L;

	private List<FormFile> myFiles = new ArrayList<FormFile>(); // 用于保存不定数量的FormFile对象

	public FormFile getFile(int i) // 索引属性
	{
		return (FormFile) myFiles.get(i);
	}

	public void setFile(int i, FormFile myFile) // 索引属性
	{
		if (myFile.getFileSize() > 0) // 只有上传文件的字节数大于0，才上传这个文件
		{
			myFiles.add(myFile);
		}
	}

	public int getFileCount() // 获得上传文件的个数
	{
		return myFiles.size();
	}
}
