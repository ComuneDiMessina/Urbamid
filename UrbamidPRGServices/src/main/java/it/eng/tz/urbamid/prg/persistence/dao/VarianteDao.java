package it.eng.tz.urbamid.prg.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.AmbitoVarianteParticella;
import it.eng.tz.urbamid.prg.persistence.model.AmbitoVarianteParticellaZto;
import it.eng.tz.urbamid.prg.persistence.model.Variante;

@Repository
public class VarianteDao extends GenericDao<Variante> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_UPDATE_VARIANTE = "UPDATE public.u_prg_variante SET nome=?, descrizione=?, cod_comune=?, data_del_adoz=?, " + 
			"			num_del_adoz=?, org_del_adoz=?, note_del_adoz=?, data_del_appr=?, " + 
			"			num_del_appr=?, org_del_appr=?, note_del_appr=?," +  
			"			data_modifica=NOW(), modificato_da=?" + 
			"			WHERE id_variante = ?";
	private static final String SQL_INSERT_VARIANTE = "INSERT INTO public.u_prg_variante(" + 
			"            nome, descrizione, cod_comune, data_del_adoz, num_del_adoz, " + 
			"            org_del_adoz, note_del_adoz, data_del_appr, num_del_appr, org_del_appr, " + 
			"            note_del_appr, stato, data_creazione, inserito_da)" + 
			"    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
	
	private static final String SQL_SELECT_PARTICELLE_VARIANTE = 
			"SELECT "+
				"(st_area(st_intersection(ST_SetSRID(a.geom,4326) , ST_SetSRID(b.geom,4326)))/st_area(ST_SetSRID(b.geom,4326))) AS area_intersect "+
			"FROM "+ 
				"(SELECT * FROM public.u_prg_ambito_variante WHERE id_variante = :idVariante AND ST_IsValid(geom)) AS a, "+ 
				"(SELECT * FROM public.u_cat_particelle WHERE foglio= :foglio AND mappale = :mappale AND ST_IsValid(geom)) AS b "+
			"WHERE "+
				"(st_area(st_intersection(ST_SetSRID(a.geom,4326) , ST_SetSRID(b.geom,4326)))/st_area(ST_SetSRID(b.geom,4326)))>0 ";

	private static final String SQL_SELECT_PARTICELLE_VARIANTE_ZTO = 
			"SELECT a.zto_1, " + 
			"    (st_area(st_intersection(ST_SetSRID(a.geom,4326) , ST_SetSRID(b.geom,4326)))/st_area(ST_SetSRID(b.geom,4326))) AS area_intersect " + 
			"FROM " + 
			"    (SELECT * FROM public.u_geo_zto WHERE ST_IsValid(geom)) AS a, " + 
			"    (SELECT * FROM public.u_cat_particelle WHERE foglio= :foglio AND mappale = :mappale AND ST_IsValid(geom)) AS b " + 
			"WHERE " + 
			"    (st_area(st_intersection(ST_SetSRID(a.geom,4326) , ST_SetSRID(b.geom,4326)))/st_area(ST_SetSRID(b.geom,4326)))>0 ";

	public void updateVariante(Variante variante, String username) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();	
				getJdbcTemplate().update(SQL_UPDATE_VARIANTE,new Object[] { 
						variante.getNome() != null ? variante.getNome().toUpperCase() : null, 
						variante.getDescrizione() != null ? variante.getDescrizione().toUpperCase() : null,
						variante.getCodComune(),
						variante.getDataDelAdoz(),
						variante.getNumDelAdoz(),
						variante.getOrgDelAdoz(),
						variante.getNoteDelAdoz(),
						variante.getDataDelAppr(),
						variante.getNumDelAppr(),
						variante.getOrgDelAppr(),
						variante.getNoteDelAdoz(),
						username,
						variante.getIdVariante()
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_VARIANTE);
				}
		}catch (Exception e){
			String message = "Errore nella modifica della variante :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

	public void insertVariante(Variante variante, String username) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();	
				getJdbcTemplate().update(SQL_INSERT_VARIANTE,new Object[] { 
						variante.getNome() != null ? variante.getNome().toUpperCase() : null, 
						variante.getDescrizione() != null ? variante.getDescrizione().toUpperCase() : null,
						variante.getCodComune(),
						variante.getDataDelAdoz(),
						variante.getNumDelAdoz(),
						variante.getOrgDelAdoz(),
						variante.getNoteDelAdoz(),
						variante.getDataDelAppr(),
						variante.getNumDelAppr(),
						variante.getOrgDelAppr(),
						variante.getNoteDelAdoz(),
						variante.getStato(),
						username
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_VARIANTE);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio della variante :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public List<AmbitoVarianteParticella> getPercentIntersect(String foglio, String mappale, Long idVariante) throws Exception{ 
		NamedParameterJdbcTemplate template=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("foglio", foglio);
		parameters.addValue("mappale", mappale);
		parameters.addValue("idVariante", idVariante);

		List<AmbitoVarianteParticella> lista = template.query(SQL_SELECT_PARTICELLE_VARIANTE, parameters, new RowMapper<AmbitoVarianteParticella>() {
			@Override
			public AmbitoVarianteParticella mapRow(ResultSet rs, int rowNum) throws SQLException {
				AmbitoVarianteParticella bean= new AmbitoVarianteParticella();
				bean.setArea_intersect(rs.getDouble("area_intersect"));
				return bean;
			}
		});

		return lista;
	}

	public List<AmbitoVarianteParticellaZto> getPercentIntersectZto(String foglio, String mappale) {
		NamedParameterJdbcTemplate template=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("foglio", foglio);
		parameters.addValue("mappale", mappale);

		List<AmbitoVarianteParticellaZto> lista = template.query(SQL_SELECT_PARTICELLE_VARIANTE_ZTO, parameters, new RowMapper<AmbitoVarianteParticellaZto>() {
			@Override
			public AmbitoVarianteParticellaZto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AmbitoVarianteParticellaZto bean= new AmbitoVarianteParticellaZto();
				bean.setZto(rs.getString("zto_1"));
				bean.setArea_intersect(rs.getDouble("area_intersect"));
				return bean;
			}
		});

		return lista;
	}

	public List<AmbitoVarianteParticella> getPercentIntersectLayerDb(String nomeTabella, String foglio, String mappale) {
		NamedParameterJdbcTemplate template=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("foglio", foglio);
		parameters.addValue("mappale", mappale);
		
		String SQL_SELECT_PARTICELLE_VARIANTE_ZTO_DIFFERENTE = 
				"SELECT " + 
				"    (st_area(st_intersection(ST_SetSRID(a.geom,4326) , ST_SetSRID(b.geom,4326)))/st_area(ST_SetSRID(b.geom,4326))) AS area_intersect " + 
				"FROM " + 
				"    (SELECT * FROM public."+ nomeTabella +" WHERE ST_IsValid(geom)) AS a, " + 
				"    (SELECT * FROM public.u_cat_particelle WHERE foglio= :foglio AND mappale = :mappale AND ST_IsValid(geom)) AS b " + 
				"WHERE " + 
				"    (st_area(st_intersection(ST_SetSRID(a.geom,4326) , ST_SetSRID(b.geom,4326)))/st_area(ST_SetSRID(b.geom,4326)))>0 ";

		List<AmbitoVarianteParticella> lista = template.query(SQL_SELECT_PARTICELLE_VARIANTE_ZTO_DIFFERENTE, parameters, new RowMapper<AmbitoVarianteParticella>() {
			@Override
			public AmbitoVarianteParticella mapRow(ResultSet rs, int rowNum) throws SQLException {
				AmbitoVarianteParticella bean= new AmbitoVarianteParticella();
				bean.setArea_intersect(rs.getDouble("area_intersect"));
				return bean;
			}
		});

		return lista;
	}
	
}
