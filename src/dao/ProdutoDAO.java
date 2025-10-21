package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import interfaces.ICrud;
import model.Produto;

public class ProdutoDAO implements ICrud<Produto> {

    //atributo
    private final ConexaoDAO conexaoDAO;

    //crud de produto
    public ProdutoDAO() {
        this.conexaoDAO = new ConexaoDAO(); 
    }

    //create
    @Override
    public void salvar(Produto produto) {
        
        String sql = "INSERT INTO PRODUTO (nome, preco, tipo, estoque, eh_resgatavel, custo_em_pontos) VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = conexaoDAO.conectar();
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1, produto.getNome());
            
            pstm.setBigDecimal(2, produto.getPreco()); 
            
            pstm.setString(3, produto.getTipo());
            
            pstm.setInt(4, produto.getQuantidadeEmEstoque());
            
            pstm.setBoolean(5, produto.isEhResgatavel()); 
            
            pstm.setInt(6, produto.getCustoEmPontos());
            
            pstm.execute();
            
            System.out.println("Produto " + produto.getNome() + " salvo.");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar Produto (INSERT SQL): " + e.getMessage());

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
    public List<Produto> consultarTodos() {
    
        String sql = "SELECT * FROM PRODUTO ORDER BY nome";

        List<Produto> listaProdutos = new java.util.ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;
        java.sql.ResultSet rs = null;

        try {
            conn = conexaoDAO.conectar();

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));

                produto.setPreco(rs.getBigDecimal("preco"));

                produto.setTipo(rs.getString("tipo"));
                produto.setQuantidadeEmEstoque(rs.getInt("estoque"));

                produto.setEhResgatavel(rs.getBoolean("eh_resgatavel"));
                produto.setCustoEmPontos(rs.getInt("custo_em_pontos"));

                listaProdutos.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar todos os Produtos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (ProdutoDAO.consultarTodos): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }

        return listaProdutos;
    }

    
    //read
    @Override
    public Produto consultarPorId(int id) {
        
        String sql = "SELECT * FROM PRODUTO WHERE id = ?";

        Produto produto = null;

        Connection conn = null;
        PreparedStatement pstm = null;
        java.sql.ResultSet rs = null;

        try {
            conn = conexaoDAO.conectar();

            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            rs = pstm.executeQuery();

            if (rs.next()) {
                produto = new Produto();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getBigDecimal("preco"));
                produto.setTipo(rs.getString("tipo"));
                produto.setQuantidadeEmEstoque(rs.getInt("estoque"));
                produto.setEhResgatavel(rs.getBoolean("eh_resgatavel"));
                produto.setCustoEmPontos(rs.getInt("custo_em_pontos"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar Produto por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (ProdutoDAO.consultarPorId): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }

        return produto; 
    }

    //update
    @Override
    public void update(Produto produto) {
        
        String sql = "UPDATE PRODUTO SET nome = ?, preco = ?, tipo = ?, estoque = ?, eh_resgatavel = ?, custo_em_pontos = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = conexaoDAO.conectar();
            
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1, produto.getNome());
            pstm.setBigDecimal(2, produto.getPreco());
            pstm.setString(3, produto.getTipo());
            pstm.setInt(4, produto.getQuantidadeEmEstoque()); 
            pstm.setBoolean(5, produto.isEhResgatavel()); 
            pstm.setInt(6, produto.getCustoEmPontos()); 
            
            pstm.setInt(7, produto.getId()); 
            
            pstm.execute();
            
            System.out.println("Produto ID " + produto.getId() + " atualizado.");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar Produto (UPDATE SQL): " + e.getMessage());
            
        } finally {
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement (UPDATE Produto): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }
    }
    
    //delete
    @Override
    public void excluir(int id) {
    
        String sql = "DELETE FROM PRODUTO WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = conexaoDAO.conectar();
            
            pstm = conn.prepareStatement(sql);
            
            pstm.setInt(1, id); 
            
            pstm.execute();
            
            System.out.println("Produto ID " + id + " excluído.");

        } catch (SQLException e) {
            System.err.println("Erro ao excluir Produto (DELETE SQL): " + e.getMessage());
            
        } finally {
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement (DELETE Produto): " + e.getMessage());
            }
            conexaoDAO.fecharConexao(conn);
        }
    }
}       