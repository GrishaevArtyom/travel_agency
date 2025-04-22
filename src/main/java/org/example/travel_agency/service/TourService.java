package org.example.travel_agency.service;

import org.example.travel_agency.model.Tour;
import org.example.travel_agency.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TourService {

    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    public Tour findById(Long id) {
        return tourRepository.findById(id).orElse(null);
    }

    public void save(Tour tour) {
        tourRepository.save(tour);
    }

    public void deleteById(Long id) {
        tourRepository.deleteById(id);
    }

    public List<Tour> getFilteredTours(String country, String type,
                                       LocalDate startFrom, LocalDate startTo,
                                       BigDecimal priceFrom, BigDecimal priceTo,
                                       String sortField) {
        Sort sort = Sort.unsorted();
        if ("price".equals(sortField)) {
            sort = Sort.by("price");
        } else if ("startDate".equals(sortField)) {
            sort = Sort.by("startDate");
        }
        return tourRepository.findFiltered(
                country == null || country.isEmpty() ? null : country,
                type == null || type.isEmpty() ? null : type,
                startFrom,
                startTo,
                priceFrom,
                priceTo,
                sort
        );
    }
}
