@echo off
REM on demarre le client
TITLE RMI Chat Client
REM Compilation des fichiers sources
javac -d ./ src/client/*.java 
java -Djava.rmi.server.hostname=10.113.31.52 -Djava.security.policy=chat.policy thePack.ClientEngine
REM EOF