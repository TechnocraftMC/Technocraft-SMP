package com.technocraft.server.commands.announce;

import com.connorlinfoot.titleapi.TitleAPI;
import com.technocraft.server.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AnnounceCommand {

    public void announce(String title, @NotNull  String subtitle)
    {



        if (title == null)
        {
            title = ChatColor.GOLD + "Announcement";
        }

        title = ChatColor.translateAlternateColorCodes('&', title);
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);

        for (Player player : Bukkit.getOnlinePlayers())
        {
            TitleAPI.sendTitle(player, 20, 100, 40, ChatColor.GOLD + title, subtitle);
        }

        Chat.pingAll();

    }

}
