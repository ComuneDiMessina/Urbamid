/*****************************************/
/** Js per la gestione dei template     **/
/*****************************************/

	/**************/
	/**  HELPERS **/
	/**************/
	Handlebars.registerHelper('ifEquals', function(arg1, arg2, options) {
	    return (arg1 == arg2) ? options.fn(this) : options.inverse(this);
	});
	
	Handlebars.registerHelper('isVisible', function(_visibilita) {				
		return (_visibilita!=null && _visibilita==true);
	});
	
	Handlebars.registerHelper('isArrayEmpty', function(arr) {
		return (arr==null || arr.length==0);
	});