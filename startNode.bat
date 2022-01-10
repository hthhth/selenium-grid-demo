set ProjectPath=%~dp0
cd libraries
java -Dwebdriver.chrome.driver="%ProjectPath%browserDrivers/chromedriver.exe" -Dwebdriver.gecko.driver="%ProjectPath%browserDrivers/geckodriver.exe" -jar "selenium-server-standalone-3.141.59.jar" -role webdriver -hub http://192.168.20.2:4444/grid/register/ -port 5555
pause