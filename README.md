# Flume Interceptor: json-body-timestamp-interceptor

This project provides an interceptor for Flume.

## requirement
  - Source data is JSON structure.
  - It's need to included timestamp value on Json head level.
  - It will put on the Sink header.

## How to use
1. Clone project
2. Build with Maven
```
cd ./json-body-timestamp-interceptor
mvn clean install
```

3. The jar file will be installed in your local maven repository and can be found in sub-directory.  
Add it on Flume classpath.
```
cp ./target/json-body-timestamp-interceptor-0.X.jar /path/to/flume/dir/lib
```

4. Configure Flume conf file.
Saved values (timestampYear, timestampMonth, timestampDay, timestampHour) on flume header.

```properties
agent.sources = r1
agent.sinks = s1

agent.sources.r1.interceptor = i1
agent.sources.r1.interceptors.i1.type = org.apache.flume.interceptor.JsonBodyTimestampInterceptor$Builder
agent.sources.r1.interceptors.i1.properties.timestampKeyName = @timestamp
agent.sources.r1.interceptors.i1.properties.dateTimeFormat = yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

agent.sinks.s1.hdfs.path = hdfs://path/%{timestampYear}/%{timestampMonth}/%{timestampDay}/%{timestampHour}
```

## Change Log
### v0.1.1
move property value on flume property file.
