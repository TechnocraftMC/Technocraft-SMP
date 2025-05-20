package com.technocraft.server.commands.announce;

import com.technocraft.server.util.Chat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;

public class AnnounceCommand {

    final Title.Times times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(2));

    public void announce(@Nullable String titleText, @NotNull String subtitleText)
    {
        //Set title to Announcement
        if (titleText == null)
        {
            titleText = ChatColor.GOLD + "Announcement";
        }

        //Set text of title and subtitle
        titleText = ChatColor.translateAlternateColorCodes('&', titleText);
        subtitleText = ChatColor.translateAlternateColorCodes('&', subtitleText);

        //Built the title method
        Title title  = Title.title(Component.text(ChatColor.GOLD + titleText), Component.text(subtitleText), times);

        //Execute title to all players
        Bukkit.getServer().showTitle(title);
        Chat.pingAll();
    }

}
