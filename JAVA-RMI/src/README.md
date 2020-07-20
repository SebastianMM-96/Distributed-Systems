# Compilación de archivos

1. Compilar los archivos fuente desde el directorio:

  * $> javac -d bin Calculadora.java CalculadoraServidor.java CalculadoraCliente.java
  * "-d bin" es para que las clases generadas las ponga en el directorio bin (crearlo antes).
  
2. Iniciar servicio de registro. Antes de ejecutar el servidor tenemos que iniciar el registro. Desde el directorio $HOME/…/Calculadora/bin/ se ejecuta.

  * $../bin> rmiregistry
  
3. Iniciar el servidor. En otra terminal y desde el directorio $HOME/…/Calculadora/

  * $> java -cp ./bin/ -Djava.rmi.server.codebase=file:./bin/ example.calculadora.CalculadoraServidor
  * Cuando inicia el servidor debe aparecer en la terminal el mensaje: _CalculadoraServidor listo!_
  
4. Ejecutar el cliente. Desde otra terminal y estando en el directorio $HOME/…/Calculadora/ ejecutar:

  * ../Calculadora> java -cp ./bin/ example.calculadora.CalculadoraCliente


Al ejecutar el cliente, hace la petición al servidor y el cliente muestra en la terminal la respuesta que le dió el servidor:
          
          2 + 3 = 5

