package controller;

import dao.ClienteDAO;
import model.Cliente;

public class ClienteController {
    
    private final ClienteDAO clienteDAO; 

    public ClienteController() {
        this.clienteDAO = new ClienteDAO(); 
    }

    public void salvarCliente(String nome, String cpf, String numero) {
        Cliente cliente = new Cliente(nome, cpf, numero);
        this.clienteDAO.salvar(cliente); 
    }
}