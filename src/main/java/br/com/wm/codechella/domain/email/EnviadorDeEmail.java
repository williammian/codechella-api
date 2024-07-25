package br.com.wm.codechella.domain.email;

public interface EnviadorDeEmail {

    void enviar(String destinatario, String assunto, String mensagem);

}
