package it.sosinski.currencyexchangeservice.configuration;

import it.sosinski.aspectdirectory.logger.MethodAroundAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfiguration {

    @Bean
    public MethodAroundAspect methodAroundAspect() {
        return new MethodAroundAspect();
    }
}