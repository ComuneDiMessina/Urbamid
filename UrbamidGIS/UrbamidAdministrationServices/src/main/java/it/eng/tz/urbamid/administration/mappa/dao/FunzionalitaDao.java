package it.eng.tz.urbamid.administration.mappa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.persistence.model.Funzionalita;

/**
 * @author Luca Tricarico
 */
@Component
public class FunzionalitaDao extends GenericDao<Funzionalita> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Funzionalita> getFunzionalita(List<String> authorities) {
		
		String SQL_FIND_FUNZIONALITA = "SELECT * FROM public.u_admin_funzionalita WHERE permesso IN (<PERMESSI>) ORDER BY ordine";
		
		String authoritiesStr = "";
		for (String aut : authorities) {
			authoritiesStr+= aut+",";
		}
		authoritiesStr = authoritiesStr.substring(0, authoritiesStr.length()-1);
		
		SQL_FIND_FUNZIONALITA = SQL_FIND_FUNZIONALITA.replace("<PERMESSI>", authoritiesStr);
		List<Funzionalita> listaFunzionalita = getJdbcTemplate().query(SQL_FIND_FUNZIONALITA, new Object[] {}, new RowMapper<Funzionalita>() {
			@Override
			public Funzionalita mapRow(ResultSet rs, int rowNum) throws SQLException {
				Funzionalita funzionalita = new Funzionalita();
				funzionalita.setCodice(rs.getString("codice"));
				funzionalita.setDenominazione(rs.getString("denominazione"));
				funzionalita.setId(rs.getLong("id"));
				funzionalita.setImgUrl(rs.getString("img_url"));
				funzionalita.setLinkUrl(rs.getString("link_url"));
				funzionalita.setLocked(rs.getBoolean("locked"));
				funzionalita.setMappa(rs.getString("mappa"));
				funzionalita.setOrdine(rs.getString("ordine"));
				funzionalita.setPermesso(rs.getString("permesso"));
				
				return funzionalita;
			}
		});
		
		return listaFunzionalita;
	}
}
