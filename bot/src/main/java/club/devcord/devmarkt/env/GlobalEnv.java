package club.devcord.devmarkt.env;

import club.devcord.devmarkt.DevmarktBot;

public class GlobalEnv {

  public static String env(String key) {
    var env = System.getenv(key);

    if (env == null) {
      DevmarktBot.getLogger().severe("Environment " + key + " not set.");
      System.exit(1);
    }

    return env;
  }

  public static String nullable(String key) {
    return System.getenv(key);
  }
}
