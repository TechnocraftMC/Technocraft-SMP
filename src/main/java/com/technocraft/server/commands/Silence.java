package com.technocraft.server.commands;

import net.ess3.api.events.PrivateMessagePreSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Silence implements CommandExecutor, Listener {
    public static boolean isSilenced;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        boolean silentMode = false;
        if (args[args.length - 1].endsWith("-s"))
        {
            silentMode = true;
        }
        if (!isSilenced)
        {
            if (!silentMode)
            {
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.MAGIC + "_" + ChatColor.RED + " The chat has been silenced.");
            }
            isSilenced = true;
            ChatBypass.bypassPlayers.clear();
        } else
        {
            if (!silentMode)
            {
                Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "" + ChatColor.MAGIC + "_" + ChatColor.GREEN + " The chat has been unsilenced.");
            }
            isSilenced = false;
        }

        if (!silentMode)
        {
            for (Player p : Bukkit.getOnlinePlayers())
            {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2F, 1F);
            }
        }
        return true;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e)
    {
         if (Silence.isSilenced)
         {
             if (!(ChatBypass.bypassPlayers.contains(e.getPlayer())))
             {
                 e.setCancelled(true);
                 e.getPlayer().sendMessage(ChatColor.RED + "The chat is silenced.");
             }
         }
    }

    @EventHandler
    public void onPrivateMessagePreSend(PrivateMessagePreSendEvent e)
    {
        if (Silence.isSilenced)
        {
            if (!(ChatBypass.bypassPlayers.contains(Bukkit.getPlayer(e.getSender().getName())) && (Bukkit.getPlayer(e.getSender().getName()).isOp())))
            {
                e.setCancelled(true);
                e.getSender().sendMessage(ChatColor.RED + "The chat is silenced.");
            }
        }
    }
}






