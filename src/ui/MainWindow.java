package ui;

import gameManagement.Calculation;
import gameManagement.GameManager;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sharedObjects.connectionObjects.interfaces.ClientInterface;
import sharedObjects.gameObjects.interfaces.Player;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -7666919368459602109L;
	private JPanel contentPane;
	private JButton btnNewButton;
	private JLabel lblPlayerName;
	private JTextField textFieldPlayerName;
	private JLabel lblAngle;
	private JLabel lblPowerms;
	private JSpinner spinnerAngle;
	private JSpinner spinnerPower;
	private JButton btnFire;
	private JLabel lblPlayer;
	private JProgressBar progressBar;
	private JProgressBar progressBar_1;
	private JLabel lblVs;
	private JLabel lblPlayer_1;
	private DrawPanel panel;
	private JLabel lblHost;
	private JTextField textFieldHost;


	/**
	 * Create the frame.
	 */
	public MainWindow() {
		initGUI();
	}
	
	public String getHostname(){
		return textFieldHost.getText();
	}
	
	private void initGUI() {
		
		int row = 0;
		
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 590);
//		setBounds(100, 100, 616, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 79, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 400, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 2.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblHost = new JLabel("Server address:");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = row;
		contentPane.add(lblHost, gbc);
		
		textFieldHost = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = row;
		contentPane.add(textFieldHost, gbc);
		textFieldHost.setColumns(10);

		btnNewButton = new JButton("Start Game");
		btnNewButton.addActionListener(new BtnNewButtonActionListener());
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = row;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		row++;
		
		lblPlayerName = new JLabel("Player Name:");
		GridBagConstraints gbc_lblPlayerName = new GridBagConstraints();
		gbc_lblPlayerName.anchor = GridBagConstraints.EAST;
		gbc_lblPlayerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayerName.gridx = 0;
		gbc_lblPlayerName.gridy = row;
		contentPane.add(lblPlayerName, gbc_lblPlayerName);
		
		textFieldPlayerName = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = row;
		contentPane.add(textFieldPlayerName, gbc_textField);
		textFieldPlayerName.setColumns(10);
		
		row++;
		
		lblAngle = new JLabel("Angle (\u00B0):");
		GridBagConstraints gbc_lblAngle = new GridBagConstraints();
		gbc_lblAngle.anchor = GridBagConstraints.EAST;
		gbc_lblAngle.insets = new Insets(0, 0, 5, 5);
		gbc_lblAngle.gridx = 0;
		gbc_lblAngle.gridy = row;
		contentPane.add(lblAngle, gbc_lblAngle);
		
		spinnerAngle = new JSpinner();
		spinnerAngle.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				GameManager manager = GameManager.getInstance();
				ClientInterface clientInterface = manager.getcInterface();
				if (clientInterface != null){					
					try {
						Player player = manager.getcInterface().getPlayer();
						if (player != null){
							int value = (Integer) ((JSpinner) e.getSource()).getValue();
							double angle = Calculation.DegreeToRadiant(value);
							player.setAngle(angle);
							panel.repaint();
						}
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.gridwidth = 3;
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = row;
		contentPane.add(spinnerAngle, gbc_spinner);
		
		btnFire = new JButton("Fire");
		btnFire.addActionListener(new BtnFireActionListener());
		GridBagConstraints gbc_btnFire = new GridBagConstraints();
		gbc_btnFire.fill = GridBagConstraints.BOTH;
		gbc_btnFire.gridheight = 2;
		gbc_btnFire.insets = new Insets(0, 0, 5, 0);
		gbc_btnFire.gridx = 4;
		gbc_btnFire.gridy = row;
		contentPane.add(btnFire, gbc_btnFire);
		
		row++;
		
		lblPowerms = new JLabel("Power (m/s): ");
		GridBagConstraints gbc_lblPowerms = new GridBagConstraints();
		gbc_lblPowerms.anchor = GridBagConstraints.EAST;
		gbc_lblPowerms.insets = new Insets(0, 0, 5, 5);
		gbc_lblPowerms.gridx = 0;
		gbc_lblPowerms.gridy = row;
		contentPane.add(lblPowerms, gbc_lblPowerms);
		
		spinnerPower = new JSpinner();
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.gridwidth = 3;
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = row;
		contentPane.add(spinnerPower, gbc_spinner_1);
		
		row++;
		
		lblPlayer = new JLabel("Player 1:");
		GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
		gbc_lblPlayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer.gridx = 0;
		gbc_lblPlayer.gridy = row;
		contentPane.add(lblPlayer, gbc_lblPlayer);
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = row;
		contentPane.add(progressBar, gbc_progressBar);
		
		lblVs = new JLabel("vs");
		GridBagConstraints gbc_lblVs = new GridBagConstraints();
		gbc_lblVs.insets = new Insets(0, 0, 5, 5);
		gbc_lblVs.gridx = 2;
		gbc_lblVs.gridy = row;
		contentPane.add(lblVs, gbc_lblVs);
		
		lblPlayer_1 = new JLabel("Player 2:");
		GridBagConstraints gbc_lblPlayer_1 = new GridBagConstraints();
		gbc_lblPlayer_1.anchor = GridBagConstraints.EAST;
		gbc_lblPlayer_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer_1.gridx = 3;
		gbc_lblPlayer_1.gridy = row;
		contentPane.add(lblPlayer_1, gbc_lblPlayer_1);
		
		progressBar_1 = new JProgressBar();
		GridBagConstraints gbc_progressBar_1 = new GridBagConstraints();
		gbc_progressBar_1.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar_1.gridx = 4;
		gbc_progressBar_1.gridy = row;
		contentPane.add(progressBar_1, gbc_progressBar_1);
		
		row++;
		
		panel = new DrawPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 5;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = row;
		contentPane.add(panel, gbc_panel);
		
		reset();
	}
	
	private void reset(){
		btnFire.setEnabled(false);
		spinnerAngle.setValue(0);
		spinnerPower.setValue(0);
		progressBar.setValue(0);
		progressBar_1.setValue(0);
	}
	
	/**
	 * Function to set the player names
	 * @param player1
	 * @param player2
	 * @throws RemoteException 
	 */
	public void setPlayerNames (Player player1, Player player2) throws RemoteException
	{
		lblPlayer.setText(player1.getName());
		lblPlayer_1.setText(player2.getName());
		
		lblPlayer.setForeground(player1.getColor());
		lblPlayer_1.setForeground(player2.getColor());
		
		setTitle(player1.getName() + " vs. " + player2.getName());
//		btnFire.setBackground(Color.GREEN);
	}
	
	
	/**
	 * Function which will enable oder disable the fire button
	 * @param enabled
	 */
	public void setFireButtonState (boolean enabled)
	{
		this.btnFire.setEnabled(enabled);
	}
	
	/**
	 * Function which will set the damage for the players in the UI
	 * @param damagePlayer1
	 * @param damagePlayer2
	 */
	public void setPlayerDamage (double damagePlayer1, double damagePlayer2)
	{
		this.progressBar.setValue((int) (damagePlayer1*100));
		this.progressBar_1.setValue((int) (damagePlayer2*100));
	}

	/**
	 * Getter for the Draw Panel
	 * @return
	 */
	public DrawPanel getDrawPanel() 
	{
		return panel;
	}
	
	
	/**
	 * Called when the Fire Button was clicked
	 */
	private class BtnFireActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			GameManager manger = GameManager.getInstance();
			double angle = Calculation.DegreeToRadiant(Double.valueOf(spinnerAngle.getValue().toString()));
			double power = Double.valueOf(spinnerPower.getValue().toString());
			
			try {
				setFireButtonState(false);
				manger.fire(angle, power);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Called when the new Game Button is clicked
	 */
	private class BtnNewButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//Start a new Game
			GameManager manager = GameManager.getInstance();
			try {
				boolean wait = manager.startGame(textFieldPlayerName.getText(), MainWindow.this);
				if (!wait)
				{
					reset();
					panel.setPaintState(DrawPanel.PaintState.WAITFORPLAYER);
					panel.repaint();
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				e1.printStackTrace();
			}
		}
	}
}
