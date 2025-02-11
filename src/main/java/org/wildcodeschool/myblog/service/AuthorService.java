package org.wildcodeschool.myblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myblog.dto.AuthorDTO;
import org.wildcodeschool.myblog.mapper.AuthorMapper;
import org.wildcodeschool.myblog.model.Author;
import org.wildcodeschool.myblog.repository.AuthorRepository;

@Service
public class AuthorService {
    
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::convertToDTO)
                .orElse(null);
    }

    public List<Author> getAuthorsByName(String searchTerms) {
        return authorRepository.findByFirstnameContainingOrLastnameContaining(searchTerms, searchTerms);
    }

    public AuthorDTO createAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.convertToDTO(savedAuthor);
    }

    public AuthorDTO updateAuthor(Long id, Author authorDetails) {
        return authorRepository.findById(id)
                .map(author -> {
                    author.setFirstname(authorDetails.getFirstname());
                    author.setLastname(authorDetails.getLastname());
                    Author updatedAuthor = authorRepository.save(author);
                    return authorMapper.convertToDTO(updatedAuthor);
                })
                .orElse(null);
    }

    public boolean deleteAuthor(Long id) {
        return authorRepository.findById(id)
                .map(author -> {
                    authorRepository.delete(author);
                    return true;
                })
                .orElse(false);
    }
}