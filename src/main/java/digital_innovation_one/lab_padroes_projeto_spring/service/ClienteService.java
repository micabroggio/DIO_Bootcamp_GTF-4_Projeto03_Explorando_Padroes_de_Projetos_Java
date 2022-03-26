package digital_innovation_one.lab_padroes_projeto_spring.service;

import digital_innovation_one.lab_padroes_projeto_spring.model.Cliente;

public interface ClienteService {

    Iterable<Cliente> buscar();

    Cliente buscarId(Long id);

    void inserir(Cliente cliente);

    void atualizarIdCliente(Long id, Cliente cliente);

    void deletar(Long id);

}
