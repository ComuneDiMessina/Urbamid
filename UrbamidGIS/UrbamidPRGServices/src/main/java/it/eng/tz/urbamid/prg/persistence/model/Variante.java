package it.eng.tz.urbamid.prg.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_prg_variante")
public class Variante implements Serializable {

	private static final long serialVersionUID = -8187272853587764056L;

	@Id 
	@SequenceGenerator(name="u_prg_variante_id_seq", sequenceName="u_prg_variante_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_variante_id_seq")
	@Column(name = "id_variante", unique = true, nullable = false, updatable=false)
	private Long idVariante;

	@Column(name = "nome", length=100, unique = true, nullable = false)
	private String nome;

	@Column(name = "descrizione", length=2000, nullable = false)
	private String descrizione;

	@Column(name = "cod_comune", length=5, nullable = false)
	private String codComune;

	@Column(name = "data_del_adoz", nullable = false)
	@Temporal(value = TemporalType.DATE)
	private Date dataDelAdoz;

	@Column(name = "num_del_adoz", nullable = false, length=20)
	private String numDelAdoz;

	@Column(name = "org_del_adoz", length=500)
	private String orgDelAdoz;

	@Column(name = "note_del_adoz", length=2000)
	private String noteDelAdoz;

	@Column(name = "data_del_appr")
	@Temporal(value = TemporalType.DATE)
	private Date dataDelAppr;

	@Column(name = "num_del_appr", length=20)
	private String numDelAppr;

	@Column(name = "org_del_appr", length=500)
	private String orgDelAppr;

	@Column(name = "note_del_appr", length=2000)
	private String noteDelAppr;

	@Column(name = "stato", length=1)
	private String stato;

	@Column(name = "data_creazione")
	private Timestamp dataCreazione;

	@Column(name = "inserito_da", length=100)
	private String inseritoDa;

	@Column(name = "data_modifica")
	private Timestamp dataModifica;

	@Column(name = "modificato_da", length=100)
	private String modificatoDa;

	@OneToMany(
	        mappedBy = "variante", 
	        fetch=FetchType.LAZY
	)
	private List<StoricoVariante> storicoVariante;

	@OneToMany(
	        mappedBy = "variante", 
	        fetch=FetchType.LAZY
	)
	private List<DocumentoVariante> documentoVariante;

	@OneToMany(
	        mappedBy = "variante", 
	        fetch=FetchType.LAZY
	)
	private List<AmbitoVariante> ambitoVariante;

	@OneToMany(
	        mappedBy = "variante", 
	        fetch=FetchType.LAZY
	)
	private List<CartografiaVariante> cartografiaVariante;

	public Long getIdVariante() {
		return idVariante;
	}

	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

	public Date getDataDelAdoz() {
		return dataDelAdoz;
	}

	public void setDataDelAdoz(Date dataDelAdoz) {
		this.dataDelAdoz = dataDelAdoz;
	}

	public String getNumDelAdoz() {
		return numDelAdoz;
	}

	public void setNumDelAdoz(String numDelAdoz) {
		this.numDelAdoz = numDelAdoz;
	}

	public String getOrgDelAdoz() {
		return orgDelAdoz;
	}

	public void setOrgDelAdoz(String orgDelAdoz) {
		this.orgDelAdoz = orgDelAdoz;
	}

	public String getNoteDelAdoz() {
		return noteDelAdoz;
	}

	public void setNoteDelAdoz(String noteDelAdoz) {
		this.noteDelAdoz = noteDelAdoz;
	}

	public Date getDataDelAppr() {
		return dataDelAppr;
	}

	public void setDataDelAppr(Date dataDelAppr) {
		this.dataDelAppr = dataDelAppr;
	}

	public String getNumDelAppr() {
		return numDelAppr;
	}

	public void setNumDelAppr(String numDelAppr) {
		this.numDelAppr = numDelAppr;
	}

	public String getOrgDelAppr() {
		return orgDelAppr;
	}

	public void setOrgDelAppr(String orgDelAppr) {
		this.orgDelAppr = orgDelAppr;
	}

	public String getNoteDelAppr() {
		return noteDelAppr;
	}

	public void setNoteDelAppr(String noteDelAppr) {
		this.noteDelAppr = noteDelAppr;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getInseritoDa() {
		return inseritoDa;
	}

	public void setInseritoDa(String inseritoDa) {
		this.inseritoDa = inseritoDa;
	}

	public Timestamp getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getModificatoDa() {
		return modificatoDa;
	}

	public void setModificatoDa(String modificatoDa) {
		this.modificatoDa = modificatoDa;
	}

	public List<StoricoVariante> getStoricoVariante() {
		return storicoVariante;
	}

	public void setStoricoVariante(List<StoricoVariante> storicoVariante) {
		this.storicoVariante = storicoVariante;
	}

	public List<DocumentoVariante> getDocumentoVariante() {
		return documentoVariante;
	}

	public void setDocumentoVariante(List<DocumentoVariante> documentoVariante) {
		this.documentoVariante = documentoVariante;
	}

	public List<AmbitoVariante> getAmbitoVariante() {
		return ambitoVariante;
	}

	public void setAmbitoVariante(List<AmbitoVariante> ambitoVariante) {
		this.ambitoVariante = ambitoVariante;
	}

	public List<CartografiaVariante> getCartografiaVariante() {
		return cartografiaVariante;
	}

	public void setCartografiaVariante(List<CartografiaVariante> cartografiaVariante) {
		this.cartografiaVariante = cartografiaVariante;
	}

}
