import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Calculadora extends Remote
{
   /* Método al que accederá el cliente.
* Se le pasan dos enteros, a y b, y devuelve la suma.
    * Lanza una excepción si hay problemas de RMI al ser
    * llamado desde el cliente. */
     
   public int suma(int a, int b) throws RemoteException;

}