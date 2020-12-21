package com.technocraft.server.commands.global;

import com.technocraft.server.util.Chat;
import com.technocraft.server.util.Help;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GlobalCommandReceiver implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 0)
        {
            Help helpBuilder = new Help("/global <message>", "Send the server a mini announcement");
            String help = Chat.help("Mini Announce", new Help[]{ helpBuilder });
            sender.sendMessage(help);
        } else
        {
            StringBuilder sb = new StringBuilder();
            String message;
            for (int i = 0; i < args.length; i++)
            {
                sb.append(args[i]).append(" ");
            }
            message = sb.toString().trim();

            new GlobalCommand().global(message);
        }
        return false;
    }
}
