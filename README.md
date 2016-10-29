# Honor For All

## Build
Build project with `mvn clean install` command.

Create Eclipse project with `mvn eclipse:eclipse` command.

Run database migrations with `mvn flyway:migrate` command.

## Run
Just run `com.honor.forall.HonorForAllApp` class `main` method.

Your application should be up and ready at [localhost:8080](http://localhost:8080)

Check that all services are successfully connected with [healthCheck](http://localhost:8081/healthcheck)

## Environment
* Build tool [Maven](https://maven.apache.org/download.cgi)
* Database [MySQL 5.7+](http://dev.mysql.com/downloads/mysql/) (for convenience please make sure that you set up database user with login `admin` and password `root` or adjust local property values for `/honor-for-all/configs/dev.yaml` and `flyway.properties` files respectively)
