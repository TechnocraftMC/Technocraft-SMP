package com.technocraft.server.commands;

import com.technocraft.server.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (args.length == 0)
        {
            Player target = (Player) sender;
            updateGamemode(target, null, (target.getGameMode().equals(GameMode.CREATIVE) ? GameMode.SURVIVAL : GameMode.CREATIVE));
            return true;

        }

        if (args.length == 1)
        {
            Player target = Bukkit.getPlayer(args[0]);
            if (target.isOnline())
            {
                if (sender instanceof Player)
                {
                    updateGamemode(target, (Player) sender, (target.getGameMode().equals(GameMode.CREATIVE) ? GameMode.SURVIVAL : GameMode.CREATIVE));
                } else
                {
                    updateGamemode(target, null, (target.getGameMode().equals(GameMode.CREATIVE) ? GameMode.SURVIVAL : GameMode.CREATIVE));
                    System.out.println(target.getName() + " has been updated to " + (target.getGameMode().equals(GameMode.CREATIVE) ? "creative" : "survival") + ".");
                }
            } else
            {
                sender.sendMessage(Chat.error("Gamemode", "That user is not online or does not exist."));
            }
        }
        return false;
    }

    private void updateGamemode(Player player, Player admin, GameMode gamemode)
    {
        if (gamemode.equals(GameMode.CREATIVE))
        {
            player.setGameMode(GameMode.CREATIVE);
            player.setVelocity(new Vector(0, .7, 0));
            player.setFlying(true);
            message(player, admin, gamemode);
            return;
        }
        if (gamemode.equals(GameMode.SURVIVAL))
        {
            player.setGameMode(GameMode.SURVIVAL);
            message(player, admin, gamemode);
            return;
        }
    }

    private void message(Player player, Player admin, GameMode gamemode)
    {
        player.sendMessage(Chat.message("Gamemode", "Your gamemode has been updated to" + Chat.value((gamemode.equals(GameMode.CREATIVE) ? "creative" : "survival"), ".")));
        if (admin != null)
        {
            admin.sendMessage(Chat.valueOnly("Chat", player.getName(), "'s gamemode has been updated to " + Chat.value((gamemode.equals(GameMode.CREATIVE) ? "creative" : "survival"), ".")));
        }
    }
}
