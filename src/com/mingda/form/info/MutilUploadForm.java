package com.mingda.form.info;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class MutilUploadForm extends ActionForm {
	private static final long serialVersionUID = 1L;

	private List<FormFile> myFiles = new ArrayList<FormFile>(); // ���ڱ��治��������FormFile����

	public FormFile getFile(int i) // ��������
	{
		return (FormFile) myFiles.get(i);
	}

	public void setFile(int i, FormFile myFile) // ��������
	{
		if (myFile.getFileSize() > 0) // ֻ���ϴ��ļ����ֽ�������0�����ϴ�����ļ�
		{
			myFiles.add(myFile);
		}
	}

	public int getFileCount() // ����ϴ��ļ��ĸ���
	{
		return myFiles.size();
	}
}
