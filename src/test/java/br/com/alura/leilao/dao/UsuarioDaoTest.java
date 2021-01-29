package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager manager;

    @BeforeEach
    public void init(){
        this.manager = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(manager);
        this.manager.getTransaction().begin();
    }

    @AfterEach
    public void finish(){
        this.manager.getTransaction().rollback();
    }

    @Test
    public void deveBuscarPorUsername(){
        Usuario usuarioCriacao = criaUsuario();
        Usuario usuario = this.dao.buscarPorUsername(usuarioCriacao.getNome());
        Assert.assertNotNull(usuario);
    }

    @Test
    public void deveRetornarNuloParaUsernameInexistente(){
        criaUsuario();
        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));
    }

    @Test
    public void deveRemoverUsuario(){
        Usuario usuario = criaUsuario();
        this.dao.deletar(usuario);

        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("fulano"));
    }

    private Usuario criaUsuario(){
        Usuario usuarioCriacao = new Usuario("fulano", "fulano@email.com", "12345");
        this.manager.persist(usuarioCriacao);
        return usuarioCriacao;
    }
}