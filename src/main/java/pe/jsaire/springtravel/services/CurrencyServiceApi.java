package pe.jsaire.springtravel.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.jsaire.springtravel.models.dto.response.CurrencyResponse;

import java.util.Currency;

@Component
public class CurrencyServiceApi {

    private final WebClient currencyWebClient;

    @Value("${api.base-currency}")
    private String baseCurrency;

    private final String CURRENCY_PATH = "/exchangerates_data/latest";

    public CurrencyServiceApi(WebClient currencyWebClient) {
        this.currencyWebClient = currencyWebClient;
    }

    public CurrencyResponse getCurrency(Currency currency) {
        return this.currencyWebClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(CURRENCY_PATH)
                                .queryParam("base", baseCurrency)
                                .queryParam("currency", currency.getCurrencyCode())
                                .build())
                .retrieve()
                .bodyToMono(CurrencyResponse.class)
                .block();
    }

}

