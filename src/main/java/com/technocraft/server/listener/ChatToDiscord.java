package com.technocraft.server.listener;

import com.technocraft.server.Main;
import com.technocraft.server.util.DiscordUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatToDiscord implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e)
    {
        DiscordUtils utils = new DiscordUtils();
        new Main().getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getListenerChannelID()).sendMessage(e.getFormat() + " " + e.getMessage());
    }
}
