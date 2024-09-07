package club.devcord.devmarkt.requests.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public record QueryResponse(
    JsonNode data,
    String errors
) {

  public <T> T mapData(ObjectMapper mapper, Class<T> type) {
    try {
      return mapper.treeToValue(data, type);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean isSuccessful() {
    return errors == null && (data != null);
  }

  public boolean isError() {
    return !isSuccessful();
  }
}
