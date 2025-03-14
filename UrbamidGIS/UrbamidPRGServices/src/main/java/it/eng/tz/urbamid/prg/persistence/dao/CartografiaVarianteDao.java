package it.eng.tz.urbamid.prg.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.CartografiaVariante;
import it.eng.tz.urbamid.prg.web.dto.CartografiaVarianteLayerDTO;

@Repository
public class CartografiaVarianteDao extends GenericDao<CartografiaVariante> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_INSERT_CARTOGRAFIA = "INSERT INTO public.u_prg_cartografia_variante" + 
			"            (id_variante, nome_layer, descrizione_layer, gruppo_layer, sorgente, nome_colonna)" + 
			"    VALUES (?, ?, ?, ?, ?, ?);";
	
	private static final String SQL_SELECT_VARIANTE = "SELECT v.id_variante, v.nome, c.nome_layer\r\n" + 
			"FROM public.u_prg_cartografia_variante c JOIN public.u_prg_variante v ON  c.id_variante = v.id_variante\r\n" + 
			"WHERE c.nome_layer = ?"; 

	public void insertCartografia(Long idVariante, String nomeLayer, String descrizioneLayer, String nomeGruppo, String sorgente, String nomeColonna) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();	
				getJdbcTemplate().update(SQL_INSERT_CARTOGRAFIA,new Object[] { 
						idVariante,
						nomeLayer,
						descrizioneLayer,
						nomeGruppo,
						sorgente,
						nomeColonna
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_CARTOGRAFIA);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio della cartografia della variante :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public List<CartografiaVarianteLayerDTO> varianteByNomeLayer(String nomeLayer) {
		
		return getJdbcTemplate().query(SQL_SELECT_VARIANTE, new RowMapper<CartografiaVarianteLayerDTO>() {

			@Override
			public CartografiaVarianteLayerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				CartografiaVarianteLayerDTO cartografiaVariante = new CartografiaVarianteLayerDTO();
				
				cartografiaVariante.setIdVariante(rs.getLong("id_variante"));
				cartografiaVariante.setNomeVariante(rs.getString("nome"));
				cartografiaVariante.setNomeLayer(rs.getString("nome_layer"));
				
				return cartografiaVariante;
			}
			
		}, nomeLayer);
		
	}

}
