package test2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientGUI {

	public static void main(String[] args) {

		try {
			String name = "Words";
			Registry registry = LocateRegistry.getRegistry(1888);
			IntDictionary comp = (IntDictionary) registry.lookup(name);

			JOptionPane.showMessageDialog(null, "$$$$$$...Most Usless Dictionary...$$$$$$");
			String userName = JOptionPane.showInputDialog("Enter Username");
			String password = JOptionPane.showInputDialog("Enter Password");

			// Invoking the Method
			boolean status = comp.authenticate(userName, password);

			if (status) {

				System.out.println("You are an authorized user...");
				JOptionPane.showMessageDialog(null, "WELCOME " + userName + "\nYou are an authorized user...");

				boolean findMore;
				do {
					String[] options = { "Show All", "Find a word", "Add a word", "Update a word", "Remove a word",
							"Exit" };

					int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

					switch (choice) {
					// Show all words
					case 0: {
						List<String> resultList = comp.list();

						StringBuilder message = new StringBuilder();
						resultList.forEach(x -> {
							message.append(x.toString() + "\n");
						});
						JOptionPane.showMessageDialog(null, new String(message));

						break;
					}
					// Find a word
					case 1: {
						String code = JOptionPane.showInputDialog("Type the word you are looking for ?");
						try {
							String resultLookup = comp.lookup(code);

							JOptionPane.showMessageDialog(null, "Word :" + code + "\n" + "Means : " + resultLookup,
									comp.lookup(code), JOptionPane.INFORMATION_MESSAGE);

						} catch (NoSuchElementException ex) {
							JOptionPane.showMessageDialog(null, "Word Not found");
						}
						break;
					}
					// Add a word
					case 2: {
						String wordd = JOptionPane.showInputDialog("Enter the word you want to add");
						String meann = JOptionPane.showInputDialog("Enter its meaning");
						try {
							comp.save(wordd, meann);

							JOptionPane.showMessageDialog(null, "Word Successfully Added =)");

						} catch (NoSuchElementException ex) {

							JOptionPane.showMessageDialog(null, "World already exist !!!");
						}
						break;
					}
					// Update Function
					case 3: {
						String wordd = JOptionPane.showInputDialog("Enter the word you want to update");
						String meann = JOptionPane.showInputDialog("Enter the new meaning : ");
						try {

							comp.replaceWord(wordd, meann);

							JOptionPane.showMessageDialog(null, "Word Updated succesfully");

						} catch (NoSuchElementException ex) {
							JOptionPane.showMessageDialog(null, "Word Not found");
						}
						break;
					}
					// Delete Function
					case 4: {
						String wordd = JOptionPane.showInputDialog("Enter the word you want to delete");

						try {

							comp.removeWord(wordd);

							JOptionPane.showMessageDialog(null, "Word Deleted succesfully");

						} catch (NoSuchElementException ex) {
							JOptionPane.showMessageDialog(null, "Word Not found");
						}
						break;
					}
					case 5: {

						JOptionPane.showMessageDialog(null, "Good bye =)\nThnak you");
						System.exit(0);

						break;
					}
					default:
						JOptionPane.showMessageDialog(null, "Good bye =)\nThnak you");
						System.exit(0);

						break;
					}
					findMore = (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
							JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);

				} while (findMore);

			} else {

				System.out.println("Unauthorized Login Attempt");
				JOptionPane.showMessageDialog(null, "KICKED OUT !!!!\nUnauthorized Login Attempt");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
