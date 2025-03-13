## CXFToShape
 
> **CXFToShape** permette di convertire i file in formato CXF nel formato Esri 
> Shapefile, supportato da i più comuni software GIS.
> Ogni file in formato CXF descrive gli oggetti cartografici che compongono 
> un singolo foglio di mappa usando cinque tipi di elementi geometrici: bordi, 
> testi, simboli, fiduciali e linee di vestizione. 
 
L’output di CXFToShape sono otto Shapefile:
1. i poligoni che descrivono la geometria di strade, acque e particelle catastali
2. i poligoni che descrivono la geometria dei fabbricati
3. i punti ai quali sono agganciate le annotazioni cartografiche
4. i punti ai quali sono agganciati i testi con i numeri delle particelle 
5. le linee di connessione tra i testi e i baricentri delle particelle
6. i punti che descrivono la posizione dei simboli di rilevanza catastale (graffe, ossi di morto, termini, ecc.)
7. i punti che descrivono la posizione dei punti fiduciali
8. le linee che descrivono la geometria delle vestizioni cartografiche. 
 
Ad ogni elemento geometrico contenuto negli Shapefile sono associati gli attributi necessari alla sua corretta rappresentazione cartografica (dimensioni, angoli di rotazione, ecc.) e alla relazione con la banca dati censuaria. 



-----
Il progetto è stato creato utilizzando l'IDE Eclipse ultima versione(Eclipse IDE 2019‑03).

-----

## Python
Il progetto è scritto in linguaggio python e la versione utilizzata è Python 3.6.7.


sudo apt-get install qgis python3-qgis qgis-plugin-grass

python3 -V

oppure 

1) wget https://www.python.org/ftp/python/3.7.3/Python-3.7.3.tar.xz

2) tar -xf Python-3.7.3.tar.xz

3) cd Python-3.6.7/

4) ./configure --enable-optimizations

5) Start the Python build process using make: make -j 8

For faster build time, modify the -j flag according to your processor. If you do not know the number of cores your processor you can find it by typing nproc. My system has 8 cores, so I am using the -j8 flag.

6) When the build is done install the Python binaries by typing: sudo make altinstall

Do not use the standard make install as it will overwrite the default system python3 binary.


7) Python 3.6 is installed and ready to be used, verify it by typing:

python3.6 --version

oppure

http://ubuntuhandbook.org/index.php/2017/07/install-python-3-6-1-in-ubuntu-16-04-lts/

http://devopspy.com/python/install-python-3-6-ubuntu-lts/

 

