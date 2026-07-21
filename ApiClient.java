package com.folhear.desktop.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Cliente HTTP genérico para conversar com a API REST do backend Folhear
 * (Spring Boot). Usa java.net.http.HttpClient (nativo do JDK) + Jackson.
 */
public class ApiClient {

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(8))
            .build();

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /** Ex: http://localhost:8080/api */
    private String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    /** Verifica se a API está no ar (usa /actuator/health, se disponível). */
    public boolean ping() {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/usuarios"))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            return resp.statusCode() < 500;
        } catch (Exception e) {
            return false;
        }
    }

    public <T> T get(String path, TypeReference<T> type) throws ApiException {
        return execute(request(path, "GET", null), type);
    }

    public <T> T get(String path, Class<T> type) throws ApiException {
        return execute(request(path, "GET", null), type);
    }

    public <T> T post(String path, Object body, Class<T> type) throws ApiException {
        return execute(request(path, "POST", body), type);
    }

    public <T> T put(String path, Object body, Class<T> type) throws ApiException {
        return execute(request(path, "PUT", body), type);
    }

    public <T> T patch(String path, Class<T> type) throws ApiException {
        return execute(request(path, "PATCH", null), type);
    }

    public void delete(String path) throws ApiException {
        execute(request(path, "DELETE", null), Void.class);
    }

    // ---------------------------------------------------------------

    private HttpRequest request(String path, String method, Object body) {
        try {
            HttpRequest.Builder b = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json");

            HttpRequest.BodyPublisher publisher = body == null
                    ? HttpRequest.BodyPublishers.noBody()
                    : HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body));

            b.method(method, publisher);
            return b.build();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao montar requisição: " + e.getMessage(), e);
        }
    }

    private <T> T execute(HttpRequest req, TypeReference<T> type) throws ApiException {
        String body = send(req);
        if (body == null || body.isBlank()) return null;
        try {
            return mapper.readValue(body, type);
        } catch (IOException e) {
            throw new ApiException("Erro ao interpretar resposta da API: " + e.getMessage());
        }
    }

    private <T> T execute(HttpRequest req, Class<T> type) throws ApiException {
        String body = send(req);
        if (type == Void.class || body == null || body.isBlank()) return null;
        try {
            return mapper.readValue(body, type);
        } catch (IOException e) {
            throw new ApiException("Erro ao interpretar resposta da API: " + e.getMessage());
        }
    }

    private String send(HttpRequest req) throws ApiException {
        try {
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
                return resp.body();
            }
            String msg = "HTTP " + resp.statusCode() + " em " + req.uri();
            if (resp.body() != null && !resp.body().isBlank()) {
                msg += "\n" + resp.body();
            }
            throw new ApiException(msg);
        } catch (ApiException e) {
            throw e;
        } catch (IOException e) {
            throw new ApiException("Não foi possível conectar à API em " + baseUrl
                    + "\nVerifique se o backend Spring Boot está rodando.\n(" + e.getMessage() + ")");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("Requisição interrompida.");
        }
    }
}
