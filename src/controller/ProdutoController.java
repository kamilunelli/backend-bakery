package controller;

import dao.ProdutoDAO;
import model.Produto;
import java.math.BigDecimal;


public class ProdutoController {
    
    private final ProdutoDAO produtoDAO; 

    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO(); 
    }
    
    //método para cadastrar produtos    
    public void salvarProduto(String nome, BigDecimal preco, String tipo, int quantidadeEmEstoque, boolean ehResgatavel, int custoEmPontos) {
        Produto produto = new Produto(nome, preco, tipo, quantidadeEmEstoque, ehResgatavel, custoEmPontos);
        this.produtoDAO.salvar(produto); 
    }
}