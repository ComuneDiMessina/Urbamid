package it.eng.tz.urbamid.catasto.storico.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.persistence.model.AggiornamentoCatasto;
import it.eng.tz.urbamid.catasto.persistence.repositories.AggiornamentoCatastoRepository;
import it.eng.tz.urbamid.catasto.persistence.repositories.StoricoRepository;
import it.eng.tz.urbamid.catasto.persistence.util.TabellaStorico;
import it.eng.tz.urbamid.catasto.storico.service.StoricoService;

@Service
public class StoricoServiceImpl implements StoricoService {

	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Repository
	 */
	private final StoricoRepository repository;
	
	/**
	 * Repository aggiornamenti
	 */
	private final AggiornamentoCatastoRepository repositoryAggiornamenti;
	
	/**
	 * Costruttore
	 * 
	 * @param repository {@link StoricoRepository}
	 */
	public StoricoServiceImpl(StoricoRepository repository, AggiornamentoCatastoRepository repositoryAggiornamenti) {
		Assert.notNull(repository, "StoricoRepository MUST not be null but don't panic!");
		Assert.notNull(repository, "AggiornamentoCatastoRepository MUST not be null but don't panic!");
		this.repository = repository;
		this.repositoryAggiornamenti = repositoryAggiornamenti;
	}
	
	@Override
	public Map<TabellaStorico, Long> storicizza(Date dataInizioValidita, Date dataFineValidita)
			throws CatastoServiceException {
		LOG.debug("Inizio storicizzazione tabelle geografiche...");
		return repository.storicizzaTabelleGeografiche(dataInizioValidita, dataFineValidita);
	}

	@Override
	public Map<TabellaStorico, Long> storicizza() throws CatastoServiceException {
		LOG.debug("Recupero le ultime 2 righe scritte nella tabella u_cat_aggiornamenti.");
		//Page<AggiornamentoCatasto> page = this.repositoryAggiornamenti.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "dataCreazione")));
		List<AggiornamentoCatasto> ultimiAggiornamenti =
				this.repositoryAggiornamenti.findTop2ByOrderByDataCreazioneDesc();
		Date dataInizioValidita = 
				ultimiAggiornamenti.stream()
					.filter( a -> a.getDataInizioValidita() != null )
						.findFirst()
							.map( AggiornamentoCatasto::getDataInizioValidita )
								.orElse( null );
		Date dataFineValidita = 
				ultimiAggiornamenti.stream()
					.filter( a -> a.getDataFineValidita() != null )
						.findFirst()
							.map( AggiornamentoCatasto::getDataFineValidita )
								.orElse( null );
		LOG.debug("Storicizzo le tabelle geografiche con data inizio validita {} e data fine validita {}.",
				dataInizioValidita, dataFineValidita);
		return repository.storicizzaTabelleGeografiche(dataInizioValidita, dataFineValidita);			
	}

	public StoricoRepository getRepository() {
		return repository;
	}
	
	public AggiornamentoCatastoRepository getRepositoryAggiornamenti() {
		return repositoryAggiornamenti;
	}

}
