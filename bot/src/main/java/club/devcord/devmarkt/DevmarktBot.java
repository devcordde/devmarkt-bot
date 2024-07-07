package club.devcord.devmarkt;

import club.devcord.devmarkt.requests.health.HealthCheck;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.logging.Logger;

public class DevmarktBot {

  private static final Logger logger = Logger.getLogger("club.devcord.devmarkt.DevmarktBot");

  public static void main(String[] args) throws URISyntaxException {
    var backendUrl = System.getenv("BACKEND_URL");

    if (backendUrl == null) {
      logger.severe("Environment BACKEND_URL not set.");
      System.exit(1);
    }

    var httpClient = HttpClient.newHttpClient();

    if (!HealthCheck.isUp(httpClient, backendUrl).join()) {
      logger.severe("Backend is not reachable.");
      System.exit(1);
    }

    var botToken = System.getenv("BOT_TOKEN");

    if (botToken == null) {
      logger.severe("Environment BOT_TOKEN not set.");
      System.exit(1);
    }

    var jda = JDABuilder
        .createDefault(botToken)
        .enableIntents(Arrays.asList(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.DIRECT_MESSAGES,
            GatewayIntent.MESSAGE_CONTENT
        ))
        .build();
  }

  public static Logger getLogger() {
    return logger;
  }
}