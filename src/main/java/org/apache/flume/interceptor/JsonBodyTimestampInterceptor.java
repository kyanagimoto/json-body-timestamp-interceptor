package org.apache.flume.interceptor;

/**
 * Created by koichi.yanagimoto on 2017/05/15.
 */

import java.time.LocalDateTime;
import java.util.*;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonBodyTimestampInterceptor implements Interceptor {
    private static final Logger LOG;

    static {
        LOG = Logger.getLogger(JsonBodyTimestampInterceptor.class);
    }

    private JsonBodyTimestampInterceptor() {}

    @Override
    public void initialize() {}

    @Override
    public Event intercept(Event event) {

        Map<String, String> headers = event.getHeaders();

        String body = new String(event.getBody());
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;

        try {
            jsonObject = (JSONObject) jsonParser.parse(body);
        } catch (org.json.simple.parser.ParseException e) {
            LOG.info("Received this log message that is not formatted in json: "+body+"\n");
            return event;
        }

        String timestamp = jsonObject.get("@timestamp").toString();
        LocalDateTime parsedDateTime = LocalDateTime.parse(timestamp);

        headers.put("timestampYear", String.valueOf(parsedDateTime.getYear()));
        headers.put("timestampMonth", String.valueOf(parsedDateTime.getMonthValue()));
        headers.put("timestampDay", String.valueOf(parsedDateTime.getDayOfMonth()));
        headers.put("timestampHour", String.valueOf(parsedDateTime.getHour()));

        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event:events) {
            intercept(event);
        }
        return events;
    }

    @Override
    public void close() {}

    public static class Builder implements Interceptor.Builder {
        @Override
        public Interceptor build() {
            return new JsonBodyTimestampInterceptor();
        }

        @Override
        public void configure(Context context) {}
    }
}