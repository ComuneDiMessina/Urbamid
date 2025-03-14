--
-- u_cat_categorie_catastali
--
CREATE TABLE public.u_cat_categorie_catastali
(
  codice character varying(10) NOT NULL,
  descrizione character varying(500),
  informazione character varying(500),
  CONSTRAINT "u_cat_categorie_catastali_pk" PRIMARY KEY (codice)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_categorie_catastali OWNER TO postgres;

--
-- u_cat_codici_diritto
--
CREATE TABLE public.u_cat_codici_diritto
(
  codice character varying(5) NOT NULL,
  descrizione character varying(100),
  CONSTRAINT "u_cat_codici_diritto_pk" PRIMARY KEY (codice)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_codici_diritto OWNER TO postgres;

--
-- u_cat_codici_qualita
--
CREATE TABLE public.u_cat_codici_qualita
(
  codice integer NOT NULL,
  descrizione character varying(100),
  CONSTRAINT "u_cat_codici_qualita_pk" PRIMARY KEY (codice)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_codici_qualita OWNER TO postgres;

--
-- u_cat_deduzioni_particella
--
CREATE SEQUENCE public.u_cat_deduzioni_particella_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_deduzioni_particella_id_seq OWNER TO postgres;

CREATE TABLE public.u_cat_deduzioni_particella (
  id bigint NOT NULL DEFAULT nextval('u_cat_deduzioni_particella_id_seq'::regclass),
  cod_comune character(4),
  sezione character(1),
  tipo_immobile character(1),
  progressivo character varying(3),
  simbolo_deduzioni character varying(6),
  id_immobile numeric(9,0),
  CONSTRAINT "u_cat_deduzioni_particella_pk" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_deduzioni_particella OWNER TO postgres;
COMMENT ON TABLE public.u_cat_deduzioni_particella IS 'Sono tabelle che derivano dalla proceduda a parte CODICI_DIRITTO, CODICI_QUALITA e CATEGORIE_CATASTALI';

--
-- u_cat_identificativi_unita_immobiliare
--
CREATE SEQUENCE public.u_cat_identificativi_unita_immobiliare_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_identificativi_unita_immobiliare_seq
  OWNER TO postgres;

CREATE TABLE public.u_cat_identificativi_unita_immobiliari
(
  cod_comune character(4),
  sezione character(1),
  id_immobile numeric(9,0),
  progressivo numeric(3,0),
  sezione_urbana character(3),
  foglio character(4),
  numero character(5),
  denominatore numeric(4,0),
  subalterno character(4),
  edificabilita character(1),
  id bigint NOT NULL DEFAULT nextval('u_cat_identificativi_unita_immobiliare_seq'::regclass),
  tipo_immobile character(1),
  foglio_orig character(4),
  numero_orig character(5),
  CONSTRAINT u_cat_identificati_unita_immobiliari_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_identificativi_unita_immobiliari
  OWNER TO postgres;

CREATE INDEX "u_cat_identificativi_unita_immobiliari_foglio_idx"
  ON public.u_cat_identificativi_unita_immobiliari
  USING btree
  (foglio COLLATE pg_catalog."default");

CREATE INDEX "u_cat_identificativi_unita_immobiliari_id_immobile_idx"
  ON public.u_cat_identificativi_unita_immobiliari
  USING btree
  (id_immobile);

CREATE INDEX "u_cat_identificativi_unita_immobiliari_numero_idx"
  ON public.u_cat_identificativi_unita_immobiliari
  USING btree
  (numero COLLATE pg_catalog."default");

CREATE INDEX "u_cat_identificativi_unita_immobiliari_sezione_idx"
  ON public.u_cat_identificativi_unita_immobiliari
  USING btree
  (sezione COLLATE pg_catalog."default");

CREATE INDEX "u_cat_identificativi_unita_immobiliari_sezione_urbana_idx"
  ON public.u_cat_identificativi_unita_immobiliari
  USING btree
  (sezione_urbana COLLATE pg_catalog."default");


--
-- u_cat_indirizzi_unita_immobiliare
--
CREATE SEQUENCE public.u_cat_indirizzi_unita_immobiliare_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_indirizzi_unita_immobiliare_id_seq OWNER TO postgres;


CREATE TABLE public.u_cat_indirizzi_unita_immobiliari
(
  cod_comune character(4),
  sezione character(1),
  id_immobile numeric(9,0),
  progressivo numeric(3,0),
  indirizzo character(50),
  civico_1 character(6),
  id bigint NOT NULL DEFAULT nextval('u_cat_indirizzi_unita_immobiliare_id_seq'::regclass),
  tipo_immobile character(1),
  civico_2 character(6),
  civico_3 character(6),
  civico_4 character(6),
  toponimo character(3),
  cod_strada character(5),
  CONSTRAINT u_cat_indirizzi_unita_immobiliare PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_indirizzi_unita_immobiliari OWNER TO postgres;


--
-- u_cat_particelle_catastali
--
CREATE SEQUENCE public.u_cat_particelle_catastali_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_particelle_catastali_id_seq OWNER TO postgres;

CREATE TABLE public.u_cat_particelle_catastali
(
  id bigint NOT NULL DEFAULT nextval('u_cat_particelle_catastali_id_seq'::regclass),
  cod_comune character(4),
  sezione character(1),
  classe character(2),
  tipo_nota_gen character(1),
  numero_nota_gen character(6),
  progressivo_nota_gen character(3),
  tipo_nota_concl character(1),
  numero_nota_concl character(6),
  progressivo_nota_concl character(3),
  partita character(7),
  foglio text,
  numero text,
  subalterno character(4),
  edificabilita character(1),
  tipo_immobile character(1),
  flag_reddito character(1),
  flag_porzione character(1),
  flag_deduzioni character(1),
  annotazione character varying(200),
  codice_causale_atto_gen character(3),
  descrizione_atto_gen character varying(100),
  codice_causale_atto_concl character varying(3),
  descrizione_atto_concl character varying(100),
  id_immobile numeric(9,0),
  progressivo numeric(3,0),
  anno_nota_gen numeric(4,0),
  anno_nota_concl numeric(4,0),
  id_mutaz_in numeric(9,0),
  id_mutaz_fin numeric(9,0),
  qualita numeric(3,0),
  ettari numeric(5,0),
  are numeric(2,0),
  reddito_dominicale_lire numeric(9,0),
  reddito_agrario_lire numeric(8,0),
  reddito_dominicale_euro numeric(9,3),
  reddito_agrario_euro numeric(9,3),
  centiare numeric(2,0),
  data_efficacia_gen character(8),
  data_registrazione_gen character(8),
  data_efficacia_concl character(8),
  data_registrazione_concl character(8),
  denominatore character(4),
  CONSTRAINT u_cat_particelle_catastali_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_particelle_catastali OWNER TO postgres;


--
-- u_cat_porzioni_particella
--
CREATE SEQUENCE public.u_cat_porzioni_particella_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_porzioni_particella_id_seq OWNER TO postgres;

CREATE TABLE public.u_cat_porzioni_particella
(
  id bigint NOT NULL DEFAULT nextval('u_cat_porzioni_particella_id_seq'::regclass),
  cod_comune character(4),
  sezione character(1),
  tipo_immobile character(1),
  progressivo character varying(3),
  identificativo_porzione character varying(2),
  classe character varying(2),
  id_immobile numeric(9,0),
  qualita numeric(3,0),
  reddito_dominicale_euro character varying(20),
  reddito_agrario_euro character varying(20),
  ettari numeric(5,0),
  are numeric(2,0),
  centiare numeric(2,0),
  CONSTRAINT u_cat_porzioni_particella_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_porzioni_particella OWNER TO postgres;


--
-- u_cat_riserve_particella
--
CREATE SEQUENCE public.u_cat_riserve_particella_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_riserve_particella_id_seq OWNER TO postgres;

CREATE TABLE public.u_cat_riserve_particella
(
  id bigint NOT NULL DEFAULT nextval('u_cat_riserve_particella_id_seq'::regclass),
  cod_comune character(4),
  sezione character(1),
  tipo_immobile character(1),
  codice_riserva character(1),
  partita_iscrizione_riserva character varying(7),
  id_immobile numeric(9,0),
  progressivo numeric(3,0),
  CONSTRAINT u_cat_riserve_particella_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_riserve_particella_id_seq OWNER TO postgres;


--
-- u_cat_riserve_unita_immobiliare
--
CREATE SEQUENCE public.u_cat_riserve_unita_immobiliare_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_riserve_unita_immobiliare_id_seq OWNER TO postgres;

CREATE TABLE public.u_cat_riserve_unita_immobiliare
(
  cod_comune character(4),
  sezione character(1),
  id_immobile numeric(9,0),
  progressivo numeric(3,0),
  id bigint NOT NULL DEFAULT nextval('u_cat_riserve_unita_immobiliare_id_seq'::regclass),
  tipo_immobile character(1),
  codice_riserva character(1),
  partita_iscrizione_riserva character(7),
  CONSTRAINT u_cat_riserve_unita_immobiliare_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_riserve_unita_immobiliare OWNER TO postgres;


--
-- u_cat_soggetti
--
CREATE SEQUENCE public.u_cat_soggetti_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_soggetti_id_seq OWNER TO postgres;


CREATE TABLE public.u_cat_soggetti
(
  id bigint NOT NULL DEFAULT nextval('u_cat_soggetti_id_seq'::regclass),
  cod_comune character(4),
  sezione character(1),
  id_soggetto numeric,
  tipo_soggetto character(1),
  cognome character varying(50),
  nome character varying(50),
  sesso character(1),
  data_nascita date,
  luogo_nascita character varying(4),
  codice_fiscale character varying(16),
  denominazione character varying(150),
  sede character varying(4),
  indicazioni_supplementari character varying(200),
  CONSTRAINT u_cat_soggetti_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_soggetti OWNER TO postgres;

CREATE INDEX u_cat_soggetti_cognome_idx ON public.u_cat_soggetti
  USING btree
  (cognome COLLATE pg_catalog."default");

CREATE INDEX u_cat_soggetti_data_nascita_idx ON public.u_cat_soggetti
  USING btree
  (data_nascita);

CREATE INDEX u_cat_soggetti_id_soggetto_idx ON public.u_cat_soggetti
  USING btree
  (id_soggetto);

CREATE INDEX u_cat_soggetti_nome_idx ON public.u_cat_soggetti
  USING btree
  (nome COLLATE pg_catalog."default");

CREATE INDEX u_cat_soggetti_tipo_soggetto_idx ON public.u_cat_soggetti
  USING btree
  (tipo_soggetto COLLATE pg_catalog."default");


--
-- u_cat_titolarita
--
CREATE SEQUENCE public.u_cat_titolarita_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_titolarita_id_seq OWNER TO postgres;

CREATE TABLE public.u_cat_titolarita
(
  id bigint NOT NULL DEFAULT nextval('u_cat_titolarita_id_seq'::regclass),
  cod_comune character(4),
  sezione character(1),
  id_soggetto numeric(9,0),
  tipo_soggetto character varying(1),
  id_immobile numeric(9,0),
  tipo_immobile character(1),
  codice_diritto character varying(3),
  titolo_non_codificato character varying(200),
  quota_numeratore character varying(9),
  quota_denominatore character varying(9),
  regime character(1),
  id_soggetto_riferimento numeric(9,0),
  data_validita_gen date,
  tipo_nota_gen character(1),
  num_nota_gen character(6),
  progr_nota_gen character(3),
  anno_nota_gen numeric(4,0),
  data_registrazione_gen date,
  partita character varying(7),
  data_validita_concl date,
  tipo_nota_concl character(1),
  num_nota_concl character(6),
  progr_nota_concl character(3),
  anno_nota_concl numeric(4,0),
  data_registrazione_concl date,
  id_mutaz_in numeric(9,0),
  id_mutaz_fin numeric(9,0),
  id_titolarita numeric,
  codice_causale_atto_gen character(3),
  descrizione_atto_gen character(100),
  codice_causale_atto_concl character(3),
  descrizione_atto_concl character(100),
  CONSTRAINT u_cat_titolarita_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_titolarita_id_seq OWNER TO postgres;

CREATE INDEX u_cat_titolarita_id_immobile_idx ON public.u_cat_titolarita
  USING btree
  (id_immobile);

CREATE INDEX u_cat_titolarita_id_soggetto_idx
  ON public.u_cat_titolarita
  USING btree
  (id_soggetto);

CREATE INDEX u_cat_titolarita_tipo_immobile_idx
  ON public.u_cat_titolarita
  USING btree
  (tipo_immobile COLLATE pg_catalog."default");

CREATE INDEX u_cat_titolarita_tipo_soggetto_idx
  ON public.u_cat_titolarita
  USING btree
  (tipo_soggetto COLLATE pg_catalog."default");



--
-- u_cat_unita_immobiliare
--
CREATE SEQUENCE public.u_cat_unita_immobiliare_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_unita_immobiliare_id_seq OWNER TO postgres;

CREATE TABLE public.u_cat_unita_immobiliare
(
  id bigint NOT NULL DEFAULT nextval('u_cat_unita_immobiliare_id_seq'::regclass),
  cod_comune character(4),
  sezione character(1),
  id_immobile numeric(9,0),
  tipo_immobile character(1),
  progressivo numeric(3,0),
  zona character(10),
  categoria character(10),
  classe character(10),
  consistenza numeric(8,2),
  superficie numeric(8,2),
  rendita_lire numeric(15,0),
  rendita_euro numeric(12,2),
  lotto character(2),
  edificio character(2),
  scala character(2),
  interno_1 character(3),
  interno_2 character(3),
  piano_1 character(4),
  piano_2 character(4),
  piano_3 character(4),
  piano_4 character(4),
  data_efficacia_gen date,
  data_registrazione_gen date,
  tipo_nota_gen character(1),
  num_nota_gen character(6),
  progr_nota_gen character(3),
  anno_nota_gen numeric(4,0),
  data_efficacia_concl date,
  data_registrazione_concl date,
  tipo_nota_concl character(1),
  num_nota_concl character(6),
  progr_nota_concl character(3),
  anno_nota_concl numeric(4,0),
  partita character(7),
  annotazione character varying(200),
  id_mutaz_in numeric(9,0),
  id_mutaz_fin numeric(9,0),
  prot_notifica character(18),
  data_notifica date,
  codice_causale_atto_gen character(3),
  descrizione_atto_gen character(100),
  codice_causale_atto_concl character(3),
  descrizione_atto_concl character(100),
  flag_classamento character(1),
  CONSTRAINT u_cat_unita_immobiliare_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_unita_immobiliare OWNER TO postgres;

CREATE INDEX u_cat_unita_immobiliare_id_immobile_idx
  ON public.u_cat_unita_immobiliare
  USING btree
  (id_immobile);

CREATE INDEX u_cat_unita_immobiliare_tipo_immobile_idx
  ON public.u_cat_unita_immobiliare
  USING btree
  (tipo_immobile COLLATE pg_catalog."default");


--
-- u_cat_utilita_unita_immobiliare
--
CREATE SEQUENCE public.u_cat_utilita_unita_immobiliare_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.u_cat_utilita_unita_immobiliare_id_seq OWNER TO postgres;

CREATE TABLE public.u_cat_utilita_unita_immobiliare
(
  id bigint NOT NULL DEFAULT nextval('u_cat_utilita_unita_immobiliare_id_seq'::regclass),
  cod_comune character(4),
  sezione character(1),
  id_immobile numeric(9,0),
  progressivo numeric(3,0),
  sezione_urbana character(3),
  foglio character(4),
  numero character(5),
  denominatore numeric(4,0),
  subalterno character(4),
  tipo_immobile character(1),
  CONSTRAINT u_cat_utilita_unita_immobiliare_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.u_cat_utilita_unita_immobiliare OWNER TO postgres;
