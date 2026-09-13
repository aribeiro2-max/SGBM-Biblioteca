# SGBM — Sistema de Gestão da Biblioteca Municipal

Sistema de consola desenvolvido em Java para gerir livros, utilizadores, empréstimos, devoluções e estatísticas.

## Funcionalidades

- Registo de livros;
- Listagem do catálogo;
- Pesquisa por título ou autor;
- Registo de utilizadores;
- Empréstimo de livros;
- Devolução de livros;
- Histórico de empréstimos;
- Estatísticas do sistema;
- Validação de IDs duplicados;
- Controlo de exemplares disponíveis.

## Requisitos

- Java JDK 17 ou superior;
- Terminal/Prompt de Comando ou uma IDE como IntelliJ IDEA, NetBeans ou Eclipse.

## Como executar pelo terminal

1. Abra o terminal na pasta onde está o ficheiro `Main.java`.
2. Compile o programa:

```bash
javac Main.java
```

3. Execute:

```bash
java Main
```

## Como executar numa IDE

1. Crie um projecto Java.
2. Adicione o ficheiro `Main.java`.
3. Execute a classe `Main`.

## Estrutura

- `Main.java`: contém o menu, as classes Livro, Utilizador, Emprestimo e Biblioteca.
- `README.md`: instruções de configuração e execução.

## Observação

Os dados são armazenados em memória através de arrays. Ao fechar o programa, os dados são apagados. Esta é uma limitação intencional do projecto, conforme o enunciado que solicita uma base de dados simulada em memória.

## Autor

Nome do estudante: ______________________

## Repositório GitHub

(https://github.com/aribeiro2-max/SGBM-Biblioteca)
