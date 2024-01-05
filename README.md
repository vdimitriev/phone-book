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


## How the application works

Right now the application works in a way that mobile phones and users (testers) are created in advance in the db, 
and the tester needs to insert his uuid and an id of the phone to make a booking or to return the phone. 

To get the info about the phone, when the call to /api/v1/phone/{phoneId} is done, the application retrieves the data 
about the phone from the db. If the information about the phone technology and bands is missing it will try to call 
fonoapi service. There is retry, circuit braker and time limiter defined when calling fonoapi so if the service is not 
available (which is currently the case), it will stop trying calling it. 

## TODO
### What next needs to be done

1. User authentication
2. Swagger definitions of api
3. More unit tests
4. Caching, for the phone info this will be very good addition as it will reduce calls to database and fonapi
5. Make the application reactive 
    - Use Mono to return data about phone info
    - Use coroutines and async-await pattern to call fonoapi service
    - Use reactive repository to fetch and store data in database. 
