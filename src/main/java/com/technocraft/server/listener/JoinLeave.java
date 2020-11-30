package com.technocraft.server.listener;

import com.technocraft.server.Main;
import com.technocraft.server.util.DiscordUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener {

    private Main main;
    DiscordUtils utils;

    public JoinLeave(Main main)
    {
        utils = new DiscordUtils();
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        TextChannel logsChannel = main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getListenerChannelID());
        logsChannel.sendMessage("*" + e.getJoinMessage().substring(2) + "\n" + Bukkit.getOnlinePlayers().size() + " players online*").queue();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        TextChannel logsChannel = main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getListenerChannelID());
        logsChannel.sendMessage("*" + e.getQuitMessage().substring(2) + "\n" + (Bukkit.getOnlinePlayers().size() - 1)+ " players online*").queue();
    }
}
