## List Of Symbols
| Acronym/Abbreviation   | Meaning |
| ---------------------- | ---------------------- |
| API | [Application Programming Interface](https://www.howtogeek.com/343877/what-is-an-api/) |
| DB | Database |
| MS | [Micro-Service](https://www.edureka.co/blog/what-is-microservices/) |
| JMS | [Java Messaging Service](https://www.javatpoint.com/jms-tutorial) |
| REST | [Representational state transfer](https://medium.com/extend/what-is-rest-a-simple-explanation-for-beginners-part-1-introduction-b4a072f8740f) |

## Index
0. [Project Definition](#0-Project-Definition)
1. [Architecture](#1-architecture)
   * [High Level Architecture Diagram](#High-level-architecture-diagram)
   * [The stack](#The-Stack)
   * [Springboot API Structure](#SpringBoot-API-Structure)
     
2. [The Micro-Services](#2-The-Micro-Services)
   * [React Front End](#React-Front-End)
   * [AccountAPI](#AccountAPI)
   * [PlayerManagerAPI](#PlayerManagerAPI)
   * [EmailAPI](#EmailAPI)
   * [ActiveMQ](#ActiveMQ)
   * [JMSConsumer](#JMSConsumer)
   * [MongoDB](#MongoDB)
     
3. [Testing](#3-Testing)
   * [Unit Testing](#Unit-Testing)
     
4. [I Don't Understand (FAQ)](#4-I-Dont-Understand-FAQ)
   
   
   
# 0. Project Definition
As a QA employee, you can register an account using your QA email address. Once registered, you can confirm that you will be joining the QA company football game for the following week.


# 1. Architecture
## High level architecture diagram

![HLD1](/HLD1.jpg)

From the diagram we can see four major components of the entire application.

**Browser**: 
* The user of the application will only see the front end, depicted in the diagram as `Browser`. More specifically they will be exposed to  `Presentation`. This of course is the HTML rendered by the browser, styled up with CSS. 
* The user will perform operations in the browser that will make REST calls to endpoints in `AccountAPI`.

**Backend Application**: 
* This is comprised of four APIs. 
* We do not want the Browser to make calls to multiple APIs. Instead we want it to make calls to a main API which will then make the appropriate calls to `PlayerManagerAPI` and `EmailAPI`. This main API will be `AccountAPI`.
* As described in the [project definition](#0-Project-Definition), the user will be able to confirm if they are playing the weekly football game. The `PlayerManagerAPI` is responsible for changing the status of the account to playing that week so their name will appear in the list of players. Further, the `PlayerManagerAPI` will be responsible to change the status of all players to not playing after the weekly football game. 
* To allow for the movement of data back to the `PlayerManagerAPI` in order for the status of all players to be changed after the weekly football game, the `MongoClientServiceAPI` will call data from the database and pass it back through to the `AccountAPI` so the status can then be changed in the `PlayerManagerAPI`.
* The user will also receive email notifications to ensure that they are aware they are playing in the weekly football game. This feature will be handled by the `EmailAPI`.


The process:

The user will create an account by entering their full name, QA company email and entering a password. Once the user clicks create, a call will be made to the AccountAPI to create the account this account will then be persisted to the database via the Queue and the JMSConsumer. 

The user will then be taken to another page which will display a list of the players that are playing in the weekly football game, and will feature a button allowing the user to join the game. Once this button is clicked, a call will be made to the PlayerManagerAPI from the AccountAPI to change the status of that account to playing which means that their name will appear in the list of players who are playing in the weekly football game.

The player will receive an email to their QA company email notifying them that they have confirmed to play in the weekly football game. This will be handled by the EmailAPI.

After the weekly football game, the status of all the players will be changed to not playing. The data will be called from the database by the MongoClientServiceAPI so it can be passed to the AccountAPI and PlayerManagementAPI to make this change.


```
For Dummies:
Consider each API as a different person with different specialties. 
One API may know about prices and another may know about phones.
As a customer, if you wanted advice on phones you would ask the phone person.
But the phone person doesn't know about prices! So they would ask the prices person for help.
The phone person can then tell the customer what they know about phones.

In this case, the customer acts as the browser, making a request to an external API for phone advice. 
```


**Queue**:
* The queue is used for communication between two applications. In this case it, the AccountAPI wants to talk to the `JMSConsumer`, since it wants to store data in the longterm database. The queue picks up data from the AccountAPI and waits for the `JMSConsumer` to take the data.


```
For Dummies: 
Imagine this as a factory conveyor belt. 
A worker places a chocolate bar on the conveyor belt. 
Another worker is waiting at the conveyor belt for chocolate bars because it is their job to store chocolate bars away. 
The worker is constantly checking the conveyor belt for the chocolate until they see one. 
Once they do, they grab it, store it away and go back to the conveyor belt.

In this sense, the AccountAPI is the worker placing chocolate on the belt, 
the Queue is the conveyor belt, and the JMSConsumer is the worker taking chocolate off the belt.
```

**JMSConsumer**:
* This API constantly checks if the `Queue` has any data that it is allowed to receive. If it does, it will take the data off of the queue. 
* Once off the queue, this API will persist the data to the database.


## The Stack
* **React**: This is a javascript library for making user interfaces. The front end of this project is a single paged application built with React. More information on React can be found [here](https://stories.jotform.com/7-reasons-why-you-should-use-react-ad420c634247)

* **Springboot**: Built on top of the Spring framework, which uses dependency injection to build decoupled systems. Much quicker and easier to deploy compared to standard web based applications. SpringBoot will be used for each of our APIs in the `Backend Application`.
More information on SpringBoot can be found [here](https://www.zoltanraffai.com/blog/what-is-spring-boot/) and [here](https://stackoverflow.com/questions/1061717/what-exactly-is-spring-framework-for)

* **ActiveMQ**: An open sourced implementation of JMS. Used for sending messages between applications. ActiveMQ is what we will use for the `Queue` in the diagram above. More information on ActiveMQ and JMS can be found [here](http://blog.christianposta.com/activemq/what-is-activemq/) and [here](https://www.javatpoint.com/jms-tutorial)

* **MongoDB**: A NoSQL database. Very flexible and not as stringent as SQL databases. Described by some as a 'sexy dustbin'. More information on Mongo can be found [here](https://www.tutorialspoint.com/mongodb/mongodb_overview.htm)


## Springboot API Structure
![HLD2](/HLD2.JPG)

Each API we have will follow this structure with slight variations depending on the purpose of the API.


* Rest:

This layer exposes the applications RESTful API. The endpoints contained in this layer are responsibile for accepting requests for a resource and delegating the processing of that request to the Service layer. 

* Service:

Any business rules required will be set in this layer.

* Persistence:

This layer has two components. 
The Domain part holds the classes of the objects you will work with. 
The repo part is abstracted from us, any details of the persistence mechanism is hidden away. We do not write any code to store/alter data in the database .
The repo uses JpaRepository which persists to a relational H2 database, using the [JPA](https://en.wikibooks.org/wiki/Java_Persistence/What_is_JPA%3F) interface with a [Hibernate](https://en.wikipedia.org/wiki/Hibernate_(framework)) implementation.


# 2. The Micro-Services

## React Front End

* Using react we have created a single page application that dynamically renders the page.

* We have included [routing](https://reacttraining.com/react-router/core/guides/philosophy) using the react-router-dom package so that routes are rendered as the app is rendering

* Most importantly, we use the axios package to make [HTTP requests](https://www.tutorialspoint.com/http/http_requests.htm) to the AccountApi. More information on the axios package can be found [here](https://alligator.io/react/axios-react/)

## AccountAPI

* This API follows the Springboot API structure above.
  
* Some notable features:
  - **Rest**: 
  One of our endpoints in this layer creates an account and another endpoint communicates with the PlayerManagerAPI to change the status of the account to playing.
  These calls are made in this layer.
  In addition to this, our rest layer performs another special function; when an account is created, it is sent to the Queue.
  - **Persistence**: 
  When an account is created/updated/deleted, changes must be made to our in-memory database, that is our H2 database, all made possible with our fancy JpaRepository interface.

  
## PlayerManagerAPI
   * A very simple API that changes the accounts playing status.


## ActiveMQ
As stated before, this is the service that will handle communication between our AccountApi and our JMSConsumer.

This mciro-serivce is not quite visible to us. It sits quietly on localhost:3306.

## JMSConsumer
This API listens to the queue on localhost:3306 and waits until there is data it can take.
It then picks the data and persists it to MongoDB.

## MongoDB
This is a seperate service running on localhost:27017. 


# 3. Testing

## Integration Testing
   * put stuff here hamish

## Unit Testing
   * Unit testing involves breaking the program down into very small pieces that we can test. More about unit testing [here](http://softwaretestingfundamentals.com/unit-testing/)
   * For our unit tests we used **Mockito**.
   * **Mockito** - Mockito is a library for effective unit testing. More about mockito [here](https://www.tutorialspoint.com/mockito/index.htm)

# 4. How To Run

## Prerequisites:
* [Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)
* [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* [Node.js](https://nodejs.org/en/download/)
* [ActiveMQ](https://activemq.apache.org/getting-started.html)
* [MongoDB](https://docs.mongodb.com/manual/installation/)

## Steps:
For a local startup: CURRENTLY FOR TESTING1!!!!!!!!!!

1. Make a new directory to work inside (Rightclick the folder you want 
2. Create a .bat file with the contents:
```
@echo off
git clone https://github.com/Nightmayr/FootballManager-AccountApi.git
cd FootballManager-AccountApi
git checkout development
cd ..

git clone https://github.com/Nightmayr/FootballManager-Consumer.git
cd FootballManager-Consumer
git checkout new-dockerfile
cd ..

https://github.com/Nightmayr/FootballManager-Email.git
cd FootballManager-Email
git checkout qa-reviewed
cd ..

git clone https://github.com/Nightmayr/FootballManager-Frontend.git
cd FootballManager-Frontend
git checkout development
cd ..

git clone https://github.com/Nightmayr/FootballManager-PlayerManager.git
cd FootballManager-PlayerManager
git checkout development
cd ..
```

3. Run the .bat file you created
4. Start the ActiveMQ service

Do this by locating the directory where you have extracted the activemq files. Open command prompt and navigate to the 'bin' folder within the activemq files. Run the command 'activemq start'. The activemq process should start. You can view the queue on localhost:8161/admin/queues.jsp. The default username and password is 'admin'. 

5. Start MongoDB

Do this by first locating the directory where mongodb is installed. The path will look similar to this *'..\MongoDB\Server\4.0\bin\'*. Open command prompt, navigate to this directory and run the mongo.exe file by typing 'mongo'. Once you hit enter, the mongo service will be running.

6. Create another .bat file with the contents:
```
cd FootballManager-AccountApi
start mvn spring-boot:run

cd ../FootballManager-Consumer
start mvn spring-boot:run

cd ../FootballManager-Email
start mvn spring-boot:run

cd ../FootballManager-MongoClientService
start mvn spring-boot:run

cd ../FootballManager-PlayerManager
start mvn spring-boot:run

cd ../FootballManager-Frontend
npm start


```
