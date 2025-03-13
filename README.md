# URBAMID
Repository del Progetto URBAMID+ - Comune di Messina

Per la build dei progetti esistono 4 profili
  | ----- profile ----- | ---  database  -----| ----  servizi  ---- | ----  geoserver  ----|
  |	locale   		    |    192.168.11.222   | 192.168.11.222/159  |    192.168.11.222    |
  | sviluppo			|    192.168.11.222   | 192.168.11.222/159  |    192.168.11.222    |
  | collaudo			|    192.168.131.32   |    192.168.131.32   |    192.168.131.32    |
  | prod-nodo-1			|   ???.???.???.???   |   ???.???.???.???   |   ???.???.???.???    |
  | prod-nodo-2			|   ???.???.???.???   |   ???.???.???.???   |   ???.???.???.???    |
  |----------------------------------------------------------------------------------------|
  
-----
#### Info Progetti repository:
| --------------------------------- | -------------------------------------------------------------------------  | ------------------------------------- |
| Progetto              			| -------------------------------------------------------------------------- | File README                        	 |
| --------------------------------- | -------------------------------------------------------------------------  | ------------------------------------- |
| CXFToShape            			| Permette di convertire i file in formato CXF nel formato Esri Shapefile.   | /CXFToShape/README.md      		  	 |
| IMPORT_CATASTO 					| JOB Geokettle 															 | /IMPORT_CATASTO/README.md  		  	 |
| UrbamidWeb						| Web Project for URBAMID													 |									  	 |
| UrbamidCore						| Core Project for URBAMID													 |									  	 |
| UrbamidAdministrationServices		| Services per gestire il lato administration di Urbamid					 |									  	 |
| UrbamidCatastoServices			| Services per gestire le richieste catastali come le estrazioni particellari|									   	 |
| UrbamidPRGServices				| Services per gestire il Piano Regolatore									 |									  	 |
| UrbamidProfileManagerServices		| Services per gestire la profilatura degli utenti, ruoli e permessi		 |									  	 |
| UrbamidToponomasticaServices		| Services per gestire i dati di toponomastica								 |									  	 |
| UrbamidWrapperGeoServices			| Services che fungerà da proxy per le richieste a geoserver				 |	/UrbamidWrapperGeoServices/README.md |
| --------------------------------- | -------------------------------------------------------------------------  | ------------------------------------- |

In tutti i microservizi sopra citati la versione di "versione spring.security.oauth2" è ferma alla 2.2.7.RELEASE

|------- URBAMID CORE -------| 
Ogni modulo (cubo) verrà definito nel package 
	it.eng.tz.urbamid.<NomeModulo>.*
	
	
	

| ------------------------------------------------------------------------------------------------------------------------------------------------------ |
| ---- Info Progetto: CONFIGURAZIONE AMBIENTE PER UNA COMUNICAZIONE CON LO IAM	 																    ---- |
| ------------------------------------------------------------------------------------------------------------------------------------------------------ |
Locale
	url metadata http://urbamid-sviluppo.eng.it:9191/UrbamidWeb/saml2/service-provider-metadata/UrbamidWeb
	entityId https://metadata-entity-urbamid-sviluppo-eng-it
Sviluppo rete ENG
	url metadata http://urbamid-collaudo-interno.eng.it:9191/UrbamidWeb/saml2/service-provider-metadata/UrbamidWeb
	entityId https://metadata-entity-urbamid-collaudo-interno-eng-it
	
per avere dalle url sopra indicate i metadata è necessario mappare nel file di host le seguenti righe:
	xxx.xxx.xx.xx urbamid-sviluppo.eng.it
	192.168.11.222 urbamid-collaudo-interno.eng.it	
