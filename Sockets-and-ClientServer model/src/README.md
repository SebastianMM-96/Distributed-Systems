# Llamados al sistema a utilizar

1. **socket**:   socket crea un extremo de una comunicación y devuelve un descriptor. El parámetro dominio especifica un dominio de comunicaciones dentro del cual tendrá lugar la comunicación; esto selecciona la familia de protocolo que deberá emplearse. Estas familias se definen en el archivo de cabecera sys/socket.h. Los formatos
actualmente reconocidos son:
  * AF_UNIX (protocolos internos de UNIX)
  * AF_INET (protocolos de ARPA Internet)
  * AF_ISO (protocolos ISO)
  * AF_NS (protocolos de Xerox Network Systems)
  * AF_IMPLINK (capa de enlace IMP "anfitrión en IMP")

El socket tiene el tipo indicado, que especifica la semántica de la comunicación. Los tipos definidos en la actualidad son
  * SOCK_STREAM
  * SOCK_DGRAM
  * SOCK_RAW
  * SOCK_SEQPACKET
  * SOCK_RDM
  
Los sockets SOCK_DGRAM y SOCK_RAW permiten el envío de datagramas a los correspondientes nombrados en llamadas a send(2). Los datagramas se reciben
generalmente con recvfrom(2), que devuelve el siguiente datagrama con su dirección de retorno. Se devuelve un -1 si ocurre un error; en otro caso el valor devuelto es un descriptor para referenciar el socket.

        #include <sys/types.h>
        #include <sys/socket.h>

        int socket (int dominio, int tipo, int protocolo);
 
 
 2. **bind**: bind da al socket sockfd, la dirección local my_addr . my_addr tiene una longitud de addrlen bytes. Tradicionalmente, esto se conoce como "asignar un nombre a un
socket" (cuando un socket se crea con socket(2), existe en un espacio de nombres (familia de direcciones) pero carece de nombre). Cero es el resultado en caso de éxito. En caso de error, -1 es el valor regresado y a errno
se le asigna un valor apropiado.

       #include <sys/types.h>
       #include <sys/socket.h>
       int bind (int sockfd, struct sockaddr *my_addr, int addrlen);
  
3. **recvfrom**: La primitiva recvfrom se emplea para recibir mensajes desde un socket, y pueden utilizarse para recibir datos de un socket sea orientado a conexión o no.
Si desde no es nulo, y el socket no es orientado a conexión, la dirección fuente del mensaje se llena. londesde es un parámetro por referencia, inicializado al tamaño del
búfer asociado con desde , y modificado cuando la función regresa para indicar el tamaño real de la dirección guardada ahí. La rutina devuelve la longitud del mensaje cuando termina bien. Si un mensaje es
demasiado largo como para caber en el búfer suministrado, los bytes que sobran pueden descartarse dependiendo del tipo de socket del que se reciba el mensaje.
Si no hay mensajes disponibles en el socket, la llamada de recepción espera que llegue un mensaje, a menos que el socket sea no bloqueante (vea fcntl(2)) en cuyo caso se
devuelve el valor -1. El argumento flags de una llamada a recv se forma aplicando el operador de bits OR a uno o más de los valores:
  * MSG_OOB procesar datos fuera-de-banda
  * MSG_PEEK mirar el mensaje entrante
  * MSG_WAITALL esperar a que se complete la petición u ocurra un error
  
        #include <sys/types.h>
        #include <sys/socket.h>
        int recvfrom (int s , void * buf , int lon , unsigned int flags ,
        struct sockaddr * desde , int * londesde );
  
 4. **sendto**: sendto (al igual que send y sendmsg ) es utilizado para transmitir un mensaje a otro socket.
La dirección de destino viene dada por to con tolen especificando su tamaño. La longitud del mensaje viene dada por len . Si el mensaje es demasiado largo para pasar
automáticamente a través del protocolo inferior, es devuelto el error EMSGSIZE y el mensaje no es transmitido. Si no existe espacio disponible en el socket para contener el mensaje a enviar, entonces
send se bloquea, a no ser que el socket ha sido habilitado en un modo de no bloqueo para la E/S (non-blocking I/O). La llamada select(2) puede ser utilizada para determinar
cuando es posible enviar más información. La llamada retorna el número de caracteres enviados, o -1 si ha ocurrido un error. 

     #include <sys/types.h>
     #include <sys/socket.h>
     int sendto (int s , const void * msg , int len , unsigned int flags , 
       const struct sockaddr * to , int tolen );
