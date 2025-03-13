package it.eng.tz.urbamid.web.dto;

import java.util.List;

public class DettaglioPtMultiploDTO {

	private List<ParticellaPorzioneDTO> listPorzione;
	private List<ParticellaDeduzioneDTO> listDeduzione;
	private List<ParticellaRiservaDTO> listRiserva;

	public List<ParticellaPorzioneDTO> getListPorzione() {
		return listPorzione;
	}
	public void setListPorzione(List<ParticellaPorzioneDTO> listPorzione) {
		this.listPorzione = listPorzione;
	}
	public List<ParticellaDeduzioneDTO> getListDeduzione() {
		return listDeduzione;
	}
	public void setListDeduzione(List<ParticellaDeduzioneDTO> listDeduzione) {
		this.listDeduzione = listDeduzione;
	}
	public List<ParticellaRiservaDTO> getListRiserva() {
		return listRiserva;
	}
	public void setListRiserva(List<ParticellaRiservaDTO> listRiserva) {
		this.listRiserva = listRiserva;
	}

}
