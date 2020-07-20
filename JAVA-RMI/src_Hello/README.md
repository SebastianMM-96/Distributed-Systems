# Compile the source files

The source files for this example can be compiled as follows:

javac -d . *.java

## Start the Java RMI registry

To start the registry, run the rmiregistry command on the server's host. This command produces no output (when successful) and is typically run in the background. For more information, see the tools documentation for rmiregistry [Solaris, Windows].

rmiregistry 

## Start the server

To start the server, run the Server class using the java command as follows:

java Server

## Run the client

Once the server is ready, the client can be run as follows:

java Client
