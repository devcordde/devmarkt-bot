package club.devcord.devmarkt;

import club.devcord.devmarkt.discord.commands.Devmarkt;
import club.devcord.devmarkt.env.GlobalEnv;
import club.devcord.devmarkt.requests.health.HealthCheck;
import de.chojo.jdautil.interactions.dispatching.InteractionHub;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.logging.Logger;

public class DevmarktBot {

  private static final Logger logger = Logger.getLogger("club.devcord.devmarkt.DevmarktBot");

  public static void main(String[] args) throws URISyntaxException {
    var backendUrl = GlobalEnv.env("BACKEND_URL");

    var httpClient = HttpClient.newHttpClient();

    if (!HealthCheck.isUp(httpClient, backendUrl).join()) {
      logger.severe("Backend is not reachable.");
      System.exit(1);
    }

    var botToken = GlobalEnv.env("BOT_TOKEN");

    var shardManager = DefaultShardManagerBuilder
        .createDefault(botToken)
        .enableIntents(
            GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES
        ).build();

    InteractionHub.builder(shardManager)
        .testMode(Boolean.parseBoolean(GlobalEnv.nullable("DEVMARKT_DEV")))
        .cleanGuildCommands(Boolean.parseBoolean(GlobalEnv.nullable("DEVMARKT_DEV")))
        .withCommands(new Devmarkt())
        .build();
  }

  public static Logger getLogger() {
    return logger;
  }
}