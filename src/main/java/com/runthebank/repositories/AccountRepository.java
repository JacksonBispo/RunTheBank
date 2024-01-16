package com.runthebank.repositories;

import com.runthebank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query(value = "SELECT  ACC.ID,ACC.BALANCE, ACC.STATUS, ACC.CUSTOMER_ID, ACC.AGENCY FROM ACCOUNT ACC LEFT JOIN CUSTOMER CUS ON CUS.customer_id = ACC.CUSTOMER_ID WHERE CUS.document =:document", nativeQuery = true)
    Optional<Account>   findByCustomerDocument(@Param("document") String document);
}
