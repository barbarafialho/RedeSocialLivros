# Rede Social de Livros - CRUD em Java Swing

Este projeto foi desenvolvido como parte da disciplina de Linguagem de Programação 1 e simula uma rede social de livros, permitindo aos usuários gerenciar suas leituras, fazer avaliações de livros e visualizar as avaliações de outros usuários. A interface gráfica foi construída utilizando Java Swing, e o banco de dados PostgreSQL é utilizado para armazenar as informações.

## Funcionalidades

- **Cadastro de Autores e Livros**: A aplicação permite o gerenciamento de autores e livros, possibilitando cadastrar, atualizar, excluir e visualizar as informações armazenadas no banco de dados. O cadastro de livros inclui validação de ISBN para evitar cadastros duplicados.

- **Validação de CPF**: No processo de cadastro de usuários, o sistema realiza validação do CPF para garantir que o mesmo não seja cadastrado mais de uma vez, evitando duplicidade de registros.

- **Tela de Login**: O sistema inclui uma tela de login que utiliza criptografia MD5 para garantir a segurança das senhas dos usuários.

- **Avaliação de Livros**: Os usuários podem avaliar os livros cadastrados. Ao realizar o login, o sistema permite que o usuário veja e edite suas próprias avaliações de livros.

- **Visualização de Avaliações de Todos os Usuários**: Além de visualizar suas próprias avaliações, o usuário tem a opção de acessar uma tela onde pode visualizar as avaliações de todos os usuários cadastrados, promovendo uma experiência semelhante a uma rede social de leitura.

- **Integração com Banco de Dados**: A aplicação se conecta a um banco de dados Postgres, onde todas as informações sobre autores, livros, avaliações e usuários são armazenadas. ComboBox dinâmicas são utilizadas para exibir dados diretamente do banco.

## Tecnologias Utilizadas

- **Java Swing**: Para a construção da interface gráfica.
- **PostgreSQL**: Para gerenciamento do banco de dados.
- **MD5**: Para criptografia das senhas no sistema de login.

## Como Executar

1. Clone este repositório:
   ```bash
   git clone https://github.com/barbarafialho/RedeSocialLivros.git
   ```

2. Configure o banco de dados Postgres utilizando o arquivo `script_db.sql` presente no projeto.
