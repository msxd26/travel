package pe.jsaire.springtravel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import pe.jsaire.springtravel.mappers.FlyMapper;
import pe.jsaire.springtravel.models.dto.response.FlyResponse;
import pe.jsaire.springtravel.repositories.FlyRepository;
import pe.jsaire.springtravel.services.abstract_service.IFlyService;
import pe.jsaire.springtravel.utils.enums.SortType;

import java.math.BigDecimal;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlyServiceImpl implements IFlyService {

    private final FlyRepository repository;
    private final FlyMapper mapper;
    private final WebClient webClient;

    @Override
    public Page<FlyResponse> getAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return repository.findAll(pageRequest).map(mapper::toResponse);
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return mapper.toSetResponse(repository.findByPriceLessThan(price));
    }

    @Override
    public Set<FlyResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return mapper.toSetResponse(repository.findByPriceBetween(min, max));
    }

    @Override
    public Set<FlyResponse> readByOriginDistination(String origin, String destination) {
        return mapper.toSetResponse(repository.findByOriginNameAndDestinyName(origin, destination));
    }
}
