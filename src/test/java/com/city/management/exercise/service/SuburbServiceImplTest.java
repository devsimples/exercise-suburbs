package com.city.management.exercise.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.city.management.exercise.api.dto.SuburbFilterRequest;
import com.city.management.exercise.model.Suburb;
import com.city.management.exercise.repository.SuburbRepository;
import com.city.management.exercise.service.impl.SuburbServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SuburbServiceImplTest {

    @Mock
    private SuburbRepository repository;

    @InjectMocks
    private SuburbServiceImpl service;

    private static final Suburb SUBURB_ALBERT = new Suburb(null, "Albert Park", 1000L);
    private static final Suburb SUBURB_BALWYN = new Suburb(null, "Balwyn", 2000L);
    private static final Suburb SUBURB_CAROLINE = new Suburb(null, "Caroline Springs", 3000L);

    @BeforeEach
    void before() {
        when(repository.findAll()).thenReturn(List.of(SUBURB_ALBERT, SUBURB_BALWYN, SUBURB_CAROLINE));
    }

    @Test
    void findByFilter_should_filter_when_filter_informed() {

        var filter = SuburbFilterRequest.builder().build();
        assertThat(service.findByFilter(filter), containsInAnyOrder(SUBURB_ALBERT, SUBURB_BALWYN, SUBURB_CAROLINE));

        filter = SuburbFilterRequest.builder().postCodeStart(1500L).build();
        assertThat(service.findByFilter(filter), containsInAnyOrder(SUBURB_BALWYN, SUBURB_CAROLINE));

        filter = SuburbFilterRequest.builder().postCodeStart(5000L).build();
        assertThat(service.findByFilter(filter), is(empty()));

        filter = SuburbFilterRequest.builder().postCodeStart(1500L).postCodeEnd(2000L).build();
        assertThat(service.findByFilter(filter), containsInAnyOrder(SUBURB_BALWYN));
    }
}
