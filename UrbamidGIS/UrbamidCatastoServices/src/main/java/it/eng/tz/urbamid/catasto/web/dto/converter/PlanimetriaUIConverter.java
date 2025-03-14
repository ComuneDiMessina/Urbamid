package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaPlanimetrieJoinDettaglio;
import it.eng.tz.urbamid.catasto.web.dto.PlanimetriaUiuDTO;

@Component
public class PlanimetriaUIConverter implements IConverter<UnitaPlanimetrieJoinDettaglio, PlanimetriaUiuDTO>{

	private static final Logger logger = LoggerFactory.getLogger(PlanimetriaUIConverter.class.getName());

	@Override
	public UnitaPlanimetrieJoinDettaglio toModel(PlanimetriaUiuDTO dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public PlanimetriaUiuDTO toDto(UnitaImmobiliare model) throws ConverterException {
//		PlanimetriaUiuDTO dto = new PlanimetriaUiuDTO();
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
	public PlanimetriaUiuDTO toDto(UnitaPlanimetrieJoinDettaglio model) throws ConverterException {
		PlanimetriaUiuDTO dto = new PlanimetriaUiuDTO();
		dto.setCatCatastale(model.getCat_Catastale());
		dto.setDug(model.getDug());
		dto.setFoglio(model.getFoglio());
		dto.setIndirizzo(model.getIndirizzo());
		dto.setNomeImage(model.getNome_Image());
		dto.setNumCivico(model.getNum_Civico());
		dto.setMappale(model.getMappale());
		dto.setScaleFrom(model.getScale_From());
		dto.setScaleTo(model.getScale_To());
		dto.setSubalterno(model.getSubalterno());
		dto.setSuperficie(model.getSuperficie());
		return dto;
	}

	@Override
	public List<PlanimetriaUiuDTO> toDto(List<UnitaPlanimetrieJoinDettaglio> models) throws ConverterException {
		List<PlanimetriaUiuDTO> result = new ArrayList<>();
		try 
		{
			for (UnitaPlanimetrieJoinDettaglio model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

//	public DettaglioSoggettiUIDTO toDtoDettaglioSoggetti(UnitaPlanimetrieJoinDettaglio model, List<Planimetria> list) throws ConverterException {
//		DettaglioSoggettiUIDTO dto = new DettaglioSoggettiUIDTO();
//		dto.setUiu(model.getId().intValue());
//		dto.setCategoria(model.getCategoria().trim());
//		dto.setSezione(model.getSezione());
//		dto.setNumero(model.getNumero());
//		dto.setFoglio(model.getFoglio());
//		dto.setSubalterno(model.getSubalterno());
//		dto.setIndirizzo(model.getIndirizzo());
//		dto.setProvincia("Messina");
//		//dto.setDiritto(model.getCategoria());
//		dto.setPartita(model.getPartita());
//		dto.setRenditaLire(model.getRenditaLire());
//		dto.setRenditaEuro(model.getRenditaEuro());
//		if (list != null && !list.isEmpty()) {
//			dto.setGeometry(list.get(0).getGeometry());
//		}
//		return dto;
//	}

	/*public List<DettaglioSoggettiUIDTO> toDtoDettaglioSoggetti(List<UnitaPlanimetrieJoinDettaglio> models) {
		List<DettaglioSoggettiUIDTO> result = new ArrayList<>();
		try 
		{
			for (UnitaPlanimetrieJoinDettaglio model : models) {
				result.add(this.toDtoDettaglioSoggetti(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}*/

}
