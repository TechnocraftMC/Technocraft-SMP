package com.technocraft.server.listener.seasonmanager;

import com.technocraft.server.Main;
import com.technocraft.server.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class SMChat implements CommandExecutor, Listener {

    public static ArrayList<Player> toggledChat = new ArrayList<>();

    private Main main;

    public SMChat(Main main)
    {
        this.main = main;
        actionBar(main);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        Player player = (Player) commandSender;
        if (toggledChat.contains(player))
        {
            toggledChat.remove(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Chat.getSMPrefix() + " &fYou have &cdisabled " + Chat.getValueColor() + "Season Manager Chat" + Chat.getBodyColor() + "."));
        } else
        {
            toggledChat.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Chat.getSMPrefix() + " &fYou have &aenabled " + Chat.getValueColor() + "Season Manager Chat" + Chat.getBodyColor() + "."));
        }
        return false;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e)
    {
        if (e.getPlayer().hasPermission("group.seasonmanager-users"))
        {
            String message = "";
            boolean sendingToSM = false;
            if (e.getMessage().startsWith("!"))
            {
                sendingToSM = true;
                message = e.getMessage().substring(1);
            } else if (toggledChat.contains(e.getPlayer()))
            {
                sendingToSM = true;
                message = e.getMessage();
            }

            if (sendingToSM)
            {
                e.setCancelled(true);
                Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', Chat.getSMPrefix() + "&f&l " + e.getPlayer().getName() + " &b" + message), "group.seasonmanager-users");
            }

        }
    }

    private static void actionBar(Main main)
    {
        Bukkit.getScheduler().runTaskTimer(main, new Runnable() {
            @Override
            public void run()
            {
                for (Player p : toggledChat)
                {
                    for (int i = 0; i < 5; i++)
                    {
                        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                            @Override
                            public void run()
                            {
                                p.sendActionBar(ChatColor.translateAlternateColorCodes('&', "&c[Management] &6Season Manager Chat &8is enabled."));
                            }
                        }, i * 20L);
                    }
                }
            }
        }, 0L, 20L * 20);
    }
}
