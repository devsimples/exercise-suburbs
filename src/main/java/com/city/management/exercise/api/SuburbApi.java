package com.city.management.exercise.api;


import com.city.management.exercise.api.dto.SuburbFilterRequest;
import com.city.management.exercise.model.Suburb;
import com.city.management.exercise.service.SuburbService;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/suburb")
@AllArgsConstructor
public class SuburbApi {

    private final SuburbService service;

    @GetMapping
    public Collection<Suburb> getSuburb() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Collection<Suburb> persistMultiple(@RequestBody List<Suburb> suburbs) {
        return service.persistMultiple(suburbs);
    }

    @GetMapping("/filter")
    public Collection<Suburb> findByFilter(SuburbFilterRequest filter) {
        return service.findByFilter(filter);
    }

}
