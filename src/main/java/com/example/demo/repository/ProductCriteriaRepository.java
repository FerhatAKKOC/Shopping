package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.model.ProductPage;
import com.example.demo.model.ProductSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/*
 * This bean used for Pagination, Searching and Filtering for Product entitu
 * */
@Repository
public class ProductCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ProductCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Product> findAllWithFilters(ProductPage productPage, ProductSearchCriteria productSearchCriteria) {

        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Predicate predicate = getPredicate(productSearchCriteria, productRoot);
        criteriaQuery.where(predicate);
        setOrder(productPage, criteriaQuery, productRoot);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(productPage.getPage() * productPage.getSize());
        typedQuery.setMaxResults(productPage.getSize());

        Pageable pageable = getPageable(productPage);

        long productsCount = getProductsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, productsCount);
    }

    private Predicate getPredicate(ProductSearchCriteria productSearchCriteria, Root<Product> productRoot) {

        List<Predicate> predicates = new ArrayList<>();


        if (Objects.nonNull(productSearchCriteria.getName())) {  // "name" search and filter criteria
            predicates.add(
                    criteriaBuilder.like(productRoot.get("name"),
                            "%" + productSearchCriteria.getName() + "%")
            );
        }

        if (Objects.nonNull(productSearchCriteria.getPrice())) {  // "price" search and filter criteria
            predicates.add(
                    criteriaBuilder.ge(productRoot.get("price"), productSearchCriteria.getPrice())
            );
        }

        if (Objects.nonNull(productSearchCriteria.getDiscount())) {  // "discount" search and filter criteria
            predicates.add(
                    criteriaBuilder.ge(productRoot.get("discount"), productSearchCriteria.getDiscount())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(ProductPage productPage,
                          CriteriaQuery<Product> criteriaQuery, Root<Product> productRoot) {
        if (productPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get(productPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(productRoot.get(productPage.getSortBy())));
        }
    }

    private Pageable getPageable(ProductPage productPage) {
        Sort sort = Sort.by(productPage.getSortDirection(), productPage.getSortBy());
        return PageRequest.of(productPage.getPage(), productPage.getSize(), sort);
    }

    private long getProductsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }


}
