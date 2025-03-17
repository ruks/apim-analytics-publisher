package org.wso2.am.analytics.publisher;

import org.wso2.am.analytics.publisher.exception.MetricReportingException;
import org.wso2.am.analytics.publisher.reporter.CounterMetric;
import org.wso2.am.analytics.publisher.reporter.MetricEventBuilder;
import org.wso2.am.analytics.publisher.reporter.MetricReporter;
import org.wso2.am.analytics.publisher.reporter.MetricSchema;
import org.wso2.am.analytics.publisher.reporter.cloud.DefaultAnalyticsMetricReporter;
import org.wso2.am.analytics.publisher.util.Constants;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class TestPublish {
    public static void main(String[] args) throws Exception {
        Map<String, String> configMap = new HashMap<>();
        String authURL = "https://analytics-event-auth.dv.choreo.dev/auth/v1";
        String authToken = "<on-prem key>";
        configMap.put(Constants.AUTH_API_URL, authURL);
        configMap.put(Constants.AUTH_API_TOKEN, authToken);

        MetricReporter metricReporter = new DefaultAnalyticsMetricReporter(configMap);
        CounterMetric metric = metricReporter.createCounterMetric("test-connection-counter", MetricSchema.RESPONSE);
        MetricEventBuilder builder = metric.getEventBuilder();
        populateBuilder(builder);
        metric.incrementCount(builder);
    }

    public static void populateBuilder(MetricEventBuilder builder) throws MetricReportingException {
        String uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, "
                + "like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";

        builder.addAttribute(Constants.REQUEST_TIMESTAMP, OffsetDateTime.now(Clock.systemUTC()).toString())
                .addAttribute(Constants.CORRELATION_ID, "1234-4567")
                .addAttribute(Constants.KEY_TYPE, "prod")
                .addAttribute(Constants.API_ID, "9876-54f1")
                .addAttribute(Constants.API_TYPE, "HTTP")
                .addAttribute(Constants.API_NAME, "PizzaShack")
                .addAttribute(Constants.API_VERSION, "1.0.0")
                .addAttribute(Constants.API_CREATION, "admin")
                .addAttribute(Constants.API_METHOD, "POST")
                .addAttribute(Constants.API_CONTEXT, "/v1/")
                .addAttribute(Constants.API_RESOURCE_TEMPLATE, "/resource/{value}")
                .addAttribute(Constants.API_CREATOR_TENANT_DOMAIN, "carbon.super")
                .addAttribute(Constants.DESTINATION, "localhost:8080")
                .addAttribute(Constants.APPLICATION_ID, "3445-6778")
                .addAttribute(Constants.APPLICATION_NAME, "default")
                .addAttribute(Constants.APPLICATION_OWNER, "admin")
                .addAttribute(Constants.REGION_ID, "NA")
                .addAttribute(Constants.GATEWAY_TYPE, "Synapse")
                .addAttribute(Constants.USER_AGENT_HEADER, uaString)
                .addAttribute(Constants.USER_NAME, "admin")
                .addAttribute(Constants.PROXY_RESPONSE_CODE, 401)
                .addAttribute(Constants.TARGET_RESPONSE_CODE, 401)
                .addAttribute(Constants.RESPONSE_CACHE_HIT, true)
                .addAttribute(Constants.RESPONSE_LATENCY, 2000L)
                .addAttribute(Constants.BACKEND_LATENCY, 3000L)
                .addAttribute(Constants.REQUEST_MEDIATION_LATENCY, 1000L)
                .addAttribute(Constants.RESPONSE_MEDIATION_LATENCY, 1000L)
                .addAttribute(Constants.USER_IP, "127.0.0.1");
    }
}
