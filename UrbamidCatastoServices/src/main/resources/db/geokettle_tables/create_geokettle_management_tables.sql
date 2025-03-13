-- TABELLA PER IL LOG DI USCITA
-- DROP TABLE public.log_geokettle;

CREATE TABLE public.u_cat_log_geokettle
(
  id_job integer NOT NULL,
  jobname character varying(255),
  status character varying(15),
  lines_read bigint,
  lines_written bigint,
  lines_updated bigint,
  lines_input bigint,
  lines_output bigint,
  errors bigint,
  startdate timestamp without time zone,
  enddate timestamp without time zone,
  logdate timestamp without time zone,
  depdate timestamp without time zone,
  replaydate timestamp without time zone,
  log_field text,
  CONSTRAINT u_cat_log_geokettle_pk PRIMARY KEY (id_job)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_log_geokettle
  OWNER TO postgres;
  
  
-- TABELLA DEI PARAMETRI USATI DA GEOKETTLE
-- DROP TABLE public.parametri_geokettle;

CREATE SEQUENCE public.u_cat_parametri_geokettle_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_parametri_geokettle_id_seq
  OWNER TO postgres;

CREATE TABLE public.u_cat_parametri_geokettle
(
  id integer NOT NULL DEFAULT nextval('u_cat_parametri_geokettle_id_seq'::regclass),
  tipo character varying(2),
  nome_parametro character varying(50) NOT NULL,
  valore_parametro text NOT NULL,
  descrizione text,
  CONSTRAINT u_cat_parametri_geokettle_pk PRIMARY KEY (id),
  CONSTRAINT check_tipo CHECK (tipo::text = ANY (ARRAY['CF'::text, 'CT'::text, 'SH'::text]))
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_parametri_geokettle
  OWNER TO postgres;


INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (3, 'CF', 'FAB_DIRECTORY_ELABORATI_PATH', '/IMPORT_ELABORATI', 'Path relativo della cartella dove saranno scritti i file di output');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (5, 'CF', 'FAB_SUB_DIRECTORY_UNZIP', '/unzip', 'Path relativo della cartella dove saranno decompressi i file');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (10, 'CT', 'TER_SUB_DIRECTORY_UNZIP', '/unzip', 'Path relativo della cartella dove saranno decompressi i file');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (8, 'CT', 'TER_DIRECTORY_ELABORATI_PATH', '/IMPORT_ELABORATI', 'Path relativo della cartella dove saranno scritti i file di output');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (4, 'CF', 'FAB_SUB_DIRECTORY_IMPORT', '/FABBRICATI', 'Path relativo della cartella dove saranno uplodati i file da elaborare');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (9, 'CT', 'TER_SUB_DIRECTORY_IMPORT', '/TERRENI', 'Path relativo della cartella dove saranno uplodati i file da elaborare');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (2, 'CF', 'FAB_DIRECTORY_MANAGE_PATH', '/MANAGE/FABBRICATI', 'Path della cartella di lavoro deil file .FAB');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (15, 'SH', 'PYTHON_SRC', '/opt/IMPORT_CATASTO/PYTHON_WORKSPACE/CXFToShape/src', 'Script python che esegue l''elaborazione prima e poi la scrittura degli SHAPEFILE del catasto');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (7, 'CT', 'TER_DIRECTORY_MANAGE_PATH', '/MANAGE/TERRENI', 'Path della cartella di lavoro deil file .TER');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (1, NULL, 'ROOT_PATH', '/opt/IMPORT_CATASTO', 'Root path di installazione file della procedura');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (22, NULL, 'GEOKETTLE_INSTALLATION_PATH', '/home/fesposit/GeoKettle', 'Path di installazione di GeoKettle.');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (21, 'SH', 'PREFIX_SHAPEFILE', 'u_cat_', 'Stringa da aggiungere come Prefisso al nome dello shapefile');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (12, 'SH', 'DIRECTORY_ELABORATI_PATH', '/IMPORT_ELABORATI', 'Path relativo della cartella dove saranno scritti i file di output');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (13, 'SH', 'DIRECTORY_MANAGE_PATH', '/MANAGE/CATASTO', 'Path della cartella di lavoro deil file .CXF');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (14, 'SH', 'DIRECTORY_UPLOAD_PATH', '/CARTOGRAFIA', 'Path relativo della cartella dove saranno uplodati i file da elaborare');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (16, 'SH', 'SUB_DIRECTORY_UNZIP_FILE', '/unzip', 'Path relativo della cartella dove sono decompressi i file');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (17, 'SH', 'SUB_DIRECTORY_IMPORT_TMP', '/tmp_import', 'Path relativo della cartella dove trasferisco temporaneamente i soli file decompressi con estensione (.cxf)');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (18, 'SH', 'SUB_DIRECTORY_WORK_FILE', '/work', 'Path relativo della cartella dove sono spostati i file cxf raggruppati per sistema di riferimento');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (19, 'SH', 'SUB_DIRECTORY_SHAPEFILE', '/SHAPEFILE', 'Path relativo della cartella dove scrivere i file di output prodotti nel formato ESRI SHAPEFILE e sistema di riferimento ESPG:4326');
INSERT INTO public.u_cat_parametri_geokettle (id, tipo, nome_parametro, valore_parametro, descrizione) VALUES (20, 'SH', 'SUB_DIRECTORY_SHELL_WORK', '/workpath', 'Path relativo della cartella di lavoro per l''esecuzione dello script python');

-- nextval e' 22
SELECT setval('u_cat_parametri_geokettle_id_seq', 23, true);