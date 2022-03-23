package com.city.management.exercise.service;

import com.city.management.exercise.api.dto.SuburbFilterRequest;
import com.city.management.exercise.model.Suburb;
import java.util.Collection;

public interface SuburbService {

    Collection<Suburb> findAll();

    Collection<Suburb> persistMultiple(Collection<Suburb> suburbs);

    Collection<Suburb> findByFilter(SuburbFilterRequest filter);
}
