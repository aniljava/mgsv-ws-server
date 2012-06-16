mgsv-ws-server
==============

Web Service Server Implementation for mGSV

### Overview
A consistent and programmatic access to mGSV is provided using SOAP Web Service. Web service accepts SOAP requests for data upload from files and urls of the synteny data and returns an unique id for the upload
which can be used to access the visualizations. Primary users of Web Services are application developers who need to extend existing bioinformatics tools to include the synteny visualization.

Biologists working with set of annotation data can use client tool that uses this Web Service to upload multiple files and urls.


### Technical Overview
Web Service is implemented using default web service toolkit that comes with standard Java Development Kit (JDK). Actual interfacing to mGSV is done using Apache HTTP Components that converts the incoming
requests to a HTTP POST call as expected by mGSV. Two functions are published.

- uploadURL(String syntenyUrl, String annotationUrl, String email):String
- uploadData(String syntenyData, String annotationData, String email):String


Both the web service functions accepts the synteny data as string or url to publicly accessible files. Upon a successful upload a string representing the id of the upload is returned. The returned id can be used
in later time to access the synteny visualization.

### Installation

#### Binary

- Download pre-built jar file <https://github.com/downloads/aniljava/mgsv-ws-server/ws-server-1.0RC1-jar-with-dependencies.jar>
- create config.properties on the same directory as that of ws-server-1.0RC1-jar-with-dependencies.jar , see configuration guide below
- run as *java -jar ws-server-1.0RC1-jar-with-dependencies.jar*


#### Source

    git clone https://github.com/aniljava/mgsv-ws-server.git
    cd mgsv-ws-server
    mvn package
    cp target/ws-server-1.0RC1-jar-with-dependencies.jar ./
    java -jar ws-server-1.0RC1-jar-with-dependencies.jar
    

### Configuration
Example config.properties

	#Configuration File for mGSV-WS-SERVER
	#Fri Jun 15 03:34:34 CDT 2012
	#Address of actual mGSV server
	mgsv_upload_url=http\://bioinfo.cas.unt.edu/mgsv.anil/
	#Interface to use, ip address, domain name or 0.0.0.0 for all interface
	ws_publish_url=http\://0.0.0.0\:8081/MGSVService
	mgsv_summary_url=http\://bioinfo.cas.unt.edu/mgsv.anil/summary.php?session_id\=
