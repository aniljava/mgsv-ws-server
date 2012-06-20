mgsv-ws-server - 1.0
==============

SOAP Web Service for [mGSV](http://cas-bioinfo.cas.unt.edu/mgsv/index.php) and [GSV](http://cas-bioinfo.cas.unt.edu/gsv/homepage.php). It is standalone
java application, that can be run alongside mGSV/GSV or in seperate machine. Allows clients to upload files and urls programmatically. A client reference
implementation can be found at [mgsv-ws-client](https://github.com/aniljava/mgsv-ws-client).

Web Service exports two methods

     uploadURL(String syntenyUrl, String annotationUrl, String email):String
     uploadData(String syntenyData, String annotationData, String email):String

WSDL can be obtained from :

    http://cas-bioinfo.cas.unt.edu:8081/MGSVService?wsdl

## Dependencies
- JDK 1.6+
- Maven 3.0+ (Source Build)

## Running
Download or produce binary using mvn package. Inside the directory where you have binary jar ()

    shell> echo "mgsv_upload_url=http://<YOUR_SERVER_DOMAIN_NAME>/mgsv" > config.properties
    shell> echo "ws_publish_url=http\://<YOUR_SERVER_DOMAIN_NAME>\:8081/MGSVService" >> config.properties
    shell> java -jar ws-server-1.0RC1-jar-with-dependencies.jar


## Running for GSV

1. Server config.properties

        mgsv_upload_url=http\://cas-bioinfo.cas.unt.edu/gsv/homepage.php
        ws_publish_url=http\://97.94.192.248\:8082/GSVService

2. Client config.properties

        remote=http\://97.94.192.248\:8082/GSVService
