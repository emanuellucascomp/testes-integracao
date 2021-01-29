package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.LeilaoBuilder;
import br.com.alura.leilao.util.UsuarioBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LeilaoDaoTest {

    private LeilaoDao dao;
    private EntityManager manager;

    @BeforeEach
    public void init(){
        this.manager = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(manager);
        this.manager.getTransaction().begin();
    }

    @AfterEach
    public void finish(){
        this.manager.getTransaction().rollback();
    }

    @Test
    public void deveCriarUmLeilao(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@gmail.com")
                .comSenha("12345")
                .criar();
        this.manager.persist(usuario);
        
        Leilao leilao = new LeilaoBuilder()
                            .comNome("Mochila")
                            .comValorInicial("500")
                            .comUsuario(usuario)
                            .criar();
        leilao = this.dao.salvar(leilao);

        Leilao salvo = this.dao.buscarPorId(leilao.getId());
        Assert.assertNotNull(salvo);

    }

    @Test
    public void deveAtualizarUmLeilao(){
        Usuario usuario = criaUsuario();
        Leilao leilao = new Leilao("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);
        leilao = this.dao.salvar(leilao);

        leilao.setNome("Celular");
        leilao.setValorInicial(new BigDecimal("400"));
        leilao = this.dao.salvar(leilao);

        Leilao salvo = this.dao.buscarPorId(leilao.getId());

        Assert.assertEquals("Celular", salvo.getNome());
        Assert.assertEquals(new BigDecimal("400"), salvo.getValorInicial());

    }

    private Usuario criaUsuario(){
        Usuario usuarioCriacao = new Usuario("fulano", "fulano@email.com", "12345");
        this.manager.persist(usuarioCriacao);
        return usuarioCriacao;
    }
}