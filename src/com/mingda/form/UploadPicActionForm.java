package com.mingda.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class UploadPicActionForm extends ActionForm {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormFile pic;
	private String x1;

	private String y1;

	private String x2;

	private String y2;
	
    public String getX1() {
		return x1;
	}

	public void setX1(String x1) {
		this.x1 = x1;
	}

	public String getX2() {
		return x2;
	}

	public void setX2(String x2) {
		this.x2 = x2;
	}

	public String getY1() {
		return y1;
	}

	public void setY1(String y1) {
		this.y1 = y1;
	}

	public String getY2() {
		return y2;
	}

	public void setY2(String y2) {
		this.y2 = y2;
	}

	public FormFile getPic() {
        return pic;
    }

    public void setPic(FormFile pic) {
        this.pic = pic;
    }

    public ActionErrors validate(ActionMapping actionMapping,
                                 HttpServletRequest httpServletRequest) {
            /** @todo: finish this method, this is just the skeleton.*/
        return null;
    }

    public void reset(ActionMapping actionMapping,
                      HttpServletRequest servletRequest) {
    }
}
