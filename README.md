# Vote Manager Application
This is a project to made a shedule vote manager for multiple associateds.

## Tecnologies
- Java Spring Boot Server REST API and WebSocket server;
    - REST API runs on port 8080 by default
    - WebSocket server runs on port 8081, unchangeble at this moment;
- Webpack module bundler for Javascript and SCSS resources compilation;
    - This compile scripts and styles for front view, the tecnologies used in front are VueJS and Bootstrap frameworks.

## Requisites
- JDK 11 to run maven and NodeJS to compile sources.

## Scripts
All scripts are simplifieds on package.json. Node keeps responsable to manage all.
- webpack-build: Build scripts and styles for production mode;
- webpack-watch: Build watching changes scripts and styles in development mode;
- maven-build: Build spring-boot jar to run inside Tomcat application;
- maven-run: Build spring-boot watching changes; 
- maven-test: Run implemented tests in spring boot;
- build: Build resoures and after that build spring boot;

# How it works
- The associated logins in plataform using REST API, if was the first user, there is auto created.
- When login, a UUID token control is sent to user and all filtered request request this token.
- Also the associated, connects in Websocket server to observe changes in shedules. Websocket server is responsable to broadcast any change in shedule;
- The associated logged can create another associates, shedules and vote if allowed.
- On login, associated CPF is sent to a heroku app by passing by backend because CORS policy of the server.

- If associated is able to vote a vote button appears, start date with time verification was not yet implemented.
