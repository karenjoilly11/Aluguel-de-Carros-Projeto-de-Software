package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.dto.ClienteRequestDTO;
import br.puc.aluguelcarros.dto.ClienteResponseDTO;
import br.puc.aluguelcarros.model.Cliente;
import br.puc.aluguelcarros.model.Rendimento;
import br.puc.aluguelcarros.repository.ClienteRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Singleton
public class ClienteService {

    private static final int MAX_RENDIMENTOS = 3;
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO dto) {
        if (clienteRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        
        // CORREÇÃO: Usando o nome correto do campo da classe Usuario
        // Em um cenário real, aqui você usaria um PasswordEncoder para fazer o Hash
        cliente.setSenhaHash(dto.senha()); 
        
        cliente.setRole("CLIENTE"); // Definindo o papel conforme exigido por Usuario
        cliente.setCpf(dto.cpf());
        cliente.setEndereco(dto.endereco());
        cliente.setProfissao(dto.profissao());

        // Vinculando Rendimentos (conforme o novo diagrama simplificado)
        if (dto.rendimentos() != null) {
            if (dto.rendimentos().size() > MAX_RENDIMENTOS) {
                throw new IllegalArgumentException("Máximo de " + MAX_RENDIMENTOS + " rendimentos.");
            }
            
            dto.rendimentos().forEach(r -> {
                Rendimento rend = new Rendimento();
                rend.setEntidadeEmpregadora(r.nomeEntidadeEmpregadora());
                rend.setValor(r.valor());
                rend.setCliente(cliente);
                cliente.getRendimentos().add(rend);
            });
        }

        return ClienteResponseDTO.from(clienteRepository.save(cliente));
    }

    @Transactional
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAllWithDetails()
                .stream()
                .map(ClienteResponseDTO::from)
                .toList();
    }

    @Transactional
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado: id=" + id));
        return ClienteResponseDTO.from(cliente);
    }

    @Transactional
    public ClienteResponseDTO atualizarDados(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado: id=" + id));

        cliente.setNome(dto.nome());
        cliente.setEndereco(dto.endereco());
        cliente.setProfissao(dto.profissao());

        // Atualização da lista de rendimentos (Orphan Removal cuida do banco)
        if (dto.rendimentos() != null) {
            if (dto.rendimentos().size() > MAX_RENDIMENTOS) {
                throw new IllegalArgumentException("Máximo de " + MAX_RENDIMENTOS + " rendimentos.");
            }
            
            cliente.getRendimentos().clear();
            dto.rendimentos().forEach(r -> {
                Rendimento rend = new Rendimento();
                rend.setEntidadeEmpregadora(r.nomeEntidadeEmpregadora());
                rend.setValor(r.valor());
                rend.setCliente(cliente);
                cliente.getRendimentos().add(rend);
            });
        }

        return ClienteResponseDTO.from(clienteRepository.update(cliente));
    }
    
    @Transactional
    public void excluirCadastro(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado: id=" + id));
        clienteRepository.delete(cliente);
    }
}