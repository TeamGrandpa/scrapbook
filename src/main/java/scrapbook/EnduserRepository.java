package scrapbook;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface EnduserRepository extends CrudRepository<Enduser, Long> {

	Optional<Enduser> findByUserName(String userName);

}
