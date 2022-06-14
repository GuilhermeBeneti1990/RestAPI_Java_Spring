package com.restspring.restapi.controllers;

import com.restspring.restapi.services.BookService;
import com.restspring.restapi.vo.BookVO;
import com.restspring.restapi.vo.PersonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private PagedResourcesAssembler assembler;

    @ApiOperation(value = "Find All Books")
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler assembler) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

        Page<BookVO> books = service.findAll(pageable);

        PagedResources<?> resources = assembler.toResource(books);

        return new ResponseEntity<>(resources, HttpStatus.OK);
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
