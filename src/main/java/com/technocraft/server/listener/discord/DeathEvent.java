package com.technocraft.server.listener.discord;

import com.technocraft.server.Main;
import com.technocraft.server.util.DiscordUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

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

        for (ItemStack item : e.getDrops())
        {
            if (!item.getType().equals(Material.AIR))
            {
                String itemSerialized = item.getType().toString().replace("LEGACY_", "").replace("NETHERITE", "N").replace("DIAMOND", "D");
                message.append(item.getAmount() + "x " + itemSerialized + (enchantmentSerializer(item.getEnchantments()) != null ? enchantmentSerializer(item.getEnchantments()) : "") + ", ");
            }
        }

        stringBuilder.append(ChatColor.YELLOW + "" + ChatColor.BOLD + target.getName() + ChatColor.WHITE + ChatColor.BOLD + "'s Death: \n");
        stringBuilder.append(ChatColor.WHITE + "- Location: " + ChatColor.YELLOW + String.format("%.3f",target.getLocation().getX()) + " " + String.format("%.3f",target.getLocation().getY()) + " " + String.format("%.3f",target.getLocation().getZ()) + "\n");
        stringBuilder.append(ChatColor.WHITE + "- Server TPS: " + ChatColor.YELLOW + String.format("%.3f", Bukkit.getServer().getTPS()[0]) + " " + String.format("%.3f", Bukkit.getServer().getTPS()[1]) + " " + String.format("%.3f", Bukkit.getServer().getTPS()[2]) + "\n");
        stringBuilder.append(ChatColor.BOLD + "" + ChatColor.WHITE + "- XP: " + ChatColor.YELLOW + e.getDroppedExp() + "\n");
        stringBuilder.append(ChatColor.BOLD + "" + ChatColor.WHITE + "- Items Dropped: " + ChatColor.GRAY + message.substring(0, message.toString().length() - 1));

        String finalMessage = stringBuilder.toString();

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (p.isOp())
            {
                p.sendMessage(finalMessage);
            }
        }

        System.out.println(finalMessage);

        try
        {
            adminLogs.sendMessage(finalMessage).queue();
        } catch (Exception exception)
        {
            exception.getCause();
        }

    }

    private String enchantmentSerializer(Map<Enchantment, Integer> enchantmentMap)
    {
        if (enchantmentMap.size() == 0 || enchantmentMap.equals(null))
        {
            return null;
        }

        String enchants = "";
        for (Map.Entry mapElement : enchantmentMap.entrySet()) {
            Enchantment key = (Enchantment) mapElement.getKey();

            int value = ((int) mapElement.getValue());
            enchants += enchantmentAbbreviate(key.getKey().getKey()) + " " + value + ", ";
        }

        enchants = enchants.trim();
        return " (" + enchants.substring(0, enchants.length() - 1) + ")";
    }

    private String enchantmentAbbreviate(String enchantName)
    {

        return enchantName.replace("a", "").replace("i", "").replace("o", "").replace("u", "").replace("e", "").substring(0, enchantName.length() / 2);
    }
}