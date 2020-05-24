package com.technocraft.server.commands;

import com.technocraft.server.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChatBypass implements CommandExecutor {
    public static ArrayList<Player> bypassPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        Player targetPlayer;
        if (args.length == 0)
        {
            if (commandSender instanceof Player)
            {
                targetPlayer = (Player) commandSender;
            } else
            {
                commandSender.sendMessage("You must be a player.");
                return false;
            }
        } else
        {

            try
            {
                targetPlayer = Bukkit.getPlayer(args[0]);
            } catch (Exception e)
            {
                commandSender.sendMessage("Please enter a valid player");
                return false;
            }
        }

        if (bypassPlayers.contains(targetPlayer))
        {
            bypassPlayers.remove(targetPlayer);
            commandSender.sendMessage(Chat.valueOnly("Chat", targetPlayer.getName(), "no longer has a chat bypass."));
            targetPlayer.sendMessage(Chat.message("Chat", "You no longer have a chat bypass during chat silence."));
        } else
        {
            bypassPlayers.add(targetPlayer);
            commandSender.sendMessage(Chat.valueOnly("Chat", targetPlayer.getName(), "now has a chat bypass."));
            targetPlayer.sendMessage(Chat.message("Chat", "You now have a chat bypass during chat silence."));
        }
        return true;
    }
}
