package club.devcord.devmarkt.discord.commands;

import club.devcord.devmarkt.discord.commands.devmarkt.First;
import de.chojo.jdautil.interactions.slash.Argument;
import de.chojo.jdautil.interactions.slash.Slash;
import de.chojo.jdautil.interactions.slash.SubCommand;
import de.chojo.jdautil.interactions.slash.provider.SlashCommand;
import net.dv8tion.jda.api.Permission;

public class Devmarkt extends SlashCommand {

  public Devmarkt() {
    super(Slash.of("devmarkt", "Devmarkt configuration command.")
        .unlocalized()
        .withPermission(Permission.MODERATE_MEMBERS)
        .guildOnly()
        .subCommand(SubCommand.of(
            "first",
            "Creates first devmarkt post.")
                .handler(new First())
                .argument(Argument.channel("devmarkt-channel", "Channel where requests").asRequired())));
  }
}
