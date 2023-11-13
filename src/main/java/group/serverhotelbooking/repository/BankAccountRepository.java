package group.serverhotelbooking.repository;

import group.serverhotelbooking.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Integer> {
    @Query("SELECT b FROM BankAccountEntity b WHERE b.idUser = :idUser AND " +
            "b.accountNumber = :accountNumber")
    BankAccountEntity checkAccountByIdUser(
            @Param("idUser") int idUser,
            @Param("accountNumber") String accountNumber
    );
}
