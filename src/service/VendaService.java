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
    
    private final VendaDAO vendaDAO;
    private final ClienteDAO clienteDAO;
    private final ProdutoDAO produtoDAO;

    public VendaService() {
        this.vendaDAO = new VendaDAO();
        this.clienteDAO = new ClienteDAO();
        this.produtoDAO = new ProdutoDAO();
    }
    
    public void registrarVenda(Venda venda) throws Exception {
        
        Cliente cliente = clienteDAO.consultarPorId(venda.getCliente().getId());
        if (cliente == null) {
            throw new Exception("Cliente não encontrado. Não é possível registrar a venda.");
        }
        
        BigDecimal valorTotalCalculado = BigDecimal.ZERO;
        
        for (ItemVenda item : venda.getItens()) {
            Produto produtoNoBD = produtoDAO.consultarPorId(item.getProduto().getId());
            
            if (produtoNoBD.getQuantidadeEmEstoque() < item.getQuantidade()) {
                throw new Exception("Estoque insuficiente para o produto: " + produtoNoBD.getNome());
            }
            
            valorTotalCalculado = valorTotalCalculado.add(item.getSubTotal());
            
            produtoNoBD.setQuantidadeEmEstoque(produtoNoBD.getQuantidadeEmEstoque() - item.getQuantidade());
            produtoDAO.update(produtoNoBD); 
        }
        
        venda.setValorTotal(valorTotalCalculado);
        
        BigDecimal fatorPontos = new BigDecimal("10.00");
        
        int pontosGanhos = valorTotalCalculado.divide(fatorPontos, 0, RoundingMode.DOWN).intValue();
        
        if (pontosGanhos > 0) {
            cliente.setTotalPontosAcumulados(cliente.getTotalPontosAcumulados() + pontosGanhos); 
            clienteDAO.update(cliente); 
        }
        
        vendaDAO.salvar(venda); 
        
        System.out.println("Venda registrada com sucesso! Pontos acumulados: " + pontosGanhos);
    }
    
}