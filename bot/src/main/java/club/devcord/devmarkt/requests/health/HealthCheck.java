package club.devcord.devmarkt.requests.health;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HealthCheck {

  public static CompletableFuture<Boolean> isUp(HttpClient client, String url) throws URISyntaxException {
    return client.sendAsync(HttpRequest
            .newBuilder()
            .GET()
            .uri(new URI(url + "/health"))
            .build(),
        HttpResponse.BodyHandlers.ofString()
    ).handleAsync((response, _err) -> response.body().contains("UP"));
  }
}
