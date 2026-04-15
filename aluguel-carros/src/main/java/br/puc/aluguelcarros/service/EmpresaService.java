package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.Empresa;
import br.puc.aluguelcarros.model.PedidoAluguel;
import br.puc.aluguelcarros.model.StatusPedido;
import br.puc.aluguelcarros.repository.EmpresaRepository;
import br.puc.aluguelcarros.repository.PedidoAluguelRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Singleton
public class EmpresaService {

    private final EmpresaRepository empresaRepository; // Injetando repositório de Empresa
    private final PedidoAluguelRepository pedidoRepository;

    public EmpresaService(EmpresaRepository empresaRepository, PedidoAluguelRepository pedidoRepository) {
        this.empresaRepository = empresaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    // --- Métodos CRUD que a Controller está pedindo ---

    @Transactional
    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    // --- Métodos de Regra de Negócio ---

    @Transactional
    public void avaliarPedido(Long idPedido, String parecer) {
        PedidoAluguel pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado"));

        if (parecer.toUpperCase().contains("APROVADO")) {
            pedido.setStatus(StatusPedido.APROVADO_PELA_EMPRESA);
        } else {
            pedido.setStatus(StatusPedido.REPROVADO_PELA_EMPRESA);
        }

        pedidoRepository.update(pedido);
        notificarStatus(idPedido, pedido.getStatus().name());
    }

    public boolean realizarAnaliseFinanceira(Long idPedido) {
        PedidoAluguel pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado"));

        double somaRendimentos = pedido.getCliente().getRendimentos().stream()
                .mapToDouble(r -> r.getValor().doubleValue())
                .sum();

        return somaRendimentos > (pedido.getValorAluguel() * 2);
    }

    public void notificarStatus(Long idPedido, String status) {
        System.out.println("LOG: Notificando alteração do pedido " + idPedido + " para o status: " + status);
    }
}