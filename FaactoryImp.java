package test2;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class FaactoryImp implements IntDictionary, Serializable {

	private static final long serialVersionUID = 1462043410155727587L;
	private static String user = "sa";
	private static String password = "";
	private static String url = "jdbc:hsqldb:mem:.";
	private static Dictionary database;

	public FaactoryImp() throws SQLException {
		this.database = new Dictionary(user, password, url);
	}

	@Override
	public void save(String word, String def) throws RemoteException {
		Word wordd = database.save(word, def);
		database.addWords(wordd);
	}

	@Override
	public String lookup(String keyword) throws RemoteException {
		List<String> result = null;
		try {
			result = database.lookup(keyword);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		String res = result.get(0);
		return res;
	}

	@Override
	public List<String> list() throws RemoteException {
		List<String> result = null;
		try {
			result = database.list();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean authenticate(String userName, String password) throws RemoteException {

		if ((userName != null && !userName.isEmpty()) && (password != null && !password.isEmpty())) {

			if (((userName.equalsIgnoreCase("admin")) && (password.equalsIgnoreCase("admin")))

					||

					((userName.equalsIgnoreCase("user1")) && (password.equalsIgnoreCase("pass1")))

					||

					((userName.equalsIgnoreCase("user2")) && (password.equalsIgnoreCase("pass2")))

			) {

				return true;
			}
		}
		return false;
	}

	@Override
	public void removeWord(String word) throws RemoteException {

		database.deleteWords(word);

	}

	@Override
	public void replaceWord(String word, String def) throws RemoteException {

		database.updateWords(word, def);

	}

}