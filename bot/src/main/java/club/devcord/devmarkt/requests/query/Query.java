package club.devcord.devmarkt.requests.query;

import club.devcord.devmarkt.env.GlobalEnv;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public record Query(
    String query,
    Map<String, String> variables
) {

  public static Query getQuery(String key, Map<String, String> variables) throws URISyntaxException, IOException {
    var queryResourcePath = Query.class.getResource("graphql." + key);
    if (queryResourcePath == null) {
      throw new IllegalArgumentException("No query found under query: [%s]".formatted(key));
    }

    var graphqlQuery = Files.readString(
        Paths.get(queryResourcePath.toURI())
    );

    return new Query(graphqlQuery, variables);
  }

  public static Query getQuery(String key) throws URISyntaxException, IOException {
    return getQuery(key, Collections.emptyMap());
  }

  public CompletableFuture<QueryResponse> executeQuery(HttpClient client, ObjectMapper mapper) throws JsonProcessingException, URISyntaxException {
    var queryBody = mapper.writeValueAsString(this);

    var request = HttpRequest.newBuilder()
        .header("Authorization", "Self %s".formatted(GlobalEnv.envOrThrow("DEVMARKT_BOT_JWT_TOKEN")))
        .uri(new URI(GlobalEnv.envOrThrow("BACKEND_URL") + "/graphql"))
        .POST(HttpRequest.BodyPublishers.ofString(queryBody))
        .build();

    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenApply(body -> {
          try {
            return mapper.readValue(body, QueryResponse.class);
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public CompletableFuture<QueryResponse> executeQuery(HttpClient client) throws URISyntaxException, JsonProcessingException {
    return executeQuery(client, new ObjectMapper());
  }

  public <T> CompletableFuture<T> executeQueryMapped(HttpClient client, ObjectMapper mapper, Class<T> type) {
    try {
      return executeQuery(client, mapper).thenApply(response -> response.mapData(mapper, type));
    } catch (JsonProcessingException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> CompletableFuture<T> executeQueryMapped(HttpClient client, Class<T> type) {
    return executeQueryMapped(client, new ObjectMapper(), type);
  }

}
