'''
Created on 23 mag 2019

@author: Marianna Memoli
'''

"""
/********************************************************************************
 *                                                                              *
 *                          CXFToShape                                          *
 *                                                                              *
 * CXFToShape permette di convertire i file in formato CXF nel formato Esri     *
 * Shapefile, supportato da i più comuni software GIS.                          *
 * Ogni file in formato CXF descrive gli oggetti cartografici che compongono    * 
 * un singolo foglio di mappa usando cinque tipi di elementi geometrici: bordi, *
 * testi, simboli, fiduciali e linee di vestizione.                             *
 *                                                                              *
 * L’output di CXFToShape sono otto Shapefile:                                  *
 * (1) i poligoni che descrivono la geometria di strade, acque e particelle     *
 *     catastali                                                                *
 * (2) i poligoni che descrivono la geometria dei fabbricati                    *
 * (3) i punti ai quali sono agganciate le annotazioni cartografiche            *
 * (4) i punti ai quali sono agganciati i testi con i numeri delle particelle   *
 * (5) le linee di connessione tra i testi e i baricentri delle particelle      *
 * (6) i punti che descrivono la posizione dei simboli di rilevanza catastale   *
 *     (graffe, ossi di morto, termini, ecc.)                                   *
 * (7) i punti che descrivono la posizione dei punti fiduciali                  *
 * (8) le linee che descrivono la geometria delle vestizioni cartografiche.     *
 *                                                                              *
 * Ad ogni elemento geometrico contenuto negli Shapefile sono associati gli     *
 * attributi necessari alla sua corretta rappresentazione cartografica          *
 * (dimensioni, angoli di rotazione, ecc.) e alla relazione con la banca dati   *
 * censuaria.                                                                   *
 *******************************************************************************/
"""

import os
from qgis.core import *
from load_cxf_memory import *
from reader_file_cxf import *
from layerutil import *

def memory_layer(self):
    
    QgsApplication.setPrefixPath('/usr', True)
    qgs = QgsApplication([], False)
    qgs.initQgis()

# ==========================================================================================

    CRS_ESPG_4326 = QgsCoordinateReferenceSystem("EPSG:4326")

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
  
    #PATH_STYLE = '/media/marianna/SAMSUNG/Workspace/UrbaMid/WorkspaceEclipse/eclipse_python/Catasto/_file/style/QML_Default/'
   
    if not PATH_SOURCE_CS:
        PATH_SOURCE_CS = '/media/marianna/SAMSUNG/Workspace/UrbaMid/WorkspaceEclipse/eclipse_python/CXFToShape/_file/sourceFile/CS'
    
    if not PATH_SOURCE_GB:
        PATH_SOURCE_GB = '/media/marianna/SAMSUNG/Workspace/UrbaMid/WorkspaceEclipse/eclipse_python/CXFToShape/_file/sourceFile/GB'
   
    if not PATH_OUTPUT_SHP:
        PATH_OUTPUT_SHP = '/media/marianna/SAMSUNG/Workspace/UrbaMid/WorkspaceEclipse/eclipse_python/CXFToShape/_file/outputFile/'

    if not PREFIX_SHAPEFILE:
        PREFIX_SHAPEFILE = "";
        
# ==========================================================================================
       
    ELAB_FILENAME_CS = []
    
    # r=root, d=directories, f = files
    for r, d, f in os.walk(PATH_SOURCE_CS):
        for file in f:
            if '.cxf' in file:
                ELAB_FILENAME_CS.append(os.path.join(r, file))
        
    print("==========================================================================================")
    
    print("Elenco file con Proj:'Cassini' che saranno elaborati: ")
    
    for f in ELAB_FILENAME_CS: print(f)

    #
    #
    #
    
    print("==========================================================================================")
    
    ELAB_FILENAME_GB = []
    
    # r=root, d=directories, f = files
    for r, d, f in os.walk(PATH_SOURCE_GB):
        for file in f:
            if '.cxf' in file:
                ELAB_FILENAME_GB.append(os.path.join(r, file))
        
    print("==========================================================================================")
    
    print("Elenco file con Proj:'Gauss' che saranno elaborati: ")
    
    for f in ELAB_FILENAME_GB: print(f)

    print("==========================================================================================")
    
# ==========================================================================================
          
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
        
# ==========================================================================================
    
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
        
# ==========================================================================================
    
    vl_conf_out = createLayerConfine(CRS_4326_AS_STRING)
    #CRS transformation
    vl_conf_out.dataProvider().addFeatures(projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_conf_cs.getFeatures()))
    vl_conf_out.dataProvider().addFeatures(projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_conf_gb.getFeatures()))
    
    #
    #
    
    vl_part_out = createLayerParticelle(CRS_4326_AS_STRING)
    #CRS transformation
    vl_part_out.dataProvider().addFeatures(projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_part_cs.getFeatures()))
    vl_part_out.dataProvider().addFeatures(projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_part_gb.getFeatures()))
    
    #
    #
    
    vl_ed_out = createLayerFabbricati(CRS_4326_AS_STRING)
    #CRS transformation
    vl_ed_out.dataProvider().addFeatures(projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_ed_cs.getFeatures()))
    vl_ed_out.dataProvider().addFeatures(projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_ed_gb.getFeatures()))
    
    #
    #
    
    vl_st_out = createLayerStrade(CRS_4326_AS_STRING)  
    vl_st_out.dataProvider().addFeatures(projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_st_cs.getFeatures()))
    vl_st_out.dataProvider().addFeatures(projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_st_gb.getFeatures()))
    
    #
    #
    
    vl_aq_out = createLayerAcque(CRS_4326_AS_STRING) 
    vl_aq_out.dataProvider().addFeatures(projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_aq_cs.getFeatures()))
    vl_aq_out.dataProvider().addFeatures(projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_aq_gb.getFeatures()))
    
    #
    #
    
    vl_linee_out = createLayerLinee(CRS_4326_AS_STRING)
    vl_linee_out.dataProvider().addFeatures(projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_linee_cs.getFeatures()))
    vl_linee_out.dataProvider().addFeatures(projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_linee_gb.getFeatures()))
    
    #
    #
    
    vl_point_out = createLayerSimboli(CRS_4326_AS_STRING)
    vl_point_out.dataProvider().addFeatures(projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_point_cs.getFeatures()))
    vl_point_out.dataProvider().addFeatures(projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_point_gb.getFeatures()))
    
    #
    #
    vl_txt_out   = createLayerTesti(CRS_4326_AS_STRING)
    vl_txt_out.dataProvider().addFeatures(projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_txt_cs.getFeatures()))
    vl_txt_out.dataProvider().addFeatures(projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_txt_gb.getFeatures()))
    
    #
    #
    
    vl_fidu_out  = createLayerFiduciali(CRS_4326_AS_STRING)
    vl_fidu_out.dataProvider().addFeatures(projTransorm(CRS_CASSINI, CRS_ESPG_4326, vl_fidu_cs.getFeatures()))
    vl_fidu_out.dataProvider().addFeatures(projTransorm(CRS_ESPG_MONTEMARIO, CRS_ESPG_4326, vl_fidu_gb.getFeatures()))
    
    #
    #
    
    livelligruppofoglio_out = [vl_aq_out, vl_st_out, vl_conf_out, vl_part_out, vl_txt_out, vl_fidu_out, vl_ed_out, vl_point_out, vl_linee_out]

# ==========================================================================================
   
    
    for l in livelligruppofoglio_out:
        writer, error = QgsVectorFileWriter.writeAsVectorFormat(
        l,
        PATH_OUTPUT_SHP + PREFIX_SHAPEFILE + l.name() + ".shp",
        'utf-8',
        CRS_ESPG_4326,
        driverName = 'ESRI Shapefile',
        forceMulti = True
        )
        
    if error == QgsVectorFileWriter.NoError:
        print ("error!")
    else:
        print ("success!")
        
    # delete the writer to flush features to disk
    del writer

# ==========================================================================================
       
print ("start")
memory_layer(None)
print("end")
