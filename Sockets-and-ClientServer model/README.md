# Modelo cliente-servidor
El modelo cliente-servidor es una abstracción de un sistema distribuido compuesto por un
conjunto de administradores de recursos y un conjunto de programas usando esos recursos.
Un administrador de recursos es un módulo de software que maneja un conjunto de recursos de un
tipo particular (una impresora, conjunto de archivos, contenido web, pantalla, aplicación, &c.).
Cada tipo de recurso requiere políticas y métodos de manejo separados, aunque también hay
requerimientos comunes, como la provisión de un esquema de nombramiento para cada clase
de recurso, permitiendo que los recursos individuales sean accedidos desde algún lugar
mediante el mapeo de los nombres de los recursos a direcciones de comunicación, y la
coordinación de accesos concurrentes que cambian el estado de recursos compartidos para
asegurar su consistencia.
En el modelo cliente–servidor hay un conjunto de procesos de tipo servidor , cada uno actuando
como un administrador para una colección de recursos de un tipo dado, y una colección de
procesos de tipo cliente , cada uno ejecutando una tarea que requiere acceder a recursos de
software y hardware compartidos. Los mismos manejadores de recursos podrían necesitar
acceder a recursos compartidos manejados por otro proceso, por ello algunos procesos son
tanto clientes como servidores. En el modelo cliente–servidor, todos los recursos compartidos
son controlados y manejados por procesos servidores. Los procesos cliente envían peticiones a
servidores cuando necesitan acceder a uno de sus recursos. Si la petición es válida, entonces el
servidor ejecuta la acción solicitada y envía una respuesta al proceso cliente.
A simple vista, en el modelo cliente–servidor cada proceso servidor es visto como un proveedor
centralizado de los recursos que maneja, lo cual no es deseable en los sistemas distribuidos.
Debido a esto se hace la distinción entre los servicios provistos a los clientes y los servidores
que los proveen. Un servicio es considerado una entidad abstracta que sería provista por varios
procesos servidores corriendo sobre computadoras separadas cooperando entre sí mediante
una red.

La programación básica involucra las primitivas de comunicación emitir ( send ) y recibir
( receive ). Esas primitivas ejecutan acciones de paso de mensajes ( message–passing ) entre
un par de procesos. Cada acción de paso de mensajes involucra la transmisión por el
proceso emisor de un conjunto de datos (un mensaje) a través de un mecanismo de
comunicación especificado (un canal o puerto) y la aceptación por el proceso receptor del
mensaje. El mecanismo puede ser con bloqueo o síncrono , lo que significa que el emisor
espera después de transmitir un mensaje hasta que el receptor ha ejecutado una
operación de recepción; o puede ser asíncrono o sin bloqueo , es decir, que el mensaje es
colocado en una cola de mensajes esperando que el receptor lo acepte y el proceso
emisor pueda continuar inmediatamente.
Los sistemas distribuidos pueden ser diseñados completamente en términos de paso de
mensajes, pero hay ciertos esquemas de comunicación que ocurren frecuentemente y
son tan útiles que deben ser considerados como parte esencial del soporte para el
diseño y construcción de sistemas distribuidos.
Los esquemas de comunicación más usados en la construcción de sistemas distribuidos
son el modelo cliente–servidor (descrito anteriormente) para comunicación entre pares
de procesos, y el modelo de grupos multicast para comunicación entre grupos de
procesos que cooperan.
