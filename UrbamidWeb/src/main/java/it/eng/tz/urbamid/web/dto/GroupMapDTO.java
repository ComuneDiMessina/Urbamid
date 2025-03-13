package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;
import java.util.List;

public class GroupMapDTO implements Serializable {

	private static final long serialVersionUID = 3362765584464248806L;

	private Long idMappa;
	private String tavoleGroup;
	private List<String> tavole;

	public Long getIdMappa() {
		return idMappa;
	}

	public void setIdMappa(Long idMappa) {
		this.idMappa = idMappa;
	}

	public String getTavoleGroup() {
		return tavoleGroup;
	}

	public void setTavoleGroup(String tavoleGroup) {
		this.tavoleGroup = tavoleGroup;
	}

	public List<String> getTavole() {
		return tavole;
	}

	public void setTavole(List<String> tavole) {
		this.tavole = tavole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMappa == null) ? 0 : idMappa.hashCode());
		result = prime * result + ((tavole == null) ? 0 : tavole.hashCode());
		result = prime * result + ((tavoleGroup == null) ? 0 : tavoleGroup.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupMapDTO other = (GroupMapDTO) obj;
		if (idMappa == null) {
			if (other.idMappa != null)
				return false;
		} else if (!idMappa.equals(other.idMappa))
			return false;
		if (tavole == null) {
			if (other.tavole != null)
				return false;
		} else if (!tavole.equals(other.tavole))
			return false;
		if (tavoleGroup == null) {
			if (other.tavoleGroup != null)
				return false;
		} else if (!tavoleGroup.equals(other.tavoleGroup))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "GroupMapDTO [idMappa=" + idMappa + ", tavoleGroup=" + tavoleGroup + ", tavole=" + tavole + "]";
	}
	
}
