package com.restspring.personcrud.controllers;

import com.restspring.personcrud.services.PersonService;
import com.restspring.personcrud.vo.PersonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(value = "Person endpoints", description = "Person API description", tags = {"Person"})
@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

   @Autowired
    private PersonService service;

   @ApiOperation(value = "Find All People")
    @GetMapping
    public List<PersonVO> findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Find Person By Id")
    @GetMapping("/{id}")
    public PersonVO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Create Person")
    @PostMapping()
    public PersonVO create(@RequestBody PersonVO PersonVO) {
        return service.create(PersonVO);
    }

    @ApiOperation(value = "Update Person")
    @PutMapping("/{id}")
    public PersonVO update(@RequestBody PersonVO PersonVO) {
        return service.update(PersonVO);
    }

    @ApiOperation(value = "Delete Person")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
