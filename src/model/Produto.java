package model;

import java.math.BigDecimal;

public class Produto {

    //atributos
    private int id;
    private String nome;
    private BigDecimal preco; //usa big decimal p nao precisar arredondar
    private String tipo;
    private int quantidadeEmEstoque;
    private boolean ehResgatavel;
    private int custoEmPontos;
    
    //construtor
    public Produto (int id, String nome, BigDecimal preco, String tipo, int quantidadeEmEstoque, boolean ehResgatavel, int custoEmPontos){
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.ehResgatavel = ehResgatavel;
        this.custoEmPontos = custoEmPontos;
    }
    
    //construtor sem id
    public Produto (String nome, BigDecimal preco, String tipo, int quantidadeEmEstoque, boolean ehResgatavel, int custoEmPontos){
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.ehResgatavel = ehResgatavel;
        this.custoEmPontos = custoEmPontos;
    }
    
    //construtor vazio
    public Produto(){
        
    }
    
    //setters e getters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getQuantidadeEmEstoque() { return quantidadeEmEstoque; }
    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) { this.quantidadeEmEstoque = quantidadeEmEstoque; }
    
    public boolean isEhResgatavel() { return ehResgatavel; } 
    public void setEhResgatavel(boolean ehResgatavel) { this.ehResgatavel = ehResgatavel; }

    public int getCustoEmPontos() { return custoEmPontos; }
    public void setCustoEmPontos(int custoEmPontos) { this.custoEmPontos = custoEmPontos; }
    
}
    