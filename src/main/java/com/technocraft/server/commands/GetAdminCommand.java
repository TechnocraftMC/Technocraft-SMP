package com.technocraft.server.commands;

import com.technocraft.server.Main;
import com.technocraft.server.util.Chat;
import com.technocraft.server.util.DiscordUtils;
import com.technocraft.server.util.Help;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class GetAdminCommand implements CommandExecutor {

    private Main main;

    public GetAdminCommand(Main main)
    {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
/*        if (!(sender instanceof Player))
        {
            System.out.println("You must be a player to run this command.");
            return false;
        }

        Player player = (Player) sender;*/


        Help helpList = new Help("/getadmin <bool state> <String reason>", "Toggle the state of Season Manager's permissions. state = true, false");
        String help = Chat.help("Get Admin", new Help[]{helpList});

        if (args.length < 1)
        {
            sender.sendMessage(help);
            return false;
        }

        String reason = "";


        if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("on"))
        {
            if (args.length < 2)
            {
                sender.sendMessage(help);
                return false;
            }

            for (int i = 1; i < args.length; i++)
            {
                reason += args[i] + " ";
            }
            reason = reason.trim();
        }



        DiscordUtils utils = new DiscordUtils();
        TextChannel adminLogs = main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getAdminLogsChannelID());

        if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("on"))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp group seasonmanager-users parent addtemp seasonmanager-perms 1h replace");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp sync");
            for (Player seasonmanager : Bukkit.getOnlinePlayers())
            {
                if (seasonmanager.hasPermission("group.seasonmanager-users"))
                {
                    seasonmanager.sendMessage(Chat.getminiAnn(Chat.getminiAnnColor() + "You now have" + Chat.valueAnn("Technocraft Season Manager elevated permissions", Chat.getminiAnnColor() + "(enabled by" + Chat.value(sender.getName(), "). Reason: " + Chat.value(reason, ".")))));
                    seasonmanager.sendMessage(ChatColor.RED + "Abuse of these permission will cause a demotion from your position. These permission are only to be used in an emergency or other season manager-related tasks. Do /help for a list of elevated commands.");
                }
            }
            System.out.println("WARNING: " + sender.getName() + " has enabled Elevated Season Manager permissions.");
            adminLogs.sendMessage("@here **" + sender.getName() + "** has enabled **Elevated Season Manager Permissions**.").queue();
            adminLogs.sendMessage("Reason: " + reason).queue();
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
                }
            }
            System.out.println("Elevated Season Manager permissions have expired.");
            adminLogs.sendMessage("**Elevated Season Manager Permissions** have expired.").queue();
            return true;
        } else
        {
            sender.sendMessage(help);
            return false;
        }
    }
}
