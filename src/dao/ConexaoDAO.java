package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {
    
    //atributos para conectar ao banco de dados (utilizando jdbc)
    private static final String URL = "jdbc:postgresql://localhost:5432/TrabalhoPadaria"; 
    
    private static final String USUARIO = "postgres"; 
    
    private static final String SENHA = "1234";
    
    //conecta ao banco
    public Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados. Erro: " + e.getMessage(), e);
        }
    }
    
    //fecha a conexao ao banco
    public void fecharConexao(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
