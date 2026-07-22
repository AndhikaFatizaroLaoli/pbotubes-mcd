--
-- PostgreSQL database dump
--

\restrict lfIEy9jsi3aFv08wd0GEFWoDlD2m1mcCzvqcF7EoFdxIqF1HUpaZ7c7fG57VU2r

-- Dumped from database version 18.4
-- Dumped by pg_dump version 18.4

-- Started on 2026-07-21 15:20:17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 867 (class 1247 OID 16565)
-- Name: order_status; Type: TYPE; Schema: public; Owner: developer
--

CREATE TYPE public.order_status AS ENUM (
    'PENDING',
    'PAID',
    'FINISHED'
);


ALTER TYPE public.order_status OWNER TO developer;

--
-- TOC entry 891 (class 1247 OID 16984)
-- Name: order_type; Type: TYPE; Schema: public; Owner: developer
--

CREATE TYPE public.order_type AS ENUM (
    'DINE_IN',
    'TAKE_AWAY'
);


ALTER TYPE public.order_type OWNER TO developer;

--
-- TOC entry 885 (class 1247 OID 18116)
-- Name: orderstatus; Type: TYPE; Schema: public; Owner: developer
--

CREATE TYPE public.orderstatus AS ENUM (
    'PENDING',
    'PAID',
    'FINISHED'
);


ALTER TYPE public.orderstatus OWNER TO developer;

--
-- TOC entry 888 (class 1247 OID 18126)
-- Name: ordertype; Type: TYPE; Schema: public; Owner: developer
--

CREATE TYPE public.ordertype AS ENUM (
    'DINE_IN',
    'TAKE_AWAY'
);


ALTER TYPE public.ordertype OWNER TO developer;

--
-- TOC entry 861 (class 1247 OID 16507)
-- Name: user_role; Type: TYPE; Schema: public; Owner: developer
--

CREATE TYPE public.user_role AS ENUM (
    'ADMIN',
    'CUSTOMER'
);


ALTER TYPE public.user_role OWNER TO developer;

--
-- TOC entry 882 (class 1247 OID 18108)
-- Name: userrole; Type: TYPE; Schema: public; Owner: developer
--

CREATE TYPE public.userrole AS ENUM (
    'ADMIN',
    'CUSTOMER'
);


ALTER TYPE public.userrole OWNER TO developer;

--
-- TOC entry 4898 (class 2605 OID 18124)
-- Name: CAST (public.orderstatus AS character varying); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (public.orderstatus AS character varying) WITH INOUT AS IMPLICIT;


--
-- TOC entry 4899 (class 2605 OID 18132)
-- Name: CAST (public.ordertype AS character varying); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (public.ordertype AS character varying) WITH INOUT AS IMPLICIT;


--
-- TOC entry 4897 (class 2605 OID 18114)
-- Name: CAST (public.userrole AS character varying); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (public.userrole AS character varying) WITH INOUT AS IMPLICIT;


--
-- TOC entry 4816 (class 2605 OID 18123)
-- Name: CAST (character varying AS public.orderstatus); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (character varying AS public.orderstatus) WITH INOUT AS IMPLICIT;


--
-- TOC entry 4817 (class 2605 OID 18131)
-- Name: CAST (character varying AS public.ordertype); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (character varying AS public.ordertype) WITH INOUT AS IMPLICIT;


--
-- TOC entry 4815 (class 2605 OID 18113)
-- Name: CAST (character varying AS public.userrole); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (character varying AS public.userrole) WITH INOUT AS IMPLICIT;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 16572)
-- Name: categories; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.categories (
    id_category integer NOT NULL,
    nama_kategori character varying(100) NOT NULL
);


ALTER TABLE public.categories OWNER TO developer;

--
-- TOC entry 221 (class 1259 OID 16571)
-- Name: categories_id_category_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.categories_id_category_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categories_id_category_seq OWNER TO developer;

--
-- TOC entry 5092 (class 0 OID 0)
-- Dependencies: 221
-- Name: categories_id_category_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.categories_id_category_seq OWNED BY public.categories.id_category;


--
-- TOC entry 224 (class 1259 OID 16581)
-- Name: menus; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.menus (
    id_menu integer NOT NULL,
    id_category integer NOT NULL,
    nama_menu character varying(150) NOT NULL,
    harga numeric(38,2) NOT NULL,
    stok integer DEFAULT 0 NOT NULL,
    gambar character varying(255),
    CONSTRAINT menus_harga_check CHECK ((harga >= (0)::numeric)),
    CONSTRAINT menus_stok_check CHECK ((stok >= 0))
);


ALTER TABLE public.menus OWNER TO developer;

--
-- TOC entry 223 (class 1259 OID 16580)
-- Name: menus_id_menu_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.menus_id_menu_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.menus_id_menu_seq OWNER TO developer;

--
-- TOC entry 5093 (class 0 OID 0)
-- Dependencies: 223
-- Name: menus_id_menu_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.menus_id_menu_seq OWNED BY public.menus.id_menu;


--
-- TOC entry 228 (class 1259 OID 16627)
-- Name: order_details; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.order_details (
    id_detail integer NOT NULL,
    id_order integer NOT NULL,
    id_menu integer NOT NULL,
    jumlah integer NOT NULL,
    subtotal numeric(38,2) NOT NULL,
    CONSTRAINT order_details_jumlah_check CHECK ((jumlah > 0)),
    CONSTRAINT order_details_subtotal_check CHECK ((subtotal >= (0)::numeric))
);


ALTER TABLE public.order_details OWNER TO developer;

--
-- TOC entry 227 (class 1259 OID 16626)
-- Name: order_details_id_detail_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.order_details_id_detail_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_details_id_detail_seq OWNER TO developer;

--
-- TOC entry 5094 (class 0 OID 0)
-- Dependencies: 227
-- Name: order_details_id_detail_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.order_details_id_detail_seq OWNED BY public.order_details.id_detail;


--
-- TOC entry 226 (class 1259 OID 16608)
-- Name: orders; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.orders (
    id_order integer NOT NULL,
    id_user integer NOT NULL,
    tanggal_order timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    nomor_antrean integer NOT NULL,
    total_harga numeric(38,2) DEFAULT 0 NOT NULL,
    status public.order_status DEFAULT 'PENDING'::public.order_status,
    tipe_pesanan public.order_type DEFAULT 'DINE_IN'::public.order_type
);


ALTER TABLE public.orders OWNER TO developer;

--
-- TOC entry 225 (class 1259 OID 16607)
-- Name: orders_id_order_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.orders_id_order_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_id_order_seq OWNER TO developer;

--
-- TOC entry 5095 (class 0 OID 0)
-- Dependencies: 225
-- Name: orders_id_order_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.orders_id_order_seq OWNED BY public.orders.id_order;


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
-- TOC entry 5096 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.users_id_user_seq OWNED BY public.users.id_user;


--
-- TOC entry 4901 (class 2604 OID 16575)
-- Name: categories id_category; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.categories ALTER COLUMN id_category SET DEFAULT nextval('public.categories_id_category_seq'::regclass);


--
-- TOC entry 4902 (class 2604 OID 16584)
-- Name: menus id_menu; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.menus ALTER COLUMN id_menu SET DEFAULT nextval('public.menus_id_menu_seq'::regclass);


--
-- TOC entry 4909 (class 2604 OID 16630)
-- Name: order_details id_detail; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.order_details ALTER COLUMN id_detail SET DEFAULT nextval('public.order_details_id_detail_seq'::regclass);


--
-- TOC entry 4904 (class 2604 OID 16611)
-- Name: orders id_order; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.orders ALTER COLUMN id_order SET DEFAULT nextval('public.orders_id_order_seq'::regclass);


--
-- TOC entry 4900 (class 2604 OID 16515)
-- Name: users id_user; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.users ALTER COLUMN id_user SET DEFAULT nextval('public.users_id_user_seq'::regclass);


--
-- TOC entry 5080 (class 0 OID 16572)
-- Dependencies: 222
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.categories (id_category, nama_kategori) FROM stdin;
1	Meals
2	Beverages
3	Desserts
4	Snacks
\.


--
-- TOC entry 5082 (class 0 OID 16581)
-- Dependencies: 224
-- Data for Name: menus; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.menus (id_menu, id_category, nama_menu, harga, stok, gambar) FROM stdin;
1	1	Big Mac	36000.00	50	/assets/images/bigmac.png
2	1	McSpicy	40000.00	40	/assets/images/mcspicy.png
3	1	Cheeseburger	30000.00	60	/assets/images/cheeseburger.png
4	2	Coca-Cola Large	15000.00	100	/assets/images/coke.png
5	2	Iced Lemon Tea	12000.00	80	/assets/images/lemontea.png
6	3	McFlurry Oreo	18000.00	30	/assets/images/mcflurry.png
7	4	French Fries Large	22000.00	70	/assets/images/fries.png
\.


--
-- TOC entry 5086 (class 0 OID 16627)
-- Dependencies: 228
-- Data for Name: order_details; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.order_details (id_detail, id_order, id_menu, jumlah, subtotal) FROM stdin;
1	1	1	1	36000.00
2	1	4	1	15000.00
3	1	7	1	22000.00
4	2	3	1	30000.00
5	3	2	1	40000.00
6	3	5	1	12000.00
7	4	1	2	72000.00
8	4	2	1	40000.00
\.


--
-- TOC entry 5084 (class 0 OID 16608)
-- Dependencies: 226
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.orders (id_order, id_user, tanggal_order, nomor_antrean, total_harga, status, tipe_pesanan) FROM stdin;
1	2	2026-07-15 21:38:33.11068	101	73000.00	FINISHED	DINE_IN
2	2	2026-07-15 21:38:33.11068	102	30000.00	PAID	DINE_IN
3	2	2026-07-15 21:38:33.11068	103	52000.00	PENDING	DINE_IN
4	6	2026-07-21 15:06:49.982858	361	112000.00	PAID	DINE_IN
\.


--
-- TOC entry 5078 (class 0 OID 16512)
-- Dependencies: 220
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.users (id_user, username, password, role) FROM stdin;
1	admin_pusat	admin123	ADMIN
4	admin	123	ADMIN
6	kiosk2	123	CUSTOMER
2	kiosk1	123	CUSTOMER
\.


--
-- TOC entry 5097 (class 0 OID 0)
-- Dependencies: 221
-- Name: categories_id_category_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.categories_id_category_seq', 6, true);


--
-- TOC entry 5098 (class 0 OID 0)
-- Dependencies: 223
-- Name: menus_id_menu_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.menus_id_menu_seq', 9, true);


--
-- TOC entry 5099 (class 0 OID 0)
-- Dependencies: 227
-- Name: order_details_id_detail_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.order_details_id_detail_seq', 8, true);


--
-- TOC entry 5100 (class 0 OID 0)
-- Dependencies: 225
-- Name: orders_id_order_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.orders_id_order_seq', 4, true);


--
-- TOC entry 5101 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.users_id_user_seq', 8, true);


--
-- TOC entry 4919 (class 2606 OID 16579)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id_category);


--
-- TOC entry 4921 (class 2606 OID 16594)
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id_menu);


--
-- TOC entry 4925 (class 2606 OID 16639)
-- Name: order_details order_details_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.order_details
    ADD CONSTRAINT order_details_pkey PRIMARY KEY (id_detail);


--
-- TOC entry 4923 (class 2606 OID 16620)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id_order);


--
-- TOC entry 4915 (class 2606 OID 16521)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id_user);


--
-- TOC entry 4917 (class 2606 OID 16523)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4926 (class 2606 OID 16595)
-- Name: menus fk_category; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT fk_category FOREIGN KEY (id_category) REFERENCES public.categories(id_category) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4928 (class 2606 OID 16645)
-- Name: order_details fk_menu; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.order_details
    ADD CONSTRAINT fk_menu FOREIGN KEY (id_menu) REFERENCES public.menus(id_menu) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4929 (class 2606 OID 16640)
-- Name: order_details fk_order; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.order_details
    ADD CONSTRAINT fk_order FOREIGN KEY (id_order) REFERENCES public.orders(id_order) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4927 (class 2606 OID 16621)
-- Name: orders fk_user; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES public.users(id_user) ON UPDATE CASCADE ON DELETE RESTRICT;


-- Completed on 2026-07-21 15:20:18

--
-- PostgreSQL database dump complete
--

\unrestrict lfIEy9jsi3aFv08wd0GEFWoDlD2m1mcCzvqcF7EoFdxIqF1HUpaZ7c7fG57VU2r

