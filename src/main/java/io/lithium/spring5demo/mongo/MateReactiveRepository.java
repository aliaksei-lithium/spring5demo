
package io.lithium.spring5demo.mongo;

import io.lithium.spring5demo.gitter.model.Mate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MateReactiveRepository extends ReactiveCrudRepository<Mate, String> {
}
