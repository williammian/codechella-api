package br.com.wm.codechella.domain.evento.vo;

import br.com.wm.codechella.domain.evento.entity.Compra;
import br.com.wm.codechella.domain.evento.entity.Ingresso;

import java.time.LocalDateTime;
import java.util.List;

public record DadosCompra(Long id, LocalDateTime data, FormaDePagamento formaDePagamento, List<DadosIngressoCompra> ingressos) {

    public DadosCompra(Compra compra, List<Ingresso> ingressos) {
        this(compra.getId(), compra.getData(), compra.getFormaDePagamento(), ingressos.stream().map(DadosIngressoCompra::new).toList());
    }

}
