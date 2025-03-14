'''
Created on 23 mag 2019

@author: Marianna Memoli
'''
from qgis.core import *

def createLayerConfine(crs):
    
    return QgsVectorLayer("Polygon?"+crs+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Confine", "memory")  
    
def createLayerParticelle(crs):
    
    return QgsVectorLayer("Polygon?"+crs+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Particelle", "memory")
    
def createLayerFabbricati(crs):
    
    return QgsVectorLayer("Polygon?"+crs+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Fabbricati", "memory")
    
def createLayerStrade(crs):
    
    return QgsVectorLayer("Polygon?"+crs+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Strade", "memory")
    
def createLayerAcque(crs):
    
    return QgsVectorLayer("Polygon?"+crs+"&field=Codice_comune:string(50)&field=Foglio:string(5)&field=Mappale:string(5)&field=Allegato:string(5)&field=Sviluppo:string(5)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Acque", "memory")
    
def createLayerLinee(crs):
    
    return QgsVectorLayer("Linestring?"+crs+"&field=Codice_comune:string(50)&field=Fg:string(5)&field=Mappale:string(5)&field=All:string(5)&field=Sez:string(5)&field=Cod_linea:string(5)",
                       "Linee", "memory")
    
def createLayerSimboli(crs):
    
    return QgsVectorLayer("Point?"+crs+"&field=Codice_comune:string(50)&field=Fg:string(5)&field=Mappale:string(5)&field=All:string(5)&field=Sez:string(5)&field=Simbolo:string(5)&field=Rot:Double",
                       "Simboli", "memory")
    
def createLayerTesti(crs):
    
    return QgsVectorLayer("Point?"+crs+"&field=Codice_comune:string(50)&field=Fg:string(5)&field=Mappale:string(5)&field=All:string(5)&field=Sez:string(5)&field=testo:string(50)&field=Htxt:Double&field=Rtxt:Double&field=Xtxt:Double&field=Ytxt:Double",
                       "Testi", "memory")

def createLayerFiduciali(crs):
    
    return QgsVectorLayer("Point?"+crs+"&field=Codice_comune:string(50)&field=Fg:string(5)&field=Mappale:string(5)&field=All:string(5)&field=Sez:string(5)&field=Codice:string(50)&field=Simbolo:string(5)&field=PosX:Double&field=PosY:Double&field=RelPosX:Double&field=RelPosY:Double",
                       "Fiduciali", "memory")
    
def projTransorm(sourceCrs, destCrs, features):
    xform = QgsCoordinateTransform()
    xform.setSourceCrs(sourceCrs)
    xform.setDestinationCrs(destCrs)

    #CRS transformation
    feats = []
    for f in features:
        g = f.geometry()
        g.transform(xform)
        f.setGeometry(g)
        feats.append(f)
    
    return feats;
