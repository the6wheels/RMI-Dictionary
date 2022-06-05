package test2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.awt.event.ActionEvent;

public class ServerGUI {

	private JFrame frmTp;
	static JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws RemoteException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
					window.frmTp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTp = new JFrame();
		frmTp.setTitle("TP_03 RMI Dictionary");
		frmTp.setBounds(100, 100, 450, 386);
		frmTp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTp.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 59, 424, 172);
		frmTp.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setForeground(Color.GREEN);
		textArea.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		textArea.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(textArea);

		JLabel lblDictionaryServer = new JLabel("Dictionary Server");
		lblDictionaryServer.setBounds(32, 32, 148, 15);
		frmTp.getContentPane().add(lblDictionaryServer);

		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				new SwingWorker() {

					@Override
					protected Object doInBackground() throws Exception {

						new EServer().start();
						return null;

					}

				}.execute();

			}
		});
		btnStartServer.setBounds(288, 262, 148, 25);
		frmTp.getContentPane().add(btnStartServer);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.append("Good Bye\nExiting....");
					Thread.sleep(500);
					System.exit(0);
				} catch (Exception ex) {
					textArea.append("Error : " + ex);
				}
			}
		});
		btnExit.setBounds(288, 298, 148, 25);
		frmTp.getContentPane().add(btnExit);

		JLabel lblTp = new JLabel("TP_03 distributed systems");
		lblTp.setBounds(12, 304, 206, 15);
		frmTp.getContentPane().add(lblTp);

		JLabel lblHadjaziAmour = new JLabel("Hadjazi & Amour RSSI G_01");
		lblHadjaziAmour.setBounds(12, 328, 206, 15);
		frmTp.getContentPane().add(lblHadjaziAmour);
	}

	static class EServer extends Thread {

		public EServer() throws RemoteException {

		}

		public void run() {

			try {

				String name = "Words";
				IntDictionary engine = new FaactoryImp();
				IntDictionary stub = (IntDictionary) UnicastRemoteObject.exportObject(engine, 0);

				Registry registry = LocateRegistry.createRegistry(1888);
				registry.rebind(name, stub);
				System.out.println("Connection Successful");
				textArea.append("Connection Successful");

			} catch (Exception e) {

				System.err.println("ERROR connecting: " + e);
				textArea.append("ERROR connecting: " + e);
				e.printStackTrace();
			}

		}
	}

}
