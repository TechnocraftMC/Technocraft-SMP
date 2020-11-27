package com.technocraft.server.commands.global;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class GlobalCommand {

    private Sound sound;
    private Collection<? extends Player> players;

    public GlobalCommand()
    {
        this.sound = Sound.BLOCK_NOTE_BLOCK_PLING;
        this.players = Bukkit.getOnlinePlayers();
    }


    public GlobalCommand(Sound sound)
    {
        this.sound = sound;
        this.players = Bukkit.getOnlinePlayers();
    }


    public GlobalCommand(Sound sound, ArrayList<Player> players)
    {
        this.sound = sound;
        this.players = players;
    }


    public void global(@NotNull String message)
    {
        message = ChatColor.translateAlternateColorCodes('&', message);
        for (Player player : players)
        {
            player.playSound(player.getLocation(), sound, 2F, 1F);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&k&l_&r") + ChatColor.BLUE + "" + ChatColor.BOLD + "ANNOUNCEMENT: " + ChatColor.AQUA + message);
        }
    }
}
