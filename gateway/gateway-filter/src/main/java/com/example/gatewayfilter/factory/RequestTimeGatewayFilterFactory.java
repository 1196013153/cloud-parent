package com.example.gatewayfilter.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
public class RequestTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestTimeGatewayFilterFactory.Config> {
    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";
    private static final String KEY = "withParams";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    public RequestTimeGatewayFilterFactory() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter filter = ((exchange, chain) -> {
            long startTime = System.currentTimeMillis();
            exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(()->{
                        StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath());
                        sb.append(":").append(System.currentTimeMillis() - startTime).append("ms");
                        if (config.isWithParams()) {
                            sb.append(" params:").append(exchange.getRequest().getQueryParams());
                        }
                        log.info(sb.toString());
                    })
            );
        }
        );
        return filter;
    }

    public static class Config {
        private boolean withParams;
        public boolean isWithParams() {
            return withParams;
        }
        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }
    }
}
