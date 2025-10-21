package service; 

import dao.ClienteDAO;
import dao.ProdutoDAO;
import model.Cliente;
import model.Produto;
import java.sql.SQLException;

public class FidelidadeService {
    
    //atributos com final pois nao sao mutaveis
    private final ClienteDAO clienteDAO;
    private final ProdutoDAO produtoDAO;

    //construtor
    public FidelidadeService() {
        this.clienteDAO = new ClienteDAO();
        this.produtoDAO = new ProdutoDAO();
    }
    
    //metodo para realizar a troca de pontos e atualizar estoque
    public void trocarPontos(int clienteId, int produtoId, int quantidade) throws Exception {
        
        Cliente cliente = clienteDAO.consultarPorId(clienteId);
        Produto produto = produtoDAO.consultarPorId(produtoId);
        
        if (cliente == null || produto == null) {
            throw new Exception("Cliente ou Produto não encontrado.");//procura o cliente, se nao existir retorna a info
        }
        
        
        if (!produto.isEhResgatavel()) {
            throw new Exception("Este produto não está cadastrado como resgatável.");//procura o produto, se nao for resgatavel retorna a info
        }
        
        if (produto.getQuantidadeEmEstoque() < quantidade) {
            throw new Exception("Estoque zerado ou insuficiente para o resgate do produto: " + produto.getNome() + ".");//procura o produto, se estoque zerado retorna a info
        }
        
        int custoTotalEmPontos = produto.getCustoEmPontos() * quantidade;
        
        if (cliente.getTotalPontosAcumulados() < custoTotalEmPontos) {
            throw new Exception("Cliente não possui pontos suficientes. Pontos necessários: " + custoTotalEmPontos + ".");//caso cliente nao tenha pts suficientes
        }
        
        produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - quantidade);//atualizar estoque
        produtoDAO.update(produto); 
        
        cliente.setTotalPontosAcumulados(cliente.getTotalPontosAcumulados() - custoTotalEmPontos);//atualizar pontos do cliente
        clienteDAO.update(cliente);
        
        System.out.println("Resgate efetuado com sucesso! Produto: " + produto.getNome() + 
                           ". Pontos gastos: " + custoTotalEmPontos + ". Saldo restante: " + cliente.getTotalPontosAcumulados() + ".");
    }
}