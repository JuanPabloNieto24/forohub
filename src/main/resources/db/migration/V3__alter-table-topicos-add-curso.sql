alter table topicos
add curso_id bigserial not null,
add constraint fk_curso foreign key (curso_id) references cursos (id);