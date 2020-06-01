package com.technocraft.server.commands;

import com.technocraft.server.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetAdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (!(sender instanceof Player))
        {
            System.out.println("You must be a player to run this command.");
            return false;
        }

        Player player = (Player) sender;
        if (args.length != 1)
        {
            player.sendMessage(Chat.help("Get Admin", new String[]{"/getadmin <bool state>"}, new String[]{"Toggle the state of Season Manager's permissions. state = true, false"}));
            return false;
        }

        if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("on"))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp group seasonmanager-users parent addtemp seasonmanager-perms 1h replace");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp sync");
            for (Player seasonmanager : Bukkit.getOnlinePlayers())
            {
                if (seasonmanager.hasPermission("group.seasonmanager-users"))
                {
                    seasonmanager.sendMessage(Chat.getminiAnn(Chat.getminiAnnColor() + "You now have" + Chat.valueAnn("Technocraft Season Manager elevated permissions", Chat.getminiAnnColor() + "(enabled by" + Chat.value(player.getName(), ")"))));
                    seasonmanager.sendMessage(ChatColor.RED + "Abuse of these permission will cause a demotion from your position. These permission are only to be used in an emergency or other season manager-related tasks. Do /help for a list of elevated commands.");
                    System.out.println("WARNING: " + player.getName() + " has enabled Elevated Season Manager permissions.");
                }
            }
            return true;
        } else if (args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("off"))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp group seasonmanager-users parent removetemp seasonmanager-perms");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp sync");
            for (Player seasonmanager : Bukkit.getOnlinePlayers())
            {
                if (seasonmanager.hasPermission("group.seasonmanager-users"))
                {
                    seasonmanager.sendMessage(Chat.getminiAnn(Chat.getminiAnnColor() + "Your season manager permissions have" + Chat.valueAnn("expired", ".")));
                    System.out.println("Elevated Season Manager permissions have expired.");
                }
            }
            return true;
        } else {
            player.sendMessage(Chat.help("Get Admin", new String[]{"/getadmin <bool state>"}, new String[]{"Toggle the state of Season Manager's permissions. state = true, false"}));
            return false;
        }
    }
}
