package it.eng.tz.urbamid.administration.mappa.dao.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.mappa.dao.model.UMappa;
public class UMappaRowMapper implements RowMapper<UMappa>{

   /* public UMappa mapRow(ResultSet rs, int rowNum) throws SQLException{
        UMappa result = new UMappa();
        result.setIsNew(false);
        result.setId(rs.getLong("id"));
        result.setCodice(rs.getString("codice"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        if (rs.getObject("descrizione") != null)
            result.setDescrizione(rs.getString("descrizione"));
        if (rs.getObject("stato") != null)
            result.setStato(rs.getString("stato"));
        result.setMappaPredefinita(rs.getBoolean("mappa_predefinita"));
        if (rs.getObject("data_creazione") != null)
            result.setDataCreazione(rs.getTimestamp("data_creazione"));
        if (rs.getObject("data_modifica") != null)
            result.setDataModifica(rs.getTimestamp("data_modifica"));
        if (rs.getObject("utente_creazione") != null)
            result.setUtenteCreazione(rs.getString("utente_creazione"));
        if (rs.getObject("utente_modifica") != null)
            result.setUtenteModifica(rs.getString("utente_modifica"));
        return result;
    }*/
    
    
    
    public UMappa mapRow(ResultSet rs, int rowNum) throws SQLException{
        UMappa result = new UMappa();
        result.setIsNew(false);
        
        result.setId(rs.getLong("id"));
        
        result.setCodice(rs.getString("code"));
        
        if (rs.getObject("title") != null)
            result.setNome(rs.getString("title"));
        
        if (rs.getObject("description") != null)
            result.setDescrizione(rs.getString("description"));
        
        if (rs.getObject("stato") != null)
            result.setStato(rs.getString("stato"));
        
        result.setMappaPredefinita(rs.getBoolean("mappa_predefinita"));
        
        /*if (rs.getObject("catalog") != null)
            result.setNome(rs.getString("catalog"));*/
        
        if (rs.getObject("data_creazione") != null)
            result.setDataCreazione(rs.getTimestamp("data_creazione"));
        
        if (rs.getObject("data_modifica") != null)
            result.setDataModifica(rs.getTimestamp("data_modifica"));
        
        if (rs.getObject("utente_creazione") != null)
            result.setUtenteCreazione(rs.getString("utente_creazione"));
        
        if (rs.getObject("utente_modifica") != null)
            result.setUtenteModifica(rs.getString("utente_modifica"));
       
        
            result.setShowCatalog(rs.getBoolean("show_catalog"));
        
            result.setZoom(rs.getInt("zoom"));
        
        return result;
    }
}