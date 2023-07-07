package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PerformanceConsultas {
        public static void main(String[] args) {
        popularBancoDeDados();
        EntityManager em = JPAUtil.getEntityManager();

        Pedido pedido = em.find(Pedido.class, 1l);
        System.out.println(pedido.getItens().size());
    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria videogames = new Categoria("VIDEOGAME");
        Categoria informatica = new Categoria("INFORMATICA");


        Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
        Produto videogame = new Produto("PS5 ", "Play 5", new BigDecimal("2000"), videogames);
        Produto macbook = new Produto("Macbook", "Macbook pro", new BigDecimal("15000"), informatica);

        Cliente cliente = new Cliente("Fer", "156");

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        produtoDao.cadastrar(celular);
        clienteDao.cadastrar(cliente);

        categoriaDao.cadastrar(videogames);
        produtoDao.cadastrar(videogame);
        clienteDao.cadastrar(cliente);

        categoriaDao.cadastrar(informatica);
        produtoDao.cadastrar(macbook);
        clienteDao.cadastrar(cliente);


        Produto produto = produtoDao.buscarPorId(1l);



        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));


        PedidoDao pedidoDao = new PedidoDao(em);
        pedidoDao.cadastrar(pedido);

        em.getTransaction().commit();
        em.close();
    }
}

