--
-- PostgreSQL database dump
--

-- Dumped from database version 10.7
-- Dumped by pg_dump version 10.7

-- Started on 2019-10-01 16:23:07 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
--SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
--SET row_security = off;

--
-- TOC entry 8 (class 2615 OID 29861)
-- Name: schema_rubrica; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS schema_rubrica;


ALTER SCHEMA schema_rubrica OWNER TO rubrica;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 198 (class 1259 OID 29867)
-- Name: CONTATTO; Type: TABLE; Schema: schema_rubrica; Owner: rubrica
--

CREATE TABLE schema_rubrica."CONTATTO" (
    "ID" uuid NOT NULL,
    "TIPO_CONTATTO" character varying(20) NOT NULL,
    "VALORE_CONTATTO" character varying(100) NOT NULL,
    "ID_UTENTE" uuid NOT NULL
);


ALTER TABLE schema_rubrica."CONTATTO" OWNER TO rubrica;

--
-- TOC entry 197 (class 1259 OID 29862)
-- Name: UTENTE; Type: TABLE; Schema: schema_rubrica; Owner: rubrica
--

CREATE TABLE schema_rubrica."UTENTE" (
    "ID" uuid NOT NULL,
    "NOME" character varying(100) NOT NULL,
    "COGNOME" character varying(100) NOT NULL,
    "DATA_NASCITA" date NOT NULL
);


ALTER TABLE schema_rubrica."UTENTE" OWNER TO rubrica;

--
-- TOC entry 2839 (class 0 OID 29867)
-- Dependencies: 198
-- Data for Name: CONTATTO; Type: TABLE DATA; Schema: schema_rubrica; Owner: rubrica
--



--
-- TOC entry 2838 (class 0 OID 29862)
-- Dependencies: 197
-- Data for Name: UTENTE; Type: TABLE DATA; Schema: schema_rubrica; Owner: rubrica
--



--
-- TOC entry 2715 (class 2606 OID 29871)
-- Name: CONTATTO CONTATTO_pkey; Type: CONSTRAINT; Schema: schema_rubrica; Owner: rubrica
--

ALTER TABLE ONLY schema_rubrica."CONTATTO"
    ADD CONSTRAINT "CONTATTO_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 2713 (class 2606 OID 29866)
-- Name: UTENTE UTENTE_pkey; Type: CONSTRAINT; Schema: schema_rubrica; Owner: rubrica
--

ALTER TABLE ONLY schema_rubrica."UTENTE"
    ADD CONSTRAINT "UTENTE_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 2716 (class 2606 OID 29872)
-- Name: CONTATTO FK_UTENTE_CONTATTO; Type: FK CONSTRAINT; Schema: schema_rubrica; Owner: rubrica
--

ALTER TABLE ONLY schema_rubrica."CONTATTO"
    ADD CONSTRAINT "FK_UTENTE_CONTATTO" FOREIGN KEY ("ID_UTENTE") REFERENCES schema_rubrica."UTENTE"("ID");


-- Completed on 2019-10-01 16:23:07 CEST

--
-- PostgreSQL database dump complete
--

