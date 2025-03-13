/*******************************************************************************************************************************************************/
/***************************************			GESTIONE PAGINA LOGIN				****************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/

/**
 * Al caricamento della pagina di index carico i cubotti
 */
$(document).ready(function() {
	
	/** GESTIONE ERRORE LOGIN **/
	var url = new URL( window.location.href );
	var error = url.searchParams.get("error");
	if (error!=null) {
		$("#mess-error").toggleClass("hide");
		setTimeout(function(){ $("#mess-error").toggleClass("hide"); }, 3000);
	} 
	
	/** GESTIONE PAGINA **/
	if ( !(typeof appCubotti === "undefined") ) {
		appCubotti.onloadCubi();
	}
});


appCubotti = {
		
		logged	: !(typeof logged === "undefined") ? logged : false,
		role	: !(typeof role === "undefined") ? role : "",
				
		start 	: function (){
			
			if ( appCubotti.logged ) {
				//IMPOSTAZIONI GRAFICHE AMMINISTRATORE
			}
		},
		
		/**
		 * I cubotti sono legati al ruolo, pertanto un utenteesterno visualizzerà solo alcuni cubotti
		 * Recupero dal db "dbUrbamid" tabella "u_ruoli_funzionalita",  i cubotti da visualizzare
		 * 
		 * @param 
		 * @returns 
		 */
		onloadCubi : function(){
			if (typeof appConfig === "undefined") {
				
			} else {
				hrefUrbamid		= appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + 
										appConfig.urbamidService + appConfig.urbamidRuoli;
				var cubotti 	= {};					//get cubotti from DB

				$.ajax({
					type 		: "GET",
					dataType	: "json",
					contentType	: "application/json",
					url 		: hrefUrbamid,
					cache 		: false,
					async		: false,
					timeout		: 1000,
					success		: function(response) {
						if (response.success) {
							cubotti.data = response.aaData;
							$.each( cubotti.data, function( keyC, valueC ) {
								if (valueC.locked)
									valueC.disable = "lock";
								else
									valueC.disable = "";
							});
							$("#cubotti").html(compilaTemplate('cubottiTemplate', cubotti));
						} else {
							/** TODO: gestire il caso di errore nel recupero dei cubotti**/
							$("#cubotti").html(compilaTemplate('emptyCubottiTemplate', cubotti));
						}
					},
					error		: function(response){
						response.message = "Errore nel recuperare i cubotti";
						/** TODO: gestire il caso di errore nel recupero dei cubotti**/
					}
				});
            }
		}
};