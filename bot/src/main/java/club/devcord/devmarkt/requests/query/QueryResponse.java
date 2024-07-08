package club.devcord.devmarkt.requests.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.Collections;

public record QueryResponse(
    @Nullable String data,
    @Nullable String extensions,
    @Nullable String errors
) {

  public <T> T mapData(ObjectMapper mapper, Class<T> type) {
    try {
      return mapper.readValue(this.data, type);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean isSuccessful() {
    return errors == null && (data != null && extensions != null);
  }

  public boolean isError() {
    return !isSuccessful();
  }

  record User(
      String name,
      String password
  ) {
  }

  public static void main(String[] args) throws URISyntaxException, IOException {
    var userResponse = new QueryResponse("user record in json", "kp was", null);

    var client = HttpClient.newHttpClient();
    var mapper = new ObjectMapper();

    var user = Query.getQuery("user", Collections.emptyMap()).executeQueryMapped(client, new ObjectMapper(), User.class);
  }
}
