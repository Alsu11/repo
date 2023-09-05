package ru.itis;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProxyConfig {

    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("files_route",
                        route -> route.path("/files-management/**")
                                .and()
                                .method(HttpMethod.GET)
                                .filters(filter -> filter.stripPrefix(1))
                                .uri("lb://files-service"))
                .route("email_route",
                        route -> route.path("/email-management/**")
                                .filters(filter -> filter.stripPrefix(1))
                                .uri("lb://email-service"))
                .route("user_route",
                        route -> route.path("/user-management/**")
                                .filters(filter -> filter.stripPrefix(1))
                                .uri("lb://user-service"))
                .route("basket-route",
                        route -> route.path("/basket-management/**")
                                .filters(filter -> filter.stripPrefix(1))
                                .uri("lb://basket-service"))
                .route("orders-route",
                        route -> route.path("/orders-management/**")
                                .filters(filter -> filter.stripPrefix(1))
                                .uri("lb://orders-service"))
                .route("products-route",
                        route -> route.path("/products-management/**")
                                .filters(filter -> filter.stripPrefix(1))
                                .uri("lb://products-service"))
                .build();
    }
}
