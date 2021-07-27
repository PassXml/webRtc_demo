CREATE SEQUENCE public.base_menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE public.base_menu
(
    id        bigint   DEFAULT nextval('public.base_menu_id_seq'::regclass) NOT NULL,
    parent_id bigint,
    sort      smallint DEFAULT 0,
    type      character varying(2)                                          NOT NULL,
    name      character varying(32)                                         NOT NULL,
    value     character varying(64),
    icon      character varying(32)
);
COMMENT
ON TABLE public.base_menu IS '用户菜单表';
COMMENT
ON COLUMN public.base_menu.parent_id IS '上级节点Id';
COMMENT
ON COLUMN public.base_menu.sort IS '排序';
COMMENT
ON COLUMN public.base_menu.type IS '类型;url;普通菜单;内嵌';
COMMENT
ON COLUMN public.base_menu.name IS '名字';
COMMENT
ON COLUMN public.base_menu.value IS '具体值';

COMMENT
ON COLUMN public.base_menu.icon IS '菜单icon';

CREATE SEQUENCE public.base_permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE public.base_permission
(
    id    bigint DEFAULT nextval('public.base_permission_id_seq'::regclass) NOT NULL,
    type  character varying(2)                                              NOT NULL,
    name  character varying(32)                                             NOT NULL,
    value character varying(64)                                             NOT NULL
);
CREATE SEQUENCE public.base_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;
CREATE TABLE public.base_role
(
    id        bigint  DEFAULT nextval('public.base_role_id_seq'::regclass) NOT NULL,
    name      character varying(32),
    flag      character varying(16),
    anonymous boolean,
    has_all   boolean DEFAULT false
);
CREATE SEQUENCE public.base_role_menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;
CREATE TABLE public.base_role_menu
(
    id      bigint DEFAULT nextval('public.base_role_menu_id_seq'::regclass) NOT NULL,
    role_id bigint                                                           NOT NULL,
    menu_id bigint                                                           NOT NULL
);
CREATE SEQUENCE public.base_role_permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


CREATE TABLE public.base_role_permission
(
    id            bigint DEFAULT nextval('public.base_role_permission_id_seq'::regclass) NOT NULL,
    role_id       bigint                                                                 NOT NULL,
    permission_id bigint                                                                 NOT NULL
);

CREATE SEQUENCE public.base_user_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


CREATE TABLE public.base_user_info
(
    id       bigint DEFAULT nextval('public.base_user_info_id_seq'::regclass) NOT NULL,
    union_id character varying(32)                                            NOT NULL,
    name     character varying(32)                                            NOT NULL,
    password character varying(32)                                            NOT NULL,
    email    character varying(64)                                            NOT NULL,
    salt     character varying(6)                                             NOT NULL
);


COMMENT
ON TABLE public.base_user_info IS '用户基础表';


COMMENT
ON COLUMN public.base_user_info.union_id IS '用户唯一标识';

COMMENT
ON COLUMN public.base_user_info.name IS '用户名';


COMMENT
ON COLUMN public.base_user_info.password IS '密码';


COMMENT
ON COLUMN public.base_user_info.email IS 'Email';


COMMENT
ON COLUMN public.base_user_info.salt IS '盐';

CREATE SEQUENCE public.base_user_menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


CREATE TABLE public.base_user_menu
(
    id      bigint DEFAULT nextval('public.base_user_menu_id_seq'::regclass) NOT NULL,
    user_id bigint                                                           NOT NULL,
    menu_id bigint                                                           NOT NULL
);

CREATE SEQUENCE public.base_user_permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


CREATE TABLE public.base_user_permission
(
    id            bigint DEFAULT nextval('public.base_user_permission_id_seq'::regclass) NOT NULL,
    user_id       bigint                                                                 NOT NULL,
    permission_id bigint                                                                 NOT NULL
);


CREATE SEQUENCE public.base_user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE public.base_user_role
(
    id      bigint DEFAULT nextval('public.base_user_role_id_seq'::regclass) NOT NULL,
    user_id bigint                                                           NOT NULL,
    role_id bigint                                                           NOT NULL
);

COMMENT
ON TABLE public.base_user_role IS '用户权限管理表';
COMMENT
ON COLUMN public.base_user_role.user_id IS '用户Id';
COMMENT
ON COLUMN public.base_user_role.role_id IS '用户组Id';
CREATE SEQUENCE public.user_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


INSERT INTO public.base_permission (id, type, name, value)
VALUES (1, '0', '登录', '/api/login');
INSERT INTO public.base_role (id, name, flag, anonymous, has_all)
VALUES (2, '匿名', 'anonymous', true, false);
INSERT INTO public.base_role (id, name, flag, anonymous, has_all)
VALUES (3, '基本用户', 'base_user', false, false);
INSERT INTO public.base_role (id, name, flag, anonymous, has_all)
VALUES (1, '管理员', 'admin', false, true);

INSERT INTO public.base_role_permission (id, role_id, permission_id)
VALUES (1, 1, 1);


INSERT INTO public.base_user_info (id, union_id, name, password, email, salt)
VALUES (1, '81692f8061624a80a7aac280d0f1205b', 'admin', 'f6fdffe48c908deb0f4c3bd36c032e72', 'admin@admin.com', 'admin');


SELECT pg_catalog.setval('public.base_menu_id_seq', 1, false);
SELECT pg_catalog.setval('public.base_permission_id_seq', 1, true);
SELECT pg_catalog.setval('public.base_role_id_seq', 3, true);
SELECT pg_catalog.setval('public.base_role_menu_id_seq', 1, false);
SELECT pg_catalog.setval('public.base_role_permission_id_seq', 1, true);
SELECT pg_catalog.setval('public.base_user_info_id_seq', 1, true);
SELECT pg_catalog.setval('public.base_user_menu_id_seq', 1, false);
SELECT pg_catalog.setval('public.base_user_permission_id_seq', 1, false);
SELECT pg_catalog.setval('public.base_user_role_id_seq', 1, false);
SELECT pg_catalog.setval('public.user_info_id_seq', 1, false);
ALTER TABLE ONLY public.base_menu
    ADD CONSTRAINT base_menu_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.base_permission
    ADD CONSTRAINT base_permission_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.base_role_menu
    ADD CONSTRAINT base_role_menu_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.base_role_permission
    ADD CONSTRAINT base_role_permission_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.base_role
    ADD CONSTRAINT base_role_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.base_user_info
    ADD CONSTRAINT base_user_info_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.base_user_info
    ADD CONSTRAINT base_user_info_union_id_key UNIQUE (union_id);


ALTER TABLE ONLY public.base_user_menu
    ADD CONSTRAINT base_user_menu_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.base_user_permission
    ADD CONSTRAINT base_user_permission_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.base_user_role
    ADD CONSTRAINT base_user_role_pkey PRIMARY KEY (id);

CREATE
INDEX base_user_info_index_email_password ON public.base_user_info USING btree (email, password);
CREATE
INDEX base_user_info_index_name_password ON public.base_user_info USING btree (name, password);
CREATE INDEX base_user_info_index_union_id_password ON public.base_user_info USING btree (union_id, password);

alter table base_role_permission
    add permission varchar(3) default 111 not null;

comment on column base_role_permission.permission is '''权限管理(Linux二进制)''';

alter table base_user_permission
    add permission varchar(3) default 111 not null;

comment on column base_user_permission.permission is '''权限管理(Linux二进制)''';


