create table topicos (
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensagem varchar(255) not null,
    usuario_id bigint not null,
    curso varchar(100) not null,
    status varchar(100) not null,
    ativo tinyint not null,
    data_criacao datetime not null,

    primary key (id),
    constraint fk_topicos_usuario_id foreign key (usuario_id) references usuarios(id)
);
