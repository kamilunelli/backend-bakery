package dao; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import interfaces.ICrud;
import model.Venda;
import model.Cliente;

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
    
    // read
    @Override
    public List<Venda> consultarTodos() {
        String sql = "SELECT v.id, v.data_hora, v.valor_total, c.id AS cliente_id, c.nome AS cliente_nome FROM VENDA v JOIN CLIENTE c ON v.cliente_id = c.id ORDER BY v.data_hora DESC";
        return null; 
    }

    //read
    @Override
    public Venda consultarPorId(int id) {
        return null; 
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