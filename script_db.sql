create database redesocial_livros;

\c redesocial_livros;

create table usuario (
	id_usuario serial,
	nome varchar(80), 
	cpf varchar(11), 
	genero varchar(10), 
	telefone varchar(11),
	email varchar(50), 
	username varchar(20), 
	senha varchar(50),
	constraint pk_usuario primary key (id_usuario)
);

create table autor (
	id_autor serial,
	nome varchar(80),
	genero varchar(10),
	nacionalidade varchar(20),
	dataNasc date,
	constraint pk_autor primary key (id_autor)
);

create table livro (
	id_livro serial,
	titulo varchar(100),
	id_autor integer,
	isbn varchar(50),
	ano integer,
	editora varchar(50),
	constraint pk_livro primary key (id_livro),
	constraint fk_autor foreign key (id_autor) references autor (id_autor),
	constraint livro_isbn_key unique(isbn)
);

create table avaliar (
	id_usuario integer,
	id_livro integer,
	lendo integer,
	queroLer integer,
	lido integer,
	comentario varchar(200),
	constraint fk_usuario foreign key (id_usuario) references usuario (id_usuario),
    constraint fk_livro foreign key (id_livro) references livro (id_livro)
);