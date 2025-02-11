package org.wildcodeschool.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myblog.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByFirstnameContainingOrLastnameContaining(String searchTerms, String searchTerms2);
}

