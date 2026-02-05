package com.api.gateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		
	
		Function<PredicateSpec,Buildable<Route>> routeFunction =
				p->p.path("/get")
				.filters(f->f.addRequestHeader("myheader", "muUrI"))
				.uri("http://httpbin.org:80");
		Function<PredicateSpec,Buildable<Route>> routeFunction2 =
						p->p.path("/exchange-rate/**")
						.uri("lb://currency-exch");	
		Function<PredicateSpec,Buildable<Route>> routeFunction3 =
						p->p.path("/currency-conversion/**")
							.uri("lb://currency-conv");
						
		Function<PredicateSpec,Buildable<Route>> routeFunction4 =
						p->p.path("/currency-conversion-new/**")
						.filters(f->f.rewritePath("/currency-conversion-new/(?<segment>.*)",
								"/currency-conversion/${segment}"))
						 .uri("lb://currency-conv");
						
		return builder.routes()
				.route(routeFunction)
				.route(routeFunction4)
						.route(routeFunction2)
						.route(routeFunction3)
						.build();
	}

}
