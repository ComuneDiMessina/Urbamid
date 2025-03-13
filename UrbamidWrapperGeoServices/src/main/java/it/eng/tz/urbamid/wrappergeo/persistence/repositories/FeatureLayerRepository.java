package it.eng.tz.urbamid.wrappergeo.persistence.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.wrappergeo.persistence.model.LayerFeatureGeom;

/**
 * Repository per la storicizzazione delle tabelle con le informazioni geografiche.
 */
@Component
public class FeatureLayerRepository {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Persistence context
	 */
	@PersistenceContext
	EntityManager entityManager;
	
	/**
	 * Storicizza una tabella tra quelle contenenti dati geografici.
	 * 
	 * @param tabellaStorico è il valore dell'enumerazione {@link TabellaStorico} che indica la tabella da 
	 * storicizzare.
	 * @param dataInizioValidita è la data di inizio validità.
	 * @param dataFineValidita è la data di fine validità.
	 * 
	 * @return il numero di righe affette dalla storicizzazione.
	 */
	@Transactional
	public LayerFeatureGeom findLayerFeatureByGeom( String tableName, String wktGeom) {
		
		Assert.notNull(tableName, "enum value for TabellaStorico MUST not be null!");
		
		Query query = this.entityManager.createQuery(
				"SELECT gid, ST_AsText(geometry) AS geometry "
						+"FROM "+tableName+" " 
						+"WHERE ST_Intersects(ST_GeometryFromText(':wktGeom', 4326), geometry)", LayerFeatureGeom.class);
		query.setParameter("wktGeom", wktGeom);
		List<LayerFeatureGeom> resultList = (ArrayList<LayerFeatureGeom>) query.getResultList();
		return resultList.get(0);
	}
	
}