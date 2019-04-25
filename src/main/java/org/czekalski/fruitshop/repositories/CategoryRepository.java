package org.czekalski.fruitshop.repositories;

import org.czekalski.fruitshop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
