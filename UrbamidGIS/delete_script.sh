#!/bin/sh
cd /opt/IMPORT_CATASTO/DATI/AGGIORNAMENTO/MANAGE/CATASTO
rm -rf tmp_import/
rm -rf unzip/
rm -rf work/
rm -rf workpath/
printf "Cancellato contenuto AGGIORNAMENTO \n"

cd /opt/IMPORT_CATASTO/DATI/ATTUALITA/MANAGE/CATASTO
rm -rf tmp_import/
rm -rf unzip/
rm -rf work/
rm -rf workpath/
printf "Cancellato contenuto ATTUALITA \n"
