package com.restspring.personcrud.services;

import com.restspring.personcrud.adapters.DozerConverter;
import com.restspring.personcrud.exception.ResourceNotFoundException;
import com.restspring.personcrud.models.Person;
import com.restspring.personcrud.repositories.PersonRepository;
import com.restspring.personcrud.vo.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    public List<PersonVO> findAll() {
        return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
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

    public void delete(Long id) {
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        repository.delete(entity);
    }
}
