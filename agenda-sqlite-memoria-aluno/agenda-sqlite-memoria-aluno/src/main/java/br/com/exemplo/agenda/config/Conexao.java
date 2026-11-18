package br.com.exemplo.agenda.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao implements AutoCloseable {
    private final Connection conexao;

    public Conexao() throws SQLException {
        // Cada conexão :memory: tem seu próprio banco. Reutilizamos esta conexão.
        conexao = DriverManager.getConnection("jdbc:sqlite::memory:");
        try {
            criarTabela();
        } catch (SQLException e) {
            try { conexao.close(); } catch (SQLException fechamento) {
                e.addSuppressed(fechamento);
            }
            throw e;
        }
    }

    public Connection abrir() {
        // Devolve a conexão existente, sem criar outro banco vazio.
        return conexao;
    }

    private void criarTabela() throws SQLException {
        String sql = "CREATE TABLE contatos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome TEXT NOT NULL, "
                + "telefone TEXT NOT NULL, "
                + "idade INTEGER NOT NULL)";
        try (Statement statement = conexao.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    @Override
    public void close() throws SQLException {
        // O Main fecha o banco somente quando o programa termina.
        conexao.close();
    }
}
