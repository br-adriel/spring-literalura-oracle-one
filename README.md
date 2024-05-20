# Literalura 📖

Projeto desenvolvido durante o acompanhamento do programa Oracle One em parceria
com a Alura. O projeto se trata de um programa de linha de comando para a
consulta de livros e autores literários, oferecendo as seguintes
funcionalidades:

- Busca de livros por título
- Salvamento automático dos livros e autores no banco de dados
- Listagem de livros e autores salvos no banco
- Busca de autores salvos no banco de dados que estavam vivos em um determinado
  ano
- Listagem de livros salvos por idioma
  - Português
  - Inglês
  - Espanhol
  - Francês

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

## Executando o projeto

1. Faça o download do repositório e abra a pasta do projeto na IDE de sua
   preferência.

2. Crie um banco de dados postgres nomeado `db_literalura` e insira a senha do
   seu usuário postgres no campo em branco do arquivo
   `src\main\resources\application.properties`

    - Caso você queira utilizar outro nome para o banco ou nome de usuário, basta alterar os valores desse mesmo arquivo para os desejados

3. Execute o comando a seguir para instalar as dependências do projeto:

    ```bash
    mvn clean install
    ```
4. Compile e execute o projeto
