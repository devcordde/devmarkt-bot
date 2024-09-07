package club.devcord.devmarkt.discord.commands.devmarkt;

import club.devcord.devmarkt.discord.request.CreateRequestInstruction;
import de.chojo.jdautil.interactions.slash.structure.handler.SlashHandler;
import de.chojo.jdautil.wrapper.EventContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class First implements SlashHandler {

  @Override
  public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
    var guildChannelUnion = event.getOption("devmarkt-channel", OptionMapping::getAsChannel);

    if (!(guildChannelUnion instanceof TextChannel textChannel)) {
      event.reply("Please enter a text channel.").setEphemeral(true).queue();
      return;
    }

    textChannel.sendMessageEmbeds(new EmbedBuilder().addField("Devmarkt \uD83D\uDCB8", "This is the first post. Read the embed below for further information", true).setColor(Color.GREEN.darker().darker()).build()).queue();

    CreateRequestInstruction.sendEmbed(textChannel, null)
        .thenCompose((a) -> event.reply("First post created").setEphemeral(true).submit())
        .join();
  }
}