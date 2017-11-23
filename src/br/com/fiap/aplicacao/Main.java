package br.com.fiap.aplicacao;

import java.util.Date;

import br.com.fiap.dao.ClientesDao;
import br.com.fiap.dao.DAOFactory;
import br.com.fiap.dao.PedidosDao;
import br.com.fiap.entity.Clientes;
import br.com.fiap.entity.Pedidos;

public class Main {

	public static void main(String[] args) {

		try {

			ClientesDao clientesDao = DAOFactory.getClientesDAO();

			Clientes cliente = new Clientes();
			cliente.setNome("Tatiane dos Santos");
			cliente.setEmail("rm44463@fiap.com.br");
			cliente = clientesDao.inserirCliente(cliente);

			PedidosDao pedidosDao = DAOFactory.getPedidosDAO();

			Pedidos pedido = new Pedidos();
			pedido.setData(new Date());
			pedido.setDescricao("Notebook");
			pedido.setValor(3500);
			pedido.setIdCliente(cliente.getId());

			pedidosDao.incluirPedido(pedido);
			
			cliente = new Clientes();
			cliente = clientesDao.buscarCliente(2);
			
			System.out.println("Pedidos do cliente: " + cliente.getNome());
			for (Pedidos pedidos : cliente.getPedidos()) {
				System.out.println(pedidos.getDescricao());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}