package com.technocraft.server.listener;

import com.connorlinfoot.titleapi.TitleAPI;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.earth2me.essentials.Essentials;
import com.technocraft.server.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PlayerFirstJoin implements Listener, CommandExecutor {
    private Main main;

    public PlayerFirstJoin(Main mainInst)
    {
        main = mainInst;
    }

    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent e)
    {

        if (!e.getPlayer().hasPlayedBefore())
        {
            welcomeMessage(e.getPlayer());
        }
    }

    public void welcomeMessage(Player player)
    {
        Essentials essentials = Essentials.getPlugin(Essentials.class);
        essentials.getUser(player.getName()).setGodModeEnabled(true);
        player.setWalkSpeed(0);
        {
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {
                    TitleAPI.sendTitle(player, 0, 100, 0, ChatColor.AQUA + "Welcome to ", ChatColor.GOLD + "" + ChatColor.BOLD + "Technocraft");
                    player.sendMessage(ChatColor.GOLD + "----- Technocraft Welcome -----");
                    player.sendMessage(ChatColor.BLUE + "> " + ChatColor.WHITE + "Thank you for joining " + ChatColor.AQUA + "" + ChatColor.BOLD + "Technocraft's EcoSMP" + ChatColor.WHITE + "!");
                }
            }, toTicks(1));
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {
                    TitleAPI.sendTitle(player, 0, 100, 0, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "This season", "Technocraft is doing an " + ChatColor.AQUA + "Economy SMP");
                    player.sendMessage(ChatColor.BLUE + "> " + ChatColor.WHITE + "This season Technocraft is doing an " + ChatColor.AQUA + "" + ChatColor.BOLD + "Economy SMP" + ChatColor.WHITE + "!");
                }
            }, toTicks(6));
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {
                    TitleAPI.sendTitle(player, 0, 100, 0, ChatColor.GOLD + "How to play?", "Do " + ChatColor.GOLD + "/help" + ChatColor.WHITE + " for a list of economy commands!");
                    player.sendMessage(ChatColor.BLUE + "> " + ChatColor.WHITE + "Do " + ChatColor.GOLD + "/help" + ChatColor.WHITE + " for a list of economy-related commands! Then," + ChatColor.AQUA + " build your base" + ChatColor.WHITE + "," + ChatColor.AQUA + " gain resources" + ChatColor.WHITE + "," + ChatColor.AQUA + " build amazing things" + ChatColor.WHITE + ", and" + ChatColor.AQUA + " have fun" + ChatColor.WHITE + "!");
                    player.sendMessage(ChatColor.GOLD + "----- Thanks for reading! -----");
                }
            }, toTicks(11));
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {
                    essentials.getUser(player.getName()).setGodModeEnabled(false);
                    player.setWalkSpeed(.2f);
                }
            }, toTicks(13));
        }
    }

    private Long toTicks(int seconds)
    {
        return seconds * 20L;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (args.length == 0)
        {
            welcomeMessage((Player) sender);
            return true;
        }

        if (args.length == 1)
        {
            Player target = null;
            try
            {
                target = Bukkit.getPlayer(args[0]);
            } catch (Exception e)
            {
                sender.sendMessage("Error");
                return false;
            }
            welcomeMessage(target);
        }

        return true;
    }
}
