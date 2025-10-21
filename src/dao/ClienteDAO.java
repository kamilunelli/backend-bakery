package dao; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List; 
import interfaces.ICrud;
import model.Cliente; 

public class ClienteDAO implements ICrud<Cliente> {

    //atributo
    private final ConexaoDAO conexaoDAO; 

    //crud de cliente
    public ClienteDAO() {
        this.conexaoDAO = new ConexaoDAO(); 
    }

    //create
    @Override
    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO CLIENTE (nome, cpf, telefone, total_pontos) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = conexaoDAO.conectar();
            
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setString(3, cliente.getTelefone());
            pstm.setInt(4, cliente.getTotalPontosAcumulados()); 
            
            pstm.execute();
            
            System.out.println("Cliente " + cliente.getNome() + " salvo.");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar Cliente (INSERT SQL): " + e.getMessage());
            
        } finally {
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn); 
        }
    }

    //read
    @Override
    public List<Cliente> consultarTodos() {
        
        String sql = "SELECT * FROM CLIENTE ORDER BY id";
        
        List<Cliente> listaClientes = new java.util.ArrayList<>();
        
        Connection conn = null;
        PreparedStatement pstm = null;
        java.sql.ResultSet rs = null;
        
        try {
            conn = conexaoDAO.conectar();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()){
                Cliente cliente = new Cliente();
                
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setTotalPontosAcumulados(rs.getInt("total_pontos"));
                
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar clientes: " + e.getMessage());
        } finally {
            try{
                if (rs != null) rs.close(); 
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (ResultSet/PreparedStatement): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);   
        }
        return listaClientes;
    }

    //read
    @Override
    public Cliente consultarPorId(int id) {
        
        String sql = "SELECT * FROM CLIENTE WHERE id = ?";
        
        Cliente cliente = null;
        
        Connection conn = null;
        PreparedStatement pstm = null;
        java.sql.ResultSet rs = null;

        try {
            conn = conexaoDAO.conectar();
            
            pstm = conn.prepareStatement(sql);
            
            pstm.setInt(1, id); 
            
            rs = pstm.executeQuery();
            
            if (rs.next()) {
                cliente = new Cliente();
                
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setTotalPontosAcumulados(rs.getInt("total_pontos"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar Cliente por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (ResultSet/PreparedStatement): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }
        
        return cliente;
    }
    
    //update
    @Override
     public void update(Cliente cliente) {
        String sql = "UPDATE CLIENTE SET nome = ?, cpf = ?, telefone = ?, total_pontos = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = conexaoDAO.conectar();
            
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setString(3, cliente.getTelefone());
            pstm.setInt(4, cliente.getTotalPontosAcumulados()); 
            pstm.setInt(5, cliente.getId()); 
            
            pstm.execute();
            
            System.out.println("Cliente ID " + cliente.getId() + " atualizado.");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar Cliente (UPDATE SQL): " + e.getMessage());
            
        } finally {
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement (UPDATE): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }
    }

    //delete
    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM CLIENTE WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = conexaoDAO.conectar();
            
            pstm = conn.prepareStatement(sql);
            
            pstm.setInt(1, id); 
            
            pstm.execute();
            
            System.out.println("Cliente ID " + id + " excluído.");

        } catch (SQLException e) {
            System.err.println("Erro ao excluir Cliente (DELETE SQL): " + e.getMessage());
            
        } finally {
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement (DELETE): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }
    }
}