-- TABELLA PER IL LOG DI USCITA
-- DROP TABLE public.log_geokettle;

CREATE TABLE public.log_geokettle
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
  CONSTRAINT log_geokettle_pk PRIMARY KEY (id_job)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.log_geokettle
  OWNER TO postgres;
  
  
-- TABELLA DEI PARAMETRI USATI DA GEOKETTLE
-- DROP TABLE public.parametri_geokettle;

CREATE TABLE public.parametri_geokettle
(
  id integer NOT NULL DEFAULT nextval('parametri_geokettle_id_seq'::regclass),
  tipo character varying(2) NOT NULL,
  nome_parametro character varying(50) NOT NULL,
  valore_parametro text NOT NULL,
  descrizione text,
  CONSTRAINT parametri_geokettle_pk PRIMARY KEY (id),
  CONSTRAINT check_tipo CHECK (tipo::text = ANY (ARRAY['CF'::text, 'CT'::text, 'SH'::text]))
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.parametri_geokettle
  OWNER TO postgres;

