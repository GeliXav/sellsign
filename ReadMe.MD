# Sell and Sign test

Hello !
This is my technical test for Sell and Sign.

# Stack

I did it with Spring-Boot 2.3.5 and Thymeleaf for the front-end.

# Requirements

You'll need Java 11 and Maven.

# How to launch

To launch the embedded tomcat just run :

```
mvn spring-boot:run
```

## Use the app

To access the front-end just go to localhost:8080. 
Keep in mind the app uses an embedded db (h2), so every time you restart the app, 
the database is erased.

On the frontend, when signing up, the user will have only read access.  Connect with the admin  user
to give him read and write.

To check the api docs go to : 
```
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
```

To get a token with an apikey user, run localhost:8080/v1/authToken with the header 
X-API-KEY and your apikey. This will give you a JWT token to access the api endpoints.


