package io.lithium.spring5reactor.reactor;

import io.lithium.spring5demo.gitter.GitterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.lithium.spring5demo.gitter")
public class ReactorConfig {

    @Bean
    public GitterProperties gitterProperties() {
        GitterProperties props = new GitterProperties();
        props.setToken("XXXX");
        return props;
    }
}
