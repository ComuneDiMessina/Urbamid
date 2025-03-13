// This will initialize the plugin 
// and show two dialog boxes: one with the text "Olá World"
// and other with the text "Good morning John!"
jQuery.i18n.properties({
    name: 'Messages', 
    path: 'bundle/', 
    mode: 'both',
    cache: 'false',
    language: 'it_IT'
});

/**
 Here's an example of a languages.json file:

	{
	    "languages": [
	        "en_GB",
	        "es_ES",
	        "pt_BR",
	        "sv_SE"
	    ]
	}
**/

/*******************************************************************************/
	$.expr[":"].contains = $.expr.createPseudo(function(arg) {
		return function( elem ) {
			return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
		};
	});
	
	/**
	 * NON CANCELLARE 
	 */
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
//	$(document).ajaxSend(function(e, xhr, options) {
//		xhr.setRequestHeader(header, token);
//	});
/*******************************************************************************/
