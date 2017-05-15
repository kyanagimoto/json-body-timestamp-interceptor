# Flume Interceptor: json-body-timestamp-interceptor

This project provides an interceptor for Flume.

## requirement
  - Source data is JSON structure.
  - It's need to included "@timestamp" key on Json head level.
  - It will put on the Sink header.

## How to use
1. Clone project
2. Build with Maven
3. The jar file will be installed in your local maven repository and can be found in sub-directory.  
Add it on Flume classpath.
4. Configure Flume conf file.

## Change Log
