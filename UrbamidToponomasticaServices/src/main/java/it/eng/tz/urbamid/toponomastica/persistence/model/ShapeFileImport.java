package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "u_topo_import_shape")
public class ShapeFileImport implements Serializable {

	private static final long serialVersionUID = 2494497345156670754L;

	@Id
	@SequenceGenerator(name="u_topo_import_shape_id_seq", sequenceName="u_topo_import_shape_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_import_shape_id_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name_file", length = 255, nullable = false )
	private String nameFile;
	
	@Column(name = "size_file", nullable = false)
	private Long sizeFile;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "data_import", nullable = false)
	private Date dataImport;
	
	@Column(name = "processato", nullable = false)
	private Boolean processato;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	public Long getSizeFile() {
		return sizeFile;
	}
	public void setSizeFile(Long sizeFile) {
		this.sizeFile = sizeFile;
	}
	public Date getDataImport() {
		return dataImport;
	}
	public void setDataImport(Date dataImport) {
		this.dataImport = dataImport;
	}
	public Boolean getProcessato() {
		return processato;
	}
	public void setProcessato(Boolean processato) {
		this.processato = processato;
	}
	

}
