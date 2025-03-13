package it.eng.tz.urbamid.profilemanager.profile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.profilemanager.dao.GenericDao;
import it.eng.tz.urbamid.profilemanager.persistence.model.Utente;
import it.eng.tz.urbamid.profilemanager.web.dto.UtenteDto;

/**
 * @author Alessandro Paolillo
 */
@Repository
public class UtenteDao extends GenericDao<Utente> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_FIND_UTENTE = "select u.* from public.u_pm_utente u where u.id = ?";
	private static final String SQL_UPDATE_UTENTE = "UPDATE public.u_pm_utente SET nome=?, cognome=?, codfiscale=?, email=?, note=?, abilitato=? WHERE username=?";
	private static final String SQL_INSERT_UTENTE = "INSERT INTO public.u_pm_utente ( username, nome, cognome, codfiscale, email, data_creazione, data_conferma_reg, note, abilitato) "
														+ "VALUES ( ?, ?, ?, ?, ?, NOW(), NOW(), ?, ?)";
	
	public Utente findById(Long id) throws Exception {
		StopWatch sw = null;
		Utente model = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();
			List<Utente> listaUtente = getJdbcTemplate().query(
					SQL_FIND_UTENTE, 
					new Object[]{id }, 
					new RowMapper<Utente>() {
						@Override
						public Utente mapRow(ResultSet rs, int rowNum) throws SQLException {
							Utente utente = new Utente();
							utente.setId(rs.getLong("id"));
							utente.setNome(rs.getString("nome"));
							utente.setCognome(rs.getString("cognome"));
							utente.setAbilitato(rs.getBoolean("abilitato"));
							utente.setCodiceFiscale(rs.getString("codfiscale"));
							utente.setEmail(rs.getString("email"));
							utente.setNote(rs.getString("note"));
							return utente;
						}
					}
			);
			model = (listaUtente!=null && listaUtente.size()>0) ? listaUtente.get(0):null;

			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_UPDATE_UTENTE);
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio dell' utente :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
		return model;
	}
	
	public void insert(UtenteDto utenteDto) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();
			getJdbcTemplate().update(SQL_INSERT_UTENTE, new Object[] {
					utenteDto.getUsername()!=null?utenteDto.getUsername():"",
					utenteDto.getNome()!=null?utenteDto.getNome():"",
					utenteDto.getCognome()!=null?utenteDto.getCognome():"",
					utenteDto.getCodiceFiscale()!=null?utenteDto.getCodiceFiscale():"",
					utenteDto.getEmail()!=null?utenteDto.getEmail():"",
					utenteDto.getNote()!=null?utenteDto.getNote():"",
					utenteDto.getAbilitato()
			});
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_UPDATE_UTENTE);
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio dell' utente :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public void updateUtente(UtenteDto utenteDto) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();	
				getJdbcTemplate().update(SQL_UPDATE_UTENTE,new Object[] { 
						utenteDto.getNome(), 
						utenteDto.getCognome(), 
						utenteDto.getCodiceFiscale(),
						utenteDto.getEmail(), 
						utenteDto.getNote(),
						utenteDto.getAbilitato(),
						utenteDto.getUsername()
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_UTENTE);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio dell' utente :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
}
