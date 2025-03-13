
--    ██████  ▄████▄   ██░ ██ ▓█████  ███▄ ▄███▓ ▄▄▄      
--  ▒██    ▒ ▒██▀ ▀█  ▓██░ ██▒▓█   ▀ ▓██▒▀█▀ ██▒▒████▄    
--  ░ ▓██▄   ▒▓█    ▄ ▒██▀▀██░▒███   ▓██    ▓██░▒██  ▀█▄  
--    ▒   ██▒▒▓▓▄ ▄██▒░▓█ ░██ ▒▓█  ▄ ▒██    ▒██ ░██▄▄▄▄██ 
--  ▒██████▒▒▒ ▓███▀ ░░▓█▒░██▓░▒████▒▒██▒   ░██▒ ▓█   ▓██▒
--  ▒ ▒▓▒ ▒ ░░ ░▒ ▒  ░ ▒ ░░▒░▒░░ ▒░ ░░ ▒░   ░  ░ ▒▒   ▓▒█░
--  ░ ░▒  ░ ░  ░  ▒    ▒ ░▒░ ░ ░ ░  ░░  ░      ░  ▒   ▒▒ ░
--  ░  ░  ░  ░         ░  ░░ ░   ░   ░      ░     ░   ▒   
--        ░  ░ ░       ░  ░  ░   ░  ░       ░         ░  ░
--           ░                                            
DROP SCHEMA IF EXISTS storico CASCADE;
CREATE SCHEMA storico AUTHORIZATION postgres;



--    ██████ ▓█████   █████   █    ██ ▓█████  ███▄    █  ▄████▄  ▓█████   ██████ 
--  ▒██    ▒ ▓█   ▀ ▒██▓  ██▒ ██  ▓██▒▓█   ▀  ██ ▀█   █ ▒██▀ ▀█  ▓█   ▀ ▒██    ▒ 
--  ░ ▓██▄   ▒███   ▒██▒  ██░▓██  ▒██░▒███   ▓██  ▀█ ██▒▒▓█    ▄ ▒███   ░ ▓██▄   
--    ▒   ██▒▒▓█  ▄ ░██  █▀ ░▓▓█  ░██░▒▓█  ▄ ▓██▒  ▐▌██▒▒▓▓▄ ▄██▒▒▓█  ▄   ▒   ██▒
--  ▒██████▒▒░▒████▒░▒███▒█▄ ▒▒█████▓ ░▒████▒▒██░   ▓██░▒ ▓███▀ ░░▒████▒▒██████▒▒
--  ▒ ▒▓▒ ▒ ░░░ ▒░ ░░░ ▒▒░ ▒ ░▒▓▒ ▒ ▒ ░░ ▒░ ░░ ▒░   ▒ ▒ ░ ░▒ ▒  ░░░ ▒░ ░▒ ▒▓▒ ▒ ░
--  ░ ░▒  ░ ░ ░ ░  ░ ░ ▒░  ░ ░░▒░ ░ ░  ░ ░  ░░ ░░   ░ ▒░  ░  ▒    ░ ░  ░░ ░▒  ░ ░
--  ░  ░  ░     ░      ░   ░  ░░░ ░ ░    ░      ░   ░ ░ ░           ░   ░  ░  ░  
--        ░     ░  ░    ░       ░        ░  ░         ░ ░ ░         ░  ░      ░  
--                                                      ░                        
DROP SEQUENCE IF EXISTS storico.u_cat_acque_storico_gid_seq;
DROP SEQUENCE IF EXISTS storico.u_cat_confine_storico_gid_seq;
DROP SEQUENCE IF EXISTS storico.u_cat_fabbricati_storico_gid_seq;
DROP SEQUENCE IF EXISTS storico.u_cat_fiduciali_storico_gid_seq;
DROP SEQUENCE IF EXISTS storico.u_cat_linee_storico_gid_seq;
DROP SEQUENCE IF EXISTS storico.u_cat_particelle_storico_gid_seq;
DROP SEQUENCE IF EXISTS storico.u_cat_simboli_storico_gid_seq;
DROP SEQUENCE IF EXISTS storico.u_cat_strade_storico_gid_seq;
DROP SEQUENCE IF EXISTS storico.u_cat_testi_storico_gid_seq;
CREATE SEQUENCE storico.u_cat_acque_storico_gid_seq MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE storico.u_cat_confine_storico_gid_seq MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE storico.u_cat_fabbricati_storico_gid_seq MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE storico.u_cat_fiduciali_storico_gid_seq MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE storico.u_cat_linee_storico_gid_seq MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE storico.u_cat_particelle_storico_gid_seq MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE storico.u_cat_simboli_storico_gid_seq MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE storico.u_cat_strade_storico_gid_seq MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE storico.u_cat_testi_storico_gid_seq MAXVALUE 9223372036854775807 NO CYCLE;


--  ▄▄▄█████▓ ▄▄▄       ▄▄▄▄    ██▓    ▓█████   ██████ 
--  ▓  ██▒ ▓▒▒████▄    ▓█████▄ ▓██▒    ▓█   ▀ ▒██    ▒ 
--  ▒ ▓██░ ▒░▒██  ▀█▄  ▒██▒ ▄██▒██░    ▒███   ░ ▓██▄   
--  ░ ▓██▓ ░ ░██▄▄▄▄██ ▒██░█▀  ▒██░    ▒▓█  ▄   ▒   ██▒
--    ▒██▒ ░  ▓█   ▓██▒░▓█  ▀█▓░██████▒░▒████▒▒██████▒▒
--    ▒ ░░    ▒▒   ▓▒█░░▒▓███▀▒░ ▒░▓  ░░░ ▒░ ░▒ ▒▓▒ ▒ ░
--      ░      ▒   ▒▒ ░▒░▒   ░ ░ ░ ▒  ░ ░ ░  ░░ ░▒  ░ ░
--    ░        ░   ▒    ░    ░   ░ ░      ░   ░  ░  ░  
--                 ░  ░ ░          ░  ░   ░  ░      ░  
--                           ░                         
DROP TABLE IF EXISTS storico.u_cat_acque_storico;
CREATE TABLE storico.u_cat_acque_storico
(
  gid integer NOT NULL DEFAULT nextval('"storico".u_cat_acque_storico_gid_seq'::regclass),
  versione integer,
  data_inizio_validita date,
  data_fine_validita date,
  codice_com character varying(50),
  foglio character varying(5),
  mappale character varying(5),
  allegato character varying(5),
  sviluppo character varying(5),
  htxt numeric,
  rtxt numeric,
  xtxt numeric,
  ytxt numeric,
  geom geometry(MultiPolygon),
  CONSTRAINT u_cat_acque_storico_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico.u_cat_acque_storico OWNER TO postgres;


DROP TABLE IF EXISTS storico.u_cat_confine_storico;
CREATE TABLE storico.u_cat_confine_storico
(
  gid integer NOT NULL DEFAULT nextval('"storico".u_cat_confine_storico_gid_seq'::regclass),
  versione integer,
  data_inizio_validita date,
  data_fine_validita date,
  codice_com character varying(50),
  foglio character varying(5),
  mappale character varying(11),
  allegato character varying(5),
  sviluppo character varying(5),
  htxt numeric,
  rtxt numeric,
  xtxt numeric,
  ytxt numeric,
  geom geometry(MultiPolygon),
  CONSTRAINT u_cat_confine_storico_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico.u_cat_confine_storico OWNER TO postgres;


DROP TABLE IF EXISTS storico.u_cat_fabbricati_storico;
CREATE TABLE storico.u_cat_fabbricati_storico
(
  gid integer NOT NULL DEFAULT nextval('"storico".u_cat_fabbricati_storico_gid_seq'::regclass),
  versione integer,
  data_inizio_validita date,
  data_fine_validita date,
  codice_com character varying(50),
  foglio character varying(5),
  mappale character varying(7),
  allegato character varying(5),
  sviluppo character varying(5),
  htxt numeric,
  rtxt numeric,
  xtxt numeric,
  ytxt numeric,
  geom geometry(MultiPolygon),
  CONSTRAINT u_cat_fabbricati_storico_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico.u_cat_fabbricati_storico OWNER TO postgres;


DROP TABLE IF EXISTS storico.u_cat_fiduciali_storico;
CREATE TABLE storico.u_cat_fiduciali_storico
(
  gid integer NOT NULL DEFAULT nextval('"storico".u_cat_fiduciali_storico_gid_seq'::regclass),
  versione integer,
  data_inizio_validita date,
  data_fine_validita date,
  codice_com character varying(50),
  fg character varying(5),
  mappale character varying(10),
  "all" character varying(5),
  sez character varying(5),
  codice character varying(50),
  simbolo character varying(5),
  posx numeric,
  posy numeric,
  relposx numeric,
  relposy numeric,
  geom geometry(MultiPoint),
  CONSTRAINT u_cat_fiduciali_storico_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico.u_cat_fiduciali_storico OWNER TO postgres;
  

DROP TABLE IF EXISTS storico.u_cat_linee_storico;
CREATE TABLE storico.u_cat_linee_storico
(
  gid integer NOT NULL DEFAULT nextval('"storico".u_cat_linee_storico_gid_seq'::regclass),
  versione integer,
  data_inizio_validita date,
  data_fine_validita date,
  codice_com character varying(50),
  fg character varying(5),
  mappale character varying(6),
  "all" character varying(5),
  sez character varying(5),
  cod_linea character varying(5),
  geom geometry(MultiLineString),
  CONSTRAINT u_cat_linee_storico_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico.u_cat_linee_storico OWNER TO postgres;


DROP TABLE IF EXISTS storico.u_cat_particelle_storico;
CREATE TABLE storico.u_cat_particelle_storico
(
  gid integer NOT NULL DEFAULT nextval('"storico".u_cat_particelle_storico_gid_seq'::regclass),
  versione integer,
  data_inizio_validita date,
  data_fine_validita date,
  codice_com character varying(50),
  foglio character varying(5),
  mappale character varying(6),
  allegato character varying(5),
  sviluppo character varying(5),
  htxt numeric,
  rtxt numeric,
  xtxt numeric,
  ytxt numeric,
  geom geometry(MultiPolygon),
  CONSTRAINT u_cat_particelle_storico_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico.u_cat_particelle_storico OWNER TO postgres;


DROP TABLE IF EXISTS storico.u_cat_simboli_storico;
CREATE TABLE storico.u_cat_simboli_storico
(
  gid integer NOT NULL DEFAULT nextval('"storico".u_cat_simboli_storico_gid_seq'::regclass),
  versione integer,
  data_inizio_validita date,
  data_fine_validita date,
  codice_com character varying(50),
  fg character varying(5),
  mappale character varying(8),
  "all" character varying(5),
  sez character varying(5),
  simbolo character varying(5),
  rot numeric,
  geom geometry(MultiPoint),
  CONSTRAINT u_cat_simboli_storico_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico.u_cat_simboli_storico OWNER TO postgres;


DROP TABLE IF EXISTS storico.u_cat_strade_storico;
CREATE TABLE storico.u_cat_strade_storico
(
  gid integer NOT NULL DEFAULT nextval('"storico".u_cat_strade_storico_gid_seq'::regclass),
  versione integer,
  data_inizio_validita date,
  data_fine_validita date,
  codice_com character varying(50),
  foglio character varying(5),
  mappale character varying(6),
  allegato character varying(5),
  sviluppo character varying(5),
  htxt numeric,
  rtxt numeric,
  xtxt numeric,
  ytxt numeric,
  geom geometry(MultiPolygon),
  CONSTRAINT u_cat_strade_storico_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico.u_cat_strade_storico OWNER TO postgres;


DROP TABLE IF EXISTS storico.u_cat_testi_storico;
CREATE TABLE storico.u_cat_testi_storico
(
  gid integer NOT NULL DEFAULT nextval('"storico".u_cat_testi_storico_gid_seq'::regclass),
  versione integer,
  data_inizio_validita date,
  data_fine_validita date,
  codice_com character varying(50),
  fg character varying(5),
  mappale character varying(6),
  "all" character varying(5),
  sez character varying(5),
  testo character varying(50),
  htxt numeric,
  rtxt numeric,
  xtxt numeric,
  ytxt numeric,
  geom geometry(MultiPoint),
  CONSTRAINT u_cat_testi_storico_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico.u_cat_testi_storico OWNER TO postgres;


