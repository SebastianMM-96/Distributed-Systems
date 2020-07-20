import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/*Objeto remoto de RMI que suma dos enteros. Implementa la interfaz Calculadora */
public class CalculadoraServidor implements Calculadora {
    public CalculadoraServidor() {}

    public int suma(int a, int b) {
        System.out.println("sumando " + a + " + " + b + " ...");
        return a + b;
    }

    public static void main(String args[]) {
        try {
            CalculadoraServidor obj = new CalculadoraServidor();
            Calculadora stub = (Calculadora) UnicastRemoteObject.exportObject(obj, 0);

            // Agrega el stud del objeto remoto al registro RMI
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Calculadora", stub);
            System.err.println("CalculadoraServidor lista!");
        } 
        // FIN del try 
        catch (Exception e) {
            System.err.println("CalculadoraServidor exception: " + e.toString());
            e.printStackTrace();
        }
    } // Fin del main
} // Fin de la clase


