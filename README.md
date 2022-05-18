# Hamid Farmani
I tried to keep the functionalities as is but change the package structure. Business logic moved to service package and model classes are now in their own package. The movies' code seemed to be an enum and it will be easier in the future to change, rather than having strings all over the places.

There were multiple "magic numbers" in the code which I tried to extract and move them in a separate file as constants.

A custom exception handler added for the time when rental can not be found. Unit tests with multiple scenarios has been implemented and logs are set.

For the commits, I followed conventional commits and ignored not needed files or directories. Also, there's a formatter which can be run by following command:
```
mvn com.coveo:fmt-maven-plugin:format 
```

I added the dependencies such as JUnit and AssertJ in ```pom.xml``` file. If I wanted to spend more time and there was a request for it, I would have done the following:
* Adding at least CRUD functionalities
* Saving the information in a real database
* Implementing RESTful APIs for the application to have interaction with user
* Creating a proper UI
* More test scenarios/exception handlers
* Dockerizing the application

To build the app and run the tests:
```
mvn clean install
```

You may run the application with following command:
```
mvn exec:java -D"exec.mainClass"="com.etraveli.refactoring.RentalApplication"
```
