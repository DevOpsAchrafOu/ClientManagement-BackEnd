# smartClientManagement-BackEnd REST API
### _v.0.1_

This is the master branch of smartClientManagement-BackEnd  REST API.
This document decribes the steps to follow to build the project in a windows architecture.

## Features
- Login and logout
- User management
- Role management
- Menu management : Create, View, Update, List
- Ville management : Create, View, Update, List
- Pays management : Create, View, Update, List


## Tech
smartClientManagement-BackEnd uses a number of open source projects to work properly:
- [Spring boot v2.7.10]
- [Maven v3]
- [Mysql connector v8.0.27][dev]
- [swagger]
- [Postgresql Database][prod]

## Installation

## Prerequisite

- To run the project, we recommand the use of:
[Java 8]
[Mysql database] v8+
- To build the project, we recommand the use of:
[Java v8]
[Maven v3]

### Install the database
We recommand the use of **Mysql Database v8.x+**


### Install App
Pull the project from the github repository (https://github.com/DevOpsAchrafOu/smartClientManagement-BackEnd)

In the install directory **_$INSTALLATION_PATH/smartClientManagement-BackEnd_**

Install the dependencies :

For Dev environments...
```sh
cd $INSTALLATION_PATH/smartClientManagement-BackEnd
mvn clean install -U -DskipTests  -P dev
```
That will generate a jar file **smartClientManagement-BackEnd-api-0.1-dev.jar** _if you pulled the v0.1 version_

For qualification environments...
```sh
cd $INSTALLATION_PATH/smartClientManagement-BackEnd
mvn clean install -U -DskipTests  -P rec
```
That will generate a jar file **smartClientManagement-BackEnd-api-0.1-rec.jar** _if you pulled the v0.1 version_

For production environments...
```sh
cd $INSTALLATION_PATH/smartClientManagement-BackEnd
mvn clean install -U -DskipTests  -P prod
```
That will generate a jar file **smartClientManagement-BackEnd-api-0.1-prod.jar** _if you pulled the v0.1 version_

### Run the App
Run the following command (Prod case)
```sh
cd $INSTALLATION_PATH/smartClientManagement-BackEnd/target
java -jar smartClientManagement-BackEnd-api-0.1-prod.jar
```

The properties file defines the values of the variables used by the app. Please refer to [Environnement Variables](#env_variables)

This will generate the necessary tables needed to run the App.
> **Note**: **`If this was not happen, please consider doing it manually`**

Check if all is well by following this link : http://localhost:8081/api/mng/v1/public/about . This should show **REST API for OverMap App - Version : 0.1**

<a name="env_variables"></a>
## Environnement variables
smartClientManagement-BackEnd uses certain numbers of variables as described bellow :
| Variable | Signification |
| ------ | ------ |
| app.version | The app version |
| host.dns | The DNS host represating the route URL of the app (ex. https://smartclientmanagement-backend.onrender.com/api/mng/v1/public/about)|
| spring.datasource.url | Database connexion URL |
| spring.datasource.username | Database user |
| spring.datasource.password | Database password |


## License

smartClientManagement-BackEnd - copyright - 2023-2024
