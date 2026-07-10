--
-- PostgreSQL database dump
--

\restrict JrkYUoPt4CFamsiGNQQmmUxgdgsGuH4b1bUeJEkNl3GjP7hYBnCOoNpy8nEMvtQ

-- Dumped from database version 18.4
-- Dumped by pg_dump version 18.4

-- Started on 2026-07-10 16:26:39

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 853 (class 1247 OID 16507)
-- Name: user_role; Type: TYPE; Schema: public; Owner: developer
--

CREATE TYPE public.user_role AS ENUM (
    'ADMIN',
    'CUSTOMER'
);


ALTER TYPE public.user_role OWNER TO developer;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16512)
-- Name: users; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.users (
    id_user integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    role public.user_role NOT NULL
);


ALTER TABLE public.users OWNER TO developer;

--
-- TOC entry 219 (class 1259 OID 16511)
-- Name: users_id_user_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.users_id_user_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_user_seq OWNER TO developer;

--
-- TOC entry 5018 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.users_id_user_seq OWNED BY public.users.id_user;


--
-- TOC entry 4859 (class 2604 OID 16515)
-- Name: users id_user; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.users ALTER COLUMN id_user SET DEFAULT nextval('public.users_id_user_seq'::regclass);


--
-- TOC entry 5012 (class 0 OID 16512)
-- Dependencies: 220
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.users (id_user, username, password, role) FROM stdin;
1	admin_pusat	admin123	ADMIN
2	customer_01	customer123	CUSTOMER
\.


--
-- TOC entry 5019 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.users_id_user_seq', 3, true);


--
-- TOC entry 4861 (class 2606 OID 16521)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id_user);


--
-- TOC entry 4863 (class 2606 OID 16523)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


-- Completed on 2026-07-10 16:26:40

--
-- PostgreSQL database dump complete
--

\unrestrict JrkYUoPt4CFamsiGNQQmmUxgdgsGuH4b1bUeJEkNl3GjP7hYBnCOoNpy8nEMvtQ

