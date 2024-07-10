package club.devcord.devmarkt.env;

import club.devcord.devmarkt.DevmarktBot;
import org.slf4j.Logger;

import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;

public class GlobalEnv {
  private static final Logger log = getLogger(GlobalEnv.class);

  public static String envOrThrow(String key) {
    var env = System.getenv(key);

    if (env == null) {
      log.error("Environment {} not set.", key);
      System.exit(1);
    }

    return env;
  }

  public static String envOrNull(String key) {
    return System.getenv(key);
  }

  public static <T> T parseEnvOrNull(String key, Function<String, T> parse) {
    var env = envOrNull(key);
    if (env == null) return null;
    return parse.apply(env);
  }

  public static <T> T parseEnvOrDefault(String key, Function<String, T> parse, T def) {
    var env = envOrNull(key);
    if (env == null) return def;
    return parse.apply(env);
  }
}
