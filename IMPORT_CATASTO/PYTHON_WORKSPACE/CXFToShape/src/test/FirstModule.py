'''
Created on 23 mag 2019

@author: Marianna Memoli
'''
import sys


def add(a,b):
    return a+b

def addFixedValue(a):
    y = 5
    return y +a

res = add(1, 1)
print (res)
print (addFixedValue(1))

# Print della versione python in uso
print (sys.version_info)

