package br.com.wm.codechella.domain.autenticacao.vo;

import br.com.wm.codechella.domain.autenticacao.entity.Usuario;

import java.time.LocalDate;

public record DadosUsuario(Long id, String nome, String cpf, String email, LocalDate dataNascimento) {

    public DadosUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail(), usuario.getDataNascimento());
    }

}
