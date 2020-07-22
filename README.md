<img src="https://github.com/mario-santoro/MongoDBProject/blob/master/Icon/db2-ICON2.png" height="300">

# MongoDBProject
Progetto per l'esame di DataBase2 della magistrale di Informatica, Università degli Studi di Salerno.

# Scopo del progetto
Questo progetto si basa sullo sviluppo di un applicativo che si interfacci con <b>DBMS NoSQL</b> (non relazionale), quale <b>MongoDB</b>, nel quale è caricato un dataset. Questo applicativo prevede una serie di query parametriche, ed una successiva visualuzzazione dei risultati in un opportuno grafico per la stessa rappresentazione.

# Tecnologie usate
<img src="https://github.com/mario-santoro/MongoDBProject/blob/master/Icon/Immagine1.png" height="200">
Le tecnologie scelte per lo sviluppo di questo applicativo sono: <b>Java</b> come linguaggio di programmazione; come DBMS non relazionale è stato scelto, come previo descritto, <b>MongoDB</b>; mentre per l'interfacciamento con l'utente è stato scelto un interfaccia web tramite il server web <b>Tomcat</b>. Per la rappresentazione grafica è stato usato lo script <a href="https://www.chartjs.org/">Chart.js</a>.

# Introduzione MongoDB
Per il nostro progetto, MongoDB è stato usato all'interno di un applicazione Java mediante il driver presente alla seguente <a href="https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-sync">pagina</a>, che ci ha permesso di eseguire connessione con il database e la possibilità di fare le relative query.
MongoDB è un DBMS non relazionale, orientato ai documenti. Classificato come un database di tipo NoSQL, MongoDB si allontana dalla struttura tradizionale basata su tabelle dei database relazionali in favore di documenti in stile JSON con schema dinamico (MongoDB chiama il formato BSON), rendendo l'integrazione di dati di alcuni tipi di applicazioni più facile e veloce. Rilasciato sotto una combinazione della GNU Affero General Public License e dell'Apache License, MongoDB è un software libero e open source. (ref <a href="https://it.wikipedia.org/wiki/MongoDB">Wikipedia</a>)

# Dataset usato
Per il dataset è stato usato il seguente <a href="https://www.kaggle.com/murderaccountability/homicide-reports">dataset</a> presente su kaggle.com. Questo dataset comprende i reports sugli omicidi dell'FBI dal 1980 al 2014 e i dati del Freedom of Information Act su oltre 600.000 omicidi che non sono stati segnalati al Dipartimento di Giustizia. Questo set di dati comprende l'età, la razza, il sesso, l'etnia delle vittime e degli autori, oltre alla relazione tra la vittima e l'autore del reato e l'arma usata. 
