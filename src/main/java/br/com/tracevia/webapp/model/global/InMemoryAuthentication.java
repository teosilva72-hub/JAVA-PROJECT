package br.com.tracevia.webapp.model.global;

public class InMemoryAuthentication {
	
	private int permission_id;
	private String username;
	private String password;
	
	public InMemoryAuthentication(int permission_id, String username, String password) {
		
		this.permission_id = permission_id;
		this.username = username;
		this.password = password;
	}
	
	public InMemoryAuthentication() {}

	public int getPermission_id() {
		return permission_id;
	}

	public void setPermission_id(int permission_id) {
		this.permission_id = permission_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
