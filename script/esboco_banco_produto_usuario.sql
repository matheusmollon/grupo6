-------------------------------Script para teste entre usuário e Produto apenas-------------------------------------------------------
create table Usuario(
 idUsuario Serial primary key,
 nomeUsuario varchar(100) not null,
 senhaUsuario varchar(100) not null

);

create table Produto(
 idProduto Serial primary key,
 nomeProduto varchar(100) not null,
 precoProduto float not null
);

create table usr_produto(
 usuario int not null,
 produto int not null,
 constraint PK_user_produto primary key(usuario,produto),
 constraint FK_user_produto foreign key(usuario) references
 Usuario(idUsuario),
 constraint FK_user_produto2 foreign key(produto) references
 Produto(idProduto)
);


---------------------------------Populando usuarios--------------------------------------------------
insert into Usuario(idUsuario, nomeUsuario, senhaUsuario) values
                          (1,'Shabazi','ioio'),
                          (2,'Eduardo','ioio'),
                          (3,'Jose','ioio'),
                          (4,'Joao','ioio'),
                          (5,'Alcksson','ioio'),
                          (6,'Lucas','ioio'),
                          (7,'Murilo','ioio'),
                          (8,'Gustavo','ioio');

                          
-----------------------------------------------------------------------------------------------------

--select * from usuario;
--drop table usr_produto;
--drop table Produto;
--drop table Usuario;




