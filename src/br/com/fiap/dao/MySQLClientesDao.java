package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.entity.Clientes;
import br.com.fiap.entity.Pedidos;

public class MySQLClientesDao implements ClientesDao {

	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/vendas?autoReconnect=true&useSSL=false";

	Connection cn = null;
	PreparedStatement stmt;
	ResultSet rs = null;

	public static Connection criarConexao() throws Exception {
		Class.forName(DRIVER);
		return DriverManager.getConnection(URL, "root", "root");
	}

	@Override
	public Clientes inserirCliente(Clientes cliente) throws Exception {

		try {
			cn = criarConexao();
			stmt = cn.prepareStatement("INSERT INTO CLIENTES (NOME, EMAIL) VALUES (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEmail());
			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				cliente.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			cn.close();
			if (stmt != null)
				stmt.close();
		}
		return cliente;
	}

	@Override
	public Clientes buscarCliente(int id) throws Exception {

		Clientes cliente = null;
		List<Pedidos> pedidos = new ArrayList<>();
		PedidosDao pedidosDao = DAOFactory.getPedidosDAO();

		try {
			pedidos = pedidosDao.listarPedidos(id);
			
			
			cn = criarConexao();
			/*String sql = "SELECT idpedido,data,descricao,valor FROM pedidos WHERE clientes_idcliente=?";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				pedidos.add(new Pedidos(rs.getDate("DATA"), rs.getString("DESCRICAO"), rs.getDouble("VALOR"),
						rs.getInt("idpedido"), id));
			}*/

			String sql = "SELECT nome,email FROM clientes WHERE idcliente=?";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				cliente = new Clientes(rs.getString("nome"), rs.getString("email"), pedidos);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			cn.close();
			stmt.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}

		return cliente;

	}
}