import os
import json
from qgis.core import *
from load_cxf_memory import *
from reader_file_cxf import *
from layerutil import *
from qgis.PyQt.QtCore import QVariant

def memory_layer(self):
    
    QgsApplication.setPrefixPath('/usr', True)
    qgs = QgsApplication([], False)
    qgs.initQgis()
    
# ==========================================================================================

    CRS_ESPG_4326 = QgsCoordinateReferenceSystem("EPSG:4326")
    if CRS_ESPG_4326.isValid():
        print("VALID CRS!")       
    else:
         print("Invalid CRS!")
        
    CRS_4326_AS_STRING = "crs=" + CRS_ESPG_4326.toWkt()
    
    #
    #
    
    CRS_ESPG_MONTEMARIO = QgsCoordinateReferenceSystem("EPSG:3004")
    
    CRS_MONTEMARIO_AS_STRING = "crs=" + CRS_ESPG_MONTEMARIO.toWkt()
    
    #
    #
    
    CRS_CASSINI = QgsCoordinateReferenceSystem()
    CRS_CASSINI.createFromUserInput('+proj=cass +lat_0=37.751236 +lon_0=14.994026 +x_0=758.41 +y_0=-1487.132 +ellps=WGS84 +units=m +no_defs')
    
    CRS_CASSINI_AS_STRING = "crs=" + CRS_CASSINI.toWkt()
     
    if not CRS_CASSINI_AS_STRING or not CRS_MONTEMARIO_AS_STRING:     
        print("Errore : CRS NON SELEZIONATO!") 
        return   
        
# ==========================================================================================
          
    cmdargs = str(sys.argv)
    print ("Lista parametri in ingresso: %s " % cmdargs)
    
    if not sys.argv:
        print("Errore : LISTA PARAMETRI IN INGRESSO VUOTA!") 
        return 
    
    # Pharsing args one by one 
    PATH_SOURCE_CS = str(sys.argv[1])
    PATH_SOURCE_GB = str(sys.argv[2])
    PATH_OUTPUT_SHP = str(sys.argv[3])
    PREFIX_SHAPEFILE = str(sys.argv[4])
  
    #PATH_STYLE = '/opt/Catasto/_file/style/QML_Default/'
   
    if not PATH_SOURCE_CS:
        PATH_SOURCE_CS = '/home/fesposit/Lavoro/git_repository/URBAMID/URBAMID/CXFToShape/_file/sourceFile/CS'
    
    if not PATH_SOURCE_GB:
        PATH_SOURCE_GB = '/home/fesposit/Lavoro/git_repository/URBAMID/URBAMID/CXFToShape/_file/sourceFile/GB'
   
    if not PATH_OUTPUT_SHP:
        PATH_OUTPUT_SHP = '/opt/CXFToShape/_file/outputFile/'

    if not PREFIX_SHAPEFILE:
        PREFIX_SHAPEFILE = "";
        
# ==========================================================================================
       
    print("==========================================================================================")
    print("INIZIO - Elaborazione dei file con Proj:'Cassini'")

    ELAB_FILENAME_CS = []
    # r=root, d=directories, f = files
    for r, d, f in os.walk(PATH_SOURCE_CS):
        for file in f:
            if '.cxf' in file:
                ELAB_FILENAME_CS.append(os.path.join(r, file))
        
    
    #print("Elenco file con Proj:'Cassini' che saranno elaborati: ")
    #for f in ELAB_FILENAME_CS: print(f)

    vl_conf_cs  = createLayerConfine(CRS_CASSINI_AS_STRING)
    vl_part_cs  = createLayerParticelle(CRS_CASSINI_AS_STRING)
    vl_ed_cs    = createLayerFabbricati(CRS_CASSINI_AS_STRING)
    vl_st_cs    = createLayerStrade(CRS_CASSINI_AS_STRING)  
    vl_aq_cs    = createLayerAcque(CRS_CASSINI_AS_STRING) 
    vl_linee_cs = createLayerLinee(CRS_CASSINI_AS_STRING)
    vl_point_cs = createLayerSimboli(CRS_CASSINI_AS_STRING)
    vl_txt_cs   = createLayerTesti(CRS_CASSINI_AS_STRING)
    vl_fidu_cs  = createLayerFiduciali(CRS_CASSINI_AS_STRING)
 
    reader_file_cxf(qgs, ELAB_FILENAME_CS, vl_aq_cs, vl_st_cs, vl_conf_cs, vl_part_cs, vl_txt_cs, vl_fidu_cs, vl_ed_cs, vl_point_cs, vl_linee_cs)

    print("FINE - Elaborazione dei file con Proj:'Cassini'")
    print("==========================================================================================")
    
    print("==========================================================================================")
    print("INIZIO - Elaborazione dei file con Proj:'Gauss'")

    ELAB_FILENAME_GB = []
    # r=root, d=directories, f = files
    for r, d, f in os.walk(PATH_SOURCE_GB):
        for file in f:
            if '.cxf' in file:
                ELAB_FILENAME_GB.append(os.path.join(r, file))
    
    #print("Elenco file con Proj:'Gauss' che saranno elaborati: ")
    #for f in ELAB_FILENAME_GB: print(f)

    vl_conf_gb  = createLayerConfine(CRS_MONTEMARIO_AS_STRING)
    vl_part_gb  = createLayerParticelle(CRS_MONTEMARIO_AS_STRING)
    vl_ed_gb    = createLayerFabbricati(CRS_MONTEMARIO_AS_STRING)
    vl_st_gb    = createLayerStrade(CRS_MONTEMARIO_AS_STRING)  
    vl_aq_gb    = createLayerAcque(CRS_MONTEMARIO_AS_STRING) 
    vl_linee_gb = createLayerLinee(CRS_MONTEMARIO_AS_STRING)
    vl_point_gb = createLayerSimboli(CRS_MONTEMARIO_AS_STRING)
    vl_txt_gb   = createLayerTesti(CRS_MONTEMARIO_AS_STRING)
    vl_fidu_gb  = createLayerFiduciali(CRS_MONTEMARIO_AS_STRING)
 
    reader_file_cxf(qgs, ELAB_FILENAME_GB, vl_aq_gb, vl_st_gb, vl_conf_gb, vl_part_gb, vl_txt_gb, vl_fidu_gb, vl_ed_gb, vl_point_gb, vl_linee_gb)

    print("FINE - Elaborazione dei file con Proj:'Gauss'")
    print("==========================================================================================")
    
# ==========================================================================================
    
    fields = QgsFields()
    atts = [
         { 'name': 'Codice_comune', 'type': QVariant.String, 'len': 50 },
         { 'name': 'Foglio', 'type':QVariant.String, 'len': 5 },
         { 'name': 'Mappale', 'type': QVariant.String, 'len': 5 },
         { 'name': 'Allegato', 'type': QVariant.String, 'len': 5 },
         { 'name': 'Sviluppo', 'type': QVariant.String, 'len': 5 },
         { 'name': 'Htxt', 'type': QVariant.Double },
         { 'name': 'Rtxt', 'type': QVariant.Double },
         { 'name': 'Xtxt', 'type': QVariant.Double },
         { 'name': 'Ytxt', 'type': QVariant.Double }
    ]

    #MODELLO 1
    #for fLay in vl_conf_out.fields():
    #    fields.append(fLay)
    
    #MODELLO 2
    #for field in provider.fields():
    #    if field.type() == 10
    #        fields.append(QgsField(field.name(), QVariant.String, len=field.length()))
    #    if field.type() == 5
    #        fields.append(QgsField(field.name(), QVariant.Double, len=field.length()))    
    
    #MODELLO 3
    #for field in vl_conf_out.dataProvider().fields():
    #    fields.append(QgsField(field.name(), field.type(), field.typeName(), field.length()))
    
    #MODELLO 4 mancano informazioni
    #fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    #fields.append(QgsField("Foglio", QVariant.String))
    #fields.append(QgsField("Mappale", QVariant.String))
    #fields.append(QgsField("Allegato", QVariant.String))
    #fields.append(QgsField("Sviluppo", QVariant.String))
    #fields.append(QgsField("Htxt", QVariant.Double))
    #fields.append(QgsField("Rtxt", QVariant.Double))
    #fields.append(QgsField("Xtxt", QVariant.Double))
    #fields.append(QgsField("Ytxt", QVariant.Double))
    
    #MODELLO 5  mancano informazioni su confine, strade, simboli
    #for att in atts:
    #    field = QgsField( att['name'], att['type'] )
    #    if 'len' in att:
    #        field.setLength( att['len'])
    #    fields.append( field )

    #CRS transformation
    print("==========================================================================================")
    print("INIZIO CONFINE")
    print("INIZIO - Trasformazione geometrie CONFINE")
    print(">>> devo aggiungere vl_conf_cs",vl_conf_cs.featureCount())
    vl_conf_out_list = projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_conf_cs.getFeatures())
    del vl_conf_cs
    print(">>> devo aggiungere vl_conf_gb",vl_conf_gb.featureCount())
    vl_conf_out_list.extend( projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_conf_gb.getFeatures()) )
    del vl_conf_gb
    print(">>> ho collezionato queste feature", len(vl_conf_out_list))

    vl_conf_out = QgsVectorLayer("Polygon?"+CRS_4326_AS_STRING+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Confine", "memory")  
    vl_conf_out.startEditing()
    for f in vl_conf_out_list:
        vl_conf_out.addFeature(f)
    vl_conf_out.commitChanges()
    vl_conf_out.updateFields()
    provider = vl_conf_out.dataProvider()
    print("FINE - Trasformazione geometrie CONFINE")
    print("INIZIO - Scrittura file CONFINE")
    fields = QgsFields()
    fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    fields.append(QgsField("Foglio", QVariant.String, "string"))
    fields.append(QgsField("Mappale", QVariant.String, "string"))
    fields.append(QgsField("Allegato", QVariant.String, "string"))
    fields.append(QgsField("Sviluppo", QVariant.String, "string"))
    fields.append(QgsField("Htxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Rtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Xtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Ytxt", QVariant.Double, '', 10, 5))
    writer = QgsVectorFileWriter(PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + vl_conf_out.name() + ".shp", "UTF-8", fields, QgsWkbTypes.Polygon, driverName="ESRI Shapefile")
    if writer.hasError() != QgsVectorFileWriter.NoError:
        print("Error when creating shapefile: ", w.errorMessage())
    # add a feature
    for f in vl_conf_out_list:
        fet = QgsFeature()
        fet.setGeometry(f.geometry())
        fet.setAttributes( f.attributes() )
        writer.addFeature(fet)
    # delete the writer to flush features to disk
    del writer
    print("FINE - Scrittura file CONFINE")
    print("FINE CONFINE")
    print("==========================================================================================")
    
    
    #CRS transformation
    print("==========================================================================================")
    print("INIZIO PARTICELLE")
    print("INIZIO - Trasformazione geometrie PARTICELLE")
    print(">>> devo aggiungere vl_part_cs",vl_part_cs.featureCount())
    vl_part_out_list = projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_part_cs.getFeatures())
    del vl_part_cs
    print(">>> devo aggiungere vl_part_gb",vl_part_gb.featureCount())
    vl_part_out_list.extend( projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_part_gb.getFeatures()) )
    del vl_part_gb
    print(">>> ho collezionato queste feature", len(vl_part_out_list))
    vl_part_out = QgsVectorLayer("Polygon?"+CRS_4326_AS_STRING+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Particelle", "memory")  
    vl_part_out.startEditing()
    for f in vl_part_out_list:
        vl_part_out.addFeature(f)
    vl_part_out.commitChanges()
    vl_part_out.updateFields()
    provider = vl_part_out.dataProvider()
    print("FINE - Trasformazione geometrie PARTICELLE")
    print("INIZIO - Scrittura file PARTICELLE")
    fields = QgsFields()
    fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    fields.append(QgsField("Foglio", QVariant.String, "string"))
    fields.append(QgsField("Mappale", QVariant.String, "string"))
    fields.append(QgsField("Allegato", QVariant.String, "string"))
    fields.append(QgsField("Sviluppo", QVariant.String, "string"))
    fields.append(QgsField("Htxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Rtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Xtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Ytxt", QVariant.Double, '', 10, 5))
    writer = QgsVectorFileWriter(PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + vl_part_out.name() + ".shp", "UTF-8", fields, QgsWkbTypes.Polygon, driverName="ESRI Shapefile")
    if writer.hasError() != QgsVectorFileWriter.NoError:
        print("Error when creating shapefile: ", w.errorMessage())
    for f in vl_part_out_list:
        fet = QgsFeature()
        fet.setGeometry(f.geometry())
        fet.setAttributes( f.attributes() )
        writer.addFeature(fet)
    del writer
    print("FINE - Scrittura file PARTICELLE")
    print("FINE PARTICELLE")
    print("==========================================================================================")
    
    #CRS transformation
    print("==========================================================================================")
    print("INIZIO FABBRICATI")
    print("INIZIO - Trasformazione geometrie FABBRICATI")
    print(">>> devo aggiungere vl_ed_cs",vl_ed_cs.featureCount())
    vl_ed_out_list = projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_ed_cs.getFeatures())
    del vl_ed_cs
    print(">>> devo aggiungere vl_ed_gb",vl_ed_gb.featureCount())
    vl_ed_out_list.extend( projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_ed_gb.getFeatures()) )
    del vl_ed_gb
    print(">>> ho collezionato queste feature", len(vl_ed_out_list))
    vl_ed_out = QgsVectorLayer("Polygon?"+CRS_4326_AS_STRING+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Fabbricati", "memory")  
    vl_ed_out.startEditing()
    for f in vl_ed_out_list:
        vl_ed_out.addFeature(f)
    vl_ed_out.commitChanges()
    vl_ed_out.updateFields()
    provider = vl_ed_out.dataProvider()
    print("FINE - Trasformazione geometrie FABBRICATI")
    print("INIZIO - Scrittura file FABBRICATI")
    #MODELLO 3
    fields = QgsFields()
    fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    fields.append(QgsField("Foglio", QVariant.String, "string"))
    fields.append(QgsField("Mappale", QVariant.String, "string"))
    fields.append(QgsField("Allegato", QVariant.String, "string"))
    fields.append(QgsField("Sviluppo", QVariant.String, "string"))
    fields.append(QgsField("Htxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Rtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Xtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Ytxt", QVariant.Double, '', 10, 5))
    writer = QgsVectorFileWriter(PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + vl_ed_out.name() + ".shp", "UTF-8", fields, QgsWkbTypes.Polygon, driverName="ESRI Shapefile")
    if writer.hasError() != QgsVectorFileWriter.NoError:
        print("Error when creating shapefile: ", w.errorMessage())
    for f in vl_ed_out_list:
        fet = QgsFeature()
        fet.setGeometry(f.geometry())
        fet.setAttributes( f.attributes() )
        writer.addFeature(fet)
    del writer
    print("FINE - Scrittura file FABBRICATI")
    print("FINE FABBRICATI")
    print("==========================================================================================")
     
    #CRS transformation
    print("==========================================================================================")
    print("INIZIO STRADE")
    print("INIZIO - Trasformazione geometrie STRADE")
    print(">>> devo aggiungere vl_st_cs",vl_st_cs.featureCount())
    vl_st_out_list = projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_st_cs.getFeatures())
    del vl_st_cs
    print(">>> devo aggiungere vl_st_gb",vl_st_gb.featureCount())
    vl_st_out_list.extend( projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_st_gb.getFeatures()) )
    del vl_st_gb
    print(">>> ho collezionato queste feature", len(vl_st_out_list))
    vl_st_out = QgsVectorLayer("Polygon?"+CRS_4326_AS_STRING+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Strade", "memory")  
    vl_st_out.startEditing()
    for f in vl_st_out_list:
        vl_st_out.addFeature(f)
    vl_st_out.commitChanges()
    vl_st_out.updateFields()
    provider = vl_st_out.dataProvider()
    print("FINE - Trasformazione geometrie STRADE")
    print("INIZIO - Scrittura file STRADE")
    #MODELLO 3
    fields = QgsFields()
    fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    fields.append(QgsField("Foglio", QVariant.String, "string"))
    fields.append(QgsField("Mappale", QVariant.String, "string"))
    fields.append(QgsField("Allegato", QVariant.String, "string"))
    fields.append(QgsField("Sviluppo", QVariant.String, "string"))
    fields.append(QgsField("Htxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Rtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Xtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Ytxt", QVariant.Double, '', 10, 5))
    writer = QgsVectorFileWriter(PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + vl_st_out.name() + ".shp", "UTF-8", fields, QgsWkbTypes.Polygon, driverName="ESRI Shapefile")
    if writer.hasError() != QgsVectorFileWriter.NoError:
        print("Error when creating shapefile: ", w.errorMessage())
    for f in vl_st_out_list:
        fet = QgsFeature()
        fet.setGeometry(f.geometry())
        fet.setAttributes( f.attributes() )
        writer.addFeature(fet)
    del writer
    print("FINE - Scrittura file STRADE")
    print("FINE STRADE")
    print("==========================================================================================")
    
    #CRS transformation
    print("==========================================================================================")
    print("INIZIO ACQUE")
    print("INIZIO - Trasformazione geometrie ACQUE")
    print(">>> devo aggiungere vl_aq_cs",vl_aq_cs.featureCount())
    vl_aq_out_list = projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_aq_cs.getFeatures())
    del vl_aq_cs
    print(">>> devo aggiungere vl_aq_gb",vl_aq_gb.featureCount())
    vl_aq_out_list.extend( projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_aq_gb.getFeatures()) )
    del vl_aq_gb
    print(">>> ho collezionato queste feature", len(vl_aq_out_list)) 
    vl_aq_out = QgsVectorLayer("Polygon?"+CRS_4326_AS_STRING+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Acque", "memory")  
    vl_aq_out.startEditing()
    for f in vl_aq_out_list:
        vl_aq_out.addFeature(f)
    vl_aq_out.commitChanges()
    vl_aq_out.updateFields()
    provider = vl_aq_out.dataProvider()
    print("FINE - Trasformazione geometrie ACQUE")
    print("INIZIO - Scrittura file ACQUE")
    fields = QgsFields()
    fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    fields.append(QgsField("Foglio", QVariant.String, "string"))
    fields.append(QgsField("Mappale", QVariant.String, "string"))
    fields.append(QgsField("Allegato", QVariant.String, "string"))
    fields.append(QgsField("Sviluppo", QVariant.String, "string"))
    fields.append(QgsField("Htxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Rtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Xtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Ytxt", QVariant.Double, '', 10, 5))
    writer = QgsVectorFileWriter(PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + vl_aq_out.name() + ".shp", "UTF-8", fields, QgsWkbTypes.Polygon, driverName="ESRI Shapefile")
    if writer.hasError() != QgsVectorFileWriter.NoError:
        print("Error when creating shapefile: ", w.errorMessage())
    for f in vl_aq_out_list:
        fet = QgsFeature()
        fet.setGeometry(f.geometry())
        fet.setAttributes( f.attributes() )
        writer.addFeature(fet)
    del writer
    print("FINE - Scrittura file ACQUE")
    print("FINE ACQUE")
    print("==========================================================================================")
    
    #CRS transformation
    print("==========================================================================================")
    print("INIZIO LINEE")
    print("INIZIO - Trasformazione geometrie LINEE")
    print(">>> devo aggiungere vl_linee_cs",vl_linee_cs.featureCount())
    vl_linee_out_list = projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_linee_cs.getFeatures())
    del vl_linee_cs
    print(">>> devo aggiungere vl_linee_gb",vl_linee_gb.featureCount())
    vl_linee_out_list.extend( projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_linee_gb.getFeatures()) )
    del vl_linee_gb
    print(">>> ho collezionato queste feature", len(vl_linee_out_list))
    vl_linee_out = QgsVectorLayer("Linestring?"+CRS_4326_AS_STRING+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Linee", "memory")  
    vl_linee_out.startEditing()
    for f in vl_linee_out_list:
        vl_linee_out.addFeature(f)
    vl_linee_out.commitChanges()
    vl_linee_out.updateFields()
    provider = vl_linee_out.dataProvider()
    print("FINE - Trasformazione geometrie LINEE")
    print("INIZIO - Scrittura file LINEE")
    fields = QgsFields()
    fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    fields.append(QgsField("Fg", QVariant.String, "string"))
    fields.append(QgsField("Mappale", QVariant.String, "string"))
    fields.append(QgsField("All", QVariant.String, "string"))
    fields.append(QgsField("Sez", QVariant.String, "string"))
    fields.append(QgsField("Cod_linea", QVariant.String, "string"))
    writer = QgsVectorFileWriter(PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + vl_linee_out.name() + ".shp", "UTF-8", fields, QgsWkbTypes.LineString, driverName="ESRI Shapefile")
    if writer.hasError() != QgsVectorFileWriter.NoError:
        print("Error when creating shapefile: ", w.errorMessage())
    for f in vl_linee_out_list:
        fet = QgsFeature()
        fet.setGeometry(f.geometry())
        fet.setAttributes( f.attributes() )
        writer.addFeature(fet)
    del writer
    print("FINE - Scrittura file LINEE")
    print("FINE LINEE")
    print("==========================================================================================")
    
    #CRS transformation
    print("==========================================================================================")
    print("INIZIO SIMBOLI")
    print("INIZIO - Trasformazione geometrie SIMBOLI")
    print(">>> devo aggiungere vl_point_cs",vl_point_cs.featureCount())
    vl_point_out_list = projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_point_cs.getFeatures())
    del vl_point_cs
    print(">>> devo aggiungere vl_point_gb",vl_point_gb.featureCount())
    vl_point_out_list.extend( projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_point_gb.getFeatures()) )
    del vl_point_gb
    print(">>> ho collezionato queste feature", len(vl_point_out_list))
    vl_point_out = QgsVectorLayer("Point?"+CRS_4326_AS_STRING+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Simboli", "memory")  
    vl_point_out.startEditing()
    for f in vl_point_out_list:
        vl_point_out.addFeature(f)
    vl_point_out.commitChanges()
    vl_point_out.updateFields()
    provider = vl_point_out.dataProvider()
    print("FINE - Trasformazione geometrie SIMBOLI")
    print("INIZIO - Scrittura file SIMBOLI")
    fields = QgsFields()
    fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    fields.append(QgsField("Fg", QVariant.String, "string"))
    fields.append(QgsField("Mappale", QVariant.String, "string"))
    fields.append(QgsField("All", QVariant.String, "string"))
    fields.append(QgsField("Sez", QVariant.String, "string"))
    fields.append(QgsField("Simbolo", QVariant.String, "string"))
    fields.append(QgsField("Rot", QVariant.Double, '', 10, 5))
    writer = QgsVectorFileWriter(PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + vl_point_out.name() + ".shp", "UTF-8", fields, QgsWkbTypes.Point, driverName="ESRI Shapefile")
    if writer.hasError() != QgsVectorFileWriter.NoError:
        print("Error when creating shapefile: ", w.errorMessage())
    for f in vl_point_out_list:
        fet = QgsFeature()
        fet.setGeometry(f.geometry())
        fet.setAttributes( f.attributes() )
        writer.addFeature(fet)
    del writer
    print("FINE - Scrittura file SIMBOLI")
    print("FINE SIMBOLI")
    print("==========================================================================================")
    
    #CRS transformation
    print("==========================================================================================")
    print("INIZIO TESTI")
    print("INIZIO - Trasformazione geometrie TESTI")
    print(">>> devo aggiungere vl_txt_cs",vl_txt_cs.featureCount())
    vl_txt_out_list = projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_txt_cs.getFeatures())
    del vl_txt_cs
    print(">>> devo aggiungere vl_txt_gb",vl_txt_gb.featureCount())
    vl_txt_out_list.extend( projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_txt_gb.getFeatures()) )
    del vl_txt_gb
    print(">>> ho collezionato queste feature", len(vl_txt_out_list))
    vl_txt_out = QgsVectorLayer("Point?"+CRS_4326_AS_STRING+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Testi", "memory")  
    vl_txt_out.startEditing()
    for f in vl_txt_out_list:
        vl_txt_out.addFeature(f)
    vl_txt_out.commitChanges()
    vl_txt_out.updateFields()
    provider = vl_txt_out.dataProvider()
    print("FINE - Trasformazione geometrie TESTI")
    print("INIZIO - Scrittura file TESTI")
    fields = QgsFields()
    fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    fields.append(QgsField("Fg", QVariant.String, "string"))
    fields.append(QgsField("Mappale", QVariant.String, "string"))
    fields.append(QgsField("All", QVariant.String, "string"))
    fields.append(QgsField("Sez", QVariant.String, "string"))
    fields.append(QgsField("testo", QVariant.String, "string"))
    fields.append(QgsField("Htxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Rtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Xtxt", QVariant.Double, '', 10, 5))
    fields.append(QgsField("Ytxt", QVariant.Double, '', 10, 5))
    writer = QgsVectorFileWriter(PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + vl_txt_out.name() + ".shp", "UTF-8", fields, QgsWkbTypes.Point, driverName="ESRI Shapefile")
    if writer.hasError() != QgsVectorFileWriter.NoError:
        print("Error when creating shapefile: ", w.errorMessage())
    for f in vl_txt_out_list:
        fet = QgsFeature()
        fet.setGeometry(f.geometry())
        fet.setAttributes( f.attributes() )
        writer.addFeature(fet)
    del writer
    print("FINE - Scrittura file TESTI")
    print("FINE TESTI")
    print("==========================================================================================")
    
    #CRS transformation
    print("==========================================================================================")
    print("INIZIO FIDUCIALI")
    print("INIZIO - Trasformazione geometrie FIDUCIALI")
    print(">>> devo aggiungere vl_fidu_cs",vl_fidu_cs.featureCount())
    vl_fidu_out_list = projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_fidu_cs.getFeatures())
    del vl_fidu_cs
    print(">>> devo aggiungere vl_fidu_gb",vl_fidu_gb.featureCount())
    vl_fidu_out_list.extend( projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_fidu_gb.getFeatures()) )
    del vl_fidu_gb
    print(">>> ho collezionato queste feature", len(vl_fidu_out_list))
    vl_fidu_out = QgsVectorLayer("Point?"+CRS_4326_AS_STRING+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Fiduciali", "memory")  
    vl_fidu_out.startEditing()
    for f in vl_fidu_out_list:
        vl_fidu_out.addFeature(f)
    vl_fidu_out.commitChanges()
    vl_fidu_out.updateFields()
    provider = vl_fidu_out.dataProvider()
    print("FINE - Trasformazione geometrie FIDUCIALI")
    print("INIZIO - Scrittura file FIDUCIALI")
    fields = QgsFields()
    fields.append(QgsField("Codice_comune", QVariant.String, "string"))
    fields.append(QgsField("Fg", QVariant.String, "string"))
    fields.append(QgsField("Mappale", QVariant.String, "string"))
    fields.append(QgsField("All", QVariant.String, "string"))
    fields.append(QgsField("Sez", QVariant.String, "string"))
    fields.append(QgsField("Codice", QVariant.String, "string"))
    fields.append(QgsField("Simbolo", QVariant.String, "string"))
    fields.append(QgsField("PosX", QVariant.Double, '', 10, 5))
    fields.append(QgsField("PosY", QVariant.Double, '', 10, 5))
    fields.append(QgsField("RelPosX", QVariant.Double, '', 10, 5))
    fields.append(QgsField("RelPosY", QVariant.Double, '', 10, 5))
    writer = QgsVectorFileWriter(PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + vl_fidu_out.name() + ".shp", "UTF-8", fields, QgsWkbTypes.Point, driverName="ESRI Shapefile")
    if writer.hasError() != QgsVectorFileWriter.NoError:
        print("Error when creating shapefile: ", w.errorMessage())
    for f in vl_fidu_out_list:
        fet = QgsFeature()
        fet.setGeometry(f.geometry())
        fet.setAttributes( f.attributes() )
        writer.addFeature(fet)
    del writer
    print("FINE - Scrittura file FIDUCIALI")
    print("FINE FIDUCIALI")
    print("==========================================================================================")
    
    #
    #
    

    
# ==========================================================================================
       
print ("start")
memory_layer(None)
print("end")