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

/**
 * Сервис для работы с турами.
 * Предоставляет бизнес-логику для управления сущностями {@link Tour},
 * включая поиск, фильтрацию и сортировку туров.
 */
@Service
public class TourService {

    /**
     * Менеджер сущностей для выполнения критериальных запросов.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Репозиторий для доступа к данным туров.
     */
    private final TourRepository tourRepository;

    /**
     * Создает новый экземпляр сервиса туров с указанным репозиторием.
     *
     * @param tourRepository репозиторий для доступа к данным туров
     */
    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    /**
     * Получает список всех туров.
     *
     * @return список всех туров
     */
    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    /**
     * Находит тур по его идентификатору.
     *
     * @param id идентификатор тура
     * @return найденный тур или null, если тур не найден
     */
    public Tour findById(Long id) {
        return tourRepository.findById(id).orElse(null);
    }

    /**
     * Сохраняет тур в базе данных.
     * Может использоваться как для создания нового тура, так и для обновления существующего.
     *
     * @param tour тур для сохранения
     */
    public void save(Tour tour) {
        tourRepository.save(tour);
    }

    /**
     * Удаляет тур по его идентификатору.
     *
     * @param id идентификатор тура, который необходимо удалить
     */
    public void deleteById(Long id) {
        tourRepository.deleteById(id);
    }

    /**
     * Получает отфильтрованный и отсортированный список туров на основе указанных критериев.
     *
     * @param country   страна назначения (может быть null или пустой строкой для игнорирования фильтра)
     * @param type      тип тура (может быть null или пустой строкой для игнорирования фильтра)
     * @param startFrom минимальная дата начала тура (может быть null для игнорирования фильтра)
     * @param startTo   максимальная дата начала тура (может быть null для игнорирования фильтра)
     * @param priceFrom минимальная цена тура (может быть null для игнорирования фильтра)
     * @param priceTo   максимальная цена тура (может быть null для игнорирования фильтра)
     * @param sortField поле для сортировки результатов (например, "startDate", "startDate,desc", "price", "price,desc")
     * @return список туров, соответствующих указанным критериям, отсортированный согласно указанному полю
     */
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

        if (sortField != null) {
            if (sortField.startsWith("startDate")) {
                if (sortField.endsWith(",desc")) {
                    query.orderBy(cb.desc(tourRoot.get("startDate")));
                } else {
                    query.orderBy(cb.asc(tourRoot.get("startDate")));
                }
            } else if (sortField.startsWith("price")) {
                if (sortField.endsWith(",desc")) {
                    query.orderBy(cb.desc(tourRoot.get("price")));
                } else {
                    query.orderBy(cb.asc(tourRoot.get("price")));
                }
            }
        }

        return entityManager.createQuery(query).getResultList();
    }
}
