package br.com.tracevia.webapp.filter;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.tracevia.webapp.controller.global.LoginAccountBean;

public class ControleDeAcesso implements Filter {
	
	LoginAccountBean loginAccount;	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		boolean master = false;
		int userRole = 0; 
		String restrictedPath = "/users/"; //Não Restrito a ROLE_SUPER_USER and ROLE_ADMIN
		String restrictedMap = "/map/map.xhtml"; //Não Restrito a ROLE_SUPER_USER and ROLE_ADMIN
		String restrictedRealtime = "/realtime/realtime.xhtml";  //Não Restrito a ROLE_SUPER_USER and ROLE_ADMIN
				
		loginAccount = new LoginAccountBean();
				
		String loginURI = request.getContextPath() + "/login.xhtml";
		String resetURI = request.getContextPath() + "/forget.xhtml";
		String resetConfirmationURI = request.getContextPath() + "/forget-confirmation.xhtml";
		
		String restrictedURI = request.getContextPath() + "/error/access-denied.xhtml";
		//String expiredURI = request.getContextPath() + "/error/session-expired.xhtml";
			
		boolean loggedIn = session != null && session.getAttribute("user") != null;	
		boolean role = session.getAttribute("nivel") != null;
	
		if(role) {			
		  userRole = Integer.parseInt(session.getAttribute("nivel").toString());	
		  master = loginAccount.permissionAdminOrSuper(userRole);		  
		}
			
		boolean loginRequest = request.getRequestURI().equals(loginURI);	
		boolean resetRequest = request.getRequestURI().equals(resetURI);	
		boolean resetConfirmationRequest = request.getRequestURI().equals(resetConfirmationURI);	
		
		boolean resourceRequest = request.getRequestURI()
				.startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
		boolean resourceRequestCSS = request.getRequestURI().contains("/resources/css");
		boolean resourceRequestIcons = request.getRequestURI().contains("/resources/icons");
		boolean resourceRequestImages = request.getRequestURI().contains("/resources/images");	
		boolean resourceRequestJs = request.getRequestURI().contains("/resources/js");	
					
		String path = request.getRequestURI().substring(request.getContextPath().length());
						
		//Acessar conteudo com login	
	  if(loggedIn || loginRequest || resourceRequest || resourceRequestCSS || resourceRequestIcons
				|| resourceRequestImages || resourceRequestJs) {	
			
				if((path.contains(restrictedPath) || path.contains(restrictedMap) || path.contains(restrictedRealtime)) && !master)
				response.sendRedirect(restrictedURI);
					
			else chain.doFilter(request, response);	
				
	   //Acesso a página de  redefinição de senha
	 } else if(resetRequest || resourceRequest || resourceRequestCSS || resourceRequestIcons
				|| resourceRequestImages || resourceRequestJs) 
		 
			chain.doFilter(request, response);	
	  
	  //Acesso a página de confirmação do envio para redefinição de senha
	 else if(resetConfirmationRequest || resourceRequest || resourceRequestCSS || resourceRequestIcons
				|| resourceRequestImages || resourceRequestJs) 
		 
			chain.doFilter(request, response);
		 	 
	else {
			response.sendRedirect(loginURI);
		 }
		
	  }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
