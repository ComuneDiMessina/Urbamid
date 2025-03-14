<html>
<head>
    <title>Richiesta abilitazione utente</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
    <p>Salve, <#if nomeUtente??><strong>${nomeUtente}</strong><#else>""</#if> <#if cognomeUtente??><strong>${cognomeUtente}</strong><#else>""</#if></p>
    <p>	In data <#if dataRichiesta??><strong>${dataRichiesta}</strong><#else>""</#if>
    	l'utente <#if (nomeUtenteRichiedente == "" && cognomeUtenteRichiedente == "") || (nomeUtenteRichiedente != "" && cognomeUtenteRichiedente == "") || (nomeUtenteRichiedente == "" && cognomeUtenteRichiedente != "") > <strong>${usernameUtente}</strong> <#elseif nomeUtenteRichiedente ?? && cognomeUtenteRichiedente ??><strong>${nomeUtenteRichiedente} ${cognomeUtenteRichiedente}</strong> </#if>
	    ha effettuato
       	una richiesta di abilitazione al gruppo <#if gruppoRichiesto??><strong>${gruppoRichiesto}</strong><#else>gruppoRichiesto utente mancante</#if>.</b></p>
    <p><b>NOTE:</b></p>
	<p>${note}</p>    
	
    <p><b>URL DI RECUPERO DATI:</b></p>
	<p>${urlDatiRichiesta}</p>    
	
	
    <p>Saluti</p>
</body>
</html>