package com.restspring.restapi.services;

import com.restspring.restapi.adapters.DozerConverter;
import com.restspring.restapi.exception.ResourceNotFoundException;
import com.restspring.restapi.models.Person;
import com.restspring.restapi.repositories.PersonRepository;
import com.restspring.restapi.vo.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    private PersonVO convertToPersonVO(Person entity) {
        return DozerConverter.parseObject(entity, PersonVO.class);
    }

    public Page<PersonVO> findAll(Pageable pageable) {
        var page = repository.findAll(pageable);
        return page.map(this:: convertToPersonVO);
    }

    public Page<PersonVO> findPersonByName(String firstName, Pageable pageable) {
        var page = repository.findPersonByName(firstName, pageable);
        return page.map(this:: convertToPersonVO);
    }

    public PersonVO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
    }

    public PersonVO create(PersonVO personVo) {
        var entity = DozerConverter.parseObject(personVo, Person.class);
        var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public PersonVO update(PersonVO personVo) {
        var entity = repository.findById(personVo.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        entity.setFirstName(personVo.getFirstName());
        entity.setLastName(personVo.getLastName());
        entity.setAddress(personVo.getAddress());
        entity.setGender(personVo.getGender());
        var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    @Transactional
    public PersonVO disableStatusPerson(Long id) {
       repository.disableStatusPerson(id);
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
    }

    public void delete(Long id) {
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        repository.delete(entity);
    }
}
