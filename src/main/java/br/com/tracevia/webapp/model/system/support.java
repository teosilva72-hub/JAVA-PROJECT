package br.com.tracevia.webapp.model.system;

public class Support {

	private String data_number;
	private String username;
	private String road_concessionaire;
	private String email;
	private String category;
	private String others;
	private String subject;
	private String message;

	

	public Support(String data_number, String username, String road_concessionaire, String email, String category, String others,
			String subject, String message) {
	
		this.data_number = data_number;
		this.username = username;
		this.road_concessionaire = road_concessionaire;
		this.email = email;
		this.category = category;
		this.others = others;
		this.subject = subject;
		this.message = message;
	}


	public Support() {}
	
	
	

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getRoad_concessionaire() {
		return road_concessionaire;
	}


	public void setRoad_concessionaire(String road_concessionaire) {
		this.road_concessionaire = road_concessionaire;
	}


			public String getEmail() {
				return email;
	}
			public void setEmail(String email) {
				this.email = email;
	}

			public String getCategory() {
				return category;
	}
			public void setCategory(String category) {
				this.category = category;
				
	}
			
			public String getOthers() {
				return others;
	}
			public void setOthers(String Others) {
				this.others = Others;
				
				
	}
			public String getSubject() {
				return subject;
	}

			public void setSubject(String subject) {
				this.subject = subject;
	}


			public String getMessage() {
				return message;
	}
			public void setMessage(String message) {
				this.message = message;
	}


			public String getData_number() {
				return data_number;
			}


			public void setData_number(String data_number) {
				this.data_number = data_number;
			}
	
	
	
	
	
}
