package controller;

import service.VendaService;
import dao.ClienteDAO;
import dao.ProdutoDAO;
import model.Venda;
import model.Cliente;
import model.Produto;
import model.ItemVenda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendaController {

    //atributos final pois nao sao mutaveis
    private final VendaService vendaService;
    private final ClienteDAO clienteDAO;
    private final ProdutoDAO produtoDAO;

    // atributos de estado (memoria temporaria da transacao)
    private List<ItemVenda> itensVendaTemp;
    private Cliente clienteAtual;

    //construtor
    public VendaController() {
        this.vendaService = new VendaService();
        this.clienteDAO = new ClienteDAO();
        this.produtoDAO = new ProdutoDAO();
        this.itensVendaTemp = new ArrayList<>();
        this.clienteAtual = null;
    }

    //definir cliente para qual a venda vai ser feita
    public void setClienteVenda(int clienteId) throws Exception {
        Cliente cliente = clienteDAO.consultarPorId(clienteId);
        if (cliente == null) {
            throw new Exception("Cliente não encontrado com o ID: " + clienteId);
        }
        this.clienteAtual = cliente;
    }

    //add item na lista de compras
    public void adicionarItem(int produtoId, int quantidade) throws Exception {
        if (quantidade <= 0) {
            throw new Exception("A quantidade deve ser maior que zero.");
        }

        Produto produto = produtoDAO.consultarPorId(produtoId);
        if (produto == null) {
            throw new Exception("Produto não encontrado com o ID: " + produtoId);
        }

        //verifica estoque
        if (produto.getQuantidadeEmEstoque() < quantidade) {
            throw new Exception("Estoque insuficiente para o produto: " + produto.getNome());
        }

        //cria o item e addd na lista
        ItemVenda novoItem = new ItemVenda(produto, quantidade, produto.getPreco());
        itensVendaTemp.add(novoItem);
    }

    //finaliza a venda
    public void finalizarVenda() throws Exception {
        if (clienteAtual == null) {
            throw new Exception("Selecione um cliente antes de finalizar a venda.");
        }
        if (itensVendaTemp.isEmpty()) {
            throw new Exception("A venda não pode ser finalizada sem produtos.");
        }

        Venda novaVenda = new Venda(clienteAtual, itensVendaTemp);

        vendaService.registrarVenda(novaVenda);

        limparVenda();
    }

    //limpa o estado da transacao
    public void limparVenda() {
        this.itensVendaTemp = new ArrayList<>();
        this.clienteAtual = null;
    }

    //getters para a view
    public List<ItemVenda> getItensVendaTemp() {
        return itensVendaTemp;
    }

    public Cliente getClienteAtual() {
        return clienteAtual;
    }
}
