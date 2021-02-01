package br.com.tracevia.webapp.model.global;

public class UserAccount {
	
	private int user_id;
	private int permission_id;
	private int permission_user_id;
	private String date_register;
	private String name;
	private String job_position;
	private String email;
	private String username; 
	private String password;
	private String permission_role;
	private String userID;
	private String confPassword;
	private String newPassword;	
	private String checkActive;
	private boolean activeStatus;
	
		
	public UserAccount(int user_id, int permission_id, int permission_user_id, String date_register, String name,
			String job_position, String email, String username, String password, String permission_role, String userID,
			String confPassword, String newPassword, String checkActive, boolean activeStatus) {
		
		this.user_id = user_id;
		this.permission_id = permission_id;
		this.permission_user_id = permission_user_id;
		this.date_register = date_register;
		this.name = name;
		this.job_position = job_position;
		this.email = email;
		this.username = username;
		this.password = password;
		this.permission_role = permission_role;
		this.userID = userID;
		this.confPassword = confPassword;
		this.newPassword = newPassword;
		this.checkActive = checkActive;
		this.activeStatus = activeStatus;
	}

	
	public UserAccount() {}
	

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getPermission_id() {
		return permission_id;
	}

	public void setPermission_id(int permission_id) {
		this.permission_id = permission_id;
	}

	public int getPermission_user_id() {
		return permission_user_id;
	}

	public void setPermission_user_id(int permission_user_id) {
		this.permission_user_id = permission_user_id;
	}

	public String getDate_register() {
		return date_register;
	}

	public void setDate_register(String date_register) {
		this.date_register = date_register;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob_position() {
		return job_position;
	}

	public void setJob_position(String job_position) {
		this.job_position = job_position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPermission_role() {
		return permission_role;
	}

	public void setPermission_role(String permission_role) {
		this.permission_role = permission_role;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getConfPassword() {
		return confPassword;
	}

	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getCheckActive() {
		return checkActive;
	}

	public void setCheckActive(String checkActive) {
		this.checkActive = checkActive;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	
}
