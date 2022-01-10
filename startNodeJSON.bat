set ProjectPath=%~dp0
cd libraries
java -Dwebdriver.chrome.driver="%ProjectPath%browserDrivers/chromedriver.exe" -Dwebdriver.gecko.driver="%ProjectPath%browserDrivers/geckodriver.exe" -jar "selenium-server-standalone-3.141.59.jar" -role node -nodeConfig "%ProjectPath%/nodeConfig.json" -port 5555
pause