create table respostas (
    id bigint not null auto_increment,
    usuario_id bigint not null,
    mensagem varchar(255) not null,
    topico_id bigint not null,
    ativo tinyint not null,
    data_criacao datetime not null,

    primary key (id),
    constraint fk_respostas_topico_id foreign key (topico_id) references topicos(id),
    constraint fk_respotas_usuario_id foreign key (usuario_id) references usuarios(id)
);
