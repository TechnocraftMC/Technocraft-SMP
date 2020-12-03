package com.technocraft.server.listener;

import com.technocraft.server.Main;
import com.technocraft.server.util.DiscordUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    private Main main;

    public CommandListener(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e)
    {
        if (e.getPlayer().hasPermission("group.seasonmanager-perms"))
        {
            DiscordUtils utils = new DiscordUtils();
            TextChannel adminLogs = main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getAdminLogsChannelID());

            String message = e.getPlayer().getName() + ": " + e.getMessage();
            adminLogs.sendMessage(message).queue();
        }
    }
}
