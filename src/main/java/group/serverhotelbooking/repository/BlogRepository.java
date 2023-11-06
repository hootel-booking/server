package group.serverhotelbooking.repository;

import group.serverhotelbooking.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogRepository extends JpaRepository<BlogEntity,Integer> {

}
