package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.entity.Pedidos;

public class MySQLPedidosDao implements PedidosDao {

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
	public Pedidos incluirPedido(Pedidos pedido) throws Exception {

		try {
			cn = criarConexao();

			String sql = "INSERT INTO PEDIDOS (clientes_idcliente,DATA,DESCRICAO,VALOR) VALUES (?,?,?,?)";
			stmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, pedido.getIdCliente());
			stmt.setDate(2, new Date(pedido.getData().getTime()));
			stmt.setString(3, pedido.getDescricao());
			stmt.setDouble(4, pedido.getValor());
			stmt.execute();

			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				pedido.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			cn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}
		return pedido;
	}

	@Override
	public List<Pedidos> listarPedidos(int idCliente) throws Exception {
		List<Pedidos> pedidos = new ArrayList<>();

		try {
			cn = criarConexao();

			String sql = "SELECT idpedido,data,descricao,valor FROM pedidos WHERE clientes_idcliente=?";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			rs = stmt.executeQuery();
			while (rs.next()) {
				pedidos.add(new Pedidos(rs.getDate("data"), rs.getString("descricao"), rs.getDouble("valor"),
						rs.getInt("idpedido"), idCliente));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			cn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}

		return pedidos;

	}
}