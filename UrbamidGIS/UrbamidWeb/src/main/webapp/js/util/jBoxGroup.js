
/*!
 * JBoxGroup
 * @author salvatore mariniello - salvo.mariniello@gmail.com
 * 
 */

function JBoxGroup(n,o) {
    this.instancename=n || 'boxgrup';
    this.newIst().settyng(o||{});
}
JBoxGroup.instance={};
JBoxGroup.prototype.settyng = function (o) {
    if (o.idMappa)
        this.pkMappa =o.idMappa;
    if (o.idBoxGroup)
        this.boxGruppiLayer= $(o.idBoxGroup);
    if (o.fnChangeOpacity && typeof o.fnChangeOpacity==="function")
    this.fnChangeOpacity=o.fnChangeOpacity;
    if (o.fnUpdateAbility && typeof o.fnUpdateAbility==="function")
    this.fnUpdateAbility=o.fnUpdateAbility;
    if (o.fnUpdateGroup && typeof o.fnUpdateGroup==="function")
    this.fnUpdateGroup=o.fnUpdateGroup;
    if (o.fnDeleteGroup && typeof o.fnDeleteGroup==="function")
    this.fnDeleteGroup=o.fnDeleteGroup;
    if (o.fnDeleteLayer && typeof o.fnDeleteLayer==="function")
    this.fnDeleteLayer=o.fnDeleteLayer;
    if (o.fnTemplateGrupp && typeof o.fnTemplateGrupp==="function")
    this.fnTemplateGrupp=o.fnTemplateGrupp;
    if (o.fnTemplateLayer && typeof o.fnTemplateLayer==="function")
    this.fnTemplateLayer=o.fnTemplateLayer;
    if (o.onShowPermessi && typeof o.onShowPermessi==="function")
    this.fnShowPermessi=o.onShowPermessi;	
    
    var UMappa = {isNew:true,id:this.pkMappa};
    this.ajaxSetting ={type: "post",
    		dataType: "json",
    		contentType: "application/json",
    		url: appUtil.getUrbamidOrigin() + appConfig.urbamidService + "layerController/group-layer-mappa", 
    		data: JSON.stringify(UMappa),
    		cache: false};
    
    if (typeof o.ajaxSetting==="object")
    	for(var n in o.ajaxSetting) 
    	this.ajaxSetting[n]=o.ajaxSetting[n]
    
    return this;
};
JBoxGroup.prototype.setIdMappa=function(id){
	this.pkMappa=id;
};
JBoxGroup.prototype.setIdBoxGroup=function(id){
	this.pkMappa=id;
};



JBoxGroup.prototype.newIst = function () {
    var UMappa = {};
    UMappa.isNew = true;
    UMappa.id = this.pkMappa;
    
    this.groups = new Map();
    this.pkMappa = undefined;
    this.boxGruppiLayer = $("<div></div>");
    this.ajaxSetting = {};
    this.nomeGruppoSelect = undefined;
    this.nomeGruppoReplaceSelect = undefined;
    this.fnChangeOpacity = function () {};
    this.fnUpdateAbility = function () {};
    this.fnUpdateGroup = function () {};
    this.fnDeleteGroup = function () {};
    this.fnDeleteLayer = function () {};
    this.fnTemplateGrupp = function () {};
    this.fnTemplateLayer = function () {}; 
    this.fnShowPermessi = function () {}; 
    
    return this;
}

JBoxGroup.prototype.initGroup = function () {
    var th__ = this;

    if (typeof th__.groups !== "undefined")
    {
        th__.groups.forEach(function(v,k){
            (function (k) {
                var UGroup = th__.instanceGroup(k);
                th__.insertHtmlGroup(UGroup);
            })(k)
            })

       
    }
    return th__;
}
JBoxGroup.prototype.getGroup = function () {
    return this.groups.getGrups().obj || {};
};
JBoxGroup.prototype.instanceGroup = function (original) {
    var UGroup = {};
    UGroup.groupName = original;
    UGroup.idParent = this.replaceId(original);
    UGroup.idMappa = this.pkMappa;
    return UGroup;
}

JBoxGroup.prototype.instanceLayer = function (name, title, original) {
    var ULayer = {};
    ULayer.isNew = true;
    ULayer.idMappa = this.pkMappa;
    ULayer.nomeLayer = name;
    ULayer.titleLayer = title;
    ULayer.tipo = 'L';
    ULayer.trasparenza = '10';
    ULayer.idParent = this.replaceId(original);
    ULayer.idParentNonReplace = original;
    ULayer.pos = 1;



    return ULayer;
}



JBoxGroup.prototype.initLayer = function () {
    var th__ = this;
    if (typeof th__.groups !== "undefined")
    {
       th__.groups.forEach(function(v,k,m){
    
          for (var i in v)
            {
                if (v.length && typeof v[i]!=="function") {
                    (function (k, i, idP) {
                        v[i].idParent = idP;
                        v[i].idParentNonReplace = k;
                        /* arry[i].pos=Number(i)+1; */
                        th__.insertItemHtmlLayer(v[i])
                    })(k, i, th__.replaceId(k))
                }
            }
       }) 
        
        th__.checkEvent();
    }
     return this;
}


JBoxGroup.prototype.checkIntDatiBase=function(listGroup){
	var th__=this;
	var gName="DATI DI BASE",
	bbol=false,
       newObj={} ;
	 
	if(typeof listGroup!=="undefined"){
	for(var gr in listGroup)
    {
        if(gr===gName)
        {
        	bbol=true;
        }
    }
    if(!bbol){
    	newObj[gName]=[];
    	newObj[gName].push({
        "isNew": true,
        "id": 0,
        "idMappa": th__.pkMappa,
        "nomeLayer": "layer",
        "titleLayer": "Layer base",
        "idParent": gName,
        "tipo": "L",
        "abilitato": false,
        "trasparenza": "10",
        "campo1": null,
        "campo2": null,
        "campo3": null
        },
        {
            "isNew": true,
            "id": 1,
            "idMappa": th__.pkMappa,
            "nomeLayer": "ortofoto",
            "titleLayer": "Ortofoto",
            "idParent": gName,
            "tipo": "L",
            "abilitato": false,
            "trasparenza": "10",
            "campo1": null,
            "campo2": null,
            "campo3": null
         }
         )
    }
    for(var gr in listGroup)
    {
     newObj[gr]=listGroup[gr];
    }
    
     th__.groups= new Map(Object.entries(newObj));
    
    th__.initGroup();
    th__.initLayer();
    
	}else{
		console.error("JBoxGroup :: listGroup { undefined }")
	}
    return th__;
};
JBoxGroup.prototype.create = function () {
    var th_=this;
    th_.loadGrups();
    return th_;
}
JBoxGroup.prototype.onShowPermessi=function(fn){
	this.fnShowPermessi=fn;
};
JBoxGroup.prototype.insertHtmlGroup = function (uGroup) {
    var templateDialog = Handlebars.compile(this.getTemplateGrup(this.instancename)),
            outputDialog = templateDialog(uGroup);

    this.boxGruppiLayer.append(outputDialog);

    var chkItems = $('#dati-layer input:checkbox');
    var nChk = chkItems.length;
    if(nChk > 0) {
        chkItems.each(function(idx, item) {
            var choice = $(item);
            choice.attr('disabled', appGestMappe.blocked)
        });
    }
}

JBoxGroup.prototype.insertItemHtmlLayer = function (layer) {
    var template = Handlebars.compile(this.getTemplateLayer(this.instancename)),
            outputDialog = template(layer);
    $("#default-layer-" + layer.idParent).append(outputDialog);

    var chkItems = $('#dati-layer input:checkbox');
    var nChk = chkItems.length;
    if(nChk > 0) {
        chkItems.each(function(idx, item) {
            var choice = $(item);
            choice.attr('disabled', appGestMappe.blocked)
        });
    }

}

JBoxGroup.prototype.replaceId = function (string) {
    return string.replace(/[^a-z0-9\s]/gi, '').replace(/[-\s]/g, '_');
};

JBoxGroup.prototype.addGroup = function(t) {
	var th__ = this;
    uGroup = null;
    
    var grp = t;
    if(grp == null || grp == undefined)
	    grp = $("#nomeGruppo").val().toUpperCase();

	if (/[`~!@#$%^&*()|+\=?;:'",.<>\{\}\[\]\\\/]/.test(grp)) {
		appUtil.showMessageAlertApplication(
				"Sono ammessi solo caratteri alfanumerici piu' [ - _ ]",
				"Info", true, false, false);
	} else {
                var bol_=false;
                th__.groups.forEach(function(v,k,m){
                    if(grp.toLowerCase()==k.toLowerCase())
                    bol_=true;  
                })
		if ((bol_))
			appUtil.showMessageAlertApplication(
					"Gruppo esistente, per la mappa corrente", "INFO", false,
					false, true);
		else {
			th__.groups.set(grp, []);
            uGroup = th__.instanceGroup(grp);
            th__.insertHtmlGroup(uGroup);
		}

	}
    return uGroup;
}
JBoxGroup.prototype.refreshGroupSelected = function () {
    var th__ = this;
    
    $("#default-layer-" + th__.nomeGruppoReplaceSelect).html("");
    
    if (typeof th__.groups !== "undefined")
    {
    	var obj_arry=th__.groups.get(th__.nomeGruppoSelect); 
    	
    	   if (typeof obj_arry !== "undefined")
            for (var i in obj_arry)
            {		 
                if (obj_arry.length && typeof obj_arry[i]!=="function") {
                    (function (grup, i, idP) {
                    	obj_arry[i].idParent = idP;
                    	obj_arry[i].idParentNonReplace = grup;
                        th__.insertItemHtmlLayer(obj_arry[i])
                    })(th__.nomeGruppoSelect, i, th__.replaceId(th__.nomeGruppoSelect))
                }

            }
            th__.checkEvent();
    }
     return this;
}
JBoxGroup.prototype.updatePosition=function() {
	var th__ = this;
    
    for(var i in th__.groups.get(th__.nomeGruppoSelect))
    {
        (function(i){
        th__.groups.get(th__.nomeGruppoSelect)[i].pos=Number(i)+1;
        })(i)

    }
 
    return th__;  
};
JBoxGroup.prototype.moveDown=function(nomeLayer) {
    var th__ = this;
    var current=0;
    for(var i in th__.groups.get(th__.nomeGruppoSelect))
    {
        if(th__.groups.get(th__.nomeGruppoSelect)[i].nomeLayer===nomeLayer)
        {
        	current=i;
        	break;
        }
    }
    	
    var	intNext=Number(current)+1;
        if(intNext>=th__.groups.get(th__.nomeGruppoSelect).length)
    	    intNext=Number(0);

    th__.groups.get(th__.nomeGruppoSelect).splice(intNext, 0, th__.groups.get(th__.nomeGruppoSelect).splice(current, 1)[0]);
    
    th__.refreshGroupSelected();
    
    $(".row-box-mapp").css("background","transparent");
    $("#"+nomeLayer+"_divchk").css("background","#f2f2f2");
   
    return th__.updatePosition();
};
JBoxGroup.prototype.moveGroupUP=function(nGroup) {
	var th__ = this,ary=[];
	ary=Array.from(th__.groups)
	th__.groups.clear();
	th__.groups=new Map(th__.arymoveUP(nGroup,ary))
	th__.refreshGrups();
	return th__;
}
JBoxGroup.prototype.moveGroupDown=function(nGroup) {
	var th__ = this,ary=[];
	ary=Array.from(th__.groups)
	th__.groups.clear();
	th__.groups=new Map(th__.arymoveDown(nGroup,ary))
	th__.refreshGrups();
	return th__;
}
JBoxGroup.prototype.moveUP=function(nomeLayer) {
	var th__ = this;
    var current=0;
    for(var i in th__.groups.get(th__.nomeGruppoSelect))
    {
        if(th__.groups.get(th__.nomeGruppoSelect)[i].nomeLayer===nomeLayer)
        {
        	current=i;
        	break;
        }
    }
    	
    var	intNext=Number(current)-1;
    	if(intNext<0)
    	intNext=Number(th__.groups.get(th__.nomeGruppoSelect).length);

    th__.groups.get(th__.nomeGruppoSelect).splice(intNext, 0, th__.groups.get(th__.nomeGruppoSelect).splice(current, 1)[0]);
    
    th__.refreshGroupSelected();
    
    $(".row-box-mapp").css("background","transparent");
    $("#"+nomeLayer+"_divchk").css("background","#f2f2f2");
   
    return th__.updatePosition();
};
JBoxGroup.prototype.checkEvent = function () {
    var th__ = this;
    $(".row-box-mapp").unbind().bind("click",function(){ $(".row-box-mapp").css("background","transparent"); $(this).css("background","#f2f2f2")})

    th__.groups.forEach(function(v,k,m){
        if(v.length != 0) {
            let isAllChecked = 0;

            $('i[data-gruppo="'+th__.replaceId(k)+'"]').each(function(e) {
                if(!$(this).hasClass('fa fa-check')) {
                    isAllChecked = 1;
                    return false;
                }
            });

            if(isAllChecked == 0) {
                $('#check_all_' + th__.replaceId(k)).prop('checked', true);
            }
        }
    });
};
JBoxGroup.prototype.setSelectedGroup = function (nomeRepalce,nomeOrig) {
    var th_ = this;
    
    if(!appGestMappe.blocked) {
        th_.nomeGruppoReplaceSelect=nomeRepalce;
        th_.nomeGruppoSelect=nomeOrig;
        $(".panel-group").removeClass( "activeGroup" );
        $("#accordion_"+nomeRepalce).addClass( "activeGroup" );
    } else {
        th_.nomeGruppoSelect = undefined;
        th_.nomeGruppoReplaceSelect = undefined;
    }
}

JBoxGroup.prototype.updateGroup = function (newNameGrup,idParent,idbox,idpar_replace) {
 var th_=this;
 var newGrup={};
 var nomeg=newNameGrup;
 
    th_.groups.forEach(function(v,k,m){
       if(k===idParent)
       newGrup[nomeg]= v; 
       else
       newGrup[k]=v;    
        
    })
 
   
  th_.groups=new Map(Object.entries(newGrup));  
this.fnUpdateGroup(newNameGrup,idParent,idbox,idpar_replace);  

this.refreshGrups();
 
}

JBoxGroup.prototype.deleteGroup = function (idMappa,idParent,idbox,idpar_replace) {
 
    var th_=this;
    var newGrup={};
 
       th_.groups.forEach(function(v,k,m){
       if(k!==idParent)
       newGrup[k]= v; 
 
    })

    th_.groups=new Map(Object.entries(newGrup)); 
    th_.fnDeleteGroup(idMappa,idParent,idbox,idpar_replace); 
    $("#"+idbox).remove();
    
    this.nomeGruppoSelect = undefined;
    this.nomeGruppoReplaceSelect = undefined;
}

JBoxGroup.prototype.selectAllGroup = function(idMappa, groupName, idParent) {
    var th_ = this;
    var layerActive = [];
    var layerNotActive = [];

    JBoxGroup.instance['gestmappa'].setSelectedGroup(idParent, groupName);

     if($('#check_all_' + idParent).is(':checked')) {
        th_.groups.forEach(function(v, k, m) {
            if(k == groupName) {
                $.each(v, function(key, value) {
                    if(!$('#' + value.idParent + '_' + value.nomeLayer + '_chkImg').hasClass('fa fa-check')) {
                        layerNotActive.push(value.nomeLayer);
                    } 
                });
            }
        });
    } else {
        th_.groups.forEach(function(v, k, m) {
            if(k == groupName) {
                $.each(v, function(key, value) {
                    if($('#' + value.idParent + '_' + value.nomeLayer + '_chkImg').hasClass('fa fa-check')) {
                        layerActive.push(value.nomeLayer);
                    } 
                });
            }
        });
    }
    
    if(layerNotActive.length != 0) {

        $.each(layerNotActive, function(key, value) {
            JBoxGroup.instance['gestmappa'].updateAbility(value,idParent,idMappa,idParent + '_' + value + '_chk',idParent + '_' + value + '_chkImg');
        });

    } else if(layerActive.length != 0) {

         $.each(layerActive, function(key, value) {
            JBoxGroup.instance['gestmappa'].updateAbility(value,idParent,idMappa,idParent + '_' + value + '_chk',idParent + '_' + value + '_chkImg');
        });

    }

}

JBoxGroup.prototype.addLayer = function (name, title, trasparenza, posizione) {
    var th__=this;
    var bbol=false;
    if(typeof th__.nomeGruppoSelect==="undefined")
    {  
    	appUtil.showMessageAlertApplication("Seleziona un Gruppo", "INFO", false, false, true);
         
        return this;
    }
    for(var i in th__.groups.get(th__.nomeGruppoSelect))
    {
        if(th__.groups.get(th__.nomeGruppoSelect)[i].nomeLayer===name)
        {
            bbol=true;
            appUtil.showMessageAlertApplication("Non si possono inserire livelli con lo stesso nome per la tavola scelta", "INFO", false, true, false);
        } 
    }
    if(th__.groups.get(th__.nomeGruppoSelect) === undefined) {

        appUtil.showMessageAlertApplication("Seleziona un Gruppo", "INFO", false, false, true);
         
        return this;

    }
    if(!bbol){
    th__.groups.get(th__.nomeGruppoSelect).push({
            "isNew": false,
            "id": 0,
            "idMappa": th__.pkMappa,
            "nomeLayer": name,
            "titleLayer": title,
            "idParent": th__.nomeGruppoSelect,
            "tipo": "L",
            "abilitato": false,
            "trasparenza": trasparenza != null || trasparenza != undefined ? trasparenza : "10",
            "campo1": null,
            "campo2": null,
            "campo3": null,
            "pos": posizione != null || posizione != undefined ? posizione : 1
        })

        var layer = this.instanceLayer(name, title, th__.nomeGruppoSelect)
        this.insertItemHtmlLayer(layer);
        
        $('#check_all_' + th__.nomeGruppoSelect).show();

        th__.refreshGroupSelected();
        th__.updateAbility(name, th__.nomeGruppoSelect, th__.pkMappa, th__.nomeGruppoReplaceSelect + '_' + name + '_chk', th__.nomeGruppoReplaceSelect + '_' + name + '_chkImg');
        th__.checkEvent();
    }
}

JBoxGroup.prototype.deleteLayer = function (nomeLayer,idMappa,idParent,idBox) {
    var th__=this;   
    if(typeof th__.nomeGruppoSelect==="undefined")
    {  
    	 appUtil.showMessageAlertApplication("Seleziona un Gruppo", "INFO", false, false, true);
        
        return this;
    }

    for(var i in th__.groups.get(th__.nomeGruppoSelect))
    {
        if(th__.groups.get(th__.nomeGruppoSelect)[i].nomeLayer===nomeLayer)
        {
            th__.groups.get(th__.nomeGruppoSelect).splice(i,1);
            $("#"+idBox).html("").remove();
            break;
        }
    }

    if(th__.groups.get(th__.nomeGruppoSelect).length != 0) {

        let isAllChecked = 0;

        $('i[data-gruppo="'+th__.nomeGruppoReplaceSelect+'"]').each(function(e) {
            if(!$(this).hasClass('fa fa-check')) {
                isAllChecked = 1;
                return false;
            }
        });

        if(isAllChecked == 0) {
            $('#check_all_' + th__.nomeGruppoReplaceSelect).prop('checked', true);
        } else {
            $('#check_all_' + th__.nomeGruppoReplaceSelect).prop('checked', false);
        }

    } else {

        $('#check_all_' + th__.nomeGruppoReplaceSelect).hide();
        $('#check_all_' + th__.nomeGruppoReplaceSelect).prop('checked', false);

    }

    this.fnDeleteLayer(nomeLayer,idMappa,idParent,idBox); 
    	
}

JBoxGroup.prototype.hideOpacity= function (layerName) {
if(!$("#"+layerName+"_divgear").hasClass("hidden")) {
   $("#"+layerName+"_divgear").toggleClass("hidden");
}
}

JBoxGroup.prototype.showOpacity=function(layerName){
$("#"+layerName+"_divgear").toggleClass("hidden");
        $("#"+layerName+"_slider").slider({
                formatter: function(value) {
                        return 'Current value: ' + value;
                }
        });
}

JBoxGroup.prototype.changeOpacity=function(layerName,idParent,idMappa, itemCheckId){
     var th__ = this; 
    var valueOpacity = $("#"+itemCheckId+"_slider").val();
     $("#"+itemCheckId+"_value-opa").html((Number(valueOpacity)*10)+"%");
     this.fnChangeOpacity(layerName,idParent,idMappa, itemCheckId);   
      if(typeof th__.nomeGruppoSelect==="undefined")
    {  
         
        appUtil.showMessageAlertApplication("Seleziona un Gruppo", "INFO", false, false, true);
        
        return this;
    }
    for(var i in th__.groups.get(th__.nomeGruppoSelect))
    {
        if(th__.groups.get(th__.nomeGruppoSelect)[i].nomeLayer===layerName)
        {
           th__.groups.get(th__.nomeGruppoSelect)[i].trasparenza=""+valueOpacity+"";
            
            break;
        }
    }
}

JBoxGroup.prototype.updateAbility=function(nomeLayer,idParent,idMappa,idCheckbox,idIClass){
     var th__ = this;
     var chek=true;
    if($('#' + th__.nomeGruppoReplaceSelect + '_' + nomeLayer + '_chkImg').hasClass('fa fa-check')){
        
        $('#'+idCheckbox).prop('checked', false);
        $("#"+idIClass).removeClass("fa fa-check");
        chek=false;

        $('#check_all_' + th__.nomeGruppoReplaceSelect).prop('checked', false);

    }else{
        $('#'+idCheckbox).prop('checked', true);
        $("#"+idIClass).addClass("fa fa-check");
        chek=true;
            
    }
    if(typeof th__.nomeGruppoSelect==="undefined")
    {  
         
        appUtil.showMessageAlertApplication("seleziona un Gruppo", "INFO", false, false, true);
        
        return this;
    }
    for(var i in th__.groups.get(th__.nomeGruppoSelect))
    {
        if(th__.groups.get(th__.nomeGruppoSelect)[i].nomeLayer===nomeLayer)
        {
           th__.groups.get(th__.nomeGruppoSelect)[i].abilitato=chek;
            
            break;
        }
    }
    
    let isAllChecked = 0;

        $('i[data-gruppo="'+th__.nomeGruppoReplaceSelect+'"]').each(function(e) {
            if(!$(this).hasClass('fa fa-check')) {
                isAllChecked = 1;
                return false;
            }
        });

        if(isAllChecked == 0) {
            $('#check_all_' + th__.nomeGruppoReplaceSelect).prop('checked', true);
        }
    
    th__.fnUpdateAbility(nomeLayer,idParent,idMappa,idCheckbox,idIClass);
}
 
JBoxGroup.prototype.showLegend=function(layerName){
 $("#"+layerName+"_divlegend").toggleClass("hidden");
}

JBoxGroup.prototype.refreshGrups = function () {
	  var th__ = this;
	  th__.boxGruppiLayer.html("");
	  
    (function(){
    	th__.initGroup();
        
    	th__.initLayer();	
    })()

  
       
};

JBoxGroup.prototype.loadGrups = function () {
    var th__ = this;
    var UMappa = {};
    UMappa.isNew = true;
    UMappa.id = th__.pkMappa;

    $.ajax({
        type: th__.ajaxSetting.type,
        dataType:th__.ajaxSetting.dataType,
        contentType: th__.ajaxSetting.contentType,
        url: th__.ajaxSetting.url, 
        data: th__.ajaxSetting.data,
        cache: th__.ajaxSetting.cache,
        async: false,
        success: function (data) {
        	th__.checkIntDatiBase(data.aaData);
        },
        error: function (response) {

        }
    });
    
    return th__;
}

JBoxGroup.prototype.dialogGrupp={dialog:function(){}}; 
JBoxGroup.prototype.showAddGruppo=function(){
	var th__=this;
        
    var templ='<div class="form-group col-xs-12 col-md-12" style="padding: 20px 15px;">'
    	+'<label for="nomeGruppo">Nome Tavola</label>'
    	+'<div class="input-group">'
    	+'<input type="text" style="font-size: 1rem;" class="form-control" id="nomeGruppo" value="" placeholder="Nome Tavola">'
    	+'</div>'     
    	+'</div>'
    	
    	 th__.dialogGrupp =$('<div style="vertical-align:middle; "></div>').html(templ)
    	 th__.dialogGrupp.dialog({
	    	classes: {'ui-dialog':'ui-dialog modale-messina'},
	    	position: { my : "center", at : "center", of : window },
	    	title:"Nuova Tavola",
	        height: 185,
	        width: 320,
	        modal: true,
	        autoOpen: false,
	        modal: true,
	        draggable: true,
	        resizable: false,
	        close: function( event, ui ) {
	         th__.dialogGrupp.dialog( "destroy" );	
	        },
	        buttons: {
	        	"Crea Gruppo":function(){
                            th__.addGroup();
                          $(this).dialog( "close" );  
                        },
	            "Chiudi": function() {
	                $(this).dialog( "close" );
	            }
	        }
});

    this.nomeGruppoSelect = undefined;
    this.nomeGruppoReplaceSelect = undefined;
    th__.dialogGrupp.dialog( "open" );
    			
};


JBoxGroup.prototype.dialogModificaGrupp={dialog:function(){}};
JBoxGroup.prototype.openUpdateGruppo=function(idMappa,idParent,idbox,idpar_replace) {
	var th__=this;
    
	var templ='<div class="form-group col-xs-12 col-md-12" style="padding: 20px 15px;">'
    	+'<label for="nomeGruppo">Nome Tavola</label>'
    	+'<div class="input-group">'
    	+'<input type="text" style="font-size: 1rem;" class="form-control" id="newIdParentGruppo" value="'+idParent+'" placeholder="Nome Tavola">'
    	+'<input type="hidden" id="idMappaGruppo" value="'+idMappa+'" >'
    	+'<input type="hidden" id="idParentGruppo" value="'+idParent+'" >'
    	+'<input type="hidden" id="idBox" value="'+idbox+'" >'
    	+'</div>'     
    	+'</div>'
    	
    	th__.dialogModificaGrupp =$('<div style="vertical-align:middle; "></div>').html(templ)
    	 th__.dialogModificaGrupp.dialog({
	    	classes: {'ui-dialog':'ui-dialog modale-messina'},
	    	position: { my : "center", at : "center", of : window },
	    	title:"Modifica",
	        height: 185,
	        width: 320,
	        modal: true,
	        autoOpen: false,
	        modal: true,
	        draggable: true,
	        resizable: false,
	        close: function( event, ui ) {
	        th__.dialogModificaGrupp.dialog( "destroy" );	
		        },
	        buttons: {
	        	"Modifica Gruppo":function(){
                          th__.updateGroup($("#newIdParentGruppo").val(),idParent);
                          $(this).dialog( "close" );   
                        },
	            "Chiudi": function() {
	                $(this).dialog( "close" );
	            }
	        }
});
    th__.dialogModificaGrupp.dialog( "open" );
    			

 };
 
JBoxGroup.prototype.dialogPermessi={dialog:function(){}};
JBoxGroup.prototype.modalePermessi=function(html,nomeLayer,callSave) {
		var th__=this;
 
	    	
	    	th__.dialogPermessi =$('<div style="vertical-align:middle; "></div>').html(html)
	    	 th__.dialogPermessi.dialog({
		    	classes: {'ui-dialog':'ui-dialog modale-messina'},
		    	position: { my : "center", at : "center", of : window },
		    	title:"Permessi - " + nomeLayer,
		        height: 385,
		        width: 750,
		        modal: true,
		        autoOpen: false,
		        modal: true,
		        draggable: true,
		        resizable: true,
		        close: function( event, ui ) {
		        th__.dialogPermessi.dialog( "destroy" );	
			        },
		        buttons: {
		        	"Salva":function(){
		        		if(typeof callSave==="function")
		        			callSave.apply(this,th__.dialogPermessi);
	                        },
		            "Chiudi": function() {
		                $(this).dialog( "close" );
		            }
		        }
	});
	    th__.dialogPermessi.dialog( "open" );
	    			

};

JBoxGroup.prototype.showModalePermessi=function(id, nomeLayer,idParent,idMappa){
	 var th__=this;
	 if(typeof th__.fnShowPermessi==="function")
	 th__.fnShowPermessi.apply(th__,[id, nomeLayer,idParent,idMappa]);
}

JBoxGroup.prototype.getTemplateGrup=function(ist){

var templateGrupp = "<div onclick=\"JBoxGroup.instance['"+ist+"'].setSelectedGroup('{{idParent}}','{{groupName}}')\" class=\"gestMappe panel-group p10 clearfix\" id=\"accordion_{{idParent}}\" data-accordion=\"accordionMenu\" role=\"tablist\" aria-multiselectable=\"true\">\n" +
        "<input type=\"hidden\" id=\"current_{{idParent}}\" value=\"{{groupName}}\">\n" +
        "<div class=\"panel panel-default\">\n" +
        "	<div class=\"panel-heading gruppo-layer\" role=\"tab\" id=\"heading_{{idParent}}\">\n" +
        "		<h4 class=\"panel-title\">\n" +
        "			<a id=\"visualizzatore{{idParent}}\" role=\"button\" data-toggle=\"collapse\" data-parent=\"#accordion_{{idParent}}\" href=\"#layers{{idParent}}\" aria-expanded=\"true\" aria-controls=\"collapse{{idParent}}\" title=\"Chiudi gruppo layer\" style=\"width: 70%; margin-left: 25px\">\n" +
        "				{{groupName}}\n" +
        "			</a>\n" +
        "           <div class=\"toolbar\">\n" +
        "               <div class=\"btn-group boxGroup\" title=\"Seleziona {{groupName}}\" style=\"display: block; position: static; float: left;margin-top: 6px;margin-left: -74%;\">\n"+
        "                   <input id=\"check_all_{{idParent}}\" type=\"checkbox\" style=\"display: block; width: 20px;\" href=\"javascript:void(0)\" onclick=\"JBoxGroup.instance['"+ist+"'].selectAllGroup('{{idMappa}}', '{{groupName}}', '{{idParent}}', '{{nomeLayer}}')\" disabled>\n" +
        "               </div>\n"+
        "               <div class=\"btn-group boxGroup\" title=\"Opzioni\" style=\"display: block; position: static; float: right;margin-top: 0;margin-right: 0px;\">\n" +
       
        "		            <button onclick=\"JBoxGroup.instance['"+ist+"'].moveGroupDown('{{groupName}}')\" type=\"button\" class=\"fa fa-arrow-circle-o-down\" style=\"line-height: 20px;border:0px!important; background: transparent;color: #4b4b4b;\" title=\"DOWN\">\n" +
        "		            </button>\n" +          
        "		            <button onclick=\"JBoxGroup.instance['"+ist+"'].moveGroupUP('{{groupName}}')\" type=\"button\" class=\"fa fa-arrow-circle-o-up\" style=\"line-height: 20px;border:0px!important; background: transparent;color: #4b4b4b;\" title=\"UP\">\n" +
        "		            </button>\n" + 
        
        "	            <button type=\"button\" class=\"fa fa-ellipsis-v\" id=\"dropdownMenuButton\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\" title=\"Opzioni layer\" style=\"line-height: 20px;border:0px!important; background: transparent;color: #4b4b4b;\" {{isDatibase groupName}}>\n" +
        "	            </button>\n" +
        "	            <div class=\"dropdown-menu\" style=\"\" aria-labelledby=\"dropdownMenuButton\">\n" +
        "			        <a style=\"color: #000;font-size: 14px;font-weight: 100;\" class=\"dropdown-item\" href=\"javascript:void(0)\" onclick=\"JBoxGroup.instance['"+ist+"'].deleteGroup('{{idMappa}}','{{groupName}}','accordion_{{idParent}}','{{idParent}}')\" title=\"Elimina\">\n" +
        "				        <i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i>\n" +
        "				        Elimina\n" +
        "			        </a>\n" +
        "		            <a style=\"color: #000;font-size: 14px;font-weight: 100;\" class=\"dropdown-item\" href=\"javascript:void(0)\" onclick=\"JBoxGroup.instance['"+ist+"'].openUpdateGruppo('{{idMappa}}','{{groupName}}','visualizzatore{{idParent}}','{{idParent}}')\" title=\"Modifica\">\n" +
        "			            <i class=\"fa fa fa-cog\" aria-hidden=\"true\"></i>\n" +
        "			            Modifica\n" +
        "		            </a>\n" +
        "	            </div>\n" +        
        "</div>\n" +
        "</div>\n" +
        "	</h4>\n" +
        "	</div>\n" +
        "	<div id=\"layers{{idParent}}\" class=\"panel-collapse collapse\" role=\"tabpanel\" aria-labelledby=\"heading_{{idParent}}\">\n" +
        "		<div id=\"default-layer-{{idParent}}\" class=\"panel-body p7\">\n" +
        "		</div>\n" +
        "	</div>\n" +
        "</div>";

return templateGrupp;

}


JBoxGroup.prototype.getTemplateLayer=function(ist){

var templateLayer = "<div id=\"itemViewer_{{nomeLayer}}_{{idParent}}\" class=\"itemViewerContainer\">	\n" +
        "<div id=\"{{nomeLayer}}_divchk\" class=\"checkbox itemVisualizzatore row-box-mapp\">\n" +
        "<label id=\"{{idParent}}_{{nomeLayer}}_chkLabel\">\n" +
        "	<input id=\"{{idParent}}_{{nomeLayer}}_chk\" data-gruppo=\"{{idParent}}\" type=\"checkbox\" onclick=\"JBoxGroup.instance['"+ist+"'].updateAbility('{{nomeLayer}}','{{idParentNonReplace}}', '{{idMappa}}','{{idParent}}_{{nomeLayer}}_chk','{{idParent}}_{{nomeLayer}}_chkImg')\" value=\"\" disabled></input>\n" +
        "	<span class=\"cr\" title=\"{{titleLayer}}\"><i id=\"{{idParent}}_{{nomeLayer}}_chkImg\" data-gruppo=\"{{idParent}}\" class=\"{{isAbilitato abilitato}}\"></i></span>\n" +
        "</label>\n" +
        "<span class=\"layerName\" title='{{titleLayer}}'>{{titleLayer}}</span>\n" +
        "<div class=\"toolbar\">\n" +
        "	<div class=\"btn-group hidden\" title=\"Legenda\">		\n" +
        "		<button type=\"button\" class=\"btn btn-secondary iconLegend\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"\n" +
        "				onclick=\"JBoxGroup.instance['"+ist+"'].showLegend('{{idParent}}_{{nomeLayer}}'\" disabled>\n" +
        "			<i class=\"fa fa-file-image-o\" aria-hidden=\"true\"></i>\n" +
        "		</button>\n" +
        "	</div>\n" +
        "	<div class=\"btn-group\" title=\"Opzioni\" style=\"width: 80px;\">\n" +
        "		<button onclick=\"JBoxGroup.instance['"+ist+"'].moveDown('{{nomeLayer}}')\" type=\"button\" class=\"fa fa-arrow-circle-o-down\" title=\"DOWN\">\n" +
        "		</button>\n" +          
        "		<button onclick=\"JBoxGroup.instance['"+ist+"'].moveUP('{{nomeLayer}}')\" type=\"button\" class=\"fa fa-arrow-circle-o-up\" title=\"UP\">\n" +
        "		</button>\n" +        
        "		<button type=\"button\" class=\"fa fa-ellipsis-v\" data-toggle=\"dropdown\" id=\"option\" aria-haspopup=\"true\" aria-expanded=\"false\" title=\"Opzioni layer\">\n" +
        "		</button>\n" +
        "		<div class=\"dropdown-menu dropdown-menu-right\" aria-labelledby=\"option\">\n" +
        "		{{#if (isDefault idParentNonReplace)}}\n" +			
        "				<a  class=\"dropdown-item\" href=\"javascript:void(0)\" onclick=\"JBoxGroup.instance['"+ist+"'].deleteLayer('{{nomeLayer}}','{{idMappa}}','{{idParentNonReplace}}','itemViewer_{{nomeLayer}}_{{idParent}}')\" title=\"Elimina\">\n" +
        "					<i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i>\n" +
        "					Elimina\n" +
        "				</a>\n" +
        "		{{/if}} \n" +	
        "			 \n" +
        "			<a class=\"dropdown-item\" href=\"javascript:void(0)\" onclick=\"JBoxGroup.instance['"+ist+"'].showOpacity('{{idParent}}_{{nomeLayer}}')\" title=\"Trasparenza\">\n" +
        "				<i class=\"fa fa fa-cog\" aria-hidden=\"true\"></i>\n" +
        "				Trasparenza\n" +
        "			</a>\n" +
        "		</div>\n" +
        
		"       <button type=\"button\" class=\"fa fa-file-image-o\" id=\"legenda\" aria-haspopup=\"true\" aria-expanded=\"false\" title=\"Legenda\" style=\"line-height: 20px;border:0px!important; background: transparent;color: #4b4b4b;\" {{isDatibase groupName}}" + 
		" onclick=\"appUrbamid.showLegend('{{idParent}}_{{nomeLayer}}_chk', appUtil.getOrigin()+appConfig.geoserverService+appConfig.wmsService+'?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER='+appConfig.workspaceGeoserver+':{{nomeLayer}}', '{{titleLayer}}')\">\n" +
        
        "	</div>\n" +
        "</div>\n" +
        "</div>\n" +
        "<div id=\"{{idParent}}_{{nomeLayer}}_divlegend\" class=\"hidden itemLegenda\">\n" +
        "	<span>Legenda</span>\n" +
        "	<!--img src=\"hrefLegend\"></img-->\n" +
        "</div>\n" +
        "<div id=\"{{idParent}}_{{nomeLayer}}_divgear\" class=\"hidden itemGear\">\n" +
        "	<span class=\"title-opa\">Trasparenza</span>\n" +
        "	<span id=\"{{idParent}}_{{nomeLayer}}_value-opa\" class=\"value-opa\" title=\"Valore trasparenza\">{{valoreTrasparenza trasparenza}}%</span>\n" +
        "	<span class=\"close-opa\"><i class=\"fa fa-times\" onclick=\"JBoxGroup.instance['"+ist+"'].hideOpacity('{{idParent}}_{{nomeLayer}}')\" id=\"catalogoClose\" data-info=\"Chiudi Catalogo\"></i></span>\n" +
        "	<input type=\"range\" title=\"Modifica trasparenza layer\" class=\"custom-range\" id=\"{{idParent}}_{{nomeLayer}}_slider\"  min=\"0\" max=\"10\" value=\"{{trasparenza}}\" onchange=\"JBoxGroup.instance['"+ist+"'].changeOpacity('{{nomeLayer}}','{{idParentNonReplace}}','{{idMappa}}','{{idParent}}_{{nomeLayer}}')\">\n" +
        "</div>\n" +
        "</div>";

return templateLayer;

};

Map.prototype.obj={};

Map.prototype.getGrups=function(){ 
var th__=this; 
th__.obj={};
th__.forEach(function(v,k,m){
th__.obj[k]=v;
})
return th__; 
};
JBoxGroup.prototype.arymoveDown=function(nGroup,array) {
	var th__ = this;
    var current=0;
    for(var i in array)
    {
        if(array[i][0]===nGroup)
        {
        	current=i;
        	break;
        }
    }
    	var intDown=Number(current)+1;
    	if(intDown>=array.length)
    	intDown=Number(0);

    	array.splice(intDown, 0, array.splice(current, 1)[0]);

    return array;  
};
JBoxGroup.prototype.arymoveUP=function(nGroup,array) {
	var th__ = this;
    var current=0;
    for(var i in array)
    {
        if(array[i][0]===nGroup)
        {
        	current=i;
        	break;
        }
    }
    var	intUp=Number(current)-1;
	if(intUp<0)
	intUp=Number(array.length);

	array.splice(intUp, 0, array.splice(current, 1)[0]);

    return array;  
};

JBoxGroup.get = function (name, object) {
    var n, o;
    if (typeof (arguments[0]) === "object")
        o = arguments[0];
    else if (typeof (arguments[0]) === "string")
        n = arguments[0];
    if (typeof (arguments[1]) === "object")
        o = arguments[1];
    else if (typeof (arguments[1]) === "string")
        n = arguments[1];
    if (typeof (n) === "undefined")
        n = new Date().getTime();

    if (typeof (JBoxGroup.instance[n]) === "undefined")
        JBoxGroup.instance[n] = new JBoxGroup(n,o);
    return JBoxGroup.instance[n];
};

/*Polyfill - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/entries */
if (!Object.entries) {
	  Object.entries = function( obj ){
	    var ownProps = Object.keys( obj ),
	        i = ownProps.length,
	        resArray = new Array(i);
	    while (i--)
	      resArray[i] = [ownProps[i], obj[ownProps[i]]];
	    
	    return resArray;
	  };
}

/*Polyfill - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/keys */
if (!Object.keys) {
	  Object.keys = (function() {
	    'use strict';
	    var hasOwnProperty = Object.prototype.hasOwnProperty,
	        hasDontEnumBug = !({ toString: null }).propertyIsEnumerable('toString'),
	        dontEnums = [
	          'toString',
	          'toLocaleString',
	          'valueOf',
	          'hasOwnProperty',
	          'isPrototypeOf',
	          'propertyIsEnumerable',
	          'constructor'
	        ],
	        dontEnumsLength = dontEnums.length;

	    return function(obj) {
	      if (typeof obj !== 'function' && (typeof obj !== 'object' || obj === null)) {
	        throw new TypeError('Object.keys called on non-object');
	      }

	      var result = [], prop, i;

	      for (prop in obj) {
	        if (hasOwnProperty.call(obj, prop)) {
	          result.push(prop);
	        }
	      }

	      if (hasDontEnumBug) {
	        for (i = 0; i < dontEnumsLength; i++) {
	          if (hasOwnProperty.call(obj, dontEnums[i])) {
	            result.push(dontEnums[i]);
	          }
	        }
	      }
	      return result;
	    };
	  }());
}
/*Polyfill - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/from */
if (!Array.from) {
	  Array.from = (function () {
	    var toStr = Object.prototype.toString;
	    var isCallable = function (fn) {
	      return typeof fn === 'function' || toStr.call(fn) === '[object Function]';
	    };
	    var toInteger = function (value) {
	      var number = Number(value);
	      if (isNaN(number)) { return 0; }
	      if (number === 0 || !isFinite(number)) { return number; }
	      return (number > 0 ? 1 : -1) * Math.floor(Math.abs(number));
	    };
	    var maxSafeInteger = Math.pow(2, 53) - 1;
	    var toLength = function (value) {
	      var len = toInteger(value);
	      return Math.min(Math.max(len, 0), maxSafeInteger);
	    };

	    return function from(arrayLike/*, mapFn, thisArg */) {
	      var C = this;	     
	      var items = Object(arrayLike);

	      if (arrayLike == null) {
	        throw new TypeError('Array.from requires an array-like object - not null or undefined');
	      }
	      var mapFn = arguments.length > 1 ? arguments[1] : void undefined;
	      var T;
	      if (typeof mapFn !== 'undefined') {
	       
	        if (!isCallable(mapFn)) {
	          throw new TypeError('Array.from: when provided, the second argument must be a function');
	        }
	        if (arguments.length > 2) {
	          T = arguments[2];
	        }
	      }
	      var len = toLength(items.length);
	      var A = isCallable(C) ? Object(new C(len)) : new Array(len);
	      var k = 0;
	      var kValue;
	      while (k < len) {
	        kValue = items[k];
	        if (mapFn) {
	          A[k] = typeof T === 'undefined' ? mapFn(kValue, k) : mapFn.call(T, kValue, k);
	        } else {
	          A[k] = kValue;
	        }
	        k += 1;
	      }
	      
	      A.length = len;
	    
	      return A;
	    };
	  }());
}
/*Handlebars*/
Handlebars.registerHelper('isPubblicato', function (stato) {
    return stato=="P"?"selected":"";
});
Handlebars.registerHelper('isDisabilitato', function (stato) {
    return stato=="D"?"selected":"";
}); 
Handlebars.registerHelper('isMappaPredefinita', function (mappaPredefinita) {
    return mappaPredefinita?"checked":"";
}); 
Handlebars.registerHelper('isNewMapp', function (value) {
	  return value !== true;
	});
Handlebars.registerHelper('isAbilitato', function (value) {
	  return value?"fa fa-check":"";
    });
Handlebars.registerHelper('isAbilitatoLayer', function(value) {
      return value ? "checked" : "";
    })
Handlebars.registerHelper('valoreTrasparenza', function (value) {
	if(value && value=="" || value==null || typeof value==="undefined")
		value=0;
	return  Number(value)*10;
	});
Handlebars.registerHelper('isDatibase', function (value) {
	if(value && value==="DATI DI BASE")
	return  " disabled";
	return "";	
});

Handlebars.registerHelper('isDefault', function (value) {
	if(value && value==="DATI DI BASE")
	return  false;
	return true;	
});

Handlebars.registerHelper('blockedMappa', function(value) {
    if(value) {
        return "disabled"
    } else {
        return "";
    }
});
