/*******************************************************************************************************************************************************/
/***************************************			CONFIGURAZIONI DI BASE				****************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/

appBaseConfig={
		
		/**
		 * Definizione del nameSpace Urbamid
		 * @requires jquery-3.4.1.min.js, ...
		 */
		urbamidProtocol			: "https",
		urbamidHostname			: "urbamidplus-collaudo.comune.messina.it",
		urbamidPort				: "",
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