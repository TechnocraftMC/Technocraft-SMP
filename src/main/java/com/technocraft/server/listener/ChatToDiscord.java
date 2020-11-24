package com.technocraft.server.listener;

import com.technocraft.server.Main;
import com.technocraft.server.util.Chat;
import com.technocraft.server.util.DiscordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatToDiscord implements Listener {

    private Main main;

    public ChatToDiscord(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e)
    {
        DiscordUtils utils = new DiscordUtils();
        main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getListenerChannelID()).sendMessage(e.getPlayer().getName() + ": " + e.getMessage()).queue();
    }
}
