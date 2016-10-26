# Honor For All

## Build
Create Eclipse project with `mvn eclipse:eclipse` command.

Build project with `mvn clean install` command.

## Run
Just run `com.honor.forall.HonorForAllApp` class `main` method.

Your application should be up and ready at [localhost:8080](http://localhost:8080)

Check that all services are successfully connected with [healthCheck](http://localhost:8081/healthcheck)

## Environment
* Database [MySQL 5.7+](http://dev.mysql.com/downloads/mysql/) (for convenience please make sure that you set up database user `admin` with password `root` or adjust local `/honor-for-all/configs/dev.yaml` properties respectively)
