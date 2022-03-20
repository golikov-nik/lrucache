package ru.ifmo.rain.golikov.docker.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.rain.golikov.docker.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
