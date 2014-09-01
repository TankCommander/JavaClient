package gameManagement;

import gameManagement.gameObjects.implementations.FlightPathImpl;
import gameManagement.interfaceImplementations.PlayerImpl;

import java.awt.Color;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import sharedObjects.connectionObjects.interfaces.ClientInterface;
import sharedObjects.connectionObjects.interfaces.ServerEntryPoint;
import sharedObjects.gameObjects.interfaces.FlightPath;
import sharedObjects.gameObjects.interfaces.GameMap;
import sharedObjects.gameObjects.interfaces.Hit;
import sharedObjects.gameObjects.interfaces.Match;
import sharedObjects.gameObjects.interfaces.Player;
import sharedObjects.gameObjects.interfaces.TimePoint;
import ui.DrawPanel;
import ui.DrawPanel.PaintState;
import ui.LocalGameMap;
import ui.MainWindow;
import control.ClientInterfaceImplementation;

/**
 * Class which manages the whole client game
 * @author markushinkelmann
 *
 */
public class GameManager {
	
	private static GameManager instance;
	private MainWindow window;
	private LocalGameMap map;
	private Match match;
	private ClientInterface cInterface;
	private FlightPath currentFlightPath;
	private Random random = new Random();
	private GameManager () {}
	public boolean winner;

	public static synchronized GameManager getInstance () {
		if (GameManager.instance == null) {
			GameManager.instance = new GameManager ();
		}
		return GameManager.instance;
	}
	
	/**
	 * Function called, when it is necessary to start a game
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @return True if a player is available otherwise false
	 */
	public boolean startGame (String playerName, MainWindow window) throws RemoteException, NotBoundException 
	{
		this.window = window;
		
		//Create a player object
		PlayerImpl player = new PlayerImpl(playerName);
		
		//Register at the Server
		Registry registry = LocateRegistry.getRegistry();
	    ServerEntryPoint server = (ServerEntryPoint) registry.lookup( "ServerEntryPoint" );
	    ClientInterfaceImplementation c = new ClientInterfaceImplementation(player);
	    player.setClientInterface(c);
	    this.cInterface = c;
	    return server.registerClient(c);
	}
	
	/**
	 * Function called when the game environment is ready to play
	 * Updates the UI, send the needed data to the DrawPanel
	 * @throws RemoteException 
	 */
	public void environmentIsReady () throws RemoteException
	{
		int i = 0;
		for (Player player : this.match.getPlayers()){
			switch (i) {
			case 0:
				player.setColor(Color.GREEN);
				break;
			case 1:
				player.setColor(Color.RED);
				break;			
			default:
				// mehr als 2 Spieler ?
				player.setColor(new Color(random.nextInt()));
				break;
			}
			i++;
		}
		
		window.setPlayerNames(this.match.getPlayers().get(0), this.match.getPlayers().get(1));
		window.getDrawPanel().setPaintState(PaintState.DRAWMAP);
		
		//Enable oder disable the fire button
		window.setFireButtonState(this.match.getActivePlayer().equalsPlayer(this.cInterface.getPlayer()));
	}
	
	
	private void drawFlightPath (final DrawPanel drawPanel) throws RemoteException
	{
		//TODO: Benno tob dich hier aus ;)
		GameManager manager = GameManager.getInstance();
		drawPanel.setCounter(0);
		
		final FlightPath fp = manager.getCurrentFlightPath();		
//		TimerFlugbahn tf = new TimerFlugbahn(fp.getTimePoints(),drawPanel);
//		tf.start();
	    
		final Timer timer = new Timer();
		
		drawPanel.setPaintState(PaintState.DRAWFLIGHTPATH);

		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				try {
					if (drawPanel.getCounter() < fp.getTimePoints().size()) {
						drawPanel.repaint();
						drawPanel.IncCounter();
					} else {
						drawPanel.setPaintState(PaintState.DRAWMAP);
						timer.cancel();
						firedFinished();
					}
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}, 0, Consts.REFRESH_RATE_MS);
		
	}
	
	public void gameEnded (boolean winner)
	{
		this.winner = winner;
		window.getDrawPanel().lastShoot = true;
	}
	
	/**
	 * Function called, when the client received a new flight path
	 * @throws RemoteException 
	 */
	public void receivedNewFlightPath (FlightPath path) throws RemoteException
	{
		currentFlightPath = new FlightPathImpl(path.getOrigin());
		for(TimePoint timePoint : path.getTimePoints()){
			currentFlightPath.getTimePoints().add(timePoint);
		}
		currentFlightPath.setHits(path.getHits());
				
		window.setFireButtonState(false);
		
		//Change the state in the drawPanel
		drawFlightPath(window.getDrawPanel());
		
		Player player1 = this.match.getPlayers().get(0);
		Player player2 = this.match.getPlayers().get(1);
		
		System.out.println("Damage player1: " + player1.getName() + ":" + player1.getDamage());
		System.out.println("Damage player2: " + player2.getName() + ":" + player2.getDamage());		
	}
	
	public void firedFinished() throws RemoteException{
		
		for (Hit hit: currentFlightPath.getHits()){
			window.getDrawPanel().showExplosion(hit.getTarget());								
		}
		
		// for testing
//		window.getDrawPanel().showExplosion(getMatch().getPlayers().get(0));						

		//Change the values of the bars
		window.setPlayerDamage(
				this.match.getPlayers().get(0).getDamage(), 
				this.match.getPlayers().get(1).getDamage());
		
		//Change the state of the fire button
		window.setFireButtonState(this.match.getActivePlayer().equalsPlayer(this.cInterface.getPlayer()));		
	}
	
	/**
	 * Function to send a fire command to the server
	 * @param angle
	 * @param power
	 * @throws RemoteException 
	 */
	public void fire (double angle, double power) throws RemoteException
	{
		if (this.match != null){
			// nur wenn Gegner da ist, sonst sollte der Button deaktiviert sein
			this.match.Fire(cInterface, angle, power);
			
			//sofort deaktivieren
			window.setFireButtonState(false);
		}
	}

	////////////////////
	//Getter + Setter//
	//////////////////
	public GameMap getMap() {
		return map;
	}

	public void setMap(LocalGameMap map) {
		this.map = map;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public ClientInterface getcInterface() {
		return cInterface;
	}

	public void setcInterface(ClientInterface cInterface) {
		this.cInterface = cInterface;
	}
	
	public FlightPath getCurrentFlightPath() {
		return currentFlightPath;
	}

	public void setCurrentFlightPath(FlightPath currentFlightPath) {
		this.currentFlightPath = currentFlightPath;
	}
	
	
}
