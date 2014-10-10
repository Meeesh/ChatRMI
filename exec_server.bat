@echo off
TITLE RMI Chat Server
REM Compilation des fichiers sources
javac -d ./ src/server/*.java 
REM on demarre le registre RMI
start rmiregistry
REM on demarre notre serveur
java -Djava.rmi.server.hostname=10.113.31.52 -Djava.security.policy=chat.policy thePack.ServerEngine
REM EOF