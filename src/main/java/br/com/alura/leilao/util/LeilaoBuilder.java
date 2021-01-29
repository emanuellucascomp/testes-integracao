package br.com.alura.leilao.util;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import java.math.BigDecimal;

public class LeilaoBuilder {

    private String nome;
    private BigDecimal valorInicial;
    private Usuario usuario;

    public LeilaoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public LeilaoBuilder comValorInicial(String valorInicial) {
        this.valorInicial = new BigDecimal(valorInicial);
        return this;
    }

    public LeilaoBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Leilao criar() {
        return new Leilao(nome, valorInicial, usuario);
    }
}
