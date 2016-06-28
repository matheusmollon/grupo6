# Instruções para execução do projeto:

 - Antes de realizar o commit pelo NetBeans limpar o projeto (clicar com o botão direito sobre o projeto e escolher a opção "Limpar" ou "Clean");
 - O arquivo persistence.xml localizado em grupo6/src/Aplicativo/src/main/resources/META-INF/ pode precisar de alterações para funcionar com contribuidores diferentes, atualmente está configurado para:
   - Host: 192.168.56.101
   - Base de dados: grupo6
   - Usuário: teste
   - Senha: teste
 - O arquivo script.sql contém a construção das tabelas da base de dados.

Observação: 
 Se mesmo após realizar as alterações acima houver problemas para uso do banco de dados, siga os seguintes passos:
   - Expanda a pasta "Outros Códigos-fonte" até encontrar o "persistence.xml";
   - Delete o  arquivo "persistence.xml";
   - No pacote "META-INF" desta mesma pasta, crie um novo arquivo de persistêcia;
   - Clique com o botão direito sobre o "META-INF" e selecione "Novo -> Outros";
   - Em "Categorias" selecione "Persistência" e em "Tipos de Arquivo" selecione "Unidade de Persistência";
   - Clique em "Próximo";
   - No campo "Nome da Unidade de Persistência" insira "AplicativoPU";
   - Em "Biblioteca de Persistência" escolha a opção "EclipseLink (JPA 2.1)";
   - Em "Conexão ao Banco de dados" selecione a conexão que contenha as tabelas do "script.sql" (caso não houver nenhuma disponível, crie uma nova conexão);
   - Selecione a opção "Nenhum" em "Estratégia de Geração de Tabela";
   - Clique em Finalizar;
   - Abra o arquivo "persistence.xml" gerado expandindo a aba "Geral" de "AplicativoPU";
   - Confira os dados de todos os campos;
   - Clique no botão "Adicionar Classe" e inclua as Classes de Entidade do projeto;
   - Vá na aba "Código-fonte" e verifique se as informações estão de acordo com sua conexão;
   - Execute o projeto novamente.
   
   
---

# Instruções gerais sobre o uso do GitHub para a execução do projeto:

a. como fazer o clone do projeto no github;

b. qual o nome do projeto (criar, caso não tenha);

c. qual o nome do banco de dados que sera utilizado;

d. explicar qual a finalidade dos diretórios.

 - Para os itens a e b veja o arquivo "Como utilizar o Github e NetBeans" presente no diretório "doc".
 - O banco de dados (c) que será utilizado é o PostgreSQL.
 - Cada diretório tem uma finalidade específica, conforme é expresso abaixo:
   - doc: Este diretório é responsável por toda a documentação do projeto.
   - script: Responsável pelo script de criação das bases no banco de dados e de população dos dados.
   - src/Template: Contém o Template para o desenvolvimento da codificação do projeto.

---
