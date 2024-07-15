create table topicos(
    id bigserial not null,
    titulo varchar(100) unique not null,
    mensaje varchar(255) unique not null,
    fecha_creacion timestamptz not null,
    status varchar(50) not null,
    autor varchar(100) not null,

    primary key(id)
);