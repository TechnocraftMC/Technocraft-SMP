package com.technocraft.server.listener;

import com.technocraft.server.util.DiscordUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class DiscordToChat extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e)
    {
        DiscordUtils utils = new DiscordUtils();
        if (e.getGuild().equals(utils.getGuildID()))
        {
            TextChannel channel = e.getGuild().getTextChannelById(utils.getListenerChannelID());
            if (e.getChannel().equals(channel))
            {
                String prefix = "";
                if (e.getMember().getRoles().contains(utils.getAdminRoleID()))
                {
                    prefix = "&4[Admin]&f";
                } else if (e.getMember().getRoles().contains(utils.getSeasonManagerRoleID()))
                {
                    prefix = "&c[Season Manager]&f";
                }

                Bukkit.getServer().broadcastMessage(prefix + " " + e.getAuthor().getAsTag() + ": " + e.getMessage().getContentDisplay());
            }
        }
    }

}
