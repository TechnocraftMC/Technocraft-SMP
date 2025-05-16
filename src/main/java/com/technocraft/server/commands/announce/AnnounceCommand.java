package com.technocraft.server.commands.announce;

import com.technocraft.server.util.Chat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class AnnounceCommand {

    public void announce(String title, @NotNull String subtitle)
    {


        if (title == null)
        {
            title = ChatColor.GOLD + "Announcement";
        }

        title = ChatColor.translateAlternateColorCodes('&', title);
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);

        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.showTitle(Title.title(Component.text(ChatColor.GOLD + title), Component.text(subtitle), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(2))));
        }

        Chat.pingAll();

    }

}
