package dao; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import interfaces.ICrud;
import model.Venda;
import model.Cliente;
import model.ItemVenda; 

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VendaDAO implements ICrud<Venda> {

    private final ConexaoDAO conexaoDAO;
    private final ItemVendaDAO itemVendaDAO; 

    public VendaDAO() {
        this.conexaoDAO = new ConexaoDAO();
        this.itemVendaDAO = new ItemVendaDAO(); 
    }

    //create
    @Override
    public void salvar(Venda venda) {
        String sqlVenda = "INSERT INTO VENDA (cliente_id, data_hora, valor_total) VALUES (?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmVenda = null;

        try {
            conn = conexaoDAO.conectar();
            conn.setAutoCommit(false); 
            
            pstmVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
            
            pstmVenda.setInt(1, venda.getCliente().getId());
            pstmVenda.setTimestamp(2, java.sql.Timestamp.valueOf(venda.getDataHora()));
            pstmVenda.setBigDecimal(3, venda.getValorTotal());
            
            pstmVenda.execute();
            
            ResultSet rs = pstmVenda.getGeneratedKeys();
            int vendaId = 0;
            if (rs.next()) {
                vendaId = rs.getInt(1);
                venda.setId(vendaId); 
            }
            rs.close(); 
            
            itemVendaDAO.salvarItens(venda.getItens(), vendaId, conn); 

            conn.commit();
            
            System.out.println("Venda ID " + vendaId + " e seus itens salvos (ICrud CREATE OK).");

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); 
                    System.err.println("Rollback realizado devido a erro na transação.");
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao realizar rollback: " + ex.getMessage());
            }
            System.err.println("Erro ao salvar Venda (ICrud CREATE): " + e.getMessage());

        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (pstmVenda != null) pstmVenda.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (VendaDAO): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }
    }
    
    //read
    @Override
    public List<Venda> consultarTodos() {
        String sql = "SELECT v.id, v.data_hora, v.valor_total, "
                   + "c.id AS cliente_id, c.nome AS cliente_nome " 
                   + "FROM VENDA v JOIN CLIENTE c ON v.cliente_id = c.id "
                   + "ORDER BY v.data_hora DESC";
        
        List<Venda> listaVendas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        java.sql.ResultSet rs = null;

        try {
            conn = conexaoDAO.conectar();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()) {
                Venda venda = new Venda();
                
                venda.setId(rs.getInt("id"));
                venda.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime()); 
                venda.setValorTotal(rs.getBigDecimal("valor_total"));
                
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("cliente_nome"));
                venda.setCliente(cliente);
                
                listaVendas.add(venda);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar todas as Vendas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (VendaDAO.consultarTodos): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }
        
        return listaVendas;
    }

    //read
    @Override
    public Venda consultarPorId(int id) {
        String sql = "SELECT v.*, c.nome AS cliente_nome, c.cpf "
                   + "FROM VENDA v JOIN CLIENTE c ON v.cliente_id = c.id "
                   + "WHERE v.id = ?";
        
        Venda venda = null;
        
        Connection conn = null;
        PreparedStatement pstm = null;
        java.sql.ResultSet rs = null;

        try {
            conn = conexaoDAO.conectar();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            
            if (rs.next()) { 
                venda = new Venda();
                
                venda.setId(rs.getInt("id"));
                venda.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                venda.setValorTotal(rs.getBigDecimal("valor_total"));
                
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("cliente_nome"));
                cliente.setCpf(rs.getString("cpf"));
                venda.setCliente(cliente);
                
                List<ItemVenda> itens = itemVendaDAO.consultarItensPorVenda(id); 
                venda.setItens(itens);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar Venda por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (VendaDAO.consultarPorId): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }
        
        return venda; 
    }

    //update
    @Override
    public void update(Venda venda) {
        System.out.println("AVISO ICRUD: Vendas são imutáveis. UPDATE não implementado na prática.");
    }

    //delete
    @Override
    public void excluir(int id) {
        System.out.println("AVISO ICRUD: Exclusão de Venda requer estorno. DELETE não implementado na prática.");
    }
}