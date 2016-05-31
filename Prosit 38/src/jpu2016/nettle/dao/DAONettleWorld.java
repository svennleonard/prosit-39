package jpu2016.nettle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAONettleWorld {
	private static String LOGIN = "svenn3";
	private static String PASSWORD = "exia";
	private static String URL = "jdbc:mysql://10.133.50.19/nettleworld?autoReconnect=true&useSSH=false";

	public static String getLOGIN() {
		return LOGIN;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static String getURL() {
		return URL;
	}

	public static Connection open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			return connection;
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (final SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void read(final String nommap) {
		try {
			final Connection op = open();
			java.sql.Statement statement;
			final ResultSet resultSet;

			statement = op.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM map WHERE `nom` LIKE '" + nommap + "';");

			while (resultSet.next()) {
				System.out.print(resultSet.getInt("id") + "\t");
				System.out.print(resultSet.getString("nom") + "\t");
				System.out.print(resultSet.getInt("longueur") + "\t");
				System.out.print(resultSet.getInt("largeur") + "\t");
				// System.out.print(resultSet.getString("map") + "\t");
				System.out.println();

			}

		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String readmap(final String nommap) {
		String map = null;
		try {
			final Connection op = open();
			java.sql.Statement statement;
			final ResultSet resultSet;

			statement = op.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM map WHERE `nom` LIKE '" + nommap + "';");

			while (resultSet.next()) {
				// System.out.print(resultSet.getString("map") + "\t");
				map = resultSet.getString("map");
				System.out.println();
			}

		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	public static void setLOGIN(final String lOGIN) {
		LOGIN = lOGIN;
	}

	public static void setPASSWORD(final String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public static void setURL(final String uRL) {
		URL = uRL;
	}

	public static void test() throws SQLException {
		open();
		read("camp");
	}

	private Connection connection;

	private java.sql.Statement statement;

	public DAONettleWorld() {
		this.statement = null;
		this.connection = null;
	}

	public void close() throws SQLException {
		this.statement.close();
	}

	public Connection getConnection() {
		return this.connection;
	}

	public java.sql.Statement getStatement() {
		return this.statement;
	}

	public void setConnection(final Connection connection) {
		this.connection = connection;
	}

	public void setStatement(final java.sql.Statement statement) {
		this.statement = statement;
	}

}
