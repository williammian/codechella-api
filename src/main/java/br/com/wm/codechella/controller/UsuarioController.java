package br.com.wm.codechella.controller;

import br.com.wm.codechella.domain.autenticacao.service.CadastroDeUsuarioService;
import br.com.wm.codechella.domain.autenticacao.vo.DadosCadastroUsuario;
import br.com.wm.codechella.domain.autenticacao.vo.DadosUsuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final CadastroDeUsuarioService cadastroDeUsuarioService;

    public UsuarioController(CadastroDeUsuarioService cadastroDeUsuarioService) {
        this.cadastroDeUsuarioService = cadastroDeUsuarioService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuario dadosParaCadastro, UriComponentsBuilder uriComponentsBuilder) {
        var dadosUsuarioCadastrado = cadastroDeUsuarioService.cadastrarUsuario(dadosParaCadastro);
        var uri = uriComponentsBuilder.path("usuarios/{id}").buildAndExpand(dadosUsuarioCadastrado.id()).toUri();
        return ResponseEntity.created(uri).body(dadosUsuarioCadastrado);
    }

}
