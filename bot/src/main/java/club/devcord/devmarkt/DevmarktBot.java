package club.devcord.devmarkt;

import club.devcord.devmarkt.discord.commands.Devmarkt;
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

  private static final Logger log = LoggerFactory.getLogger(DevmarktBot.class);

  public static void main(String[] args) throws URISyntaxException {
    var backendUrl = GlobalEnv.envOrThrow("BACKEND_URL");

    var httpClient = HttpClient.newHttpClient();

    if (!HealthCheck.isUp(httpClient, backendUrl).join()) {
      log.error("Backend is not reachable.");
      System.exit(1);
    }

    var botToken = GlobalEnv.envOrThrow("BOT_TOKEN");

    var shardManager = DefaultShardManagerBuilder
        .createDefault(botToken)
        .enableIntents(
            GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES
        )
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
}
