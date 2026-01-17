package pe.jsaire.springtravel.services.abstract_service;


import org.springframework.data.domain.Page;
import pe.jsaire.springtravel.utils.enums.SortType;

import java.math.BigDecimal;
import java.util.Set;

public interface ICatalgoService<RS> {


    String FIELD_BY_SORT = "price";

    Page<RS> getAll(Integer page, Integer size, SortType sortType);

    Set<RS> readLessPrice(BigDecimal price);

    Set<RS> readBetweenPrices(BigDecimal min, BigDecimal max);
}
