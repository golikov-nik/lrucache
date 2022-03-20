package ru.ifmo.rain.golikov.docker.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.rain.golikov.docker.exchange.model.Company;

import java.util.*;

@Repository
public interface ExchangeRepository extends JpaRepository<Company, Long> {
  Optional<Company> findByName(final String name);
}
