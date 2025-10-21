//TESTES NAO UTILIZADOS

package testes;

import model.Cliente;
import dao.ClienteDAO;
import java.util.List;

public class TesteClienteDAO {

    public static void testarConsulta(ClienteDAO dao) {
        System.out.println("\n--- Testando Consulta de Todos os Clientes ---");
        List<Cliente> clientes = dao.consultarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado no banco de dados.");
        } else {
            System.out.println("Clientes encontrados: " + clientes.size());
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.getId()
                        + ", Nome: " + c.getNome()
                        + ", CPF: " + c.getCpf()
                        + ", Pontos: " + c.getTotalPontosAcumulados());
            }
        }
    }

    public static void testarConsultaPorId(ClienteDAO dao, int id) {
        System.out.println("\n--- Testando Consulta de Cliente por ID: " + id + " ---");
        Cliente cliente = dao.consultarPorId(id);

        if (cliente != null) {
            System.out.println("SUCESSO! Cliente encontrado:");
            System.out.println("ID: " + cliente.getId()
                    + ", Nome: " + cliente.getNome()
                    + ", Pontos: " + cliente.getTotalPontosAcumulados());
        } else {
            System.out.println("FALHA! Cliente com ID " + id + " não encontrado.");
        }
    }

    public static void testarUpdate(ClienteDAO dao, int idParaAtualizar) {
        System.out.println("\n--- Testando Atualização de Cliente (ID: " + idParaAtualizar + ") ---");

        Cliente clienteParaAtualizar = dao.consultarPorId(idParaAtualizar);

        if (clienteParaAtualizar != null) {

            clienteParaAtualizar.setTotalPontosAcumulados(50); // Adiciona 50 pontos!
            clienteParaAtualizar.setTelefone("99999-9999");

            System.out.println("Novo saldo de pontos do cliente " + clienteParaAtualizar.getNome() + ": " + clienteParaAtualizar.getTotalPontosAcumulados());

            dao.update(clienteParaAtualizar);

            Cliente clienteVerificado = dao.consultarPorId(idParaAtualizar);
            if (clienteVerificado.getTotalPontosAcumulados() == clienteParaAtualizar.getTotalPontosAcumulados()) {
                System.out.println("SUCESSO! Atualização de pontos e telefone confirmada no BD.");
            } else {
                System.err.println("FALHA! O valor dos pontos no BD não corresponde ao esperado.");
            }

        } else {
            System.out.println("Não foi possível testar o UPDATE. Cliente com ID " + idParaAtualizar + " não encontrado.");
        }
    }

    public static void testarExclusao(ClienteDAO dao, int idParaExcluir) {
        System.out.println("\n--- Testando Exclusão de Cliente (ID: " + idParaExcluir + ") ---");

        dao.excluir(idParaExcluir);

        Cliente clienteVerificado = dao.consultarPorId(idParaExcluir);

        if (clienteVerificado == null) {
            System.out.println("SUCESSO! Cliente ID " + idParaExcluir + " foi removido do BD.");
        } else {
            System.err.println("FALHA! Cliente ID " + idParaExcluir + " ainda existe no BD.");
        }
    }

    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente("Kamille Lunelli", "09762298977", "47988831136");
        clienteDAO.salvar(cliente);

        Cliente cliente2 = new Cliente("Samuel", "09085438946", "11987654322");
        clienteDAO.salvar(cliente2);

    }

}
