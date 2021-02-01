package br.com.tracevia.webapp.model.global;

public class Usuario {

	private int id;
	private String data_cadastro;
	private String nome;
	private String cargo;
	private String email;
	private String usuario;
	private String password;
	private String confirmPassword;
	private String status;
	private String nivel;
	
	public Usuario(int id, String data_cadastro, String nome, String cargo, String email, String usuario,
			String password, String confirmPassword, String status, String nivel) {
		this.id = id;
		this.data_cadastro = data_cadastro;
		this.nome = nome;
		this.cargo = cargo;
		this.email = email;
		this.usuario = usuario;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.status = status;
		this.nivel = nivel;
	}
	
	public Usuario(int id, String nome, String email, String usuario, String nivel, String status) {
		
		this.id = id;		
		this.nome = nome;	
		this.email = email;
		this.usuario = usuario;	
		this.nivel = nivel;
		this.status = status;
	}	
	
	public Usuario() {}	
	
	public int getId() {
	return id;
	}
	public void setId(int id) {
		this.id = id;
	}		
	public String getData_cadastro() {
		return data_cadastro;
	}
	public void setData_cadastro(String data_cadastro) {
		this.data_cadastro = data_cadastro;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
}
