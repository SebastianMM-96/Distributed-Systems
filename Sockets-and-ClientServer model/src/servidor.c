#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <time.h>

#define ETHSIZE   400

struct sockaddr_in serv, cli;

 


int main ()
{
	char cadena[20] = "hola";

	int fd, idb, cli_len, size_recv;
   	char request [ETHSIZE];
   	
   	puts ("Se crea el socket");
	fd = socket (PF_INET, SOCK_DGRAM, 0);
	
	if (fd == -1)
		puts ("error, no se pudo crear el socket");
	else 
		puts ("Socket creado");

	puts ("Asignando atributos al socket");


	memset (&serv, sizeof (serv), 0);
    serv.sin_family = AF_INET;
    serv.sin_addr.s_addr = htonl (INADDR_ANY);
    serv.sin_port = htons (7778);


	idb = bind (fd, (struct sockaddr *)&serv, sizeof (serv));

	if (idb < 0)
	    puts ("No se asignaron los atributos ");
	else
	    puts ("Si se asignaron los atributos");
	
	while(1){

    	time_t tiempo = time(0);
		struct tm *hora = localtime(&tiempo);
		char output[50];
		strftime(output,50,"%H:%M:%S\n",hora);

		char *datos_para_el_cliente = &output; //Salida de la hora
	
		/* Aqui esperamos la peticion del cliente */
	 
	   	cli_len = ETHSIZE;
	   	size_recv = recvfrom (fd,(void *)request, ETHSIZE,0,
		(struct sockaddr *)&cli, (socklen_t *)&cli_len);

	   	if (size_recv < 0) {
		   	puts ("hubo un problema con el recvfrom");
		   _exit (1);
	   	} else {
	     	puts ("el request recibido fue:");
	     	puts (request);
	  	}

	  	if (request == 0)
	  	{
	  		return 0;
	  	}
		/* Aqui enviamos los datos al cliente */

	   sendto (fd,datos_para_el_cliente, strlen (datos_para_el_cliente),0,
		   (struct sockaddr *)&cli, (socklen_t )cli_len); 

	    
	}

	return 0;

}

