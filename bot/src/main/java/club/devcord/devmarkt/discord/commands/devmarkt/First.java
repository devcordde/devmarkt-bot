package club.devcord.devmarkt.discord.commands.devmarkt;

import de.chojo.jdautil.interactions.slash.structure.handler.SlashHandler;
import de.chojo.jdautil.wrapper.EventContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class First implements SlashHandler {

  @Override
  public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
    var guildChannelUnion = event.getOption("devmarkt-channel", OptionMapping::getAsChannel);

    if (!(guildChannelUnion instanceof TextChannel textChannel)) {
      event.reply("Please enter a text channel.").setEphemeral(true).queue();
      return;
    }

    textChannel.sendMessageEmbeds(new EmbedBuilder()
            .addField("Devmarkt \uD83D\uDCB8", "This is the first post. Read the embed below for further information", true)
            .setColor(Color.GREEN.darker().darker())
            .build())
        .queue();

    var embedMessageField = "Press the button below to create a request.";

    var embed = new EmbedBuilder()
        .setColor(Color.ORANGE)
        .addField("Devmarkt \uD83D\uDCB8", embedMessageField, true)
        .build();

    MessageHistory.getHistoryFromBeginning(textChannel).queue(history -> {
      history.getRetrievedHistory().getLast().delete().queue(success -> {

        textChannel.sendMessageEmbeds(embed)
            .addActionRow(Button.success("create_request", "\uD83D\uDCB8"))
            .queue();

        event.reply("First post created!").setEphemeral(true).queue();
      });
    });
  }
}