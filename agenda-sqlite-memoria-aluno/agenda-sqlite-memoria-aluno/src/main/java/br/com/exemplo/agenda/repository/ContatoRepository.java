package br.com.exemplo.agenda.repository;

import br.com.exemplo.agenda.config.Conexao;
import br.com.exemplo.agenda.model.Contato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContatoRepository {
    // Fechamos statements e resultados aqui, mas não a conexão compartilhada.
    private final Conexao conexao;

    public ContatoRepository(Conexao conexao) { this.conexao = conexao; }

    public long inserir(Contato contato) throws SQLException {
        String sql = "INSERT INTO contatos (nome, telefone, idade) VALUES (?, ?, ?)";
        Connection c = conexao.abrir();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, contato.getNome());
            p.setString(2, contato.getTelefone());
            p.setInt(3, contato.getIdade());
            p.executeUpdate();
            try (PreparedStatement chave = c.prepareStatement("SELECT last_insert_rowid()");
                 ResultSet chaves = chave.executeQuery()) {
                if (chaves.next()) return chaves.getLong(1);
            }
            throw new SQLException("O banco não retornou o ID do contato.");
        }
    }

    public List<Contato> listar() throws SQLException {
        String sql = "SELECT id, nome, telefone, idade FROM contatos ORDER BY nome, id";
        List<Contato> contatos = new ArrayList<Contato>();
        Connection c = conexao.abrir();
        try (PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) contatos.add(mapear(r));
        }
        return contatos;
    }

    public Contato buscarPorId(long id) throws SQLException {
        String sql = "SELECT id, nome, telefone, idade FROM contatos WHERE id = ?";
        Connection c = conexao.abrir();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? mapear(r) : null;
            }
        }
    }

    public boolean atualizar(Contato contato) throws SQLException {
        String sql = "UPDATE contatos SET nome = ?, telefone = ?, idade = ? WHERE id = ?";
        Connection c = conexao.abrir();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, contato.getNome());
            p.setString(2, contato.getTelefone());
            p.setInt(3, contato.getIdade());
            p.setLong(4, contato.getId());
            return p.executeUpdate() > 0;
        }
    }

    public boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM contatos WHERE id = ?";
        Connection c = conexao.abrir();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            return p.executeUpdate() > 0;
        }
    }

    private Contato mapear(ResultSet r) throws SQLException {
        return new Contato(r.getLong("id"), r.getString("nome"),
                r.getString("telefone"), r.getInt("idade"));
    }
}
