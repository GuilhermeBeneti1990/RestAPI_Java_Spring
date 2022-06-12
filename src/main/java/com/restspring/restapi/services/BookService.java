package com.restspring.restapi.services;

import com.restspring.restapi.adapters.DozerConverter;
import com.restspring.restapi.exception.ResourceNotFoundException;
import com.restspring.restapi.models.Book;
import com.restspring.restapi.repositories.BookRepository;
import com.restspring.restapi.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public List<BookVO> findAll() {
        return DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
    }

    public BookVO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        return DozerConverter.parseObject(repository.save(entity), BookVO.class);
    }

    public BookVO create(BookVO bookVO) {
        var entity = DozerConverter.parseObject(bookVO, Book.class);
        var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
        return vo;
    }

    public BookVO update(BookVO bookVO) {
        var entity = repository.findById(bookVO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        entity.setAuthor(bookVO.getAuthor());
        entity.setTitle(bookVO.getTitle());
        entity.setLaunchDate(bookVO.getLaunchDate());
        entity.setPrice(bookVO.getPrice());
        var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
        return vo;
    }

    public void delete(Long id) {
        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        repository.delete(entity);
    }
}
