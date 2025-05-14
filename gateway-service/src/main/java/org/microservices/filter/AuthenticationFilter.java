package org.microservices.filter;


import com.netflix.discovery.converters.Auto;
import org.microservices.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

// We are creating this filter in gateway because
// we don't want to manually validate the tokens in the requests externally.
// So, the API gateway will itself validate the token when any request comes.
// To tell the gateway to call this filter before redirect to any microservice,
// We can add this filter in the application.yml file.

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory {

    @Autowired
    private RouteValidator routeValidator;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;



    public static class Config{

    }

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            ServerHttpRequest httpRequest = null;
            if(routeValidator.isSecured.test(exchange.getRequest())){
//                Check if header contains Token or Not
                if(! exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization Header..!!");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader != null && authHeader.startsWith("Bearer")){
//                  Since the token starts with "Bearer " which is 7 characters.
//                  So we are taking substring after the 7th Index.
                    authHeader  = authHeader.substring(7);
                }
                try{
//                   REST call to AUTH service to validate the token
//                    restTemplate.getForObject("http://IDENTITY-SERVICE-V1/validate?token"+authHeader, String.class);

//                  Rather than doing the REST call to the AUTH service,
//                  We can directly write the logic to validate in gateway service as well.
//                  This way there will be less network usage.
//                  Also, using REST, someone can inspect and find out the AUTH service endpoint to validate.
                    jwtUtil.validateToken(authHeader);

//                    To pass logged in user details also in the request to the concerned microservices
//                    we need to pass the username as a header in the claim as well

                    String loggedInUser = jwtUtil.extractUserName(authHeader);

                    httpRequest = exchange.getRequest()
                                    .mutate()
                                    .header("loggedInUser", loggedInUser)
                                    .build();

                }
                catch (Exception e ){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access to application");
                }

            }
            return chain.filter(exchange.mutate().request(httpRequest).build());
        });
    }

    public AuthenticationFilter(){
        super(Config.class);
    }
}
