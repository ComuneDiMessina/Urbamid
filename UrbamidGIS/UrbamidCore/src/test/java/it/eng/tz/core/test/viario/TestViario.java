package it.eng.tz.core.test.viario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.core.config.UrbamidConfiguration;
import it.eng.tz.urbamid.core.dao.NumCivicoDao;
import it.eng.tz.urbamid.core.dao.ToponimoDao;
import it.eng.tz.urbamid.webgis.dao.model.NumCivico;
import it.eng.tz.urbamid.webgis.dao.model.Toponimo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={UrbamidConfiguration.class})
public class TestViario {
	
	private static final Logger logger = LoggerFactory.getLogger(TestViario.class);
	
	@Autowired
	private NumCivicoDao numCivicoDao;
	
	@Autowired
	private ToponimoDao toponimoDao;
	
	
//	@Ignore
	@Test
	public void generaNumeriCivici() { 
		try {
			logger.info(">>>> START generaNumeriCivici");

			
			// carica elenco strade
			List<Toponimo> strade = toponimoDao.getAllStreet();
			
			
			Map<String, List<Toponimo>> stradeMap = new HashMap<String, List<Toponimo>>();
			
			// per ogni strada creare almeno 50 numeri 10 almeno con esponente
			for (Toponimo strada : strade) 
			{
				if (StringUtils.hasText(strada.getDenominazione())) 
				{
					//logger.info(">>>>>>>> Strada [" + strada.getId() + "] - ["+ strada.getCodStrada() +"]: " + strada.getDenominazione());
					
					if (!stradeMap.containsKey(strada.getCodStrada())) 
					{
						List<Toponimo> stradaList =  new ArrayList<Toponimo>();
						stradaList.add(strada);
						stradeMap.put(strada.getCodStrada(), stradaList);
					}
					else 
					{
						stradeMap.get(strada.getCodStrada()).add(strada);
					}
				}

			}
			
			/*
			 *  Finita la prima passata si cicla sugli elementi della mappa, per ogni elemento si vede l'array quante posizioni ha
			 *  si divide il numero possibile dei civici per il numero di archi di strada
			 */
			
			
			for (Map.Entry<String, List<Toponimo>> entry : stradeMap.entrySet()) {
			    
				List<Toponimo> stradaList = entry.getValue();
				int size = stradaList.size();
				
				for (int i= 0; i<stradaList.size(); i++) 
				{
					int blocco = 50/stradaList.size(); 
					Toponimo strada = stradaList.get(i);
					logger.info(">>>>>>>> Strada [" + strada.getId() + "] - ["+ strada.getCodStrada() +"]: " + strada.getDenominazione());
					for (int j=blocco*i+1; j<blocco*(i+1); j++) 
					{
						// salva numero civico
						NumCivico civico = new NumCivico();						
						civico.setCodStrada(strada.getId().toString());
						civico.setNumero(j);
						civico.setSysRef("4326");
						civico.setLatitudine(strada.getLatitudine());
						civico.setLongitudine(strada.getLongitudine());
						logger.info(">>>>>>>> NumCivico [" + civico.getCodStrada() +"]: " + civico.getNumero());
						numCivicoDao.insertCivico(civico);
												
					}
					
				} 
			}
			
			
			
			
			logger.info(">>>> generaNumeriCivici");

		} catch (Exception e) {
			logger.error(">>>> generaNumeriCivici",e);
		}
		
	}

}
