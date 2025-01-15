package org.wildcodeschool.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myblog.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
}
