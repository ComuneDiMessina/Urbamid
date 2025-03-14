/*******************************************************************************************************************************************************/
/***************************************			CONFIGURAZIONI DI BASE				****************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/

appBaseConfig={
		
		/**
		 * Definizione del nameSpace Urbamid
		 * @requires jquery-3.4.1.min.js, ...
		 */
		urbamidProtocol			: "http",
		urbamidHostname			: "urbamid-collaudo-interno.eng.it",
		urbamidPort				: "9191",
		urbamidService			: "/UrbamidWeb/",
		urbamidRuoli			: "adminController/getFunzionalita",
		
        getUrlVars 			    : function (){
									    var vars = {};
									    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
									        vars[key] = value;
									    });
									    return vars;
								  }
};

$(document).ready(function() {
	
});