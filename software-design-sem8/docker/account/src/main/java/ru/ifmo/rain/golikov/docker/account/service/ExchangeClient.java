package ru.ifmo.rain.golikov.docker.account.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.ifmo.rain.golikov.docker.account.http.ResponseParser;
import ru.ifmo.rain.golikov.docker.account.model.Stock;

@Service
public class ExchangeClient {
  private final RestTemplate restTemplate;
  private final ResponseParser parser = new ResponseParser();
  private final String HOST = "http://localhost:8080/exchange";

  public ExchangeClient(final RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public Stock getStock(final String company) {
    return parser.parseStock(restTemplate.getForObject(HOST + "/get-company?name={name}", String.class, company));
  }

  public void buyStocks(final String company, final long amount) {
    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("name", company);
    map.add("amount", amount);
    restTemplate.postForEntity(HOST + "/buy-stocks", new HttpEntity<>(map), String.class);
  }

  public void sellStocks(final String company, final long amount) {
    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("name", company);
    map.add("amount", amount);
    restTemplate.postForEntity(HOST + "/add-stocks", new HttpEntity<>(map), String.class);
  }
}
