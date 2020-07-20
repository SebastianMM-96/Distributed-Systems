import javax.swing.JFrame;
import java.rmi.Naming;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;

public class Client extends JFrame
{
	private static I_InfoGame game = null;
	private Player player;
	
	public Client(){
			//Instancia un nuevo jugador
			player = new Player(game);
			add(player);
			setTitle("Pacman");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(380, 420);
			setLocationRelativeTo(null);
			setVisible(true);
	}
	
	static public void main(String[] args){
		try {
			game = (I_InfoGame) Naming.lookup("rmi://localhost:1099/I_InfoGame");
		} catch (NotBoundException e){
			System.out.println("El servicio no esta publicado en el servidor");
			System.exit(128);
		} catch (MalformedURLException e){
			System.out.println("Conexion Invalida");
			System.exit(128);
		} catch (RemoteException e){
			System.out.println("Excepcion remota tratando de conectarse al servidor");
			System.exit(128);
		}
			new Client();
	}
}
