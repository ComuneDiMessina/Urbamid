package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Modello per i parametri di connessione al database per un job di import", value="ConnessioneDatabase")
public class ConnessioneDatabaseDTO implements Serializable {

	private static final long serialVersionUID = 8617976402487815072L;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String host;
	
	@NotBlank
	private String port;
	
	@NotBlank
	private String user;
	
	@NotBlank
	private String password;
	
	public ConnessioneDatabaseDTO() {
		super();
	}

	@ApiModelProperty(name="nome", position=1, required=true, value="Nome del database", example = "dbUrbamid")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@ApiModelProperty(name="host", position=2, required=true, value="Hostname del database", example = "localhost")
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@ApiModelProperty(name="port", position=3, required=true, value="Numero di porto", example = "5432")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@ApiModelProperty(name="user", position=4, required=true, value="Username di connessione al database", example = "postgres")
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@ApiModelProperty(name="password", position=5, required=true, value="Password di connessione al database", example = "postgres")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		//SE MODIFICHI IL TO-STRING ATTENTO A NON STAMPARE LA PASSWORD!
		return String.format("ConnessioneDatabaseDTO [nome=%s, host=%s, port=%s, user=%s]", nome, host,
				port, user);
	}
	
}
