package repository;

import entity.DVD;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vlad on 18-Nov-17.
 */
public interface DVDRepository extends JpaRepository<DVD,Integer> {
}
