package com.restspring.restapi.controllers;

import com.restspring.restapi.services.BookService;
import com.restspring.restapi.vo.BookVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(value = "Books endpoints", description = "Books API description", tags = {"Books"})
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

   @Autowired
    private BookService service;

    @ApiOperation(value = "Find All Books")
    @GetMapping
    public List<BookVO> findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Find Book By Id")
    @GetMapping("/{id}")
    public BookVO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Create Book")
    @PostMapping()
    public BookVO create(@RequestBody BookVO bookVO) {
        return service.create(bookVO);
    }

    @ApiOperation(value = "Update Book")
    @PutMapping("/{id}")
    public BookVO update(@RequestBody BookVO bookVO) {
        return service.update(bookVO);
    }

    @ApiOperation(value = "Delete Book")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
