package jpu2016.nettle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingUtilities;

import jpu2016.nettle.dao.DAONettleWorld;
import jpu2016.nettle.play.NettlePlay;
import jpu2016.nettle.view.INettleFrame;
import jpu2016.nettle.view.NettleFrame;
import jpu2016.nettle.world.INettleWorld;
import jpu2016.nettle.world.NettleWorld;

public final class Nettle implements Runnable {
	private static final Nettle QueryJavaCell = null;

	/*
	 * public static String getQueryInsert(final NettleWorld nettleWorld, final
	 * int idSave) { String query =
	 * "INSERT INTO `javapetri`.`RGBCELL` (`ID`, `X`, `Y`, `STRENGTH`, `COLOR`, `ID_JAVAPETRI`) VALUES "
	 * ; Boolean isFirst = true; for (final NettleWorld nettleWorld2 :
	 * Nettle.getJavaCells()) { if (isFirst) { isFirst = false; } else { query
	 * += ", "; } query += "(NULL, '" + javaCell.getPosition().getX() + "', '" +
	 * javaCell.getPosition().getY() + "', '" + javaCell.getStrength() + "', '"
	 * + javaCell.getColor().getRGB() + "', '" + idSave + "')"; } return query;
	 * }
	 */

	private Connection connection;
	private INettleFrame nettleFrame;
	private final NettlePlay nettlePlay;
	private final INettleWorld nettleWorld;

	private Statement statement;

	public Nettle() throws IOException {
		this.nettleWorld = new NettleWorld(DAONettleWorld.readmap("nettleworld2"));
		this.nettlePlay = new NettlePlay(this.nettleWorld);
		SwingUtilities.invokeLater(this);
	}

	private ResultSet executeQuery(final String query) throws SQLException {

		return this.statement.executeQuery(query);

	}

	private int executeUpdate(final String query) throws SQLException {

		return this.statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	}

	public Connection getConnection() {
		return this.connection;
	}

	private int getLastId() {
		try {
			final ResultSet resultSet = this.statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public INettleFrame getNettleFrame() {
		return this.nettleFrame;
	}

	private NettlePlay getNettlePlay() {
		return this.nettlePlay;
	}

	private INettleWorld getNettleWorld() {
		return this.nettleWorld;
	}

	public Statement getStatement() {
		return this.statement;
	}

	/*
	 * public void insertJavaPetri(final NettleWorld nettleWorld) throws
	 * SQLException { if (executeUpdate(getQueryInsert(nettleWorld, 0)) == 1) {
	 * final int idJavaPetri = getLastId(); if (idJavaPetri != -1) {
	 * executeUpdate(QueryJavaCell.getQueryInsert(javaPetri, idJavaPetri)); } }
	 * }
	 */

	@Override
	public void run() {
		this.nettleFrame = new NettleFrame("Welcome to NettleWorld", getNettleWorld(), getNettlePlay());
		this.nettlePlay.setNettleFrame(this.nettleFrame);
	}

	public void setConnection(final Connection connection) {
		this.connection = connection;
	}

	public void setNettleFrame(final INettleFrame nettleFrame) {
		this.nettleFrame = nettleFrame;
	}

	public void setStatement(final Statement statement) {
		this.statement = statement;
	}

	public static Nettle getQueryjavacell() {
		return QueryJavaCell;
	}
}
