package scrapbook;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface HeartRepository extends CrudRepository<Heart, Long> {

	Optional<Heart> findByEnduserAndImage(Enduser enduser, Image image);
	void deleteByEnduserAndImage(Enduser enduser, Image image);
	
}
