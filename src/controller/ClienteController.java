package controller;

import dao.ClienteDAO;
import model.Cliente;
import java.util.List;

public class ClienteController {
    
    //atributo
    private final ClienteDAO clienteDAO; 

    //construtor
    public ClienteController() {
        this.clienteDAO = new ClienteDAO(); 
    }
    
    //salva cliente no bd
    public void salvarCliente(String nome, String cpf, String numero) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do cliente é obrigatório.");
        }
        
        Cliente cliente = new Cliente(nome, cpf, numero);
        this.clienteDAO.salvar(cliente);
    }
    
    //atualizar cliente
    public void salvarCliente(Cliente cliente) throws Exception {

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new Exception("O nome do cliente é obrigatório para atualização.");
        }
        
        this.clienteDAO.update(cliente);
    }
    

    //buscar os clientes cadastrados
    public List<Cliente> buscarTodosClientes() {
        return clienteDAO.consultarTodos();
    }

    //buscar os clientes pelo id
    public Cliente buscarClientePorId(int id) {
        return clienteDAO.consultarPorId(id);
    }
    
    //deletar cliente
    public void excluirCliente(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID inválido para exclusão.");
        }
        clienteDAO.excluir(id);
    }
}  
