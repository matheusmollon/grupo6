--Criar esta tabela dentro de um database de nome 'app'
--DROP TABLE Usuario;
--SELECT * FROM Usuario;
CREATE TABLE Usuario(
	uNome 		varchar(30) 	not null,
	uSenha 		varchar(30) 	not null,
	uCelular 	varchar(20) 	not null,
	constraint uPK 
		primary key(uNome)
);

	
	
