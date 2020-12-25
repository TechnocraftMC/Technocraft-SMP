package com.technocraft.server.listener;

import com.technocraft.server.Main;
import com.technocraft.server.listener.seasonmanager.SMChat;
import com.technocraft.server.util.DiscordUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Collections;

public class ChatToDiscord implements Listener {

    private Main main;

    public ChatToDiscord(Main main)
    {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e)
    {
        DiscordUtils utils = new DiscordUtils();

        String userMessage = e.getMessage();
        if (e.getMessage().startsWith("!"))
        {
            userMessage = userMessage.substring(1);
        }


        TextChannel normal = main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getListenerChannelID());
        TextChannel sm = main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getSeasonPlannerChannelID());

        String messageToSend = e.getPlayer().getName() + ": " + userMessage;



        if (SMChat.toggledChat.contains(e.getPlayer()) || (e.getMessage().startsWith("!") && e.getPlayer().hasPermission("group.seasonmanager-users")))
        {
            sm.sendMessage(messageToSend).queue();
        } else
        {
            if (!e.isCancelled())
            {
                normal.sendMessage(messageToSend).allowedMentions(Collections.EMPTY_LIST).queue();
            }
        }
    }
}
