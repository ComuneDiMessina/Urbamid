package it.eng.tz.urbamid.catasto.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.catasto.filter.ImmobileFilter;
import it.eng.tz.urbamid.catasto.filter.IntestatariFilter;
import it.eng.tz.urbamid.catasto.filter.ParticellaFilter;
import it.eng.tz.urbamid.catasto.filter.SoggettoFilter;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaCustomDTO;

@Service
public interface ExportService {

	File exportXls(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	
	File exportXlsSoggetti(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	
	File exportXlsIntestazioni(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	
	File exportXlsUiu(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	
	File exportXlsTerreni(List<ParticellaCustomDTO> lsParticelle) throws Exception;

	File exportPersoneFisiche(SoggettoFilter filter) throws Exception;

	File exportSoggettiGiuridici(SoggettoFilter filter) throws Exception;

	File exportImmobiliUIU(ImmobileFilter filter) throws Exception;

	File exportParticellePT(ParticellaFilter filter) throws Exception;

	File exportIntestazioniPF(IntestatariFilter filter) throws Exception;

	File exportIntestazioniSG(IntestatariFilter filter) throws Exception;

	File exportFabbricatiPerNominativo(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception;

	File exportTerreniPerNominativo(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception;

	File exportFabbricatiPerParticella(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception;

	File exportTerreniPerParticella(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception;

	File exportPdf(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception;

	File exportVisuraCatastaleFabbricati(Long idImmobile) throws Exception;

	File exportVisuraCatastaleTerreni(Long idImmobile) throws Exception;

	File exportVisuraCatastaleStoricaTerreni(Long idImmobile) throws Exception;

	File exportVisuraCatastaleStoricaFabbricati(Long idImmobile) throws Exception;

}
