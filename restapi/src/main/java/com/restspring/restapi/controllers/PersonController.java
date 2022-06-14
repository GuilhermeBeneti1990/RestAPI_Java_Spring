package com.restspring.restapi.controllers;

import com.restspring.restapi.services.PersonService;
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
@Api(value = "Person endpoints", description = "Person API description", tags = {"Person"})
@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @Autowired
    private PagedResourcesAssembler assembler;

    @ApiOperation(value = "Find All People")
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

        Page<PersonVO> persons = service.findAll(pageable);

        PagedResources<?> resources = assembler.toResource(persons);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation(value = "Find All People By Part of Firstname")
    @GetMapping(value = "/findPersonByName/{firstName}")
    public ResponseEntity<?> findPersonByName(
            @PathVariable("firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

        Page<PersonVO> persons = service.findPersonByName(firstName, pageable);

        PagedResources<?> resources = assembler.toResource(persons);

        return new ResponseEntity<>(resources, HttpStatus.OK);
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

    @ApiOperation(value = "Disable a specific status person by Id")
    @PatchMapping("/{id}")
    public PersonVO disableStatusPerson(@PathVariable("id") Long id) {
        return service.disableStatusPerson(id);
    }

    @ApiOperation(value = "Delete Person")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
