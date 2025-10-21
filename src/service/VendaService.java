package service; 

import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.VendaDAO;
import model.Cliente;
import model.Produto;
import model.Venda;
import model.ItemVenda;
import java.math.BigDecimal;
import java.math.RoundingMode; 

public class VendaService {

    //atributos com final pois nao sao mutaveis
    private final VendaDAO vendaDAO;
    private final ClienteDAO clienteDAO;
    private final ProdutoDAO produtoDAO;

    //construtor
    public VendaService() {
        this.vendaDAO = new VendaDAO();
        this.clienteDAO = new ClienteDAO();
        this.produtoDAO = new ProdutoDAO();
    }
    
    //vende, calcula pontos e atualiza estoque no banco de dados
    public void registrarVenda(Venda venda) throws Exception {
        
        Cliente cliente = clienteDAO.consultarPorId(venda.getCliente().getId());
        if (cliente == null) {
            throw new Exception("Cliente não encontrado. Não é possível registrar a venda.");
        }
        
        BigDecimal valorTotalCalculado = BigDecimal.ZERO;
        
        for (ItemVenda item : venda.getItens()) {
            Produto produtoNoBD = produtoDAO.consultarPorId(item.getProduto().getId());
            
            if (produtoNoBD.getQuantidadeEmEstoque() < item.getQuantidade()) {
                throw new Exception("Estoque insuficiente para o produto: " + produtoNoBD.getNome());//analisar estoque
            }
            
            valorTotalCalculado = valorTotalCalculado.add(item.getSubTotal());
            
            produtoNoBD.setQuantidadeEmEstoque(produtoNoBD.getQuantidadeEmEstoque() - item.getQuantidade());//atualizar estoque
            produtoDAO.update(produtoNoBD); 
        }
        
        venda.setValorTotal(valorTotalCalculado);
        
        BigDecimal fatorPontos = new BigDecimal("10.00");//acumula pontos (1 ponto a cada 10 reais gastos)
        
        int pontosGanhos = valorTotalCalculado.divide(fatorPontos, 0, RoundingMode.DOWN).intValue();//calcula pontos ganhos
        
        if (pontosGanhos > 0) {
            cliente.setTotalPontosAcumulados(cliente.getTotalPontosAcumulados() + pontosGanhos);//atualiza os pontos do cliente 
            clienteDAO.update(cliente); 
        }
        
        vendaDAO.salvar(venda); //salva a venda
        
        System.out.println("Venda registrada com sucesso! Pontos acumulados: " + pontosGanhos);
    }
    
}