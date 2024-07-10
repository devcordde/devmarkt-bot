package club.devcord.devmarkt.env;

import club.devcord.devmarkt.DevmarktBot;

import java.util.function.Function;

public class GlobalEnv {

  public static String envOrThrow(String key) {
    var env = System.getenv(key);

    if (env == null) {
      DevmarktBot.getLogger().severe("Environment " + key + " not set.");
      System.exit(1);
    }

    return env;
  }

  public static String envOrNull(String key) {
    return System.getenv(key);
  }

  public static <T> T envOrDefault(String key, Function<T, String> parse) {

  }
}
