Project Name: com.app.storytel.challenge

Description: This project serves RESTful API endpoints for a public message box, 
it provides CRUD functionalities for messages in the software.

Specs: The projects makes use of H2 in-memory database (which is only available when 
application is running), 
and the required libraries have been included in the application pom file, so no 
additional configuration is required. Sample data is loaded upon application start-up
and this data is wiped everytime the application is shutdown.

Endpoints: There are three major endpoints
<ol>
    <li>
        <em>/api/public</em>: this serves the publicly available messages APIs
    </li>
    <li>
        <em>/api/messages</em>: this serves all the restricted messages APIs, 
        basically all APIs that add or modified message data
    </li>
    <li>
        <em>/api/authentication</em>: this serves APIs calls to create new user 
        and generated a jwt token for clients who are able to successfully sign-in
    </li>
</ol> 

Build Instructions:
    To build the application you can run any of these commands from the root 
    folder of the application
    <ul>
        <li>
            <em>mvn install</em>. 
            This will run all the tests in the application and generate an executable 
            jar file in the target folder of the application.
        </li>
        <li>
            <em>mvn clean install</em>. 
            This will first delete all generated files from previous successful run 
            commands and then run all the tests in the application again and generate 
            an executable jar file in the target folder of the application.
        </li>
        <li>
            <em>mvn clean install -Dmaven.test.skip=true</em>. 
            This does the same has the previous command with the only difference being 
            that this does not run any of the tests.
        </li>
    </ul>

Run Instructions:
    There are three ways to run the application the first two requires that you have
     successfully completed a build process:
    <ul>
        <li>java -jar target/com.app.storytel.challenge-0.0.1-SNAPSHOT.jar</li>
        <li>
            Use Docker:
            <ul>
                <li>build: <em>docker build -t storytelchallenge . </em></li>
                <li>run: docker run -p 8080:8080 storytelchallenge</li>
            <ul>
        </li>
    </ul>
    
Testing:
By default application is reachable via http://localhost:8080
To see all data in the database goto http://localhost:8080/h2-console
Credentials:
    JDBC URL: jdbc:h2:mem:storytel
    Username: sa
    Driver Class: org.h2.Driver

I have also included a file <em>"${project-home}/src/main/resources/Stroytel.postman_collection.json"</em>, 
which you can import into your local postman client 
(not tested with browser plugin). This will give you a collection of all postman 
tests that I did and hopefully save you the time of having to write one by yourself 
for each endpoints.
