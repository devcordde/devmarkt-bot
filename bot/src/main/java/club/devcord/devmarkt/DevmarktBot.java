package club.devcord.devmarkt;

import club.devcord.devmarkt.discord.commands.Devmarkt;
import club.devcord.devmarkt.discord.events.CreateCreationMessage;
import club.devcord.devmarkt.env.GlobalEnv;
import club.devcord.devmarkt.requests.health.HealthCheck;
import de.chojo.jdautil.interactions.dispatching.InteractionHub;
import de.chojo.jdautil.localization.ILocalizer;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.jetbrains.annotations.NotNull;

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
        )
        .setEventManagerProvider(value -> new AnnotatedEventManager())
        .addEventListeners(
            new CreateCreationMessage())
        .build();

    var devMode = Boolean.parseBoolean(System.getenv("DEVMARKT_DEV"));

    InteractionHub.builder(shardManager)
        .testMode(devMode)
        .cleanGuildCommands(!devMode)
        .withCommands(new Devmarkt())
        .build();

  }

  public static Logger getLogger() {
    return logger;
  }
}