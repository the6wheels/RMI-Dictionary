package test2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Dictionary implements AutoCloseable {

	private static String user = "sa";
	private static String password = "";
	private static String url = "jdbc:hsqldb:mem:.";
	private static Connection conn;

	public Dictionary(String user, String password, String url) throws SQLException {
		this.conn = DriverManager.getConnection(url, user, password);
		try {
			createTables(conn);
			addWords(new Word("plane",
					"a flat surface on which a straight line joining any two points on it would wholly lie."));
			addWords(new Word("car",
					"a four-wheeled road vehicle that is powered by an engine and is able to carry a small number of people."));
			addWords(new Word("boat",
					"a small vessel for travelling over water, propelled by oars, sails, or an engine."));
			addWords(new Word("sea",
					"the expanse of salt water that covers most of the earth's surface and surrounds its land masses."));
			addWords(new Word("toad",
					"a tailless amphibian with a short stout body and short legs, typically having dry warty skin that can exude poison."));
			printWords();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws Exception {
		conn.close();
	}

	public Word save(String wword, String defofword) {
		Word c = new Word(wword, defofword);
		return c;
	}

	private void createTables(Connection conn) throws SQLException {
		try (Statement st = conn.createStatement()) {
			st.executeUpdate("DROP TABLE IF EXISTS dictionary;");
			st.executeUpdate("CREATE TABLE dictionary (wword VARCHAR(80) PRIMARY KEY, defofword VARCHAR(200));");
			System.out.println("Tables created");
		}
	}

	public void addWords(Word word) {
		try (PreparedStatement st = conn.prepareStatement("INSERT INTO dictionary (wword, defofword) VALUES (?, ?);")) {
			st.setString(1, word.getwword());
			st.setString(2, word.getdefofword());
			st.addBatch();
			st.executeBatch();
		} catch (SQLException ex) {
			System.out.println("Word Already Exist!");
		}
	}

	public void updateWords(String word, String def) {
		try (PreparedStatement st = conn
				.prepareStatement("UPDATE dictionary SET wword = ?, defofword = ? WHERE wword = ?;")) {
			st.setString(1, word);
			st.setString(2, def);
			st.setString(3, word);
			st.addBatch();
			st.executeBatch();
		} catch (SQLException ex) {
			System.out.println("Error Updating!");
		}
	}

	public void deleteWords(String word) {
		try (PreparedStatement st = conn.prepareStatement("DELETE FROM dictionary WHERE wword ='" + word + "';")) {

			st.execute();
		} catch (SQLException ex) {
			System.out.println("ERROR Deleting!");
		}
	}

	public void printWords() throws SQLException {
		try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * from dictionary;")) {
			while (rs.next()) {
				final String wword = rs.getString("wword");
				final String defofword = rs.getString("defofword");
				System.out.println(wword + " : " + defofword);
			}
		}
	}

	public List<String> list() throws SQLException {
		List<String> result = new ArrayList<>();
		try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * from dictionary;")) {
			while (rs.next()) {
				final String wword = rs.getString("wword");
				final String defofword = rs.getString("defofword");
				result.add(wword + " : " + defofword);
			}
		}
		return result;
	}

	public List<String> lookup(String parameter) throws SQLException {
		List<String> result = new ArrayList<>();
		PreparedStatement st = conn
				.prepareStatement("SELECT * from dictionary WHERE wword LIKE ? OR defofword LIKE ?;");
		st.setString(1, '%' + parameter + '%');
		st.setString(2, '%' + parameter + '%');
		ResultSet rs = st.executeQuery();
		try {
			while (rs.next()) {
				final String wword = rs.getString("wword");
				final String defofword = rs.getString("defofword");
				result.add(wword + " : " + defofword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}