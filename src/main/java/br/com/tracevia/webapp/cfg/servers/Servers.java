package br.com.tracevia.webapp.cfg.servers;

public enum Servers {
		
	ServerViaPaulista("10.12.32.227"),
	ServerViaSul("10.161.0.17");
	
	private String server;
	
	Servers(String server) {
		this.server = server;
	}
	
	public String getServer() {
		return server;
	}

}
