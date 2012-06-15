mgsv-ws-server
==============

Web Service Server Implementation for mGSV

### Overview
Provides a programmatic access to mGSV server. Synteny and Annotation files can be uploaded to mGSV server as block of string or url pointing to the synteny files.

### Services offered

- uploadURL(String syntenyUrl, String annotationUrl, String email):String
- uploadData(String synteny, String annotation, String email):String

url can point to any publicly accessible plain text file, gzipped or zipped files.

