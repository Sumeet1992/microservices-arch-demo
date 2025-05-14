package org.microservices.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

//    We don't want to validate tokens for the auth service endpoints.
//    For that purpose, this RouteValidator is created
@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints =List.of(
                                            "/auth/register",
                                            "/auth/token",
                                            "/auth/validate");


//    Here the ServerHttpRequest should be from the webflux package.
    public Predicate<ServerHttpRequest>  isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
