# My-Retail-RESTFul-Service
This is a RESTful service that provides API to get the product details from an external API and get the price details from the database and provide integrated results to the users. <br />
It also provides API to change the price of the product. <br />

*Technologies used:* 

	Spring boot (v1.5.3.RELEASE)
	MongoDB 4.0.2 (Community edition)
	Gradle 4.10.2
	JUnit 4.12 

*Prerequisites:* 

	Java 8 
	Postman

*Execution Procedure:*
    
    Run the following command to build JAR in command line
        ./gradlew bootJar
    
    Run the following command in command prompt 
    	java -jar build/lib/MyRetail-0.0.1-SNAPSHOT.jar
    
    Enter the following urls in the address bar of a web browser to get the product details. 
    	https://localhost:8080/product/13860428 
    
    To access the get method open Postman enter the url in the address bar and select method PUT. 
    	https://localhost:8080/product/13860428 
    
    The request to the PUT method must be sent in JSON format in message body. 
    	{"currentPrice":{"price":99.9,"currency":"USD"}} 	 

