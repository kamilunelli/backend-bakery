package controller;

import dao.ProdutoDAO;
import model.Produto;
import java.math.BigDecimal;
import java.util.List;


public class ProdutoController {
    
    //atributo final pois nao eh mutavel
    private final ProdutoDAO produtoDAO; 

    //construtor
    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO(); 
    }
    
    //método para cadastrar produtos    
    public void salvarProduto(String nome, BigDecimal preco, String tipo, int quantidadeEmEstoque, boolean ehResgatavel, int custoEmPontos) {
        Produto produto = new Produto(nome, preco, tipo, quantidadeEmEstoque, ehResgatavel, custoEmPontos);
        this.produtoDAO.salvar(produto); 
    }
    
    public void salvarProduto(Produto produto) throws Exception {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new Exception("O nome do produto é obrigatório para atualização.");
        }
        // Se o produto já tem ID, o DAO sabe que é UPDATE.
        this.produtoDAO.update(produto);
    }
    
    //buscar os produtos
    public List<Produto> buscarTodosProdutos() {
        return produtoDAO.consultarTodos();
    }
    
    //buscar pelo id
    public Produto buscarProdutoPorId(int id) {
        return produtoDAO.consultarPorId(id);
    }
    
    //deletar produto
    public void excluirProduto(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID inválido para exclusão.");
        }
        produtoDAO.excluir(id);
    }
}
