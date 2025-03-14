$(document).ready(function(){
	
	/**
	 * verifica che non vi siano più di 10 finestre iconizzate, se così fosse deve
	 * collassare tutte in una select
	 */
	verifyIconSize();
	
});


/**
 *   TODO --> COSA FA
 */
$(document).on("dblclick",".ui-dialog-dblclick-full-screen .ui-dialog-title",function(e){
	e.preventDefault();
	maximize($(this));
});


/**
 * verifica che non vi siano più di 10 finestre iconizzate, se così fosse deve
 * collassare tutte in una select
 */
function verifyIconSize() {
	if ($("#iconizedDialog").children().length > 10) {
		$("#iconizedDialog").addClass("dropdown-menu");
		$("#dropupIconized").addClass("collaps");
	} else {
		$("#iconizedDialog").removeClass("dropdown-menu");
		$("#dropupIconized").removeClass("collaps");
	}
};


/**
 * ripristina le dialog iconizzate
 * 
 * @param idElem ->
 *            id dialog
 * @param tipo ->
 *            identifica il tipo di dialog
 */
function deiconize(idElem, tipo) {
	$("#" + idElem).closest(".ui-dialog").removeClass("hidden");
	$("li[data-dialog-ref='" + idElem + "']").remove();
	verifyIconSize();
	if (tipo != undefined && tipo == "evento") {
		$("#strumenti").removeClass("nohref");
	}
};


/**
 * iconizza le dialog
 * 
 * @param elem ->
 *            id dialog
 * @param tipo ->
 *            identifica il tipo di dialog
 */
function iconize(elem, tipo) {
	if (tipo == undefined) tipo = "";

	$(elem).closest(".ui-dialog").addClass("hidden");
	var title = $(elem).closest(".ui-dialog");
	var titleName = title.find(".ui-dialog-title").text();
	var ref = title.find(".ui-dialog-content").attr("id");
	$("#iconizedDialog").append(
			"<li data-dialog-ref=\"" + ref + "\">" +
				"<button type=\"button\" onclick=\"deiconize('" + ref + "','" + tipo + "')\" alt=\"" + titleName + "\" title=\"" + titleName + "\" class=\"trasparent\">" + 
					titleName +
				"</button>" + 
			"</li>");
	if (tipo == "evento") {
		$("#strumenti").addClass("nohref");
	}
};


/**
 *  TODO --> COSA FA
 *  
 * @param dialog
 * @param event
 */
function toggleSearch(dialog, event){
	var destId = $(event.currentTarget).data("toggle");
	$(dialog + " " + destId).toggleClass("hidden hover");
	$(dialog).toggleClass("searchable");
};


/**
 *  TODO --> COSA FA
 *  
 * @param id
 * @param nstep
 */
function gotoStep(id,nstep){
	if(!$(id + " .step" + nstep).is(":visible")){
		$(id + " div[class*=\"step\"]").addClass("hidden");
		$(id + " .step" + nstep).removeClass("hidden");
		$(id + "_DIALOG").parent().removeClass(function(index, css) {
		    return (css.match (/(^|\s)step\S+/g) || []).join('');
		});
		$(id + "_DIALOG").parent().addClass("step" + nstep);
	}
};


/**
 *  TODO --> COSA FA
 * @param id
 */
function gotoNextStep(id){
	$(id + " .step2").removeClass("hidden");
	$(id + " .step1").addClass("hidden");
	$(id + "_DIALOG").parent().removeClass(function (index, css) {
	    return (css.match (/(^|\s)step\S+/g) || []).join('');
	});
	$(id + "_DIALOG").parent().addClass("step2");
};


/**
 * Espande a tutto schermo la finestra della funzionalità su cui si sta operando.
 * 
 * @param elem ->
 *            id dialog
 */
function maximize(elem) {
	$(elem).closest(".ui-dialog").toggleClass("maximize");
};
