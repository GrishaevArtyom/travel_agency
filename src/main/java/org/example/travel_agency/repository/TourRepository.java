package org.example.travel_agency.repository;

import org.example.travel_agency.model.Tour;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    @Query("SELECT t FROM Tour t " +
            "WHERE (:country IS NULL OR t.country ILIKE %:country%) " +
            "AND (:type IS NULL OR t.type = :type) " +
            "AND (:startFrom IS NULL OR t.startDate >= :startFrom) " +
            "AND (:startTo IS NULL OR t.startDate <= :startTo) " +
            "AND (:priceFrom IS NULL OR t.price >= :priceFrom) " +
            "AND (:priceTo IS NULL OR t.price <= :priceTo)")
    List<Tour> findFiltered(
            @Param("country") String country,
            @Param("type") String type,
            @Param("startFrom") LocalDate startFrom,
            @Param("startTo") LocalDate startTo,
            @Param("priceFrom") BigDecimal priceFrom,
            @Param("priceTo") BigDecimal priceTo,
            Sort sort
    );

}
