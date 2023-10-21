package group.serverhotelbooking.repository;

import group.serverhotelbooking.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail (String email);
}

