package br.com.wm.codechella.domain.evento.service;

import br.com.wm.codechella.domain.RegraDeNegocioException;
import br.com.wm.codechella.domain.evento.entity.Evento;
import br.com.wm.codechella.domain.evento.entity.Ingresso;
import br.com.wm.codechella.domain.evento.repository.EventoRepository;
import br.com.wm.codechella.domain.evento.vo.DadosCadastroEvento;
import br.com.wm.codechella.domain.evento.vo.DadosCadastroIngresso;
import br.com.wm.codechella.domain.evento.vo.DadosEvento;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Cacheable(value = "proximosEventos")
    public List<DadosEvento> listarProximosEventos() {
        var proximosEventos = eventoRepository.findAllByDataAfter(LocalDateTime.now());
        return proximosEventos.stream().map(DadosEvento::new).toList();
    }

    @CacheEvict(value = "proximosEventos", allEntries = true)
    public DadosEvento cadastrar(DadosCadastroEvento dadosCadastro) {
        var eventoJaCadastrado = eventoRepository.existsByNomeIgnoringCase(dadosCadastro.nome());
        if (eventoJaCadastrado) {
            throw new RegraDeNegocioException("Evento já cadastrado com esse nome!");
        }

        var ingressos = criarIngressos(dadosCadastro.ingressos());
        var evento = new Evento(dadosCadastro, ingressos);
        this.eventoRepository.save(evento);

        return new DadosEvento(evento);
    }

    public DadosEvento detalhar(Long id) {
        var evento = eventoRepository.findById(id).get();
        return new DadosEvento(evento);
    }

    private List<Ingresso> criarIngressos(List<DadosCadastroIngresso> dadosIngressos) {
        var ingressos = new ArrayList<Ingresso>();

        dadosIngressos.forEach(dados -> {
            IntStream.range(0, dados.quantidade()).forEach(i -> {
                ingressos.add(new Ingresso(dados.descricao(), dados.preco()));
            });
        });

        return ingressos;
    }

}
