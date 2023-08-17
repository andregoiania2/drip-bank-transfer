# Challenge, Senior Java Developer - Andrelino

# Background 
Model in which you're gonna operate: Banks have customers. Customers have accounts. Accounts hold money. 
Transfers are done between accounts. Account holds a list of all transfers. 

There can be two types of transfers: 

Intra-bank transfers, between accounts of the same bank. They don't have commissions, they don't have limits and they always succeed. 

Inter-bank transfers, between accounts in different banks. They have R$5 commissions, they have a limit of R$5000 per transfer and they have a 30% chance of failure. 

Part 1 - Define a set of data structures to accurately reflect the described model. Make sure that new type of transfers can be added with minimal effort.

Part 2 - Create a Transfer Agent that receives an order to transfer money from account A to account B, it issues transfers to the banks considering commissions, limits and handles the possibility of failures.

Expose the Transfer Agent functionality through a rest controller.

For this test scope consider that the transfer agent holds the banks. 

# Back-end API

Developed with the Spring ecosystem, using all the ease of spring boot, automated tests. It has crud for creating customers, accounts, banks and also a registration end point to facilitate the creation of the challenge, the transfer agent.

The database used was postgres, it has a docker-compose.yml file that raises a container with the database, the API access configuration to the database is in the project, in the resource package, application.properties one of the main configuration points, the embedded server will go up on the port  8001.

#Installing in local environment

1 - prerequisite, Java version 17+ and Maven 3+, Docker and Git.

2 - Make a clone of the project in terminal type <b>git clone https://github.com/andregoiania2/drip-bank-transfer.git</b>.

3 - Let's raise the container with the database, at the root of the directory <b>drip-bank-transfer</b> there is a file with the name docker-compose.yml let's run it with the code in terminal <b>docker-compose up -d</b>.

4 - Check if you really uploaded the container <docker ps> must have as a response the information of the two containers the one of the bank <b>postgres</b> and the <b>pgadmin4</b> in the postgres container copy the CONTAINER ID. If the result does not show the two containers, run docker-compose again in step 3 and repeat step 4.

5 - Let's get the IP number to be used in the database configuration, execute the command <b>docker inspect <container_id> | grep "IPAddress"</b> remember to replace the container ID, copy the IP number.

6 - Enter the directory <b>/drip-bank-transfer/src/main/resources</b>, open the file application.properties, and put the IP number you copied in the previous step on line 3 <b>spring.datasource.url=jdbc:postgresql://172.30.0.2:5432/dados
</b>, save the change.

7- Let's compile the project to generate the artifact <b>desafio-drip-andrelino
.jar</b> enter the directory <b>/drip-bank-transfer</b> in the terminal type the command <b>mvn clean install</b>
.
8 - With the above command executed after being successful, a folder will be created <b>target</b>, let's now upload the API, enter the folder in the terminal and execute the command <b>java -jar desafio-drip-andrelino.jar </b>  

9 - To access the API swagger, in the browser type http://localhost:8001/swagger-ui/index.html.

10 - To test in Postman import a collection <b>desafio-usedrip.postman_collection.json</b> which is in the directory <b>/drip-bank-transfer</b> 


# Divida Técnica

There's an error displaying the swagger, I've already reviewed the dependencies and also the configuration, but I couldn't find it, it must be some incompatibility with kotlin.
