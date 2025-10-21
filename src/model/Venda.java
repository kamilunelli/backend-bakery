package model;

import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Venda {
    
    //atributos
    private int id;
    private Cliente cliente;
    private LocalDateTime dataHora;
    private List<ItemVenda> itens;
    private BigDecimal valorTotal;
    
    //construtor vazio
    public Venda (){
    }
    
    //construtor para nova venda
    public Venda(Cliente cliente, List<ItemVenda> itens){
        this.cliente = cliente;
        this.itens = itens;
        this.dataHora = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;
    }
    
    //construtor
    public Venda(int id, Cliente cliente, LocalDateTime dataHora, BigDecimal valorTotal){
        this.id = id;
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.valorTotal = valorTotal; 
    }
    
    //setters e getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public List<ItemVenda> getItens(){
        return itens;
    }
    
    public void setItens(List<ItemVenda> itens){
        this.itens = itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
