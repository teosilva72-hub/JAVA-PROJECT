package br.com.tracevia.webapp.util;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static HttpSession getSession() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		
		return session;
	}
	
	public static void setParam(String key, Object value) {
		getSession().setAttribute(key, value);
	}
	
	public static Object getParam(String key) {
		return getSession().getAttribute(key);
	}
	
	public static void remove(String key) {
		getSession().removeAttribute(key);
	}
	
	public static void invalidate() {
		getSession().invalidate();
	}

}
