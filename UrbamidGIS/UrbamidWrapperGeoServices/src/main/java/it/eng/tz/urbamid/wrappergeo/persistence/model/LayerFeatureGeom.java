package it.eng.tz.urbamid.wrappergeo.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_cat_particelle")
//@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "gid")) })
public class LayerFeatureGeom implements Serializable {

	private static final long serialVersionUID = 3613532055234295657L;
	
	@Id 
	@SequenceGenerator(name="u_cat_particelle_gid_seq", sequenceName="u_cat_particelle_gid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_cat_particelle_gid_seq")
	@Column(name = "gid", unique = true, nullable = false, updatable=false)
	private Long gid;

	@Column(name = "geometry")
	private String geometry;
	
	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
	
	
	
}
