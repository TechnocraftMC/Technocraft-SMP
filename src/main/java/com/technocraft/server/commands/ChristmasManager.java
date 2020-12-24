package com.technocraft.server.commands;

import com.technocraft.server.Main;
import com.technocraft.server.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class ChristmasManager implements CommandExecutor {

    private static ArrayList<Player> playersWithChristmas = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        Player player;
        if (commandSender instanceof Player)
        {
            player = (Player) commandSender;
        } else
        {
            return true;
        }

        if (playersWithChristmas.contains(player))
        {
            playersWithChristmas.remove(player);
            player.sendMessage(Chat.message("Holiday", "Holiday perks have been " + ChatColor.RED + "disabled" + Chat.getBodyColor() + "."));
        } else
        {
            playersWithChristmas.add(player);
            player.sendMessage(Chat.message("Holiday", "Holiday perks have been " + ChatColor.GREEN + "enabled" + Chat.getBodyColor() + "."));
        }

        return false;
    }

    public static void effects(Main main)
    {
        Bukkit.getScheduler().runTaskTimer(main, new Runnable() {
            @Override
            public void run()
            {
                for (Player player : playersWithChristmas)
                {
                    if (player.isOnline())
                    {
                        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 10 * 20, 0, true, false).withIcon(true);
                        player.addPotionEffect(speed);
                        PotionEffect haste = new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, 1, true, false).withIcon(true);
                        player.addPotionEffect(haste);
                    }
                }
            }
        }, 0L, 20 * 9L);
    }
}
