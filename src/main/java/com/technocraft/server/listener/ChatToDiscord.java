package com.technocraft.server.listener;

import com.technocraft.server.Main;
import com.technocraft.server.listener.seasonmanager.SMChat;
import com.technocraft.server.util.Chat;
import com.technocraft.server.util.DiscordUtils;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.Collections;

public class ChatToDiscord implements Listener {

    private Main main;

    public ChatToDiscord(Main main)
    {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e)
    {
        if (!e.isCancelled())
        {
            DiscordUtils utils = new DiscordUtils();

            TextChannel normal = main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getListenerChannelID());
            TextChannel sm = main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getSeasonPlannerChannelID());
            String message = e.getPlayer().getName() + ": " + e.getMessage();
            if (SMChat.toggledChat.contains(e.getPlayer()) || (e.getMessage().startsWith("!") && e.getPlayer().hasPermission("seasonmanager-users")))
            {
                sm.sendMessage(message).queue();
            } else
            {
                normal.sendMessage(message).allowedMentions(Collections.EMPTY_LIST).queue();
            }
        }
    }
}
