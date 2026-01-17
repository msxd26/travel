package pe.jsaire.springtravel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.springtravel.mappers.HotelMapper;
import pe.jsaire.springtravel.models.dto.response.HotelResponse;
import pe.jsaire.springtravel.repositories.HotelRepository;
import pe.jsaire.springtravel.services.abstract_service.IHotelService;
import pe.jsaire.springtravel.utils.enums.SortType;

import java.math.BigDecimal;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HotelServiceImpl implements IHotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;


    @Override
    public Page<HotelResponse> getAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return repository
                .findAll(pageRequest)
                .map(mapper::toResponse);
    }

    @Override
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return mapper.toSetResponse(repository.findByPriceLessThan(price));
    }

    @Override
    public Set<HotelResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return mapper.toSetResponse(repository.findByPriceBetween(min, max));
    }


    @Override
    public Set<HotelResponse> readByRating(Integer rating) {
        return mapper.toSetResponse(repository.findByRating(rating));
    }
}
