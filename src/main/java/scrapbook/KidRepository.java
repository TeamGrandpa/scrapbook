package scrapbook;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface KidRepository extends CrudRepository<Kid, Long> {

	Optional<Kid> findByName(String name);

}
