# Exercício: acrescentar e-mail

Esta versão contém apenas nome, telefone e idade. Complete as etapas mantendo as camadas.

1. Em config/Conexao.java, no SQL de criarTabela(), troque:

```java
+ "idade INTEGER NOT NULL)";
```

por:

```java
+ "idade INTEGER NOT NULL, "
+ "email TEXT NOT NULL)";
```

2. Em model/Contato.java, crie String email, acrescente o parâmetro ao construtor e implemente getEmail().
3. Em repository/ContatoRepository.java, inclua email no INSERT, nos SELECT e no UPDATE. Passe o quarto parâmetro no cadastro. No UPDATE, email é o quarto parâmetro e id passa a ser o quinto. Em mapear, leia r.getString("email") e passe ao construtor. Preserve SELECT last_insert_rowid(): é a leitura do ID no SQLite.
4. Em service/ContatoService.java, acrescente email aos métodos cadastrar, alterar e validar. Valide texto não vazio, limite de 150 caracteres e presença de @ (verificação didática). Passe email.trim() ao criar Contato.
5. Em view/AgendaView.java, crie lerEmail() e lerNovoEmail() e mostre email em mostrarContato().
6. Em controller/ContatoController.java, leia o novo campo pela View e repasse ao Service em cadastrar() e alterar().
7. Execute novamente. Cadastre, liste, busque e altere um contato. Confirme que e-mail vazio é rejeitado.
8. Feche e reabra: os dados devem desaparecer, pois este exercício usa memória. Esse comportamento é esperado.

Não execute CREATE DATABASE, USE ou AUTO_INCREMENT: essas instruções do MySQL não fazem parte desta versão. Não é necessário instalar Workbench nem abrir outro programa de banco de dados.
