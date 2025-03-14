package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "u_topo_documenti")
public class Documento implements Serializable {

	private static final long serialVersionUID = 3189381381953123736L;

	@Id
	@SequenceGenerator(name="u_topo_documenti_id_seq", sequenceName="u_topo_documenti_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_documenti_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;
	
	@Column(name = "nome_parametro", length = 50, nullable = false)
	private String nomeParametro;
	
	@Column(name = "valore_parametro", nullable = false)
	private String valoreParametro;
	
	@Column(name = "descrizione", nullable = false)
	private String descrizione;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo", nullable = false)
	private TipoRisorsa tipo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeParametro() {
		return nomeParametro;
	}

	public void setNomeParametro(String nomeParametro) {
		this.nomeParametro = nomeParametro;
	}

	public String getValoreParametro() {
		return valoreParametro;
	}

	public void setValoreParametro(String valoreParametro) {
		this.valoreParametro = valoreParametro;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
