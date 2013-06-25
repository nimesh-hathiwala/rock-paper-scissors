eBay Rock, Paper, Scissors Unattended Test Solution
---------------------------------------------------
Author:
Nimesh Hathiwala
n_hathiwala@hotmail.com


Building / Deploying
--------------------
  
The source code is built using Maven, for example -
    
    mvn clean install

By default, the integration (Selenium) tests are disabled. 
They can be executed by passing in the following parameter to Maven, eg. -
    
    mvn clean install -DskipITs=false

A test coverage report can be generated using the following command -

    mvn clean install cobertura:cobertura
    
The report can be found in -
    
    <project-dir>target/site/cobertura/index.html

The web application war file should be deployed in an application server.
The application has been tested on Tomcat v7.
Once deployed, it can be accessed via a web browser, eg. -
    
    http://localhost:8080/rock-paper-scissors/


Design Considerations & Assumptions
-----------------------------------

- Assume a web application is an accetable user interface.
- Assume a single page hosting both games is acceptable.
- Assume a single button to reset both games is acceptable.
- Assume no logging is required for this exercise.


