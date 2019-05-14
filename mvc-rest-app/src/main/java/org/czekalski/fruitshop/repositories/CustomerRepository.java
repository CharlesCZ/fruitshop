package org.czekalski.fruitshop.repositories;

import org.czekalski.fruitshop.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
