# phone-book

Web application for booking mobile phones for testing.


###

The application has 3 rest api methods. Two put methods for booking and returning of phone, and 
one for phone info. 

When phone info method is call, the application returns back info about the phone, the info contains
data if the phone is available, who booked the phone and when, as well as history of all bookings. 

There is also a call to fonoapi to get more info about the phone. The api is called once, and if it is
available and device info is provided, the info is stored in the db, so that the api is not called again 
for the same model. 


### Build and run 

- To build and run the application it is necessary to have docker and maven installed on the machine(because of testcontainers and postgresql).
- Then run from terminal:

> mvn clean package

- To run the application it ise necessary first to start postgresql docker container

> docker run -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres:16

- Then create the database using the script: 

> src/main/resources/db/scripts/init_db.sql

- After that run from terminal 

> mvn spring-boot:run
