mgsv-ws-server
==============

Web Service Server Implementation for mGSV

### Overview
Provides a programmatic access to mGSV server. Synteny and Annotation files can be uploaded to mGSV server as block of string or url pointing to the synteny files.

### Services offered

- uploadURL(String syntenyUrl, String annotationUrl, String email):String
- uploadData(String synteny, String annotation, String email):String

url can point to any publicly accessible plain text file, gzipped or zipped files.

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
