package controller;

import dao.ClienteDAO;
import model.Cliente;
import java.util.List;

public class ClienteController {
    
    private final ClienteDAO clienteDAO; 

    public ClienteController() {
        this.clienteDAO = new ClienteDAO(); 
    }
    
    public void salvarCliente(String nome, String cpf, String numero) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do cliente é obrigatório.");
        }
        
        Cliente cliente = new Cliente(nome, cpf, numero);
        this.clienteDAO.salvar(cliente);
    }
    

    public void salvarCliente(Cliente cliente) throws Exception {

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new Exception("O nome do cliente é obrigatório para atualização.");
        }
        
        this.clienteDAO.update(cliente);
    }
    

    public List<Cliente> buscarTodosClientes() {
        return clienteDAO.consultarTodos();
    }

    public Cliente buscarClientePorId(int id) {
        return clienteDAO.consultarPorId(id);
    }
    
    public void excluirCliente(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID inválido para exclusão.");
        }
        clienteDAO.excluir(id);
    }
}  
