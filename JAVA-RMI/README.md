# Llamada a métodos remotos
## Java RMI

RMI (Remote Method Invocation) es un mecanismo que permite realizar llamadas a metodos de objetos remotos situados en distintas (o la misma) máquinas virtuales de Java, compartiendo así recursos y carga de procesamiento a través de varios sistemas.
El soporte para RMI de Java está basado en las interfaces y clases definidas en los paquetes java.rmi y java.rmi.server.

RMI ofrece lo siguiente:

* Mecanismos para crear servidores y objetos cuyos métodos se puedan invocar remotamente
* Mecanismos que permiten a los clientes localizar los objetos remotos

## Arquitectura RMI

* Nivel de transporte: Se encarga de las comunicaciones y de establecer las conexiones necesarias.
* Nivel de gestión de referencias remotas: Trata los aspectos relacionados con el comportamiento esperado de las referencias remotas
* Nivel de resguardo/esqueleto: Se encarga del aplanamiento de los parámetros, hay dos tipos:
  * Proxy: Resguardo local. Cuando un cliente realiza una invocación remota, en realidad hace una invocación de un método de resguardo local.
  * Esqueleto: Recibe las peticiones de los clientes, realiza la invocación del método y devuelve los resultados.

Toda aplicación RMI se descompone en dos partes:	
1.	Servidor: crea algunos objetos remotos, crea referencias para hacerlos accesibles, y espera a que el cliente lo invoque.
2.	Cliente: que obtiene una referencia a objetos remotos del servidor, y los invoca.

## Aplicación RMI

