'''
Created on 23 mag 2019

@author: Marianna Memoli
'''

from qgis.core import *
import os
import sys
import ntpath
import xml.etree.ElementTree as etree
import contextlib
import urllib
import multiprocessing as mp 

class foglio(object):
    pass
class matrix(object):
    pass
class trasform(object):
    pass
    
def converti(self,trasf,x,y):
 if trasf=="e":
     return trasf_e(self,x,y)    
 elif trasf=="eAf":
     return trasf_eAF(self,x,y)   
 elif trasf=="eAfn":
     return trasf_eAFn(self,x,y)       
 elif trasf=="proj4":
     return trasf_proj4(self,x,y)    
 else:    
     return (y,x)    
     
 
def trasf_e(self,x,y):
       tx = matrix.T1e +(matrix.Se *( (matrix.R1e * y) + (matrix.R2e * x) )  )
       ty = matrix.T2e +(matrix.Se *( (matrix.R2e * y) + (matrix.R1e * x) )  )
       return (tx,ty)
       
def trasf_eAF(self,x,y):
       tx = (matrix.T1e + ((matrix.SeX * (matrix.R1e - (matrix.R2e * matrix.Sband))) * y)) + ((matrix.SeX * (matrix.R2e + (matrix.R1e * matrix.Sband))) * x)
       ty = (matrix.T2e + ((matrix.SeY * -(matrix.R2e)) * y)) + ((matrix.SeY * matrix.R1e) * x)
       return (tx,ty)

def trasf_eAFn(self,x,y):
       tx = (matrix.T1e + ((matrix.SeX * (matrix.R1e - (matrix.R2e * matrix.Sband))) * y)) + ((matrix.SeX * (matrix.R2e + (matrix.R1e * matrix.Sband))) * x)
       ty = (matrix.T2e + ((matrix.SeY * -(matrix.R2e)) * y)) + ((matrix.SeY * matrix.R1e) * x)
       #print "-"+str(tx)
       gauss=foglio.trasf.transform(QgsPoint(tx,ty)) 
       tx=gauss[0]
       #print str(tx)
       ty=gauss[1] 
           
          # print str(tx),str(ty)
       return (tx,ty)        
# def trasf_eAF(self,x,y):
#        tx = (matrix.T1e + ((matrix.SeX * (matrix.R1e - (matrix.R2e * matrix.Sband))) * y)) + ((matrix.SeX * (matrix.R2e + (matrix.R1e * matrix.Sband))) * x)
#        ty = (matrix.T2e + ((matrix.SeY * -(matrix.R2e)) * y)) + ((matrix.SeY * matrix.R1e) * x)
#        return (tx,ty)
def trasf_proj4(self,x,y):   
        geom =foglio.trasf.transform(QgsPoint(y,x))
        return (geom.x(),geom.y())       






                