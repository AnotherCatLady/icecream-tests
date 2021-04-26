# Icecream Tests
Tests für die Eiscreme-App. 
Benötigt wird Maven (3.5+) und Java (11+). Außerdem wird der Chrome-Browser benötigt. 
Für Windows Chrome (Version 90.0.4430.xx) bzw auf Linux (Version 80.0.3987.xx). 

### Starten der Tests
Zunächst muss die Eiscreme-Anwendung gestartet werden. Diese sollte unter http://localhost:8080 erreichbar sein. 
Anschließend mit
``mvn clean verify``
(alternativ aus der IDE heraus) die Tests starten.

Unter Linux kann es ggf vorkommen, dass der Chromedriver keine Ausführungsrechte hat und die Tests daher nicht starten können (Fehlermeldung chromedriver not executable). In diesem Fall ist es notwendig dem Chromedriver die entsprechenden Rechte zu gewähren (mittels ``chmod``).
