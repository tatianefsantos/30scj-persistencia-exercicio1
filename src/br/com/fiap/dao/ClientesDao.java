package br.com.fiap.dao;

import br.com.fiap.entity.Clientes;

public interface ClientesDao {
	Clientes inserirCliente(Clientes cliente) throws Exception;

	Clientes buscarCliente(int id) throws Exception;
}