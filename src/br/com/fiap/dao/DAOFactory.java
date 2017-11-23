package br.com.fiap.dao;

public abstract class DAOFactory {
	
	public static ClientesDao getClientesDAO(){
		return new MySQLClientesDao();
	}
	
	public static PedidosDao getPedidosDAO(){
		return new MySQLPedidosDao();
	}
}