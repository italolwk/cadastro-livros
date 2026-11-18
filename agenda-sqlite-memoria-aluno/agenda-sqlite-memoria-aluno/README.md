# Agenda SQLite em memória — versão inicial do aluno

Java de console, sem framework e sem Maven. Cadastro, listagem, busca por ID, alteração e exclusão de contatos com nome, telefone e idade. O campo e-mail fica para o exercício.

## Execução no Windows / VS Code

1. Instale um JDK 8 ou posterior. Confira `java -version` e `javac -version` no terminal.
2. Extraia o ZIP e abra a pasta agenda-sqlite-memoria-aluno no VS Code.
3. No terminal PowerShell dessa pasta, execute: `.\executar.bat`.

O driver já está em lib/. Não instale MySQL, SQLite ou servidor de banco. Não há usuário nem senha. A tabela é criada automaticamente ao iniciar. No VS Code com Extension Pack for Java, também é possível executar Main.java pelo botão Run Java.

Linux/macOS: `bash executar.sh`.

## Como funciona a memória

A URL é `jdbc:sqlite::memory:`. Cada conexão tem seu banco em memória. Conexao cria e mantém uma única conexão; o Repository reutiliza essa conexão e fecha apenas statements e resultados. O Main fecha o banco ao terminar. Os contatos persistem entre as operações enquanto a agenda estiver aberta, mas desaparecem ao sair. Reiniciar começa com a tabela vazia.

Não altere o Repository para fechar Connection em cada operação: isso destruiria o banco compartilhado. Esta versão é de console e executa as operações de forma sequencial.

## Pastas e camadas

Main cria os objetos e chama ContatoController.iniciar(). View recebe e apresenta dados; Controller coordena o fluxo; Service valida; Repository executa SQL; Model representa o contato; Config gerencia a conexão e a tabela. A arquitetura permanece a mesma do exercício anterior.

## Exercício de e-mail

Leia EXERCICIO_EMAIL.md. A principal adaptação é a criação da coluna em Conexao.criarTabela(). Como o banco nasce vazio em cada execução, não é preciso rodar ALTER TABLE em um programa externo.
