O scurt descriere a proiectului:
* frontend :
	- am folosit angular pentru implementare
	- aici se incarca imagina si se introduce cheia care sunt trimise mai departe catre backend
	- se conexteaza la backendul aplicatiei pe portul 8088
* broker : 
	- implementat cu Spring. Clasa BrokerApplication.java creaza brkerul, tinand cont daca aplicatia este ornita local sau intr-un container Docker
	- ActivateMq container 
	- ruleaza pe tcp://0.0.0.0:61616 
* backend : 
	- am folosit Spring pentru implementare
	- primeste imaginea in format base64 de la frontend
	- ruleaza in containerul backend si am reusit conectarea la activeMq prin setarea in environment a URL-ului corespunzator (tcp:broker:61616 - broker fiind containerul pentru ActiveMq) 
	- exista o clasa JmsPublisher care trimite imaginea primita si cheia catre ActiveMQ 

* encrypt:
	- container unde ruleaza cod OpenMp
	- creaza un executabil care este apelat din clientul de ActiveMq(subscriber)
	- cripteaza imaginea pe care o primeste de la client 
	- rezultatul este pus intr un fisier care este apoi citit de client si salvat in baza

* decrypt:
	- container unde ruleaza cod OpenMp
	- creaza un executabil care este apelat din clientul de ActiveMq(subscriber)
	- decripteaza imaginea pe care o primeste de la client 
	- rezultatul este pus intr un fisier care este apoi citit de client si salvat in baza

* database: 
	- Container in care se ruleza mysql
	- Am intampinat dificultati in accesarea bazei (am reusit sa pornec conexiunea dupa ce am creat alt user si am acordat drepturi, nu a fost suficient setarea variabilelor in environment in docker-compose)
	- clientul(subscriberul) se conecteaza la baza de date, urmand sa salveze in baza dupa ce primeste rezultatul de la executabilele de encrypt/decrypt


* client:
	- Implementat cu Spring
	- Face subscribe la ActiveMq si primeste imaginea si cheia
	- apeleaza executabilele de encrypt decrypt 
	- salveaza in baza dupa ce se primeste rezultatul 
	
			