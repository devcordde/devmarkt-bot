package club.devcord.devmarkt;

import club.devcord.devmarkt.discord.commands.Devmarkt;
import club.devcord.devmarkt.discord.events.ClickRequestButtonEvent;
import club.devcord.devmarkt.env.GlobalEnv;
import club.devcord.devmarkt.requests.health.HealthCheck;
import de.chojo.jdautil.interactions.dispatching.InteractionHub;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.http.HttpClient;

public class DevmarktBot {

  private static final String backendHost = GlobalEnv.envOrThrow("DEVMARKT_BACKEND_HOST");
  private static final String backendPort = GlobalEnv.envOrThrow("DEVMARKT_BACKEND_PORT");

  private static final Logger log = LoggerFactory.getLogger(DevmarktBot.class);

  public static void main(String[] args) throws URISyntaxException {
    var httpClient = HttpClient.newHttpClient();

    if (!HealthCheck.isUp(httpClient, backendUrl).join()) {
      log.error("Backend is not reachable.");
      System.exit(1);
    }

    var botToken = GlobalEnv.envOrThrow("DEVMARKT_BOT_TOKEN");

    var shardManager = DefaultShardManagerBuilder
        .createDefault(botToken)
        .enableIntents(
            GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES
        )
        .addEventListeners(new ClickRequestButtonEvent())
        .build();

    var devMode = GlobalEnv.parseEnvOrDefault("DEVMARKT_DEV", Boolean::parseBoolean, false);

    InteractionHub.builder(shardManager)
        .testMode(devMode)
        .cleanGuildCommands(!devMode)
        .withCommands(new Devmarkt())
        .build();
  }

  public static Logger getLogger() {
    return log;
  }

  public static String backendUrl = "http://%s:%s".formatted(backendHost, backendPort);
  public static String backendJwtToken = GlobalEnv.envOrThrow("DEVMARKT_BOT_JWT_TOKEN");
}
