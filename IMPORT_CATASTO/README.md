### Installazione Geokettle v2.5

Accedere alla pagina https://sourceforge.net/projects/geokettle/ e in base al proprio SO scegliere l'installer appropriato.

*Per SO Ubuntu:*
1. Scaricare il file (.deb) dalla pagina web indicata
2. Lanciare da linea di comando l'installazione: `dpkg -i <path>/geokettle_2.5.deb`

-----


### Configurazioni JOB:

Settare la variabile d'ambiente **KETTLE_REPOSITORY** la quale deve essere valorizzata con il path di dove è stato scaricato il repository GIT. 

Ad esempio:  
```
KETTLE_REPOSITORY="<..>/URBAMID/IMPORT_CATASTO/JOB_GEOKETTLE"

PATH="<...>:$KETTLE_REPOSITORY"
```




