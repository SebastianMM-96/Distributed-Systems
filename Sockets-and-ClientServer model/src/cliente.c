#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#define ETHSIZE		400
//Constante del tamaño del Buffer

struct sockaddr_in serv;
//Guarda informacion del socket


/* 
 *  sintaxis: cliente <direccion IP> <# puerto>
 */

int main (int argc, char *argv [])
{
	int i = 1;

	while(i){

	   int fd, idb, result_sendto;
	   //fd: descriptor del socket
	   char *request = "Dame la hora";
	   char buffer [ETHSIZE];


	   if (argc != 3) {
		   puts ("sintaxis: cliente <direccion IP> <# puerto>");
		   _exit (1);
	   } 

	   puts ("abriendo el socket");
	   fd = socket (PF_INET, SOCK_DGRAM, 0);
	   if (fd == -1)
		   puts ("error, no se pudo abrir el socket");
	   else 
		   puts ("socket abierto");

	   puts ("asignando atributos al socket");


	   memset (&serv, sizeof (serv), 0);
	   serv.sin_family = AF_INET;
	   /* inet_addr convierte de cadena de caracteres (formato puntado
	    * "128.112.123.1") a
	    * octetos.
	    */

	   serv.sin_addr.s_addr = inet_addr(argv [1]);

	  /* htons convierte un entero largo en octetos cuyo orden entienden
	   * las funciones de los sockets
	   */
	   serv.sin_port = htons (atoi(argv[2]));


	   /*
	    * Para enviar datos usar sendto()
	    */
	    
	   result_sendto = sendto (fd,request,200,0, 
			   (const struct sockaddr *)&serv, sizeof (serv));
	   if (result_sendto < 0){
		   puts ("Problemas al enviar la peticion");
	           _exit (1); 
	   } 
	   else {
	     puts ("La peticion fue enviado al servidor");
	    }


	  /* para recibir datos usar recvfrom 
	   */

	   recvfrom (fd,(void *)buffer,ETHSIZE,0,
		    (struct sockaddr *)NULL, (socklen_t *)NULL);

	  puts ("Estos son los datos enviados por el servidor:");
	  puts (buffer);
	  puts("\n");

	  puts ("Volver a pedir la hora?\n");
	  puts ("Si(1)/No(0)");

	  scanf("%d", &i);

	  if (i == 0)
	  {
	  	i = 0;
	  }

  	}
  return 0;

}

