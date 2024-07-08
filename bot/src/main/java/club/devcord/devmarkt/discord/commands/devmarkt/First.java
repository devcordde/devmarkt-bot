package club.devcord.devmarkt.discord.commands.devmarkt;

import club.devcord.devmarkt.env.GlobalEnv;
import de.chojo.jdautil.interactions.slash.structure.handler.SlashHandler;
import de.chojo.jdautil.wrapper.EventContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class First implements SlashHandler {

  private final long DEVMARKT_CHANNEL_ID = Long.parseLong(GlobalEnv.env("DEVMARKT_CHANNEL"));

  @Override
  public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
    var guild = event.getGuild();

    if (guild == null) {
      event.reply("Only use this command in a guild.").setEphemeral(true).queue();
      return;
    }

    var devmarktChannel = event.getGuild().getTextChannelById(DEVMARKT_CHANNEL_ID);

    if (devmarktChannel == null) {
      event.reply("Devmarkt channel with given id not found.").setEphemeral(true).queue();
      return;
    }

    event.replyEmbeds(new EmbedBuilder()
            .addField("Devmarkt \uD83D\uDCB8", "This is the first post. Interact with the button below to create a request.", true)
            .setColor(Color.GREEN.darker().darker())
            .build()).
        setEphemeral(false).queue();
  }
}
