--
-- PostgreSQL database dump
--

-- Dumped from database version 15.0
-- Dumped by pg_dump version 15.0

-- Started on 2023-03-31 17:44:19

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 98620)
-- Name: app_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.app_user (
    id character varying(255) NOT NULL,
    authorities smallint[],
    email character varying(255) NOT NULL,
    firstname character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    password character varying(255) NOT NULL
);


ALTER TABLE public.app_user OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 98627)
-- Name: collaboration_request; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.collaboration_request (
    id character varying(255) NOT NULL,
    additional_contact character varying(255),
    message character varying(255) NOT NULL,
    collaboration_response_id character varying(255),
    idea_id character varying(255) NOT NULL,
    requester_id character varying(255) NOT NULL
);


ALTER TABLE public.collaboration_request OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 98634)
-- Name: collaboration_response; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.collaboration_response (
    id character varying(255) NOT NULL,
    additional_contact character varying(255),
    message character varying(255) NOT NULL,
    status smallint NOT NULL
);


ALTER TABLE public.collaboration_response OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 98641)
-- Name: domain; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.domain (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.domain OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 98648)
-- Name: idea; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.idea (
    id character varying(255) NOT NULL,
    date timestamp(6) without time zone NOT NULL,
    description text NOT NULL,
    is_in_progress boolean NOT NULL,
    is_paid boolean NOT NULL,
    is_private boolean NOT NULL,
    purpose character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    icon_url_id character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL
);


ALTER TABLE public.idea OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 98655)
-- Name: idea_domain; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.idea_domain (
    idea_id character varying(255) NOT NULL,
    url_id character varying(255) NOT NULL
);


ALTER TABLE public.idea_domain OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 98662)
-- Name: idea_technology; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.idea_technology (
    idea_id character varying(255) NOT NULL,
    technology_id character varying(255) NOT NULL
);


ALTER TABLE public.idea_technology OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 98669)
-- Name: idea_topic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.idea_topic (
    idea_id character varying(255) NOT NULL,
    topic_id character varying(255) NOT NULL
);


ALTER TABLE public.idea_topic OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 98676)
-- Name: moderator; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.moderator (
    id character varying(255) NOT NULL,
    app_user_id character varying(255) NOT NULL
);


ALTER TABLE public.moderator OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 98683)
-- Name: owner; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.owner (
    id character varying(255) NOT NULL,
    app_user_id character varying(255) NOT NULL
);


ALTER TABLE public.owner OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 98690)
-- Name: reaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reaction (
    id character varying(255) NOT NULL,
    date timestamp(6) without time zone NOT NULL,
    reaction_type smallint NOT NULL,
    idea_id character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL
);


ALTER TABLE public.reaction OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 98711)
-- Name: regular_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.regular_user (
    id character varying(255) NOT NULL,
    app_user_id character varying(255) NOT NULL,
    verified_company boolean NOT NULL,
    verification_request_id character varying(255)
);


ALTER TABLE public.regular_user OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 98697)
-- Name: regular_user_domain; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.regular_user_domain (
    app_user_id character varying(255) NOT NULL,
    domain_id character varying(255) NOT NULL
);


ALTER TABLE public.regular_user_domain OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 98704)
-- Name: regular_user_topic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.regular_user_topic (
    app_user_id character varying(255) NOT NULL,
    topic_id character varying(255) NOT NULL
);


ALTER TABLE public.regular_user_topic OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 98718)
-- Name: technology; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.technology (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.technology OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 98725)
-- Name: topic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.topic (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.topic OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 98732)
-- Name: url; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.url (
    id character varying(255) NOT NULL,
    url character varying(255) NOT NULL,
    preset_icon boolean NOT NULL,
    supported_idea_id character varying(255)
);


ALTER TABLE public.url OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 98739)
-- Name: verification_request; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.verification_request (
    company_oui_number character varying(255) NOT NULL,
    company_name character varying(255) NOT NULL,
    explanation character varying(255) NOT NULL,
    status smallint NOT NULL,
    supporting_document_url character varying(255) NOT NULL
);


ALTER TABLE public.verification_request OWNER TO postgres;

--
-- TOC entry 3456 (class 0 OID 98620)
-- Dependencies: 214
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.app_user (id, authorities, email, firstname, lastname, password) FROM stdin;
e9e9d24d-6f06-4e39-b078-61de39f08e95	{0}	robsab@gmail.com	Robert	Sabourin	$2a$10$GvLbzfNAQ/LJ.ctsYENOLeowXBJZOx00ygJWIOT57LuRfg216YauW
746b7923-c74d-4ad0-8fd7-59d818c7675e	{1}	neeraj.katiyar@mail.mcgill.ca	Neeraj	Katiyar	$2a$10$sFDaRz9t4IUJcteGyERP3uChg2W9yIXZ4oYCbnoGsHGBwGXbHDiSi
9f815de8-1f5e-4203-99a2-ebbb32fe070f	{2}	siger.ma@mail.mcgill.ca	Siger	Ma	$2a$10$SceUV8o.vq9nJJuTidCrB.LDHRn7Gl01aKoAwJsajE1KuGkxK3OqK
fa15b0af-2b8f-45ce-a4d4-d11061ff1610	{2}	wassim.jabbour@mail.mcgill.ca	Wassim	Jabbour	$2a$10$cJy/TrtStSMcfJouENnie.JavZDKsJco5TfvADloFpiyjUbxrq02.
321758d6-bf0d-4056-8bf8-946c76865705	{2}	shidan.javaheri@mail.mcgill.ca	Shidan	Javaheri	$2a$10$lx73vJgwF4hYebsK4zxjPeMEa2MEer9EYT.cLCLTE84.0iH1FEDb2
7346e556-9492-430a-af4e-a50c91c398d1	{2}	sasha.denouvilliez-pech@mail.mcgill.ca	Sasha	Denouvilliez-Pech	$2a$10$xAocwU23TkoVPytSqzUd8uiv62UXRmOPvYQ9CclUVzIDzSR00j54u
d6c1aa33-5abd-40e1-b46b-e5a1c542cd82	{2}	enzo.benoit-jeannin@mail.mcgill.ca	Enzo	Benoit-Jeannin	$2a$10$M7wUeotrPBGJxgbrHOivrufZ3svC.bk1MfvDP1GHup39nR3iFVXB.
3788a93d-9dc8-435c-8c0f-3ceb1ec7337b	{2}	adam.kazma@mail.mcgill.ca	Adam	Kazma	$2a$10$3QQyhqCMKIKzHl9nlcR14exTGGJxaUatxwnL3vt6eBazvFE7.nXO.
8c899d44-6c9b-4fc7-9790-b587c7013e16	{2}	athmane.benarous@mail.mcgill.ca	Athmane	Benarous	$2a$10$0Ukp01nS7gUmrqsZQy5o6OafkjxHum7IyWW83LSWXVDAiJqNJOHry
889eff24-d555-41ea-9012-891effa83237	{2}	ramin.akhavan@mail.mcgill.ca	Ramin	Akhavan-Sarraf	$2a$10$Jyq1YAQRzEPLsz06hidWEuNAAdB49ayctE2BZWkxFfDZWOgR87L2q
675b41ce-a9e0-4023-b5d5-2c4f56c422c9	{2}	thibaut.baguette@mail.mcgill.ca	Thibaut	Baguette	$2a$10$N1jYrM48Tz9T5sSCQZGgwuHZ/8bYB6vMRAd58v74KUphQKWzSdSs2
a054f5d6-dd43-4c4c-bf25-cc65baeb8294	{2}	abdullah.arafat@mail.mcgill.ca	Abe	Arafat	$2a$10$runSAXI0T6hMmAOm1O/qTOBy78NZ.zQ1iiR1WkM7b67c9TYyAVjsy
\.

--
-- TOC entry 3457 (class 0 OID 98627)
-- Dependencies: 215
-- Data for Name: collaboration_request; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.collaboration_request (id, additional_contact, message, collaboration_response_id, idea_id, requester_id) FROM stdin;
e9f4c833-11fc-4d06-9f09-308e9f144686	\N	I love the idea! I would love to join the team and work on this project.	9e49e5a7-fac9-43d2-91d6-a13d9db028e4	943cc4bf-f998-4f0d-bf41-9029180f2e65	5b8c95da-b341-48c7-939a-2ff6a5026963
5d696e6f-10d4-4654-a301-971bb309d406	\N	I love the idea! I would love to join the team and work on this project.	8a5ad5c5-c341-4096-8034-f297edd41b4a	943cc4bf-f998-4f0d-bf41-9029180f2e65	ef20cbca-48c1-4a8b-9a2a-6fd933dc1eb2
d2b47a1b-74ce-461d-82c4-40e4a9d6e45f	\N	I love the idea! I would love to join the team and work on this project.	552e67ef-9c65-4657-bc53-dfb12c21c962	943cc4bf-f998-4f0d-bf41-9029180f2e65	d61af7e4-c420-432a-a499-ca7d6d5cb37b
f292e515-ab0b-4788-a9a8-db3dcc78c742	\N	I love the idea! I would love to join the team and work on this project.	18e140b0-4bbe-4db5-af8f-6c638900a9fa	943cc4bf-f998-4f0d-bf41-9029180f2e65	3779d64c-ead4-4132-931f-48b23d265313
f44a4020-777e-4fd0-8beb-34e27bacd76d	\N	I love the idea! I would love to join the team and work on this project.	7d28e10e-354f-49b4-ae81-a6ee2eeaec13	943cc4bf-f998-4f0d-bf41-9029180f2e65	fca5877e-6662-42ce-b2bb-0b43f0f2ec28
3442f8d7-e6d6-4dc8-8a96-bf963fdaae63	\N	I love the idea! I would love to join the team and work on this project.	399822d5-24a5-4760-a9a3-7d64f034eb5d	943cc4bf-f998-4f0d-bf41-9029180f2e65	ab905bad-06cf-4992-86ce-41a37b95e6b1
70a10665-6dd0-4c32-964f-f1f8a1da0bc6	\N	I love the idea! I would love to join the team and work on this project.	6a3123cd-c0c8-4fc1-9748-e5b1c5466096	943cc4bf-f998-4f0d-bf41-9029180f2e65	38a6416d-ac0c-42b8-9097-b2e879f08d58
8a43012a-e5e5-4af9-8d64-af19c4dac412	\N	I love the idea! I would love to join the team and work on this project.	e7a8498b-d0c3-42d5-b363-84c48add43e2	943cc4bf-f998-4f0d-bf41-9029180f2e65	5fb50b22-43b3-49f3-b269-7b685860e6ed
bd273750-1888-4fc3-8393-f6471b65b59b	\N	I love the idea! I would love to join the team and work on this project.	44fff547-21b5-47a6-9e84-6f368a6ed1c0	943cc4bf-f998-4f0d-bf41-9029180f2e65	38e9e27e-78bd-4512-bed5-e41d1bd45ee7
\.


--
-- TOC entry 3458 (class 0 OID 98634)
-- Dependencies: 216
-- Data for Name: collaboration_response; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.collaboration_response (id, additional_contact, message, status) FROM stdin;
9e49e5a7-fac9-43d2-91d6-a13d9db028e4	Slack invite link: https://join.slack.com/t/ecse428kings/shared_invite/zt-1scffp59h-TNdqBVMqGjnhmTBjHCREsw	Welcome to the team! Great to have you	0
8a5ad5c5-c341-4096-8034-f297edd41b4a	Slack invite link: https://join.slack.com/t/ecse428kings/shared_invite/zt-1scffp59h-TNdqBVMqGjnhmTBjHCREsw	Welcome to the team! Great to have you	0
552e67ef-9c65-4657-bc53-dfb12c21c962	Slack invite link: https://join.slack.com/t/ecse428kings/shared_invite/zt-1scffp59h-TNdqBVMqGjnhmTBjHCREsw	Welcome to the team! Great to have you	0
18e140b0-4bbe-4db5-af8f-6c638900a9fa	Slack invite link: https://join.slack.com/t/ecse428kings/shared_invite/zt-1scffp59h-TNdqBVMqGjnhmTBjHCREsw	Welcome to the team! Great to have you	0
7d28e10e-354f-49b4-ae81-a6ee2eeaec13	Slack invite link: https://join.slack.com/t/ecse428kings/shared_invite/zt-1scffp59h-TNdqBVMqGjnhmTBjHCREsw	Welcome to the team! Great to have you	0
399822d5-24a5-4760-a9a3-7d64f034eb5d	Slack invite link: https://join.slack.com/t/ecse428kings/shared_invite/zt-1scffp59h-TNdqBVMqGjnhmTBjHCREsw	Welcome to the team! Great to have you	0
6a3123cd-c0c8-4fc1-9748-e5b1c5466096	Slack invite link: https://join.slack.com/t/ecse428kings/shared_invite/zt-1scffp59h-TNdqBVMqGjnhmTBjHCREsw	Welcome to the team! Great to have you	0
e7a8498b-d0c3-42d5-b363-84c48add43e2	Slack invite link: https://join.slack.com/t/ecse428kings/shared_invite/zt-1scffp59h-TNdqBVMqGjnhmTBjHCREsw	Welcome to the team! Great to have you	0
44fff547-21b5-47a6-9e84-6f368a6ed1c0	Slack invite link: https://join.slack.com/t/ecse428kings/shared_invite/zt-1scffp59h-TNdqBVMqGjnhmTBjHCREsw	Welcome to the team! Great to have you	0
\.


--
-- TOC entry 3459 (class 0 OID 98641)
-- Dependencies: 217
-- Data for Name: domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.domain (id, name) FROM stdin;
bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a	Software Engineering
\.


--
-- TOC entry 3460 (class 0 OID 98648)
-- Dependencies: 218
-- Data for Name: idea; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.idea (id, date, description, is_in_progress, is_paid, is_private, purpose, title, icon_url_id, user_id) FROM stdin;
943cc4bf-f998-4f0d-bf41-9029180f2e65	2023-03-31 14:55:43.83	Our project is to develop a 2-sided marketplace platform for sharing project ideas. On one side, users with ideas, including companies, can post their projects or problems that they would like help completing, optionally with the price they’re willing to pay. On the other side, motivated creative minds can browse posted projects and have discussions to clarify details before taking on work, which implies the existence of a chat system enabling communication. The idea is to give people a platform to connect with ideas that address practical needs in industry or their communities, inspiring projects that are built purposefully.	t	f	f	Create a platform for people to share project ideas and connect	Purposeful	1e721522-78f2-41b8-bd01-46a683fc044c	ff3539c5-ec4d-490e-ac3c-2a9059d5bd59
979bd3e3-8580-420d-9a75-345fc66993cc	2023-03-31 15:00:31.446	Description of the idea	f	f	f	Showcase a fun project	Siger's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	5b8c95da-b341-48c7-939a-2ff6a5026963
bd08a6eb-6af3-447b-9175-3c2d5e68c863	2023-03-31 15:02:06.343	Description of the idea	f	f	f	Showcase a fun project	Wassim's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	d61af7e4-c420-432a-a499-ca7d6d5cb37b
c250d964-7236-4b94-9bc5-4a200ca05478	2023-03-31 15:03:33.509	Description of the idea	f	f	f	Showcase a fun project	Shidan's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	ff3539c5-ec4d-490e-ac3c-2a9059d5bd59
e5665c43-9f98-4451-8d17-b6733ed75df3	2023-03-31 15:04:45	Description of the idea	f	f	f	Showcase a fun project	Sasha's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	ef20cbca-48c1-4a8b-9a2a-6fd933dc1eb2
f92b2ed1-d503-42d2-99ce-0d643228e973	2023-03-31 15:06:43.201	Description of the idea	f	f	f	Showcase a fun project	Enzo's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	3779d64c-ead4-4132-931f-48b23d265313
fcb31db2-bbb5-47f4-a213-ddd75741f0ee	2023-03-31 16:40:17.314	Description of the idea	f	f	f	Showcase a fun project	Adam's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	fca5877e-6662-42ce-b2bb-0b43f0f2ec28
f2d98f96-8e34-4e63-be0c-01571f52fd89	2023-03-31 16:41:40.458	Description of the idea	f	f	f	Showcase a fun project	Athmane's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	ab905bad-06cf-4992-86ce-41a37b95e6b1
75fac32b-9ae5-4402-b83a-fa8335c83630	2023-03-31 16:42:33.275	Description of the idea	f	f	f	Showcase a fun project	Ramin's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	38a6416d-ac0c-42b8-9097-b2e879f08d58
54b877c8-eaed-47c5-9a31-459a1278bdc2	2023-03-31 16:43:39.067	Description of the idea	f	f	f	Showcase a fun project	Thibaut's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	5fb50b22-43b3-49f3-b269-7b685860e6ed
320565d9-4cc6-4919-9f4e-48073bc78052	2023-03-31 16:44:50.691	Description of the idea	f	f	f	Showcase a fun project	Abe's wonderful idea	1e721522-78f2-41b8-bd01-46a683fc044c	38e9e27e-78bd-4512-bed5-e41d1bd45ee7
\.


--
-- TOC entry 3461 (class 0 OID 98655)
-- Dependencies: 219
-- Data for Name: idea_domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.idea_domain (idea_id, url_id) FROM stdin;
943cc4bf-f998-4f0d-bf41-9029180f2e65	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
979bd3e3-8580-420d-9a75-345fc66993cc	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
bd08a6eb-6af3-447b-9175-3c2d5e68c863	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
c250d964-7236-4b94-9bc5-4a200ca05478	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
e5665c43-9f98-4451-8d17-b6733ed75df3	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
f92b2ed1-d503-42d2-99ce-0d643228e973	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
fcb31db2-bbb5-47f4-a213-ddd75741f0ee	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
f2d98f96-8e34-4e63-be0c-01571f52fd89	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
75fac32b-9ae5-4402-b83a-fa8335c83630	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
54b877c8-eaed-47c5-9a31-459a1278bdc2	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
320565d9-4cc6-4919-9f4e-48073bc78052	bc3d6f05-c52c-4b5a-a4a7-7f0f0451766a
\.


--
-- TOC entry 3462 (class 0 OID 98662)
-- Dependencies: 220
-- Data for Name: idea_technology; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.idea_technology (idea_id, technology_id) FROM stdin;
943cc4bf-f998-4f0d-bf41-9029180f2e65	47b36944-1bd8-4cc6-abb1-722fc26763a6
943cc4bf-f998-4f0d-bf41-9029180f2e65	f2c7400f-8dac-4a4e-8cdf-0269c2e9b912
943cc4bf-f998-4f0d-bf41-9029180f2e65	205ded72-6932-46c7-9313-5446108ea05b
943cc4bf-f998-4f0d-bf41-9029180f2e65	8847a8b1-a329-455a-889a-80702a811236
943cc4bf-f998-4f0d-bf41-9029180f2e65	14e99754-1c08-4bc4-babf-8605214e3955
943cc4bf-f998-4f0d-bf41-9029180f2e65	c477e156-fdf2-4bad-988f-b6ce7115fc6f
943cc4bf-f998-4f0d-bf41-9029180f2e65	8d089089-0f01-407b-bf6c-f5ddd2fe0ff1
943cc4bf-f998-4f0d-bf41-9029180f2e65	178474e0-b112-43ca-bf2c-db82ab616fe3
943cc4bf-f998-4f0d-bf41-9029180f2e65	014e1908-ea61-40b8-a0d7-ab6999f4d911
943cc4bf-f998-4f0d-bf41-9029180f2e65	41ce1dc0-1af7-4bf3-bbb5-061922c36833
979bd3e3-8580-420d-9a75-345fc66993cc	5a634239-d2b3-4e94-acb3-bd6b41459dfb
979bd3e3-8580-420d-9a75-345fc66993cc	9a4df873-4a09-42ad-add5-a608beb63bf9
979bd3e3-8580-420d-9a75-345fc66993cc	2b334424-9f4b-4bc0-9b98-07726ae37014
bd08a6eb-6af3-447b-9175-3c2d5e68c863	5a634239-d2b3-4e94-acb3-bd6b41459dfb
bd08a6eb-6af3-447b-9175-3c2d5e68c863	2b334424-9f4b-4bc0-9b98-07726ae37014
bd08a6eb-6af3-447b-9175-3c2d5e68c863	9a4df873-4a09-42ad-add5-a608beb63bf9
c250d964-7236-4b94-9bc5-4a200ca05478	5a634239-d2b3-4e94-acb3-bd6b41459dfb
c250d964-7236-4b94-9bc5-4a200ca05478	9a4df873-4a09-42ad-add5-a608beb63bf9
c250d964-7236-4b94-9bc5-4a200ca05478	2b334424-9f4b-4bc0-9b98-07726ae37014
e5665c43-9f98-4451-8d17-b6733ed75df3	5a634239-d2b3-4e94-acb3-bd6b41459dfb
e5665c43-9f98-4451-8d17-b6733ed75df3	2b334424-9f4b-4bc0-9b98-07726ae37014
e5665c43-9f98-4451-8d17-b6733ed75df3	9a4df873-4a09-42ad-add5-a608beb63bf9
f92b2ed1-d503-42d2-99ce-0d643228e973	9a4df873-4a09-42ad-add5-a608beb63bf9
f92b2ed1-d503-42d2-99ce-0d643228e973	5a634239-d2b3-4e94-acb3-bd6b41459dfb
f92b2ed1-d503-42d2-99ce-0d643228e973	2b334424-9f4b-4bc0-9b98-07726ae37014
fcb31db2-bbb5-47f4-a213-ddd75741f0ee	2b334424-9f4b-4bc0-9b98-07726ae37014
fcb31db2-bbb5-47f4-a213-ddd75741f0ee	9a4df873-4a09-42ad-add5-a608beb63bf9
fcb31db2-bbb5-47f4-a213-ddd75741f0ee	5a634239-d2b3-4e94-acb3-bd6b41459dfb
f2d98f96-8e34-4e63-be0c-01571f52fd89	2b334424-9f4b-4bc0-9b98-07726ae37014
f2d98f96-8e34-4e63-be0c-01571f52fd89	5a634239-d2b3-4e94-acb3-bd6b41459dfb
f2d98f96-8e34-4e63-be0c-01571f52fd89	9a4df873-4a09-42ad-add5-a608beb63bf9
75fac32b-9ae5-4402-b83a-fa8335c83630	2b334424-9f4b-4bc0-9b98-07726ae37014
75fac32b-9ae5-4402-b83a-fa8335c83630	5a634239-d2b3-4e94-acb3-bd6b41459dfb
75fac32b-9ae5-4402-b83a-fa8335c83630	9a4df873-4a09-42ad-add5-a608beb63bf9
54b877c8-eaed-47c5-9a31-459a1278bdc2	5a634239-d2b3-4e94-acb3-bd6b41459dfb
54b877c8-eaed-47c5-9a31-459a1278bdc2	2b334424-9f4b-4bc0-9b98-07726ae37014
54b877c8-eaed-47c5-9a31-459a1278bdc2	9a4df873-4a09-42ad-add5-a608beb63bf9
320565d9-4cc6-4919-9f4e-48073bc78052	2b334424-9f4b-4bc0-9b98-07726ae37014
320565d9-4cc6-4919-9f4e-48073bc78052	9a4df873-4a09-42ad-add5-a608beb63bf9
320565d9-4cc6-4919-9f4e-48073bc78052	5a634239-d2b3-4e94-acb3-bd6b41459dfb
\.


--
-- TOC entry 3463 (class 0 OID 98669)
-- Dependencies: 221
-- Data for Name: idea_topic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.idea_topic (idea_id, topic_id) FROM stdin;
943cc4bf-f998-4f0d-bf41-9029180f2e65	91dce191-c529-4686-a9da-6f56cf9016d1
943cc4bf-f998-4f0d-bf41-9029180f2e65	9ecf6730-6aa5-453a-bdb1-279859549d48
979bd3e3-8580-420d-9a75-345fc66993cc	2ecd210c-6a36-468c-b72c-51a8d47519fb
bd08a6eb-6af3-447b-9175-3c2d5e68c863	a046dd14-fdd2-40d9-85a6-b2ccb5b3d91d
c250d964-7236-4b94-9bc5-4a200ca05478	0be55590-0bfd-4b8f-8b86-c19b0f153cdd
e5665c43-9f98-4451-8d17-b6733ed75df3	8960142e-7e81-4122-9a6a-a3922852c800
f92b2ed1-d503-42d2-99ce-0d643228e973	9e1f710e-df50-4d79-8d87-6c4d2eb10e09
fcb31db2-bbb5-47f4-a213-ddd75741f0ee	e840055e-718c-42c4-804f-052d06fa35a8
f2d98f96-8e34-4e63-be0c-01571f52fd89	a046dd14-fdd2-40d9-85a6-b2ccb5b3d91d
f2d98f96-8e34-4e63-be0c-01571f52fd89	9e1f710e-df50-4d79-8d87-6c4d2eb10e09
75fac32b-9ae5-4402-b83a-fa8335c83630	0be55590-0bfd-4b8f-8b86-c19b0f153cdd
75fac32b-9ae5-4402-b83a-fa8335c83630	9ecf6730-6aa5-453a-bdb1-279859549d48
54b877c8-eaed-47c5-9a31-459a1278bdc2	91dce191-c529-4686-a9da-6f56cf9016d1
54b877c8-eaed-47c5-9a31-459a1278bdc2	8960142e-7e81-4122-9a6a-a3922852c800
320565d9-4cc6-4919-9f4e-48073bc78052	0be55590-0bfd-4b8f-8b86-c19b0f153cdd
320565d9-4cc6-4919-9f4e-48073bc78052	e840055e-718c-42c4-804f-052d06fa35a8
\.


--
-- TOC entry 3464 (class 0 OID 98676)
-- Dependencies: 222
-- Data for Name: moderator; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.moderator (id, app_user_id) FROM stdin;
0c92c140-954e-411a-83f3-295fa84b31ff	746b7923-c74d-4ad0-8fd7-59d818c7675e
\.


--
-- TOC entry 3465 (class 0 OID 98683)
-- Dependencies: 223
-- Data for Name: owner; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.owner (id, app_user_id) FROM stdin;
e126e1bd-c02d-4bd3-8787-5601d45cc551	e9e9d24d-6f06-4e39-b078-61de39f08e95
\.


--
-- TOC entry 3466 (class 0 OID 98690)
-- Dependencies: 224
-- Data for Name: reaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reaction (id, date, reaction_type, idea_id, user_id) FROM stdin;
d4e64d2f-87a4-4c52-851d-bfff27f449de	2023-03-31 16:48:47.591	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	5b8c95da-b341-48c7-939a-2ff6a5026963
5863c358-10fb-4006-9b6e-e60a0f6ccd57	2023-03-31 16:49:36.043	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	d61af7e4-c420-432a-a499-ca7d6d5cb37b
467beb36-35a2-4301-acad-d04ab332077b	2023-03-31 16:50:17.296	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	ff3539c5-ec4d-490e-ac3c-2a9059d5bd59
0bdb9e30-b5ed-4c7c-bbea-0033f35038a2	2023-03-31 16:50:51.964	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	ef20cbca-48c1-4a8b-9a2a-6fd933dc1eb2
8e903408-9458-454f-a3bd-5e7018aaa97a	2023-03-31 16:51:25.762	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	3779d64c-ead4-4132-931f-48b23d265313
b423f547-1f83-4ff5-adef-96166fca3b92	2023-03-31 16:51:58.044	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	fca5877e-6662-42ce-b2bb-0b43f0f2ec28
06d6297e-213f-474e-8d6b-d68b6c553cc8	2023-03-31 16:52:29.111	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	ab905bad-06cf-4992-86ce-41a37b95e6b1
79c08eff-bd47-496e-91ed-9242c6690eed	2023-03-31 16:53:06.828	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	38a6416d-ac0c-42b8-9097-b2e879f08d58
5311891a-52b0-48fb-88fe-1f280d24134c	2023-03-31 16:53:33.043	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	5fb50b22-43b3-49f3-b269-7b685860e6ed
7fee6d5b-2314-4b42-9816-4e8c17e7a523	2023-03-31 16:54:00.686	0	943cc4bf-f998-4f0d-bf41-9029180f2e65	38e9e27e-78bd-4512-bed5-e41d1bd45ee7
\.


--
-- TOC entry 3469 (class 0 OID 98711)
-- Dependencies: 227
-- Data for Name: regular_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.regular_user (id, app_user_id, verified_company, verification_request_id) FROM stdin;
5b8c95da-b341-48c7-939a-2ff6a5026963	9f815de8-1f5e-4203-99a2-ebbb32fe070f	f	\N
d61af7e4-c420-432a-a499-ca7d6d5cb37b	fa15b0af-2b8f-45ce-a4d4-d11061ff1610	f	\N
ff3539c5-ec4d-490e-ac3c-2a9059d5bd59	321758d6-bf0d-4056-8bf8-946c76865705	f	\N
ef20cbca-48c1-4a8b-9a2a-6fd933dc1eb2	7346e556-9492-430a-af4e-a50c91c398d1	f	\N
3779d64c-ead4-4132-931f-48b23d265313	d6c1aa33-5abd-40e1-b46b-e5a1c542cd82	f	\N
fca5877e-6662-42ce-b2bb-0b43f0f2ec28	3788a93d-9dc8-435c-8c0f-3ceb1ec7337b	f	\N
ab905bad-06cf-4992-86ce-41a37b95e6b1	8c899d44-6c9b-4fc7-9790-b587c7013e16	f	\N
38a6416d-ac0c-42b8-9097-b2e879f08d58	889eff24-d555-41ea-9012-891effa83237	f	\N
5fb50b22-43b3-49f3-b269-7b685860e6ed	675b41ce-a9e0-4023-b5d5-2c4f56c422c9	f	\N
38e9e27e-78bd-4512-bed5-e41d1bd45ee7	a054f5d6-dd43-4c4c-bf25-cc65baeb8294	f	\N
\.


--
-- TOC entry 3467 (class 0 OID 98697)
-- Dependencies: 225
-- Data for Name: regular_user_domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.regular_user_domain (app_user_id, domain_id) FROM stdin;
\.


--
-- TOC entry 3468 (class 0 OID 98704)
-- Dependencies: 226
-- Data for Name: regular_user_topic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.regular_user_topic (app_user_id, topic_id) FROM stdin;
\.


--
-- TOC entry 3470 (class 0 OID 98718)
-- Dependencies: 228
-- Data for Name: technology; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.technology (id, name) FROM stdin;
14e99754-1c08-4bc4-babf-8605214e3955	Spring boot
5a634239-d2b3-4e94-acb3-bd6b41459dfb	Autocad
2b334424-9f4b-4bc0-9b98-07726ae37014	Microsoft Office Suite
9a4df873-4a09-42ad-add5-a608beb63bf9	Adobe Suite
8d089089-0f01-407b-bf6c-f5ddd2fe0ff1	Java
205ded72-6932-46c7-9313-5446108ea05b	JavaScript
f2c7400f-8dac-4a4e-8cdf-0269c2e9b912	Node.Js
47b36944-1bd8-4cc6-abb1-722fc26763a6	React.Js
178474e0-b112-43ca-bf2c-db82ab616fe3	Next.Js
41ce1dc0-1af7-4bf3-bbb5-061922c36833	Gradle
014e1908-ea61-40b8-a0d7-ab6999f4d911	Git
8847a8b1-a329-455a-889a-80702a811236	GitHub
c477e156-fdf2-4bad-988f-b6ce7115fc6f	PostgreSQL
\.


--
-- TOC entry 3471 (class 0 OID 98725)
-- Dependencies: 229
-- Data for Name: topic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.topic (id, name) FROM stdin;
8960142e-7e81-4122-9a6a-a3922852c800	Music
2ecd210c-6a36-468c-b72c-51a8d47519fb	Medicine
0be55590-0bfd-4b8f-8b86-c19b0f153cdd	Biology
e840055e-718c-42c4-804f-052d06fa35a8	English
9e1f710e-df50-4d79-8d87-6c4d2eb10e09	Finance
a046dd14-fdd2-40d9-85a6-b2ccb5b3d91d	Sports
91dce191-c529-4686-a9da-6f56cf9016d1	Education
9ecf6730-6aa5-453a-bdb1-279859549d48	Technology
\.


--
-- TOC entry 3472 (class 0 OID 98732)
-- Dependencies: 230
-- Data for Name: url; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.url (id, url, preset_icon, supported_idea_id) FROM stdin;
1e721522-78f2-41b8-bd01-46a683fc044c	https://images.unsplash.com/photo-1550745165-9bc0b252726f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80	t	\N
73f28fbd-f0e9-4c3d-91dd-e39a80bbff1e	https://source.unsplash.com/random/purposeful	f	943cc4bf-f998-4f0d-bf41-9029180f2e65
7bb4daa2-f8f8-4c40-bfa3-43905bf05ef4	https://source.unsplash.com/random/purposeful1	f	943cc4bf-f998-4f0d-bf41-9029180f2e65
5b3d43b9-26d0-4fdd-8a24-dd8d68e798f2	https://source.unsplash.com/random/purposeful2	f	943cc4bf-f998-4f0d-bf41-9029180f2e65
8327197b-f4cd-4b69-b77d-d50203d338fc	https://source.unsplash.com/random/Siger	f	979bd3e3-8580-420d-9a75-345fc66993cc
e9625c7c-227d-4984-937c-afb9e112e9c6	https://source.unsplash.com/random/Siger1	f	979bd3e3-8580-420d-9a75-345fc66993cc
39cfacf2-27dd-45f3-9d4e-225566f45a37	https://source.unsplash.com/random/Siger2	f	979bd3e3-8580-420d-9a75-345fc66993cc
f42b1350-1eec-45e2-87a6-c19d41cab149	https://source.unsplash.com/random/Wassim	f	bd08a6eb-6af3-447b-9175-3c2d5e68c863
7131e356-fc24-4af6-86a9-7173a11c0596	https://source.unsplash.com/random/Wassim1	f	bd08a6eb-6af3-447b-9175-3c2d5e68c863
93444b71-ac83-4a31-97c8-a929d3c71ac6	https://source.unsplash.com/random/Wassim2	f	bd08a6eb-6af3-447b-9175-3c2d5e68c863
2a1f737d-ea2d-4dda-917c-b1651a683c0c	https://source.unsplash.com/random/Shidan	f	c250d964-7236-4b94-9bc5-4a200ca05478
307a940d-527e-452e-b2ba-d6c96b661e95	https://source.unsplash.com/random/Shidan1	f	c250d964-7236-4b94-9bc5-4a200ca05478
e9899235-6b4a-4ea6-8d44-447687292e7f	https://source.unsplash.com/random/Shidan2	f	c250d964-7236-4b94-9bc5-4a200ca05478
c3e4a09c-1836-47e0-a16c-c210d497fc37	https://source.unsplash.com/random/Sasha	f	e5665c43-9f98-4451-8d17-b6733ed75df3
83ca9005-5ef3-4ac4-8333-7be66e667298	https://source.unsplash.com/random/Sasha1	f	e5665c43-9f98-4451-8d17-b6733ed75df3
8c6db17b-ed2d-486e-b469-fe55ac0a63a7	https://source.unsplash.com/random/Sasha2	f	e5665c43-9f98-4451-8d17-b6733ed75df3
70e892f1-8cfc-4ded-a0d3-d333565500e6	https://source.unsplash.com/random/Enzo	f	f92b2ed1-d503-42d2-99ce-0d643228e973
03ec06d9-d6e2-4c29-aae3-7fd693fdc258	https://source.unsplash.com/random/Enzo1	f	f92b2ed1-d503-42d2-99ce-0d643228e973
b885732c-d18b-4112-bd14-0b0e4a132e29	https://source.unsplash.com/random/Enzo2	f	f92b2ed1-d503-42d2-99ce-0d643228e973
de750cf0-9489-4748-927d-6eb38cb56796	https://source.unsplash.com/random/Adam	f	fcb31db2-bbb5-47f4-a213-ddd75741f0ee
fbe0847e-ebcc-43f2-8082-9ac17e29d69b	https://source.unsplash.com/random/Adam1	f	fcb31db2-bbb5-47f4-a213-ddd75741f0ee
8f26e969-4e7d-4855-bb80-ebbcaca2364c	https://source.unsplash.com/random/Adam2	f	fcb31db2-bbb5-47f4-a213-ddd75741f0ee
06d16a33-0a1a-4a59-9346-1d64d62229fd	https://source.unsplash.com/random/Athmane	f	f2d98f96-8e34-4e63-be0c-01571f52fd89
f1fae75a-f9fa-452d-9540-c06a40556d2d	https://source.unsplash.com/random/Athmane1	f	f2d98f96-8e34-4e63-be0c-01571f52fd89
5e61e7ca-6f47-4c88-9bd0-fdaaaca78eb7	https://source.unsplash.com/random/Athmane2	f	f2d98f96-8e34-4e63-be0c-01571f52fd89
48946d2d-3601-4cfd-af43-8988ade61a7b	https://source.unsplash.com/random/Ramin	f	75fac32b-9ae5-4402-b83a-fa8335c83630
22f86632-637a-44b6-9d3c-2ea14d94dc52	https://source.unsplash.com/random/Ramin1	f	75fac32b-9ae5-4402-b83a-fa8335c83630
3e236bda-d930-4bc8-aa9f-5d5200056861	https://source.unsplash.com/random/Ramin2	f	75fac32b-9ae5-4402-b83a-fa8335c83630
8c2ca912-58bc-4fa2-92cd-9991cdb07618	https://source.unsplash.com/random/Thibaut	f	54b877c8-eaed-47c5-9a31-459a1278bdc2
477d9f51-e63e-4140-a369-54fe4814288e	https://source.unsplash.com/random/Thibaut1	f	54b877c8-eaed-47c5-9a31-459a1278bdc2
ed0d6b48-c602-448f-8b98-7b23d3c1ef6a	https://source.unsplash.com/random/Thibaut2	f	54b877c8-eaed-47c5-9a31-459a1278bdc2
acbeb4bc-e6b1-490a-aa94-4fff9c72d7e0	https://source.unsplash.com/random/Abe	f	320565d9-4cc6-4919-9f4e-48073bc78052
4958c1d2-f415-4f81-a26c-99e4f7a76789	https://source.unsplash.com/random/Abe1	f	320565d9-4cc6-4919-9f4e-48073bc78052
92940a9f-3cfd-4957-83f4-b24f5f8bf452	https://source.unsplash.com/random/Abe2	f	320565d9-4cc6-4919-9f4e-48073bc78052
\.


--
-- TOC entry 3473 (class 0 OID 98739)
-- Dependencies: 231
-- Data for Name: verification_request; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.verification_request (company_oui_number, company_name, explanation, status, supporting_document_url) FROM stdin;
\.


--
-- TOC entry 3245 (class 2606 OID 98626)
-- Name: app_user app_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);


--
-- TOC entry 3249 (class 2606 OID 98633)
-- Name: collaboration_request collaboration_request_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collaboration_request
    ADD CONSTRAINT collaboration_request_pkey PRIMARY KEY (id);


--
-- TOC entry 3251 (class 2606 OID 98640)
-- Name: collaboration_response collaboration_response_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collaboration_response
    ADD CONSTRAINT collaboration_response_pkey PRIMARY KEY (id);


--
-- TOC entry 3253 (class 2606 OID 98647)
-- Name: domain domain_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.domain
    ADD CONSTRAINT domain_pkey PRIMARY KEY (id);


--
-- TOC entry 3259 (class 2606 OID 98661)
-- Name: idea_domain idea_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea_domain
    ADD CONSTRAINT idea_domain_pkey PRIMARY KEY (idea_id, url_id);


--
-- TOC entry 3257 (class 2606 OID 98654)
-- Name: idea idea_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea
    ADD CONSTRAINT idea_pkey PRIMARY KEY (id);


--
-- TOC entry 3261 (class 2606 OID 98668)
-- Name: idea_technology idea_technology_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea_technology
    ADD CONSTRAINT idea_technology_pkey PRIMARY KEY (idea_id, technology_id);


--
-- TOC entry 3263 (class 2606 OID 98675)
-- Name: idea_topic idea_topic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea_topic
    ADD CONSTRAINT idea_topic_pkey PRIMARY KEY (idea_id, topic_id);


--
-- TOC entry 3265 (class 2606 OID 98682)
-- Name: moderator moderator_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderator
    ADD CONSTRAINT moderator_pkey PRIMARY KEY (id);


--
-- TOC entry 3267 (class 2606 OID 98689)
-- Name: owner owner_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owner
    ADD CONSTRAINT owner_pkey PRIMARY KEY (id);


--
-- TOC entry 3269 (class 2606 OID 98696)
-- Name: reaction reaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reaction
    ADD CONSTRAINT reaction_pkey PRIMARY KEY (id);


--
-- TOC entry 3271 (class 2606 OID 98703)
-- Name: regular_user_domain regular_user_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user_domain
    ADD CONSTRAINT regular_user_domain_pkey PRIMARY KEY (app_user_id, domain_id);


--
-- TOC entry 3275 (class 2606 OID 98717)
-- Name: regular_user regular_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user
    ADD CONSTRAINT regular_user_pkey PRIMARY KEY (id);


--
-- TOC entry 3273 (class 2606 OID 98710)
-- Name: regular_user_topic regular_user_topic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user_topic
    ADD CONSTRAINT regular_user_topic_pkey PRIMARY KEY (app_user_id, topic_id);


--
-- TOC entry 3279 (class 2606 OID 98724)
-- Name: technology technology_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.technology
    ADD CONSTRAINT technology_pkey PRIMARY KEY (id);


--
-- TOC entry 3283 (class 2606 OID 98731)
-- Name: topic topic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.topic
    ADD CONSTRAINT topic_pkey PRIMARY KEY (id);


--
-- TOC entry 3247 (class 2606 OID 98747)
-- Name: app_user uk_1j9d9a06i600gd43uu3km82jw; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT uk_1j9d9a06i600gd43uu3km82jw UNIQUE (email);


--
-- TOC entry 3277 (class 2606 OID 98751)
-- Name: regular_user uk_5jt07k6d1yvtb8ou76xax7qm8; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user
    ADD CONSTRAINT uk_5jt07k6d1yvtb8ou76xax7qm8 UNIQUE (verification_request_id);


--
-- TOC entry 3281 (class 2606 OID 98753)
-- Name: technology uk_7lsjqbr6g2jexyhbknslswkjc; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.technology
    ADD CONSTRAINT uk_7lsjqbr6g2jexyhbknslswkjc UNIQUE (name);


--
-- TOC entry 3255 (class 2606 OID 98749)
-- Name: domain uk_ga2sqp4lboblqv6oks9oryd9q; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.domain
    ADD CONSTRAINT uk_ga2sqp4lboblqv6oks9oryd9q UNIQUE (name);


--
-- TOC entry 3285 (class 2606 OID 98755)
-- Name: topic uk_mbunn9erv8nmf5lk1r2nu0nex; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.topic
    ADD CONSTRAINT uk_mbunn9erv8nmf5lk1r2nu0nex UNIQUE (name);


--
-- TOC entry 3287 (class 2606 OID 98738)
-- Name: url url_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.url
    ADD CONSTRAINT url_pkey PRIMARY KEY (id);


--
-- TOC entry 3289 (class 2606 OID 98745)
-- Name: verification_request verification_request_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verification_request
    ADD CONSTRAINT verification_request_pkey PRIMARY KEY (company_oui_number);


--
-- TOC entry 3295 (class 2606 OID 98771)
-- Name: idea fk3kl6rqjhb84ge0y245nv3i4ot; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea
    ADD CONSTRAINT fk3kl6rqjhb84ge0y245nv3i4ot FOREIGN KEY (icon_url_id) REFERENCES public.url(id);


--
-- TOC entry 3292 (class 2606 OID 98761)
-- Name: collaboration_request fk5k4huefstrxa6fnqxnd6wys93; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collaboration_request
    ADD CONSTRAINT fk5k4huefstrxa6fnqxnd6wys93 FOREIGN KEY (idea_id) REFERENCES public.idea(id) ON DELETE CASCADE;


--
-- TOC entry 3297 (class 2606 OID 98786)
-- Name: idea_domain fk5m4v0xk03hi09egydags0a587; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea_domain
    ADD CONSTRAINT fk5m4v0xk03hi09egydags0a587 FOREIGN KEY (idea_id) REFERENCES public.idea(id);


--
-- TOC entry 3298 (class 2606 OID 98781)
-- Name: idea_domain fk72g2ctn4o3nos5s6133ent7my; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea_domain
    ADD CONSTRAINT fk72g2ctn4o3nos5s6133ent7my FOREIGN KEY (url_id) REFERENCES public.domain(id);


--
-- TOC entry 3309 (class 2606 OID 98841)
-- Name: regular_user_topic fk7d6wk7k0d46y63ulqunt6nwsb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user_topic
    ADD CONSTRAINT fk7d6wk7k0d46y63ulqunt6nwsb FOREIGN KEY (topic_id) REFERENCES public.topic(id);


--
-- TOC entry 3305 (class 2606 OID 98826)
-- Name: reaction fk8qrudb3djitbld4nm6coctbsh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reaction
    ADD CONSTRAINT fk8qrudb3djitbld4nm6coctbsh FOREIGN KEY (user_id) REFERENCES public.regular_user(id) ON DELETE CASCADE;


--
-- TOC entry 3310 (class 2606 OID 98846)
-- Name: regular_user_topic fk993r38u5j0pkynljry0be4gqv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user_topic
    ADD CONSTRAINT fk993r38u5j0pkynljry0be4gqv FOREIGN KEY (app_user_id) REFERENCES public.regular_user(id);


--
-- TOC entry 3303 (class 2606 OID 98811)
-- Name: moderator fk_2cxp7lqbm00cjypomfy5radd6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderator
    ADD CONSTRAINT fk_2cxp7lqbm00cjypomfy5radd6 FOREIGN KEY (app_user_id) REFERENCES public.app_user(id);


--
-- TOC entry 3311 (class 2606 OID 98856)
-- Name: regular_user fk_fvqgqq1yxcfjktaphbprg63ey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user
    ADD CONSTRAINT fk_fvqgqq1yxcfjktaphbprg63ey FOREIGN KEY (app_user_id) REFERENCES public.app_user(id);


--
-- TOC entry 3304 (class 2606 OID 98816)
-- Name: owner fk_jeriaj2hvq86km0muvqe0q7xh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owner
    ADD CONSTRAINT fk_jeriaj2hvq86km0muvqe0q7xh FOREIGN KEY (app_user_id) REFERENCES public.app_user(id);


--
-- TOC entry 3293 (class 2606 OID 98756)
-- Name: collaboration_request fkbqrgko7eknm9vd062o4rqcv1x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collaboration_request
    ADD CONSTRAINT fkbqrgko7eknm9vd062o4rqcv1x FOREIGN KEY (collaboration_response_id) REFERENCES public.collaboration_response(id);


--
-- TOC entry 3294 (class 2606 OID 98766)
-- Name: collaboration_request fkbsokxqm0s2l7poy42etf5r7hv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collaboration_request
    ADD CONSTRAINT fkbsokxqm0s2l7poy42etf5r7hv FOREIGN KEY (requester_id) REFERENCES public.regular_user(id) ON DELETE CASCADE;


--
-- TOC entry 3307 (class 2606 OID 98831)
-- Name: regular_user_domain fkbthcckw5as07fw1r0nvtv52j8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user_domain
    ADD CONSTRAINT fkbthcckw5as07fw1r0nvtv52j8 FOREIGN KEY (domain_id) REFERENCES public.domain(id);


--
-- TOC entry 3313 (class 2606 OID 98861)
-- Name: url fkcm8um5yct2kml9elwr74pkj1l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.url
    ADD CONSTRAINT fkcm8um5yct2kml9elwr74pkj1l FOREIGN KEY (supported_idea_id) REFERENCES public.idea(id);


--
-- TOC entry 3301 (class 2606 OID 98806)
-- Name: idea_topic fkh0rp06a3psfg4622brmh9yq0b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea_topic
    ADD CONSTRAINT fkh0rp06a3psfg4622brmh9yq0b FOREIGN KEY (idea_id) REFERENCES public.idea(id);


--
-- TOC entry 3312 (class 2606 OID 98851)
-- Name: regular_user fkjkk1pjskvv1qqmwolxq9lim9n; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user
    ADD CONSTRAINT fkjkk1pjskvv1qqmwolxq9lim9n FOREIGN KEY (verification_request_id) REFERENCES public.verification_request(company_oui_number);


--
-- TOC entry 3296 (class 2606 OID 98776)
-- Name: idea fklix3lql4n2mq786vrby9le34u; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea
    ADD CONSTRAINT fklix3lql4n2mq786vrby9le34u FOREIGN KEY (user_id) REFERENCES public.regular_user(id) ON DELETE CASCADE;


--
-- TOC entry 3308 (class 2606 OID 98836)
-- Name: regular_user_domain fkltdjwotp7kp10krpxfd99ll4v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regular_user_domain
    ADD CONSTRAINT fkltdjwotp7kp10krpxfd99ll4v FOREIGN KEY (app_user_id) REFERENCES public.regular_user(id);


--
-- TOC entry 3299 (class 2606 OID 98796)
-- Name: idea_technology fkltmkhyynbvvy580tnfa8kjj4i; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea_technology
    ADD CONSTRAINT fkltmkhyynbvvy580tnfa8kjj4i FOREIGN KEY (idea_id) REFERENCES public.idea(id);


--
-- TOC entry 3302 (class 2606 OID 98801)
-- Name: idea_topic fkm11d8nfuwnqviv4rfxrjwc13t; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea_topic
    ADD CONSTRAINT fkm11d8nfuwnqviv4rfxrjwc13t FOREIGN KEY (topic_id) REFERENCES public.topic(id);


--
-- TOC entry 3306 (class 2606 OID 98821)
-- Name: reaction fkrrtgoxmyrq7dlf6nhfrl6xdy3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reaction
    ADD CONSTRAINT fkrrtgoxmyrq7dlf6nhfrl6xdy3 FOREIGN KEY (idea_id) REFERENCES public.idea(id) ON DELETE CASCADE;


--
-- TOC entry 3300 (class 2606 OID 98791)
-- Name: idea_technology fks9lnbaft0nt70r6xdg3pd0li2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.idea_technology
    ADD CONSTRAINT fks9lnbaft0nt70r6xdg3pd0li2 FOREIGN KEY (technology_id) REFERENCES public.technology(id);


-- Completed on 2023-03-31 17:44:19

--
-- PostgreSQL database dump complete
--

