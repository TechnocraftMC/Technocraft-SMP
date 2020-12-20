package com.technocraft.server.commands;

import com.technocraft.server.Main;
import com.technocraft.server.commands.announce.AnnounceCommand;
import com.technocraft.server.commands.global.GlobalCommand;
import com.technocraft.server.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Updater implements CommandExecutor {
    private Main main;

    public Updater(Main main)
    {
        this.main = main;
    }

    private boolean isShutdown = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        //Default restart time = 10 seconds
        int secondsUntilRestartSet = 10;

        //Make sure command is valid
        if (args.length > 0)
        {
            String timeArg = args[0];

            try
            {
                if (timeArg.endsWith("m"))
                {
                    timeArg = timeArg.substring(0, timeArg.length() - 1);
                    secondsUntilRestartSet = Integer.parseInt(timeArg) * 60;
                } else
                {
                    secondsUntilRestartSet = Integer.parseInt(timeArg);

                }
            } catch (NumberFormatException e)
            {
                sender.sendMessage(Chat.error("Network Restarter", "The first argument must be a number."));
                return false;
            }
        }

        if (args.length > 1)
        {
            if (args[1].equalsIgnoreCase("-shutdown"))
            {
                isShutdown = true;
            }
        }

        final int secondsUntilRestart = secondsUntilRestartSet;
        sender.sendMessage(Chat.message("Network Restarter", "You have initiated a network restart."));


        String timeDisplay;
        String unitsDisplay;
        if (secondsUntilRestart >= 60)
        {
            timeDisplay = secondsToString(Math.toIntExact(secondsUntilRestart));
            unitsDisplay = "MINUTES";
        } else
        {
            timeDisplay = String.valueOf(secondsUntilRestart);
            unitsDisplay = "SECONDS";
        }

        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.getWorld().playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F, 1F);
        }


        new AnnounceCommand().announce(getAction(true, true), getAction(false, false) + " IN " + ChatColor.GOLD + timeDisplay + " " + unitsDisplay + ChatColor.WHITE + "...");
        new GlobalCommand().global(getAction(false, false) + " IN " + timeDisplay + " " + unitsDisplay + "!");

        GlobalCommand global = new GlobalCommand(Sound.ENTITY_ENDER_DRAGON_GROWL);



        //1 minute warning

        if (secondsUntilRestart - 60 > 10)
        {
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {
                    actionBar(1, "MINUTE");
                }
            }, toTicks(secondsUntilRestart - 60));
        }

        //2 minute warning
        if (secondsUntilRestart - 120 > 10)
        {
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {
                    actionBar(2, "MINUTE");
                }
            }, toTicks(secondsUntilRestart - 120));
        }


        //5 minute warning

        if (secondsUntilRestart - 300 > 10)
        {
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {
                    actionBar(5, "MINUTES");
                }
            }, toTicks(secondsUntilRestart - 300));
        }


        //10 minute warning

        if (secondsUntilRestart - 600 > 10)
        {
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {
                    actionBar(10, "MINUTES");
                }
            }, toTicks(secondsUntilRestart - 600));
        }


        //6 seconds before
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run()
            {
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, .5F, 1F);
                }
            }
        }, toTicks(secondsUntilRestart - 6));

        //5 seconds before
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run()
            {
                global.global("Server " + getActionLowerCase() + " in 10 seconds...");
            }
        }, toTicks(secondsUntilRestart - 5));


        //RESTART
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run()
            {
                global.global("Server " + getActionLowerCase() + " in 5 seconds...");
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
                            if (isShutdown)
                            {
                                p.kickPlayer("The server has closed. Check Discord for updates.");
                            } else
                            {
                                p.kickPlayer("The server is restarting... Please rejoin in about 30 seconds!");
                            }
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
        }, toTicks(secondsUntilRestart + 8));
        return false;
    }

    private Long toTicks(int seconds)
    {
        return seconds * 20L;
    }

    private String secondsToString(int pTime)
    {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }

    private String getAction(boolean getColorCodes, boolean doBold)
    {
        String action;
        if (getColorCodes)
        {
            action = "" + ChatColor.DARK_PURPLE + (doBold ? ChatColor.BOLD : "") + "SERVER RESTART";
            if (isShutdown)
            {
                action = "" + ChatColor.RED  + (doBold ? ChatColor.BOLD : "") + "SERVER SHUTDOWN";
            }
        } else
        {
            action = "" + "SERVER RESTART";
            if (isShutdown)
            {
                action = "" + "SERVER SHUTDOWN";
            }
        }
        return action;
    }

    private String getActionLowerCase()
    {
        if (isShutdown)
        {
            return "shutdown";
        }
        return "restart";
    }

    private void actionBar(int time, String timeunit)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            for (int i = 0; i < 10; i++)
            {
                Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                    @Override
                    public void run()
                    {
                        p.sendActionBar(getAction(true, true) + ChatColor.WHITE + " IN " + ChatColor.GOLD + "" + ChatColor.BOLD + time + " " + ChatColor.WHITE + "" + ChatColor.BOLD + timeunit);
                    }
                }, toTicks(i));
            }
        }
        Chat.pingAll();
    }

}