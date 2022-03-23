package com.city.management.exercise.service.impl;

import com.city.management.exercise.api.dto.SuburbFilterRequest;
import com.city.management.exercise.model.Suburb;
import com.city.management.exercise.repository.SuburbRepository;
import com.city.management.exercise.service.SuburbService;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SuburbServiceImpl implements SuburbService {

    private final SuburbRepository repository;

    @Override
    public Collection<Suburb> findAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Suburb> persistMultiple(Collection<Suburb> suburbs) {
        return repository.saveAll(suburbs);
    }

    @Override
    public Collection<Suburb> findByFilter(SuburbFilterRequest filter) {
        // This could be managed by SQL query but as requested by the exercise a stream need be used.
        return findAll().stream()
                .filter( s -> filter.getPostCodeStart() == null || s.getPostCode() >= filter.getPostCodeStart())
                .filter( s -> filter.getPostCodeEnd() == null || s.getPostCode() <= filter.getPostCodeEnd())
                .collect(Collectors.toList());
    }
}

