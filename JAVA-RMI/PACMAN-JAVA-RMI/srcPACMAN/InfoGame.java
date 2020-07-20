import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.net.MalformedURLException;

public class InfoGame  extends UnicastRemoteObject implements I_InfoGame{

	
    private final int blocksize = 24;
    private final int nrofblocks = 15;
    private final int scrsize = nrofblocks * blocksize;
    private int nrofghosts = 6;
    private int deathcounter = 0;
    private int[] ghostx, ghosty;

    private ArrayList<I_InfoPlayer> players;

	private short[] screendata;

	private Server server = null;

	private int nbPlayer = 0;

	private int nbPlayerExpected = 1;
	private int nbPlayerWaiting = 0;
	private int nbPlayerPlaying = 0;

	private static final long serialVersionUID = 1L;
	
	private boolean playing = false;
	private boolean waiting = true;
	private boolean ended = false;


	protected InfoGame() throws RemoteException {
		super();
	}
	

    public void newPlayer(String name) throws RemoteException{

	try {
		I_InfoPlayer player = null;
		player = new InfoPlayer(name);
		Naming.rebind("rmi://localhost:1099/"+name, player);
		players.add(player);
		nbPlayer += 1;
	} catch (RemoteException e){
		System.out.println("Hubo una excepcion creando la instancia del objeto distribuido");
	} catch (MalformedURLException e){
		System.out.println("URL mal formada al tratar de publicar el objeto");
	}

    }

    public void moveGhosts() throws RemoteException {
	    server.moveGhosts();
    }

    public void levelContinue() throws RemoteException {
	    server.LevelContinue();
    }

	public  ArrayList<I_InfoPlayer> getPlayers() throws RemoteException {
		return this.players;
	}

	public void setNbPlayerExpected(int n){
		nbPlayerExpected = n;
	}

	public int getNbPlayerExpected() throws RemoteException{
		return nbPlayerExpected;
	}

	public void setNumberPlayer(int n) {
		nbPlayer = n;
	}

	public int getNumberPlayer() {
		return nbPlayer;
	}

	public int getNbrPlayerInGame() throws RemoteException{
		return nbPlayerPlaying;
	}


    public void setServer(Server s) {
    	server = s;
	players = new ArrayList<I_InfoPlayer>();
    }

    public boolean isEnded()  throws RemoteException{
		return ended;
	}

	public void setEnded(boolean ended)  throws RemoteException{
		this.ended = ended;
	}

    
    public boolean isPlaying()  throws RemoteException{
		return playing;
	}

	public void setPlaying(boolean playing)  throws RemoteException{
		this.playing = playing;
	}

    public boolean isWaiting()  throws RemoteException{
		return waiting;
	}
	public void setWaiting(boolean waiting)  throws RemoteException{
		this.waiting = waiting;
	}
    
    public void InitScreenData(){
        screendata = new short[nrofblocks * nrofblocks];
    }    
    
    public void Initghostx(int[] m){
        setGhostx(m);
    }   
    
    public void Initghosty(int[] m){
        setGhosty(m);
    }

	public int getNrofghosts() {
		return nrofghosts;
	}


	public void setNrofghosts(int nrofghosts) {
		this.nrofghosts = nrofghosts;
	}


	public int getDeathcounter() {
		return deathcounter;
	}


	public void setDeathcounter(int deathcounter) {
		this.deathcounter = deathcounter;
	}


	public int[] getGhostx() {
		return ghostx;
	}


	public void setGhostx(int[] ghostx) {
		this.ghostx = ghostx;
	}


	public int[] getGhosty() {
		return ghosty;
	}


	public void setGhosty(int[] ghosty) {
		this.ghosty = ghosty;
	}


	public short[] getScreendata() {
		return screendata;
	}


	public void setScreendata(short screendata, int i) {
		this.screendata[i] = screendata;
	}


	public int getBlocksize() {
		return blocksize;
	}


	public int getNrofblocks() {
		return nrofblocks;
	}


	public int getScrsize() {
		return scrsize;
	}

	public void GameInit() {
			server.GameInit();
	}


	public boolean checkInit() {
		return true;

	}


	public void addPlayerWaiting()  throws RemoteException{
		nbPlayerWaiting++;
		if(nbPlayerWaiting == nbPlayerExpected){
			waiting = false;
			playing = true;
			nbPlayerPlaying = nbPlayerWaiting;
			nbPlayerWaiting = 0;
			for (int i = 0; i<players.size(); i++){
				if(players.get(i).isWaiting()){
					players.get(i).setWaiting(false);
					players.get(i).setPlaying(true);
				}
			}

		}
	}

	public int getNbPlayerWaiting() throws RemoteException{
		return nbPlayerWaiting;
	}

	public void addPlayerPlaying()  throws RemoteException{
		nbPlayerPlaying++;
	}

	public void removePlayerPlaying()  throws RemoteException{
		nbPlayerPlaying--;
	}

	public void finished() {
		server.finished();
		try{
		} catch (Exception exc) {
				exc.printStackTrace();
        	}
	}

	public void exit(String name) {
		try {
			int i;
			for (i=0; i<players.size(); i++) {
				String val = players.get(i).getName();
				if (val.equals(name)) {
					break;
				}
			}

			if(players.get(i).isPlaying()){
				nbPlayerPlaying--;
			}
			if(players.get(i).isWaiting()){
				nbPlayerWaiting--;
			}
			nbPlayer--;

			players.remove(i);

			if(playing && (nbPlayerPlaying<nbPlayerExpected)){
				nbPlayerWaiting = nbPlayerPlaying;
				nbPlayerPlaying = 0;
				playing = false;
				waiting = true;
				for (int j = 0; j<players.size(); j++){
					if(players.get(j).isPlaying()){
						players.get(j).setWaiting(true);
						players.get(j).setPlaying(false);
					}
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		try {  
			Naming.unbind("rmi://localhost:1099/"+name);  
		}  catch (Exception e) {  
			System.out.println("URL mal formada al tratar de publicar el objeto");  
		} 
	}

	public boolean allDead() throws RemoteException{

      		for(int k = 0; k<players.size(); k++){
			if(players.get(k).isPlaying()){
				return false;
			}
      		}
		return true;
	}

	public void restart() throws RemoteException{

      		for(int k = 0; k<players.size(); k++){
			players.get(k).playerInit();
      		}
		server.LevelInit();
		waiting = true;
		playing = false;
		ended = false;
	}
}

