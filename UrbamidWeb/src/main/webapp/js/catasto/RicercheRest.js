/**
 * @include util.js
 */

ricercheSoggettiRest = {
		
	ricercaPersoneFisiche : function(nome, cognome, sesso, codiceFiscale, dataNascitaDa, dataNascitaA, provincia, comune, note) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/ricercaPersoneFisiche?';
		if (nome !== null && nome !== undefined && nome !== '') 
			url += '&nome=' + nome;
		if (cognome !== null && cognome !== undefined && cognome !== '') 
			url += '&cognome=' + cognome;
		if (sesso !== null && sesso !== undefined && sesso !== '') 
			url += '&sesso=' + sesso;
		if (codiceFiscale !== null && codiceFiscale !== undefined && codiceFiscale !== '') 
			url += '&codiceFiscale=' + codiceFiscale;
		if (dataNascitaDa !== null && dataNascitaDa !== undefined && dataNascitaDa !== '') 
			url += '&dataNascitaDa=' + dataNascitaDa;
		if (dataNascitaA !== null && dataNascitaA !== undefined && dataNascitaA !== '') 
			url += '&dataNascitaA=' + dataNascitaA;
		if (provincia !== null && provincia !== undefined && provincia !== '') 
			url += '&provincia=' + provincia;
		if (comune !== null && comune !== undefined && comune !== '') 
			url += '&comune=' + comune;
		if (note !== null && note !== undefined && note !== '') 
			url += '&note=' + note;
		return url;
	},

	dettaglioSoggettiPT : function(idSoggetto) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettaglioSoggettiPT?&idSoggetto='+idSoggetto;
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	dettaglioSoggettiUIU : function(idSoggetto) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettaglioSoggettiUIU?&idSoggetto='+idSoggetto;
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	ricercaSoggettiGiuridici : function(denominazione, provinciaNascita, comuneNascita, codiceFiscale) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/ricercaSoggettiGiuridici?';
		if (denominazione !== null && denominazione !== undefined && denominazione !== '') 
			url += '&denominazione=' + denominazione;
		if (provinciaNascita !== null && provinciaNascita !== undefined && provinciaNascita !== '') 
			url += '&provinciaNascita=' + provinciaNascita;
		if (comuneNascita !== null && comuneNascita !== undefined && comuneNascita !== '') 
			url += '&comuneNascita=' + comuneNascita;
		if (codiceFiscale !== null && codiceFiscale !== undefined && codiceFiscale !== '') 
			url += '&codiceFiscale=' + codiceFiscale;
		return url;
	},

	exportPersoneFisiche : function(nome, cognome, sesso, codiceFiscale, dataNascitaDa, dataNascitaA, provincia, comune, note) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportPersoneFisiche?';
		if (nome !== null && nome !== undefined && nome !== '') 
			url += '&nome=' + nome;
		if (cognome !== null && cognome !== undefined && cognome !== '') 
			url += '&cognome=' + cognome;
		if (sesso !== null && sesso !== undefined && sesso !== '') 
			url += '&sesso=' + sesso;
		if (codiceFiscale !== null && codiceFiscale !== undefined && codiceFiscale !== '') 
			url += '&codiceFiscale=' + codiceFiscale;
		if (dataNascitaDa !== null && dataNascitaDa !== undefined && dataNascitaDa !== '') 
			url += '&dataNascitaDa=' + dataNascitaDa;
		if (dataNascitaA !== null && dataNascitaA !== undefined && dataNascitaA !== '') 
			url += '&dataNascitaA=' + dataNascitaA;
		if (provincia !== null && provincia !== undefined && provincia !== '') 
			url += '&provincia=' + provincia;
		if (comune !== null && comune !== undefined && comune !== '') 
			url += '&comune=' + comune;
		if (note !== null && note !== undefined && note !== '') 
			url += '&note=' + note;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	exportSoggettiGiuridici : function(denominazione, provinciaNascita, comuneNascita, codiceFiscale) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportSoggettiGiuridici?';
		if (denominazione !== null && denominazione !== undefined && denominazione !== '') 
			url += '&denominazione=' + denominazione;
		if (provinciaNascita !== null && provinciaNascita !== undefined && provinciaNascita !== '') 
			url += '&provinciaNascita=' + provinciaNascita;
		if (comuneNascita !== null && comuneNascita !== undefined && comuneNascita !== '') 
			url += '&comuneNascita=' + comuneNascita;
		if (codiceFiscale !== null && codiceFiscale !== undefined && codiceFiscale !== '') 
			url += '&codiceFiscale=' + codiceFiscale;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	ricercaImmobiliUIU : function(comune, indirizzo, zona, sezioneUrbana, consistenza, categoria, foglio, superficie, classe, numero, renditaLire, partita, subalterno, renditaEuro, soppresso) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/ricercaImmobiliUIU?';
		if (comune !== null && comune !== undefined && comune !== '') 
			url += '&comune=' + comune;
		if (indirizzo !== null && indirizzo !== undefined && indirizzo !== '') 
			url += '&indirizzo=' + indirizzo;
		if (zona !== null && zona !== undefined && zona !== '') 
			url += '&zona=' + zona;
		if (sezioneUrbana !== null && sezioneUrbana !== undefined && sezioneUrbana !== '') 
			url += '&sezioneUrbana=' + sezioneUrbana;
		if (consistenza !== null && consistenza !== undefined && consistenza !== '') 
			url += '&consistenza=' + consistenza;
		if (categoria !== null && categoria !== undefined && categoria !== '') 
			url += '&categoria=' + categoria;
		if (foglio !== null && foglio !== undefined && foglio !== '') 
			url += '&foglio=' + foglio;
		if (superficie !== null && superficie !== undefined && superficie !== '') 
			url += '&superficie=' + superficie;
		if (classe !== null && classe !== undefined && classe !== '') 
			url += '&classe=' + classe;
		if (numero !== null && numero !== undefined && numero !== '') 
			url += '&numero=' + numero;
		if (renditaLire !== null && renditaLire !== undefined && renditaLire !== '') 
			url += '&renditaLire=' + renditaLire;
		if (partita !== null && partita !== undefined && partita !== '') 
			url += '&partita=' + partita;
		if (subalterno !== null && subalterno !== undefined && subalterno !== '') 
			url += '&subalterno=' + subalterno;
		if (renditaEuro !== null && renditaEuro !== undefined && renditaEuro !== '') 
			url += '&renditaEuro=' + renditaEuro;
		if (soppresso !== null && soppresso !== undefined && soppresso !== '') 
			url += '&soppresso=' + soppresso;
		return url;
	},

	dettaglioImmobiliUIUIdentificativi : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettaglioImmobiliUIUIdentificativi?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	dettaglioImmobiliUIUPersoneFisiche : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettaglioImmobiliUIUPersoneFisiche?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	dettaglioImmobiliUIUSoggettiGiuridici : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettaglioImmobiliUIUSoggettiGiuridici?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	dettaglioImmobiliUIUPlanimetrie : function(foglio, numero, subalterno) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettaglioImmobiliUIUPlanimetrie?foglio=' + foglio+'&numero='+numero;
		if (subalterno !== null && subalterno !== undefined && subalterno !== '') 
			url += '&subalterno=' + subalterno;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},
	
	exportImmobiliUIU : function(comune, indirizzo, zona, sezioneUrbana, consistenza, categoria, foglio, superficie, classe, numero, renditaLire, partita, subalterno, renditaEuro, soppresso) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportImmobiliUIU?';
		if (comune !== null && comune !== undefined && comune !== '') 
			url += '&comune=' + comune;
		if (indirizzo !== null && indirizzo !== undefined && indirizzo !== '') 
			url += '&indirizzo=' + indirizzo;
		if (zona !== null && zona !== undefined && zona !== '') 
			url += '&zona=' + zona;
		if (sezioneUrbana !== null && sezioneUrbana !== undefined && sezioneUrbana !== '') 
			url += '&sezioneUrbana=' + sezioneUrbana;
		if (consistenza !== null && consistenza !== undefined && consistenza !== '') 
			url += '&consistenza=' + consistenza;
		if (categoria !== null && categoria !== undefined && categoria !== '') 
			url += '&categoria=' + categoria;
		if (foglio !== null && foglio !== undefined && foglio !== '') 
			url += '&foglio=' + foglio;
		if (superficie !== null && superficie !== undefined && superficie !== '') 
			url += '&superficie=' + superficie;
		if (classe !== null && classe !== undefined && classe !== '') 
			url += '&classe=' + classe;
		if (numero !== null && numero !== undefined && numero !== '') 
			url += '&numero=' + numero;
		if (renditaLire !== null && renditaLire !== undefined && renditaLire !== '') 
			url += '&renditaLire=' + renditaLire;
		if (partita !== null && partita !== undefined && partita !== '') 
			url += '&partita=' + partita;
		if (subalterno !== null && subalterno !== undefined && subalterno !== '') 
			url += '&subalterno=' + subalterno;
		if (renditaEuro !== null && renditaEuro !== undefined && renditaEuro !== '') 
			url += '&renditaEuro=' + renditaEuro;
		if (soppresso !== null && soppresso !== undefined && soppresso !== '') 
			url += '&soppresso=' + soppresso;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	localizzaUIU : function(foglio, numero) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/localizzaUIU?foglio=' + foglio + '&numero=' + numero;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	ricercaParticellePT : function(comune, sezione, foglio, numero, subalterno, partita, redditoDominicaleEuro, redditoDominicaleLire, redditoAgrarioEuro, redditoAgrarioLire, superficie, soppresso) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/ricercaParticellePT?';
		if (comune !== null && comune !== undefined && comune !== '') 
			url += '&comune=' + comune;
		if (sezione !== null && sezione !== undefined && sezione !== '') 
			url += '&sezione=' + sezione;
		if (foglio !== null && foglio !== undefined && foglio !== '') 
			url += '&foglio=' + foglio;
		if (numero !== null && numero !== undefined && numero !== '') 
			url += '&numero=' + numero;
		if (subalterno !== null && subalterno !== undefined && subalterno !== '') 
			url += '&subalterno=' + subalterno;
		if (partita !== null && partita !== undefined && partita !== '') 
			url += '&partita=' + partita;
		if (redditoDominicaleEuro !== null && redditoDominicaleEuro !== undefined && redditoDominicaleEuro !== '') 
			url += '&redditoDominicaleEuro=' + redditoDominicaleEuro;
		if (redditoDominicaleLire !== null && redditoDominicaleLire !== undefined && redditoDominicaleLire !== '') 
			url += '&redditoDominicaleLire=' + redditoDominicaleLire;
		if (redditoAgrarioEuro !== null && redditoAgrarioEuro !== undefined && redditoAgrarioEuro !== '') 
			url += '&redditoAgrarioEuro=' + redditoAgrarioEuro;
		if (redditoAgrarioLire !== null && redditoAgrarioLire !== undefined && redditoAgrarioLire !== '') 
			url += '&redditoAgrarioLire=' + redditoAgrarioLire;
		if (superficie !== null && superficie !== undefined && superficie !== '') 
			url += '&superficie=' + superficie;
		if (soppresso !== null && soppresso !== undefined && soppresso !== '') 
			url += '&soppresso=' + soppresso;
		return url;
	},

	dettagliParticelleUIUIdentificativi : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettagliParticelleUIUIdentificativi?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	localizzaPT : function(foglio, numero) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/localizzaPT?foglio=' + foglio + '&numero=' + numero;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	dettagliParticellePTMultiplo : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettagliParticellePTMultiplo?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	dettaglioParticellePTPersoneFisiche : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettaglioParticellePTPersoneFisiche?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	dettaglioParticellePTSoggettiGiuridici : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/dettaglioParticellePTSoggettiGiuridici?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	exportParticellePT : function(comune, sezione, foglio, numero, subalterno, partita, redditoDominicaleEuro, redditoDominicaleLire, redditoAgrarioEuro, redditoAgrarioLire, superficie, soppresso) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportParticellePT?';
		if (comune !== null && comune !== undefined && comune !== '') 
			url += '&comune=' + comune;
		if (sezione !== null && sezione !== undefined && sezione !== '') 
			url += '&sezione=' + sezione;
		if (foglio !== null && foglio !== undefined && foglio !== '') 
			url += '&foglio=' + foglio;
		if (numero !== null && numero !== undefined && numero !== '') 
			url += '&numero=' + numero;
		if (subalterno !== null && subalterno !== undefined && subalterno !== '') 
			url += '&subalterno=' + subalterno;
		if (partita !== null && partita !== undefined && partita !== '') 
			url += '&partita=' + partita;
		if (redditoDominicaleEuro !== null && redditoDominicaleEuro !== undefined && redditoDominicaleEuro !== '') 
			url += '&redditoDominicaleEuro=' + redditoDominicaleEuro;
		if (redditoDominicaleLire !== null && redditoDominicaleLire !== undefined && redditoDominicaleLire !== '') 
			url += '&redditoDominicaleLire=' + redditoDominicaleLire;
		if (redditoAgrarioEuro !== null && redditoAgrarioEuro !== undefined && redditoAgrarioEuro !== '') 
			url += '&redditoAgrarioEuro=' + redditoAgrarioEuro;
		if (redditoAgrarioLire !== null && redditoAgrarioLire !== undefined && redditoAgrarioLire !== '') 
			url += '&redditoAgrarioLire=' + redditoAgrarioLire;
		if (superficie !== null && superficie !== undefined && superficie !== '') 
			url += '&superficie=' + superficie;
		if (soppresso !== null && soppresso !== undefined && soppresso !== '') 
			url += '&soppresso=' + soppresso;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	downloadPlanimetria : function(nomeImage) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/getUIUPlanimetrie?';
		if (nomeImage !== null && nomeImage !== undefined && nomeImage !== '') 
			url += '&nomeImage=' + nomeImage;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},
	
	ricercaIntestazioniPersoneFisiche : function(nome, cognome, codiceFiscale, checkboxUiuPf, checkboxPtPf) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/ricercaIntestazioniPersoneFisiche?';
		if (nome !== null && nome !== undefined && nome !== '') 
			url += '&nome=' + nome;
		if (cognome !== null && cognome !== undefined && cognome !== '') 
			url += '&cognome=' + cognome;
		if (codiceFiscale !== null && codiceFiscale !== undefined && codiceFiscale !== '') 
			url += '&codiceFiscale=' + codiceFiscale;
		if (checkboxUiuPf !== null && checkboxUiuPf !== undefined && checkboxUiuPf !== '') 
			url += '&checkboxUiuPf=' + checkboxUiuPf;
		if (checkboxPtPf !== null && checkboxPtPf !== undefined && checkboxPtPf !== '') 
			url += '&checkboxPtPf=' + checkboxPtPf;
		return url;
	},

	informazioniParticellaByIdImmobile : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/informazioniParticellaByIdImmobile?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	informazioniUnitaImmobiliareByIdImmobile : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/informazioniUnitaImmobiliareByIdImmobile?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	exportIntestazioniPF : function(nome, cognome, codiceFiscale, checkboxUiuPf, checkboxPtPf) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportIntestazioniPF?';
		if (nome !== null && nome !== undefined && nome !== '') 
			url += '&nome=' + nome;
		if (cognome !== null && cognome !== undefined && cognome !== '') 
			url += '&cognome=' + cognome;
		if (codiceFiscale !== null && codiceFiscale !== undefined && codiceFiscale !== '') 
			url += '&codiceFiscale=' + codiceFiscale;
		if (checkboxUiuPf !== null && checkboxUiuPf !== undefined && checkboxUiuPf !== '') 
			url += '&checkboxUiuPf=' + checkboxUiuPf;
		if (checkboxPtPf !== null && checkboxPtPf !== undefined && checkboxPtPf !== '') 
			url += '&checkboxPtPf=' + checkboxPtPf;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	ricercaIntestazioniSoggettiGiuridici : function(denominazione, provincia, comune, codiceFiscale, checkboxUiuSg, checkboxPtSg) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/ricercaIntestazioniSoggettiGiuridici?';
		if (denominazione !== null && denominazione !== undefined && denominazione !== '') 
			url += '&denominazione=' + denominazione;
		if (provincia !== null && provincia !== undefined && provincia !== '') 
			url += '&provincia=' + provincia;
		if (comune !== null && comune !== undefined && comune !== '') 
			url += '&comune=' + comune;
		if (codiceFiscale !== null && codiceFiscale !== undefined && codiceFiscale !== '') 
			url += '&codiceFiscale=' + codiceFiscale;
		if (checkboxUiuSg !== null && checkboxUiuSg !== undefined && checkboxUiuSg !== '') 
			url += '&checkboxUiuSg=' + checkboxUiuSg;
		if (checkboxPtSg !== null && checkboxPtSg !== undefined && checkboxPtSg !== '') 
			url += '&checkboxPtSg=' + checkboxPtSg;
		return url;
	},

	exportIntestazioniSG : function(denominazione, provincia, comune, codiceFiscale, checkboxUiuSg, checkboxPtSg) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportIntestazioniSG?';
		if (denominazione !== null && denominazione !== undefined && denominazione !== '') 
			url += '&denominazione=' + denominazione;
		if (provincia !== null && provincia !== undefined && provincia !== '') 
			url += '&provincia=' + provincia;
		if (comune !== null && comune !== undefined && comune !== '') 
			url += '&comune=' + comune;
		if (codiceFiscale !== null && codiceFiscale !== undefined && codiceFiscale !== '') 
			url += '&codiceFiscale=' + codiceFiscale;
		if (checkboxUiuSg !== null && checkboxUiuSg !== undefined && checkboxUiuSg !== '') 
			url += '&checkboxUiuSg=' + checkboxUiuSg;
		if (checkboxPtSg !== null && checkboxPtSg !== undefined && checkboxPtSg !== '') 
			url += '&checkboxPtSg=' + checkboxPtSg;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	getComuni : function() {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/catastoController/getComuni',
			cache : true,
			//async: true
		});
	},

	getProvince : function() {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/catastoController/getProvince',
			cache : true,
		});
	},

	getComuniByProvincia : function(idProvincia) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/catastoController/getComuniByProvincia?idProvincia=' + idProvincia,
			cache : true,
			async: true
		});
	},

	ricercaListaIntestatariTranneCorrenteConDiritto : function(idImmobile, idSoggetto) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/ricercaListaIntestatariTranneCorrenteConDiritto?idImmobile=' + idImmobile + '&idSoggetto=' + idSoggetto,
			cache : true,
			async: true
		});
	},

	getDataUltimoAggiornamento : function() {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/getDataUltimoAggiornamento',
			cache : true,
			async: true
		});
	},

	exportVisuraCatastaleFabbricati : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportVisuraCatastaleFabbricati?';
		if (idImmobile !== null && idImmobile !== undefined && idImmobile !== '') 
			url += '&idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	exportVisuraCatastaleTerreni : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportVisuraCatastaleTerreni?';
		if (idImmobile !== null && idImmobile !== undefined && idImmobile !== '') 
			url += '&idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	exportVisuraCatastaleStoricaTerreni : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportVisuraCatastaleStoricaTerreni?';
		if (idImmobile !== null && idImmobile !== undefined && idImmobile !== '') 
			url += '&idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	exportVisuraCatastaleStoricaFabbricati : function(idImmobile) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) :appConfig.urbamidPort) + '/UrbamidWeb/ricercheController/exportVisuraCatastaleStoricaFabbricati?';
		if (idImmobile !== null && idImmobile !== undefined && idImmobile !== '') 
			url += '&idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},
		
}