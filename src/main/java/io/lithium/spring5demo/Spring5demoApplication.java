package io.lithium.spring5demo;

import io.lithium.spring5demo.gitter.GitterProperties;
import io.lithium.spring5demo.gitter.model.Mate;
import io.lithium.spring5demo.mongo.MateReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
@EnableConfigurationProperties({GitterProperties.class, MongoProperties.class})
@EnableReactiveMongoRepositories
public class Spring5demoApplication {

    @Autowired
    private AnnotationConfigApplicationContext context;

    @Autowired
    private MateReactiveRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Spring5demoApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(GitterProperties props, MongoProperties mongoProperties) {
        return args -> {
            context.registerBean(Mate.class, () -> new Mate("Lithium", "Alex", true));
            Mate mate = context.getBean(Mate.class);
            System.out.println("Mate from context: " + mate.nickname);
            System.out.println("Gitter Room: " + props.getRoom());

            Flux<Mate> people = Flux.just(
                    new Mate("aliaksei-lithium", "Aliaksei"),
                    new Mate("IRus", "Ruslan"),
                    new Mate("bsiamionau", "Bahdan")
            );
            repository.deleteAll().thenMany(repository.save(people)).blockLast();
        };
    }
}
