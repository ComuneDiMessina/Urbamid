package it.eng.tz.urbamid.amministrazione.dao.model;

public class RuoliFunzionalita implements java.io.Serializable {

	private static final long serialVersionUID = 3467989111284292050L;

	private Long id;
	private String ruolo;
	private String funzionalita;

	public RuoliFunzionalita() {
	}

	public RuoliFunzionalita(Long id, String ruolo, String funzionalita) {
		super();
		this.id = id;
		this.ruolo = ruolo;
		this.funzionalita = funzionalita;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getFunzionalita() {
		return funzionalita;
	}

	public void setFunzionalita(String funzionalita) {
		this.funzionalita = funzionalita;
	}

}
