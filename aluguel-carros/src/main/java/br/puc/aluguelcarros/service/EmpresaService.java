package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.Empresa;
import br.puc.aluguelcarros.repository.EmpresaRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;

@Singleton
public class EmpresaService {

    private final EmpresaRepository repository;

    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Empresa salvar(Empresa empresa) {
        return repository.save(empresa);
    }

    public List<Empresa> listarTodas() {
        return repository.findAll();
    }

    // Métodos do Diagrama
    public void avaliarPedido(Long idPedido, String parecer) {
        // Lógica: Empresa avaliando se o pedido de aluguel é viável
    }

    public boolean realizarAnaliseFinanceira(Long idPedido) {
        // Lógica: Verificar se o cliente tem crédito para este pedido
        return true; 
    }

    public void notificarStatus(Long idPedido, String status) {
        // Lógica: Notificar o cliente sobre o andamento
    }
}