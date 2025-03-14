'''
Created on 23 mag 2019

@author: Marianna Memoli
'''
import os
from qgis.core import *
from load_cxf_memory import *


def reader_file_cxf(qgs, filename, vl_aq, vl_st, vl_conf, vl_part, vl_txt, vl_fidu, vl_ed, vl_point, vl_linee):
    
# ==========================================================================================
          
    for f in filename:        
        liv=os.path.basename(os.path.splitext( str(f))[0])
        load_cxf(qgs,str(f),liv,vl_aq,vl_st,vl_conf,vl_part,vl_txt,vl_fidu,vl_ed,vl_point,vl_linee)
        

# ==========================================================================================    

    vl_part.commitChanges()
    vl_ed.commitChanges()
    vl_st.commitChanges()
    vl_aq.commitChanges() 
    vl_conf.commitChanges()
    vl_linee.commitChanges()
    vl_point.commitChanges()
    vl_txt.commitChanges()
    vl_fidu.commitChanges() 
        
# ==========================================================================================
       
