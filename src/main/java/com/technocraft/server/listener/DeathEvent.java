package com.technocraft.server.listener;

import com.technocraft.server.Main;
import com.technocraft.server.util.DiscordUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.awt.event.TextListener;
import java.util.Map;

public class DeathEvent implements Listener {

    private Main main;

    public DeathEvent(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {

        DiscordUtils utils = new DiscordUtils();
        TextChannel adminLogs = main.getJDA().getGuildById(utils.getGuildID()).getTextChannelById(utils.getAdminLogsChannelID());


        Player target = e.getEntity();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder message = new StringBuilder();

        String enchants = "";
        for (ItemStack item : e.getDrops())
        {
            for (Map.Entry mapElement : item.getEnchantments().entrySet()) {
                Enchantment key = (Enchantment) mapElement.getKey();

                int value = ((int) mapElement.getValue());
                enchants += key.getKey().getKey() + " " + value + ", ";
            }
            enchants = enchants.trim();

            if (!item.getEnchantments().isEmpty())
            {
                message.append(item.getAmount() + "x " + item.getType().toString().replace("LEGACY_", "") + " (" + enchants + "), ");
            } else
            {
                message.append(item.getAmount() + "x " + item.getType().toString().replace("LEGACY_", "") + ", ");
            }
        }



        stringBuilder.append(ChatColor.YELLOW + "" + ChatColor.BOLD + target.getName() + ChatColor.WHITE + ChatColor.BOLD + "'s Death: \n");
        stringBuilder.append(ChatColor.WHITE + "- Location: " + ChatColor.YELLOW + target.getLocation().getX() + " " + target.getLocation().getY() + " " + target.getLocation().getZ() + "\n");
        stringBuilder.append(ChatColor.BOLD + "" + ChatColor.WHITE + "- XP: " + ChatColor.YELLOW + e.getDroppedExp() + "\n");
        stringBuilder.append(ChatColor.BOLD + "" + ChatColor.WHITE + "- Items Dropped: " + ChatColor.GRAY + message.toString());

        String finalMessage = stringBuilder.toString();

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (p.isOp())
            {
                p.sendMessage(finalMessage);
            }
        }

        System.out.println(finalMessage);
        adminLogs.sendMessage(finalMessage).queue();
    }
}