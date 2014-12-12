package com.mingda.common;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class OperateImage {

	// ===ԴͼƬ·��������:c:\1.jpg
	private String srcpath;

	// ===����ͼƬ���·������.��:c:\2.jpg
	private String subpath;

	// ===���е�x����
	private int x;

	private int y;

	// ===���е����
	private int width;

	private int height;

	public OperateImage() {

	}

	public OperateImage(int x, int y, int x1, int y1) {
		this.x = x;
		this.y = y;
		this.width = x1-x;
		this.height =y1-y;
	}

	/** */
	/** */
	/** */
	/**
	 * ��ͼƬ�ü������Ѳü��군��ͼƬ���� ��
	 */
	public void cut() throws IOException{ 
	           
	          FileInputStream is = null ;
	           ImageInputStream iis =null ;
	       
	           try{   
	               // ��ȡͼƬ�ļ�
	              is = new FileInputStream(srcpath); 
	              
	              /** *//**//*
							 * ���ذ������е�ǰ��ע�� ImageReader �� Iterator����Щ ImageReader
							 * �����ܹ�����ָ����ʽ�� ������formatName - ��������ʽ��ʽ���� . ������
							 * "jpeg" �� "tiff"���� ��
							 */
	              Iterator it = ImageIO.getImageReadersByFormatName("jpg"); 
	              ImageReader reader = (ImageReader) it.next(); 
	              // ��ȡͼƬ��
	              iis = ImageIO.createImageInputStream(is);
	                 
	              /** *//**//*
							 * <p>iis:��ȡԴ.true:ֻ��ǰ���� </p>.�������Ϊ ��ֻ��ǰ��������
							 * ��������ζ�Ű���������Դ�е�ͼ��ֻ��˳���ȡ���������� reader
							 * ���⻺���������ǰ�Ѿ���ȡ��ͼ����������ݵ���Щ���벿�֡�
							 */
	              reader.setInput(iis,true) ;
	              
	              /** *//**//*
							 * <p>������ζ������н������<p>.����ָ�����������ʱ�� Java Image I/O
							 * ��ܵ��������е���ת��һ��ͼ���һ��ͼ�������ض�ͼ���ʽ�Ĳ�� ������ ImageReader
							 * ʵ�ֵ� getDefaultReadParam �����з��� ImageReadParam ��ʵ����
							 */
	           ImageReadParam param = reader.getDefaultReadParam(); 
	             
	           /** *//**//*
							 * ͼƬ�ü�����Rectangle ָ��������ռ��е�һ������ͨ�� Rectangle ����
							 * �����϶�������꣨x��y�������Ⱥ͸߶ȿ��Զ����������
							 */ 
	            Rectangle rect = new Rectangle(x, y, width, height); 
	           
	            
	          // �ṩһ�� BufferedImage���������������������ݵ�Ŀ�ꡣ
	          param.setSourceRegion(rect);
	 
	          /** *//**//*
						 * ʹ�����ṩ�� ImageReadParam ��ȡͨ������ imageIndex ָ���Ķ��󣬲���
						 * ����Ϊһ�������� BufferedImage ���ء�
						 */
	         BufferedImage bi = reader.read(0,param);                
	   
           // ������ͼƬ
	           ImageIO.write(bi, "jpg", new File(subpath));     
	      }
	      
       finally{
	           if(is!=null)
	              is.close() ;       
	           iis.close(); 
	      } 
	       
	        
	    
  }

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSrcpath() {
		return srcpath;
	}

	public void setSrcpath(String srcpath) {
		this.srcpath = srcpath;
	}

	public String getSubpath() {
		return subpath;
	}

	public void setSubpath(String subpath) {
		this.subpath = subpath;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static void main(String[] args) throws Exception {
		String name = "d:\2005121210161588950.jpg";

		OperateImage o = new OperateImage(100, 100, 100, 100);
		o.setSrcpath(name);
		o.setSubpath("D:\2.jpg");
		o.cut();

	}

}