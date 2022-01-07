package br.com.tracevia.webapp.model.system;

public class support {

	private String name;
	private String email;
	private String road_concessionaire;
	private String category;
	private String theme;
	private String message;

	
	public support(String name, String email, String road_concessionaire, String category, String theme,
			String message) {
		
		this.name = name;
		this.email = email;
		this.road_concessionaire = road_concessionaire;
		this.category = category;
		this.theme = theme;
		this.message = message;
	}
	

	public support() {}
	
			public String getName() {
				return name;
	}
			public void setName(String name) {
				this.name = name;
	}
			public String getEmail() {
				return email;
	}
			public void setEmail(String email) {
				this.email = email;
	}
			public String getRoad_concessionaire() {
				return road_concessionaire;
	}
			public void setRoad_concessionaire(String road_concessionaire) {
				this.road_concessionaire = road_concessionaire;
	}
			public String getCategory() {
				return category;
	}
			public void setCategory(String category) {
				this.category = category;
	}
			public String getTheme() {
				return theme;
	}
			public void setTheme(String theme) {
				this.theme = theme;
	}		
			public String getMessage() {
				return message;
	}
			public void setMessage(String message) {
				this.message = message;
	}
	
	
	
	
	
}
