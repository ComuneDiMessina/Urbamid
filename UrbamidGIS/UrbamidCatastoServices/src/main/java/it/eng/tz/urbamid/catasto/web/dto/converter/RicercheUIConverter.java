package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareJoin;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioSoggettiUIDTO;
import it.eng.tz.urbamid.catasto.web.dto.UnitaImmobiliareDTO;

@Component
public class RicercheUIConverter implements IConverter<UnitaImmobiliareJoin, UnitaImmobiliareDTO>{

	private static final Logger logger = LoggerFactory.getLogger(RicercheUIConverter.class.getName());

	@Override
	public UnitaImmobiliareJoin toModel(UnitaImmobiliareDTO dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public UnitaImmobiliareDTO toDto(UnitaImmobiliare model) throws ConverterException {
//		UnitaImmobiliareDTO dto = new UnitaImmobiliareDTO();
//		dto.setId(model.getId());
//		dto.setComune("Messina");
//		dto.setSezione(model.getSezione());
//		dto.setIdImmobile(model.getIdImmobile());
//		dto.setTipoImmobile(model.getTipoImmobile());
//		dto.setProgressivo(model.getProgressivo());
//		dto.setZona(model.getZona());
//		dto.setCategoria(model.getCategoria().trim());
//		dto.setClasse(model.getClasse());
//		dto.setConsistenza(model.getConsistenza());
//		dto.setSuperficie(model.getSuperficie());
//		dto.setRenditaLire(model.getRenditaLire());
//		dto.setRenditaEuro(model.getRenditaEuro());
//		dto.setLotto(model.getLotto());
//		dto.setEdificio(model.getEdificio());
//		dto.setScala(model.getScala());
//		dto.setInterno1(model.getInterno1());
//		dto.setInterno2(model.getInterno2());
//		dto.setPiano1(model.getPiano1());
//		dto.setPiano2(model.getPiano2());
//		dto.setPiano3(model.getPiano3());
//		dto.setPiano4(model.getPiano4());
//		dto.setDataEfficaciaGen(model.getDataEfficaciaGen());
//		dto.setDataRegistrazioneGen(model.getDataRegistrazioneGen());
//		dto.setTipoNotaGen(model.getTipoNotaGen());
//		dto.setNumNotaGen(model.getNumNotaGen());
//		dto.setProgrNotaGen(model.getProgrNotaGen());
//		dto.setAnnoNotaGen(model.getAnnoNotaGen());
//		dto.setDataEfficaciaConcl(model.getDataEfficaciaConcl());
//		dto.setDataRegistrazioneConcl(model.getDataRegistrazioneConcl());
//		dto.setTipoNotaConcl(model.getTipoNotaConcl());
//		dto.setNumNotaConcl(model.getNumNotaConcl());
//		dto.setProgrNotaConcl(model.getProgrNotaConcl());
//		dto.setAnnoNotaConcl(model.getAnnoNotaConcl());
//		dto.setPartita(model.getPartita());
//		dto.setAnnotazione(model.getAnnotazione());
//		dto.setIdMutazIn(model.getIdMutazIn());
//		dto.setIdMutazFin(model.getIdMutazFin());
//		dto.setProtNotifica(model.getProtNotifica());
//		dto.setDataNotifica(model.getDataNotifica());
//		dto.setCodiceCausaleAttoGen(model.getCodiceCausaleAttoGen());
//		dto.setDescrizioneAttoGen(model.getDescrizioneAttoGen());
//		dto.setCodiceCausaleAttoConcl(model.getCodiceCausaleAttoConcl());
//		dto.setDescrizioneAttoConcl(model.getDescrizioneAttoConcl());
//		dto.setFlagClassamento(model.getFlagClassamento());
//		return dto;
//	}

	@Override
	public UnitaImmobiliareDTO toDto(UnitaImmobiliareJoin model) throws ConverterException {
		UnitaImmobiliareDTO dto = new UnitaImmobiliareDTO();
		dto.setId(model.getId());
		dto.setComune("Messina");
		dto.setIdImmobile(model.getIdImmobile());
		dto.setIndirizzo(model.getIndirizzo());
		dto.setCivico1(model.getCivico1());
		dto.setCivico2(model.getCivico2());
		dto.setNumero(model.getNumero());
		dto.setFoglio(model.getFoglio());
		dto.setSubalterno(model.getSubalterno());
		dto.setZona(model.getZona());
		dto.setCategoria(model.getCategoria().trim());
		dto.setClasse(model.getClasse());
		dto.setConsistenza(model.getConsistenza());
		dto.setSuperficie(model.getSuperficie());
		dto.setRenditaLire(model.getRenditaLire());
		dto.setRenditaEuro(model.getRenditaEuro());
		dto.setPartita(model.getPartita());
		return dto;
	}

	@Override
	public List<UnitaImmobiliareDTO> toDto(List<UnitaImmobiliareJoin> models) throws ConverterException {
		List<UnitaImmobiliareDTO> result = new ArrayList<>();
		try 
		{
			for (UnitaImmobiliareJoin model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	public DettaglioSoggettiUIDTO toDtoDettaglioSoggetti(UnitaImmobiliareJoin model/*, List<FabbricatiGeom> list*/) throws ConverterException {

		List<String> diritti = new ArrayList<>();
		diritti.add("Proprieta 1/1");
		diritti.add("Proprieta 10/10");
		diritti.add("Proprieta 100/100");
		diritti.add("Proprieta 1000/1000");

		DettaglioSoggettiUIDTO dto = new DettaglioSoggettiUIDTO();
		dto.setUiu(model.getId().intValue());
		dto.setIdImmobile(model.getIdImmobile());
		dto.setCategoria(model.getCategoria().trim());
		dto.setSezione(model.getSezione());
		dto.setNumero(model.getNumero());
		dto.setFoglio(model.getFoglio());
		dto.setSubalterno(model.getSubalterno());
		dto.setIndirizzo(model.getIndirizzo());
		dto.setComune(model.getCodComune());
		dto.setPartita(model.getPartita());
		dto.setRenditaLire(model.getRenditaLire());
		dto.setRenditaEuro(model.getRenditaEuro());
		dto.setDiritto(model.getDiritto());
		if (diritti.contains(dto.getDiritto())) {
			dto.setSingoloProprietario(true);
		} else {
			dto.setSingoloProprietario(false);
		}
		/*if (list != null && !list.isEmpty()) {
			dto.setGeometry(list.get(0).getGeometry());
		}*/
		return dto;
	}

	public List<DettaglioSoggettiUIDTO> toDtoDettaglioSoggetti(List<UnitaImmobiliareJoin> models) {
		List<DettaglioSoggettiUIDTO> result = new ArrayList<>();
		try 
		{
			for (UnitaImmobiliareJoin model : models) {
				result.add(this.toDtoDettaglioSoggetti(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

}
