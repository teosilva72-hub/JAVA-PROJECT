package br.com.tracevia.webapp.model.global;

public class Modules {
	
	private int moduleID;
	private String module;	
	private boolean enabled;
		
	public Modules(int moduleID, String module, boolean enabled) {
		
		this.moduleID = moduleID;
		this.module = module;		
		this.enabled = enabled;
	}
	
	public Modules() {}
	
	public int getModuleID() {
		return moduleID;
	}
	
	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}
	
	public String getModule() {
		return module;
	}
	
	public void setModule(String module) {
		this.module = module;
	}
		
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
		

}
