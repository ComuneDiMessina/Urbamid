package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.*;
import java.math.*;
import org.springframework.jdbc.core.RowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappaLayer;

/**
 *  u_mappa_layer
 */
public class UMappaLayerRowMapper implements RowMapper<UMappaLayer>{

    public UMappaLayer mapRow(ResultSet rs, int rowNum) throws SQLException{
        UMappaLayer result = new UMappaLayer();
        result.setIsNew(false); 
        result.setId(rs.getInt("id"));
        result.setIdMappa(rs.getInt("id_mappa"));
        if (rs.getObject("nome_layer") != null)
            result.setNomeLayer(rs.getString("nome_layer"));
        if (rs.getObject("title_layer") != null)
            result.setTitleLayer(rs.getString("title_layer"));
        if (rs.getObject("id_parent") != null)
            result.setIdParent(rs.getString("id_parent"));
        if (rs.getObject("tipo") != null)
            result.setTipo(rs.getString("tipo"));
        result.setAbilitato(rs.getBoolean("abilitato"));
        if (rs.getObject("trasparenza") != null)
            result.setTrasparenza(rs.getString("trasparenza"));
        if (rs.getObject("campo_1") != null)
            result.setCampo1(rs.getString("campo_1"));
        if (rs.getObject("campo_2") != null)
            result.setCampo2(rs.getString("campo_2"));
        if (rs.getObject("campo_3") != null)
            result.setCampo3(rs.getString("campo_3"));
        	result.setPos(rs.getInt("pos"));
        	
        return result;
    }
}
