
CREATE DATABASE testesoftplan
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE SCHEMA gerproc
    AUTHORIZATION postgres;

CREATE TABLE gerproc.usuario
(
    usuario_id bigint NOT NULL DEFAULT nextval('gerproc.usuario_usuario_id_seq'::regclass),
    nome character varying(150) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(20) COLLATE pg_catalog."default" NOT NULL,
    tipousuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id),
    CONSTRAINT usuario_tipousuario_check CHECK (tipousuario::text = ANY (ARRAY['ADMINISTRADOR'::character varying::text, 'TRIADOR'::character varying::text, 'FINALIZADOR'::character varying::text]))
)

TABLESPACE pg_default;

ALTER TABLE gerproc.usuario
    OWNER to postgres;

-- Table: gerproc.processo

-- DROP TABLE gerproc.processo;

CREATE TABLE gerproc.processo
(
    processo_id bigint NOT NULL DEFAULT nextval('gerproc.processo_processo_id_seq'::regclass),
    nome character varying(50) COLLATE pg_catalog."default" NOT NULL,
    descricao character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT processo_pkey PRIMARY KEY (processo_id)
)

TABLESPACE pg_default;

ALTER TABLE gerproc.processo
    OWNER to postgres;

-- Table: gerproc.processousuario

-- DROP TABLE gerproc.processousuario;

CREATE TABLE gerproc.processousuario
(
    processo bigint NOT NULL,
    usuariotriador bigint NOT NULL,
    usuariofinalizador bigint NOT NULL,
    status character varying(20) COLLATE pg_catalog."default" NOT NULL DEFAULT 'CRIADO'::character varying,
    parecer character varying(1000) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "	 processousuario_pkey" PRIMARY KEY (processo, usuariotriador),
    CONSTRAINT processousuario_processo_fkey FOREIGN KEY (processo)
        REFERENCES gerproc.processo (processo_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT processousuario_usuariofinalizador_fkey FOREIGN KEY (usuariofinalizador)
        REFERENCES gerproc.usuario (usuario_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT processousuario_usuariotriador_fkey FOREIGN KEY (usuariotriador)
        REFERENCES gerproc.usuario (usuario_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT processousuario_status_check CHECK (status::text = ANY (ARRAY['CRIADO'::character varying::text, 'PENDENTE'::character varying::text, 'FINALIZADO'::character varying::text]))
)

TABLESPACE pg_default;

ALTER TABLE gerproc.processousuario
    OWNER to postgres;