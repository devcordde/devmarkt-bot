package club.devcord.devmarkt.discord.events;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ClickRequestButtonEvent extends ListenerAdapter {

  @Override
  public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
    if (!Objects.equals(event.getButton().getId(), "create_request")) return;

    event.getUser().openPrivateChannel().queue(channel -> {

    });
  }
}
