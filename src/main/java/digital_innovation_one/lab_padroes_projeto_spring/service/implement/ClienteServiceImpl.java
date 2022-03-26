package digital_innovation_one.lab_padroes_projeto_spring.service.implement;

import digital_innovation_one.lab_padroes_projeto_spring.model.Cliente;
import digital_innovation_one.lab_padroes_projeto_spring.model.ClienteRepositorio;
import digital_innovation_one.lab_padroes_projeto_spring.model.Endereco;
import digital_innovation_one.lab_padroes_projeto_spring.model.EnderecoRepositorio;
import digital_innovation_one.lab_padroes_projeto_spring.service.ClienteService;
import digital_innovation_one.lab_padroes_projeto_spring.service.ViaCepService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private EnderecoRepositorio enderecoRepositorio;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscar() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Cliente buscarId(Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizarIdCliente(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepositorio.findById(id);
        if (clienteBd.isPresent()) {
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepositorio.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepositorio.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultaCep(cep);
            enderecoRepositorio.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepositorio.save(cliente);
    }

}
