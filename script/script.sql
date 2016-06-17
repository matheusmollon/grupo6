CREATE TABLE usuario(
	u_nome 		varchar(30) 	not null,
	u_senha 	varchar(30) 	not null,
	u_celular 	varchar(20) 	not null,
	constraint u_pk
		primary key(u_nome)
);
