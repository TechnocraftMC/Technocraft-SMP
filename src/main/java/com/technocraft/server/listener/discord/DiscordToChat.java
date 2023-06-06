package com.technocraft.server.listener.discord;

import com.technocraft.server.Main;
import com.technocraft.server.util.Chat;
import com.technocraft.server.util.DiscordUtils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DiscordToChat extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e)
    {
        if (e.getAuthor().isBot())
        {
            return;
        }

        if (e.getAuthor().isFake())
        {
            return;
        }

        if (e.getMessage().isWebhookMessage())
        {
            return;
        }

        DiscordUtils utils = new DiscordUtils();
        if (e.getGuild().getIdLong() == utils.getGuildID())
        {
            if (e.getChannel().getIdLong() == utils.getListenerChannelID() || e.getChannel().getIdLong() == utils.getAdminChannelID())
            {

                String prefix = "";

                if (e.getMember().getRoles().contains(e.getGuild().getRoleById(utils.getAdminRoleID())))
                {
                    prefix = "&4[Admin]&f";
                    if (e.getChannel().getIdLong() == utils.getAdminChannelID())
                    {
                        Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', Chat.getSMPrefix() + " &f&l" + e.getAuthor().getAsTag() + " &b" + e.getMessage().getContentDisplay()), Main.getAdminUserPerm);
                    } else
                    {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + e.getAuthor().getAsTag() + "&8: &f" + e.getMessage().getContentDisplay()).trim());
                    }
                }

            }
        }
    }
}
