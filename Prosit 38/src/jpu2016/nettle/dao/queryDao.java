package jpu2016.nettle.dao;

public class queryDao {
	public static String getQuerySelectMap(final String nom) {
		return "SELECT * FROM map WHERE `nom` LIKE " + nom;
	}
}
