package com.technocraft.server.listener;

import com.technocraft.server.Main;
import com.technocraft.server.util.DiscordUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    private JDA jda;

    public CommandListener(Main main)
    {
        jda = main.getJDA();
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e)
    {
        if (e.getPlayer().hasPermission("group.seasonmanager-perms"))
        {
            DiscordUtils utils = new DiscordUtils();
            TextChannel channel = jda.getGuildById(utils.getGuildID()).getTextChannelById(utils.getAdminLogsChannelID());

            String message = e.getPlayer().getName() + ": " + e.getMessage();
            channel.sendMessage(message).queue();
        }
    }
}
