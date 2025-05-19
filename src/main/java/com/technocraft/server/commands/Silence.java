package com.technocraft.server.commands;

import com.technocraft.server.util.Chat;
import net.ess3.api.events.PrivateMessagePreSendEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

import javax.inject.Named;

public class Silence implements CommandExecutor, Listener {
    public static boolean isSilenced;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        boolean silentMode = false;

        if (args.length > 0)
        {
            if (args[args.length - 1].endsWith("-s"))
            {
                silentMode = true;
            }
        }
        if (!isSilenced)
        {
            if (!silentMode)
            {
                Component silencedMessage =
                        Component.text("F", NamedTextColor.DARK_RED, TextDecoration.BOLD, TextDecoration.OBFUSCATED)
                        .append(Component.text(" The chat has been silence.", NamedTextColor.RED, TextDecoration.BOLD));
                Bukkit.getServer().sendMessage(silencedMessage);
                Bukkit.getServer().sendActionBar(silencedMessage);
                Chat.pingAll();
            }
            isSilenced = true;
            ChatBypass.bypassPlayers.clear();
        } else
        {
            if (!silentMode)
            {
                Component unsilencedMessage =
                        Component.text("F", NamedTextColor.DARK_GREEN, TextDecoration.BOLD, TextDecoration.OBFUSCATED)
                                .append(Component.text(" The chat has been unsilenced.", NamedTextColor.GREEN, TextDecoration.BOLD));
                Bukkit.getServer().sendMessage(unsilencedMessage);
                Bukkit.getServer().sendActionBar(unsilencedMessage);
                Chat.pingAll();
            }
            isSilenced = false;
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
                if (!e.getPlayer().isOp() || !e.getPlayer().hasPermission("group.admin-users"))
                {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "The chat is silenced.");
                }
            }
        }
    }

    @EventHandler
    public void onPrivateMessagePreSend(PrivateMessagePreSendEvent e)
    {
        if (Silence.isSilenced)
        {
            if (!(ChatBypass.bypassPlayers.contains(Bukkit.getPlayer(e.getSender().getName()))))
            {
                if (!Bukkit.getPlayer(e.getSender().getName()).isOp() || !Bukkit.getPlayer(e.getSender().getName()).hasPermission("group.admin-users"))
                {
                    e.setCancelled(true);
                    e.getSender().sendMessage(ChatColor.RED + "The chat is silenced.");
                }
            }
        }
    }
}






