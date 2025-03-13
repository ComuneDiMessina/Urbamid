'''
Created on 23 mag 2019

@author: Marianna Memoli
'''
import os

path = '/media/marianna/SAMSUNG/Workspace/UrbaMid/WorkspaceEclipse/eclipse_python/Catasto/GB'

files = []
# r=root, d=directories, f = files
for r, d, f in os.walk(path):
    for file in f:
        if '.cxf' in file:
            files.append(os.path.join(r, file))

for f in files:
    print(f)