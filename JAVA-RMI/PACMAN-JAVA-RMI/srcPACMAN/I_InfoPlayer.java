import java.rmi.*;
import java.util.ArrayList;
 
public interface I_InfoPlayer extends Remote {

	public void setName(String s) throws RemoteException;

	public String getName() throws RemoteException;

    public boolean isDead() throws RemoteException;
    
    public void setDead(boolean b) throws RemoteException;

    public boolean isPlaying() throws RemoteException;
    
    public void setPlaying(boolean b) throws RemoteException;
    
    public boolean isWaiting() throws RemoteException;
    
    public void setWaiting(boolean b) throws RemoteException;

    
    public boolean getDying() throws RemoteException;
    
    public void setDying(boolean b) throws RemoteException;
       
    public int getScore() throws RemoteException;
    
    
    public void setScore(int i) throws RemoteException;
        
    
    public int getViewdx() throws RemoteException;
    
    
    public void setViewdx(int i) throws RemoteException;
    
    
    public int getViewdy() throws RemoteException;
    
    
    public void setViewdy(int i) throws RemoteException;
    

	public int getPacmanx()  throws RemoteException;


	public void setPacmanx(int pacmanx)  throws RemoteException;


	public int getPacmany()  throws RemoteException;


	public void setPacmany(int pacmany)  throws RemoteException;


	public int getPacmandx()  throws RemoteException;


	public void setPacmandx(int pacmandx)  throws RemoteException;


	public int getPacmandy()  throws RemoteException;


	public void setPacmandy(int pacmandy)  throws RemoteException;


	public int getPacsleft()  throws RemoteException;


	public void setPacsleft(int pacsleft)  throws RemoteException;


	public int getReqdx()  throws RemoteException;


	public void setReqdx(int reqdx)  throws RemoteException;


	public int getReqdy()  throws RemoteException;


	public void setReqdy(int reqdy)  throws RemoteException;


	public int getPacmanspeed()  throws RemoteException;

	public void playerInit()  throws RemoteException;

	public void playerInitPos()  throws RemoteException;


}
