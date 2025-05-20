package com.technocraft.server.listener;


import com.technocraft.server.Main;
import com.technocraft.server.util.Chat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.Duration;

public class FirstJoinEvent implements Listener, CommandExecutor {

    final String dInvite = "GkYJ3vEkKS";
    final String dLink = "https://discord.gg/" + dInvite;

    private Main main;

    public FirstJoinEvent(Main mainInst) {
        main = mainInst;
    }

    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) {
            welcomeMessage(e.getPlayer());
        }
    }

    public void welcomeMessage(Player player) {
        Component message = Component.text(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "_" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + " WELCOME " + ChatColor.AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + " TO THE SERVER! " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "_");
        Bukkit.getServer().sendMessage(message);
        Bukkit.getServer().sendActionBar(message);
        for(Player p : Bukkit.getOnlinePlayers())
        {
            p.playSound(p.getLocation(), Sound.BLOCK_BELL_USE, 2F, 1F);
        }

        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run() {
                player.showTitle(Title.title(Component.text(ChatColor.AQUA + "" + ChatColor.BOLD + "Welcome to "), Component.text(ChatColor.GOLD + "" + ChatColor.BOLD + "Technocraft"), Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(3), Duration.ofSeconds(0))));
                player.sendMessage(ChatColor.GOLD + "----- Technocraft Welcome -----");
                player.sendMessage(ChatColor.BLUE + "> " + ChatColor.WHITE + "Thank you for joining " + ChatColor.AQUA + "" + ChatColor.BOLD + "Technocraft" + ChatColor.WHITE + "!");
            }
        }, toTicks(3));
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run() {
                player.showTitle(Title.title(Component.space(), Component.text(ChatColor.GOLD + "" + ChatColor.BOLD + "Join the Discord ..."), Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(3), Duration.ofSeconds(2))));
                player.sendMessage(Component.text(ChatColor.BLUE + "> " + ChatColor.WHITE + "Join the Discord to get important updates about the server: " + ChatColor.GOLD + "" + ChatColor.UNDERLINE + dLink + ChatColor.WHITE + ".").clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, dLink)));
            }
        }, toTicks(8));
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run() {
                player.showTitle(Title.title(Component.space(), Component.text(ChatColor.GOLD + "" + ChatColor.BOLD + "... type " + ChatColor.AQUA + "" + ChatColor.BOLD + "/rules" + ChatColor.GOLD + "" + ChatColor.BOLD + " to read the rules ..."), Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(3), Duration.ofSeconds(2))));
                player.sendMessage(Component.text(ChatColor.BLUE + "> " + ChatColor.WHITE + "Type " + ChatColor.GOLD + "/rules" + ChatColor.WHITE + " to read the rules").clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/rules")));
            }
        }, toTicks(13));
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run() {
                player.showTitle(Title.title(Component.space(), Component.text(ChatColor.AQUA + "" + ChatColor.BOLD + "... and invite your friends to the server!"), Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(3), Duration.ofSeconds(2))));
                player.sendMessage(ChatColor.BLUE + "> " + ChatColor.WHITE + "Contact " + ChatColor.GOLD + "@anand.p" + ChatColor.WHITE + " on Discord for any questions! Feel free to invite any of your friends to the server.");
                player.sendMessage(ChatColor.GOLD + "----- Thanks for reading! -----");
            }
        }, toTicks(18));
    }

    private Long toTicks(int seconds) {
        return seconds * 20L;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            welcomeMessage((Player) sender);
            return true;
        }

        if (args.length == 1) {
            Player target = null;
            try {
                target = Bukkit.getPlayer(args[0]);
            } catch (Exception e) {
                sender.sendMessage("Error");
                return false;
            }
            welcomeMessage(target);
        }

        return true;
    }
}