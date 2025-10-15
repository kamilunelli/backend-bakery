package service; 

import dao.ClienteDAO;
import dao.ProdutoDAO;
import model.Cliente;
import model.Produto;
import java.sql.SQLException;

public class FidelidadeService {
    
    private final ClienteDAO clienteDAO;
    private final ProdutoDAO produtoDAO;

    public FidelidadeService() {
        this.clienteDAO = new ClienteDAO();
        this.produtoDAO = new ProdutoDAO();
    }
    
    public void trocarPontos(int clienteId, int produtoId, int quantidade) throws Exception {
        
        Cliente cliente = clienteDAO.consultarPorId(clienteId);
        Produto produto = produtoDAO.consultarPorId(produtoId);
        
        if (cliente == null || produto == null) {
            throw new Exception("Cliente ou Produto não encontrado.");
        }
        
        
        if (!produto.isEhResgatavel()) {
            throw new Exception("Este produto não está cadastrado como resgatável.");
        }
        
        if (produto.getQuantidadeEmEstoque() < quantidade) {
            throw new Exception("Estoque zerado ou insuficiente para o resgate do produto: " + produto.getNome() + ".");
        }
        
        int custoTotalEmPontos = produto.getCustoEmPontos() * quantidade;
        
        if (cliente.getTotalPontosAcumulados() < custoTotalEmPontos) {
            throw new Exception("Cliente não possui pontos suficientes. Pontos necessários: " + custoTotalEmPontos + ".");
        }
        
        produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - quantidade);
        produtoDAO.update(produto); 
        
        cliente.setTotalPontosAcumulados(cliente.getTotalPontosAcumulados() - custoTotalEmPontos);
        clienteDAO.update(cliente);
        
        System.out.println("Resgate efetuado com sucesso! Produto: " + produto.getNome() + 
                           ". Pontos gastos: " + custoTotalEmPontos + ". Saldo restante: " + cliente.getTotalPontosAcumulados() + ".");
    }
}