package club.devcord.devmarkt.query;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

public record Query(
    String query,
    Map<String, String> variables
) {

  public Query(String query) {
    this(query, Collections.emptyMap());
  }

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
}
