set ProjectPath=%~dp0
cd libraries
java -jar selenium-server-standalone-3.141.59.jar -role hub -hubConfig "%ProjectPath%/hubConfig.json"
pause