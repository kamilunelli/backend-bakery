package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.ICrud;
import model.ItemVenda;
import model.Produto;

public class ItemVendaDAO implements ICrud<ItemVenda> {

    private final ConexaoDAO conexaoDAO;

    public ItemVendaDAO() {
        this.conexaoDAO = new ConexaoDAO();
    }

    //create
    @Override
    public void salvar(ItemVenda itemVenda) {
        String sql = "INSERT INTO ITEM_VENDA (venda_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = conexaoDAO.conectar();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, itemVenda.getVenda().getId());
            pstm.setInt(2, itemVenda.getProduto().getId());
            pstm.setInt(3, itemVenda.getQuantidade());
            pstm.setBigDecimal(4, itemVenda.getPrecoUnitario());
            pstm.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar ItemVenda (ICrud): " + e.getMessage());
        } finally {
            if (pstm != null) try {
                pstm.close();
            } catch (SQLException e) {
            }
            conexaoDAO.fecharConexao(conn);
        }
    }

    public void salvarItens(List<ItemVenda> itens, int vendaId, Connection conn) throws SQLException {
        String sql = "INSERT INTO ITEM_VENDA (venda_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        try {
            for (ItemVenda item : itens) {
                pstm.setInt(1, vendaId);
                pstm.setInt(2, item.getProduto().getId());
                pstm.setInt(3, item.getQuantidade());
                pstm.setBigDecimal(4, item.getPrecoUnitario());
                pstm.addBatch();
            }
            pstm.executeBatch();
        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }
    }

    //read
    @Override
    public List<ItemVenda> consultarTodos() {
        return new ArrayList<>();
    }

    //read
    public List<ItemVenda> consultarItensPorVenda(int vendaId) throws SQLException {

        String sql = "SELECT iv.*, p.nome, p.preco "
                + "FROM ITEM_VENDA iv JOIN PRODUTO p ON iv.produto_id = p.id "
                + "WHERE iv.venda_id = ?";

        List<ItemVenda> itens = new java.util.ArrayList<>();

        ConexaoDAO conexaoDAO = new ConexaoDAO();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = conexaoDAO.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, vendaId);

            rs = pstm.executeQuery();

            while (rs.next()) {
                ItemVenda item = new ItemVenda();

                item.setId(rs.getInt("id"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));

                Produto produto = new Produto();
                produto.setId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("nome"));

                item.setProduto(produto);

                itens.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Erro SQL ao consultar Itens de Venda: " + e.getMessage());
            throw e;

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            conexaoDAO.fecharConexao(conn);
        }

        return itens;
    }

    //read
    @Override
    public ItemVenda consultarPorId(int id) {
        return null;
    }

    //update
    @Override
    public void update(ItemVenda itemVenda) {
        System.out.println("AVISO: Atualização de ItemVenda não é recomendada.");
    }

    //delete
    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM ITEM_VENDA WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = conexaoDAO.conectar();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            System.out.println("ItemVenda ID " + id + " excluído (Cuidado!).");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir ItemVenda: " + e.getMessage());
        } finally {
            if (pstm != null) try {
                pstm.close();
            } catch (SQLException e) {
            }
            conexaoDAO.fecharConexao(conn);
        }
    }
}
