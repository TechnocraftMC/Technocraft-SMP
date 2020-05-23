package com.technocraft.server.commands;

import com.connorlinfoot.titleapi.TitleAPI;
import com.technocraft.server.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class AnnounceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 0)
        {
            String help = Chat.help("Server Announce", new String[]{"/announce <message>"}, new String[]{"Send the server a message"});
            sender.sendMessage(help);
            Chat.help("Server Announce", new String[]{"/announce <message>"}, new String[]{"Send the server a message"});
            return false;
        }


        StringBuilder sb = new StringBuilder();
        String message;
        for (int i = 0; i < args.length; i++)
        {
            sb.append(args[i]).append(" ");
        }
        message = sb.toString().trim();
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&k&l_&r") + ChatColor.BLUE + "" + ChatColor.BOLD + "ANNOUNCEMENT: " + ChatColor.AQUA + ChatColor.translateAlternateColorCodes('&', message));
        announceMessage(null, ChatColor.translateAlternateColorCodes('&', message), Bukkit.getOnlinePlayers());
        return false;
    }

    public static void announceMessage(String title, String subtitle, Collection<? extends Player> players)
    {
        if (title == null)
        {
            title = ChatColor.GOLD + "Announcement";
        }
        for (Player player : players)
        {
            TitleAPI.sendTitle(player, 20, 100, 40, ChatColor.GOLD + title, subtitle);
        }
        Chat.pingAll();

    }
}
