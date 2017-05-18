package org.kyanagimoto.flume.interceptor;

/**
 * Created by koichi.yanagimoto on 2017/05/18.
 */

import org.apache.flume.Event;
import org.apache.flume.event.JSONEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.apache.flume.Context;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class JsonBodyTimestampInterceptorTest {
    @Before
    public void prepare() {}

    private JsonBodyTimestampInterceptor getInterceptor(Context context) {
        JsonBodyTimestampInterceptor.Builder interceptorBuilder = new JsonBodyTimestampInterceptor.Builder();
        interceptorBuilder.configure(context);

        JsonBodyTimestampInterceptor interceptor;
        interceptor = (JsonBodyTimestampInterceptor) interceptorBuilder.build();
        interceptor.initialize();
        return interceptor;
    }

    private String getDefaultEventBody() {
        return "{ \"@timestamp\":\"2017-05-17T09:59:59.911Z\"," +
                "\"beat\":{ " +
                "\"hostname\":\"localhost\"," +
                "\"version\":\"5.3.0\"" +
                " }";
    }

    private Event getEvent(Map<String, String> headers, String body) {
        Event event = new JSONEvent();
        event.setBody(body.getBytes());
        event.setHeaders(headers);
        return event;
    }

    private Context getDefaultContext(String timestampKeyName, String dateTimeFormat) {
        Context context = new Context();
        context.put("timestampKeyName", timestampKeyName);
        context.put("dateTimeFormat", dateTimeFormat);
        return context;
    }

    @Test
    public void BasicFunction() {
        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("existingKey", "existingValue");

        String body = getDefaultEventBody();
        Event event = getEvent(headers, body);

        String timestampKeyName = "@timestamp";
        String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        Context context = getDefaultContext(timestampKeyName, dateTimeFormat);

        JsonBodyTimestampInterceptor interceptor = getInterceptor(context);

        Event interceptedEvent = interceptor.intercept(event);

        assertEquals("Event body should not have been altered",
                body,
                new String(interceptedEvent.getBody()));
    }
}
