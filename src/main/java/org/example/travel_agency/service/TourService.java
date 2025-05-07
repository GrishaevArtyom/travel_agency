package org.example.travel_agency.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.travel_agency.model.Tour;
import org.example.travel_agency.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TourService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TourRepository tourRepository;

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

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tour> query = cb.createQuery(Tour.class);
        Root<Tour> tourRoot = query.from(Tour.class);

        List<Predicate> predicates = new ArrayList<>();

        if (country != null && !country.isEmpty()) {
            predicates.add(cb.like(cb.lower(tourRoot.get("country")), "%" + country.toLowerCase() + "%"));
        }

        if (type != null && !type.isEmpty()) {
            predicates.add(cb.equal(tourRoot.get("type"), type));
        }

        if (startFrom != null) {
            predicates.add(cb.greaterThanOrEqualTo(tourRoot.get("startDate"), startFrom));
        }

        if (startTo != null) {
            predicates.add(cb.lessThanOrEqualTo(tourRoot.get("startDate"), startTo));
        }

        if (priceFrom != null) {
            predicates.add(cb.greaterThanOrEqualTo(tourRoot.get("price"), priceFrom));
        }

        if (priceTo != null) {
            predicates.add(cb.lessThanOrEqualTo(tourRoot.get("price"), priceTo));
        }

        query.select(tourRoot).where(predicates.toArray(new Predicate[0]));

        // Сортировка
        if (sortField != null) {
            if ("startDate".equals(sortField)) {
                query.orderBy(cb.asc(tourRoot.get("startDate")));
            } else if ("price".equals(sortField)) {
                query.orderBy(cb.asc(tourRoot.get("price")));
            }
        }

        return entityManager.createQuery(query).getResultList();
    }
}