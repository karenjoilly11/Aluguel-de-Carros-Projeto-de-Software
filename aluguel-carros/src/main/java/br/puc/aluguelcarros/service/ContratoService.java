package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.Contrato;
import br.puc.aluguelcarros.model.Banco;
import br.puc.aluguelcarros.repository.ContratoRepository;
import br.puc.aluguelcarros.repository.PedidoAluguelRepository;
import br.puc.aluguelcarros.repository.BancoRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

@Singleton
public class ContratoService {

    private final ContratoRepository repository;
    private final PedidoAluguelRepository pedidoRepository;
    private final BancoRepository bancoRepository;

    public ContratoService(ContratoRepository repository, PedidoAluguelRepository pedidoRepository, BancoRepository bancoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
        this.bancoRepository = bancoRepository;
    }

    @Transactional
    public Contrato gerarContrato(Long pedidoId, String termos, double valor, Long bancoId) {
        Contrato contrato = new Contrato();
        contrato.setTermos(termos);
        contrato.setValorFinal(valor);
        
        pedidoRepository.findById(pedidoId).ifPresent(contrato::setPedidoAluguel);
        
        if (bancoId != null) {
            bancoRepository.findById(bancoId).ifPresent(contrato::vincularCreditoBancario);
        }

        return repository.save(contrato);
    }

    @Transactional
    public void assinar(Long id) {
        repository.findById(id).ifPresent(c -> {
            c.registrarAssinatura();
            repository.update(c);
        });
    }
}