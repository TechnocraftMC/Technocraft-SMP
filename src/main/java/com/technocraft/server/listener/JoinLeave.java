package com.technocraft.server.listener;

import com.technocraft.server.Main;
import com.technocraft.server.util.DiscordUtils;
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
        main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getListenerChannelID()).sendMessage(e.getJoinMessage().substring(2)).queue();
        utils.updateChannelDescription(main.getJDA(), utils.getListenerChannelID(), Bukkit.getOnlinePlayers().size() + " players online");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getListenerChannelID()).sendMessage(e.getQuitMessage().substring(2)).queue();
        utils.updateChannelDescription(main.getJDA(), utils.getListenerChannelID(), Bukkit.getOnlinePlayers().size() + " players online");
    }
}
