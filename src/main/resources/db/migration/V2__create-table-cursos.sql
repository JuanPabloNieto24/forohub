create table cursos(
    id bigserial not null,
    nombre varchar(100) not null,
    categoria varchar(100),

    primary key(id)
);