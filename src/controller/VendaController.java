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

    private final VendaService vendaService;
    private final ClienteDAO clienteDAO;
    private final ProdutoDAO produtoDAO;

    // VARIÁVEIS DE ESTADO (MEMÓRIA TEMPORÁRIA da transação)
    private List<ItemVenda> itensVendaTemp;
    private Cliente clienteAtual;

    public VendaController() {
        // Inicializa as dependências do Service e DAO
        this.vendaService = new VendaService();
        this.clienteDAO = new ClienteDAO();
        this.produtoDAO = new ProdutoDAO();
        // Inicializa o estado da transação
        this.itensVendaTemp = new ArrayList<>();
        this.clienteAtual = null;
    }

    /**
     * 1. Define o cliente da venda (chamado uma vez ao adicionar o primeiro
     * item).
     */
    public void setClienteVenda(int clienteId) throws Exception {
        Cliente cliente = clienteDAO.consultarPorId(clienteId);
        if (cliente == null) {
            throw new Exception("Cliente não encontrado com o ID: " + clienteId);
        }
        this.clienteAtual = cliente;
    }

    /**
     * 2. Adiciona um item à lista temporária (Chamado pelo botão Adicionar
     * Item).
     */
    public void adicionarItem(int produtoId, int quantidade) throws Exception {
        if (quantidade <= 0) {
            throw new Exception("A quantidade deve ser maior que zero.");
        }

        Produto produto = produtoDAO.consultarPorId(produtoId);
        if (produto == null) {
            throw new Exception("Produto não encontrado com o ID: " + produtoId);
        }

        // Verifica estoque (Rápido)
        if (produto.getQuantidadeEmEstoque() < quantidade) {
            throw new Exception("Estoque insuficiente para o produto: " + produto.getNome());
        }

        // Cria o ItemVenda e adiciona na lista temporária
        ItemVenda novoItem = new ItemVenda(produto, quantidade, produto.getPreco());
        itensVendaTemp.add(novoItem);
    }

    /**
     * 3. Finaliza a transação (Chamado pelo botão Finalizar Venda).
     */
    public void finalizarVenda() throws Exception {
        if (clienteAtual == null) {
            throw new Exception("Selecione um cliente antes de finalizar a venda.");
        }
        if (itensVendaTemp.isEmpty()) {
            throw new Exception("A venda não pode ser finalizada sem produtos.");
        }

        // 1. Cria o objeto Venda completo com os dados temporários
        Venda novaVenda = new Venda(clienteAtual, itensVendaTemp);

        // 2. Chama o Service (que faz Estoque, Pontos, Salvar DB)
        vendaService.registrarVenda(novaVenda);

        // 3. Limpa os dados temporários após o sucesso
        limparVenda();
    }

    /**
     * 4. Limpa o estado da transação.
     */
    public void limparVenda() {
        this.itensVendaTemp = new ArrayList<>();
        this.clienteAtual = null;
    }

    // --- Getters auxiliares para a View ---
    public List<ItemVenda> getItensVendaTemp() {
        return itensVendaTemp;
    }

    public Cliente getClienteAtual() {
        return clienteAtual;
    }
}
