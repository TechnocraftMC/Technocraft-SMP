package com.technocraft.server.commands;

import com.technocraft.server.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;


public class Updater implements CommandExecutor {
    private Main main;

    public Updater(Main main)
    {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Long secondsUntilRestartSet = 10L;

        if (args.length == 1)
        {
            try
            {
                secondsUntilRestartSet = Long.parseLong(args[0]);
            } catch (NumberFormatException e)
            {
                sender.sendMessage("Argument 1 must be an number");
                return false;
            }
        }

        final Long secondsUntilRestart = secondsUntilRestartSet;
        sender.sendMessage("You have initiated a network restart.");

        String secondsDisplay = null;
        String unitsDisplay = null;
        if (secondsUntilRestart >= 60)
        {
            secondsDisplay = secondsToString(Math.toIntExact(secondsUntilRestart));
                unitsDisplay = "MINUTES";
        } else
        {
            secondsDisplay = String.valueOf(secondsUntilRestart);
            unitsDisplay = "SECONDS";
        }

        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.getWorld().playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F, 1F);
        }
        AnnounceCommand.announceMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "SERVER RESTART", "SERVER RESTART IN " + secondsUntilRestart + " " + unitsDisplay + "...", Bukkit.getOnlinePlayers());
        GlobalCommand.chatAnnounceMessage("" + ChatColor.BOLD + "SERVER RESTART IN " + secondsDisplay + " " + unitsDisplay + "!", new ArrayList<>(Bukkit.getOnlinePlayers()), null);


        //x-5 seconds in
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run()
            {
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, .5F, 1F);
                }
            }
        }, toTicks(secondsUntilRestart - 6L));

        //x seconds in
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run()
            {
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    GlobalCommand.chatAnnounceMessage("Server restart in 10 seconds...", new ArrayList<>(Collections.singletonList(p)), null);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1F, 1F);
                }
            }
        }, toTicks(secondsUntilRestart - 5L));


        //x seconds in
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run()
            {
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    GlobalCommand.chatAnnounceMessage("Server restart in 5 seconds...", new ArrayList<>(Collections.singletonList(p)), null);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1F, 1F);
                }
            }
        }, toTicks(secondsUntilRestart));

        //5 seconds in
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run()
            {
                for (World world : Bukkit.getWorlds())
                {
                    for (Player p : world.getPlayers())
                    {
                        if (!Bukkit.getWhitelistedPlayers().contains(p))
                        {
                            p.kickPlayer("The server is restarting... Please rejoin in about 30 seconds!");
                        }
                    }
                    world.save();
                }
                Bukkit.savePlayers();
            }
        }, toTicks(secondsUntilRestart + 5));


        //8 seconds in

        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run()
            {
                Bukkit.shutdown();
            }
        }, toTicks(secondsUntilRestart + 8L));
        return false;
    }

    private Long toTicks(Long seconds)
    {
        return seconds * 20L;
    }

    private String secondsToString(int pTime)
    {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }
}