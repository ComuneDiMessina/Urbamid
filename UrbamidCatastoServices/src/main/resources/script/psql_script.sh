#      ____  _________    ____  __  _________
#     / __ \/ ____/   |  / __ \/  |/  / ____/
#    / /_/ / __/ / /| | / / / / /|_/ / __/   
#   / _, _/ /___/ ___ |/ /_/ / /  / / /___   
#  /_/ |_/_____/_/  |_/_____/_/  /_/_____/   
#                                            
# Questo script serve per il caricamento in base dati delle informazioni cartografiche degli shapefile
# N.B. ricorda sempre che la risposta alla vita, l'universo e tutto quanto è 42 (casomai dovesse servire).

# password del database
export PGPASSWORD=postgres
# nome del campo geometria 
export GEOMETRY=geometry
# path dove sono locati gli shapefile
export PATH_SHAPEFILE=/opt/geoKettle/IMPORT_CATASTO/DATI/SHAPEFILE
# SRID
export SRID=4326
# Database schema
export DATABASE_SCHEMA=public
# Database nome
export DATABASE_NAME=dbUrbamid
# Database host
export DATABASE_HOST=localhost
# Database user
export DATABASE_USER=postgres
## -d drop	(modalità per il caricamento di un catasto completo)
## -a append (modalità per il caricamento di un aggiornamento)
## -c create table and populate
## -p only create table
export SHP_2_PSQL_MODE=-a

shp2pgsql $SHP_2_PSQL_MODE -g $GEOMETRY -W UTF-8 -I $PATH_SHAPEFILE/u_cat_Acque.shp $DATABASE_SCHEMA.u_cat_acque -s $SRID | psql -h $DATABASE_HOST -U $DATABASE_USER -d $DATABASE_NAME -w 
shp2pgsql $SHP_2_PSQL_MODE -g $GEOMETRY -W UTF-8 -I $PATH_SHAPEFILE/u_cat_Confine.shp $DATABASE_SCHEMA.u_cat_confine -s $SRID | psql -h $DATABASE_HOST -U $DATABASE_USER -d $DATABASE_NAME -w
shp2pgsql $SHP_2_PSQL_MODE -g $GEOMETRY -W UTF-8 -I $PATH_SHAPEFILE/u_cat_Fabbricati.shp $DATABASE_SCHEMA.u_cat_fabbricati -s $SRID | psql -h $DATABASE_HOST -U $DATABASE_USER -d $DATABASE_NAME -w
shp2pgsql $SHP_2_PSQL_MODE -g $GEOMETRY -W UTF-8 -I $PATH_SHAPEFILE/u_cat_Fiduciali.shp $DATABASE_SCHEMA.u_cat_fiduciali -s $SRID | psql -h $DATABASE_HOST -U $DATABASE_USER -d $DATABASE_NAME -w
shp2pgsql $SHP_2_PSQL_MODE -g $GEOMETRY -W UTF-8 -I $PATH_SHAPEFILE/u_cat_Linee.shp $DATABASE_SCHEMA.u_cat_linee -s $SRID | psql -h $DATABASE_HOST -U $DATABASE_USER -d $DATABASE_NAME -w
shp2pgsql $SHP_2_PSQL_MODE -g $GEOMETRY -W UTF-8 -I $PATH_SHAPEFILE/u_cat_Particelle.shp $DATABASE_SCHEMA.u_cat_particelle -s $SRID | psql -h $DATABASE_HOST -U $DATABASE_USER -d $DATABASE_NAME -w
shp2pgsql $SHP_2_PSQL_MODE -g $GEOMETRY -W UTF-8 -I $PATH_SHAPEFILE/u_cat_Simboli.shp $DATABASE_SCHEMA.u_cat_simboli -s $SRID | psql -h $DATABASE_HOST -U $DATABASE_USER -d $DATABASE_NAME -w
shp2pgsql $SHP_2_PSQL_MODE -g $GEOMETRY -W UTF-8 -I $PATH_SHAPEFILE/u_cat_Strade.shp $DATABASE_SCHEMA.u_cat_strade -s $SRID | psql -h $DATABASE_HOST -U $DATABASE_USER -d $DATABASE_NAME -w
shp2pgsql $SHP_2_PSQL_MODE -g $GEOMETRY -W UTF-8 -I $PATH_SHAPEFILE/u_cat_Testi.shp $DATABASE_SCHEMA.u_cat_testi -s $SRID | psql -h $DATABASE_HOST -U $DATABASE_USER -d $DATABASE_NAME -w

