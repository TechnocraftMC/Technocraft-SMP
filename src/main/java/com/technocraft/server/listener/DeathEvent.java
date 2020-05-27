package com.technocraft.server.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        Player target = e.getEntity();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder message = new StringBuilder();

        for (ItemStack item : e.getDrops())
        {
            message.append(item.getAmount() + "x " + item.getType().toString().replace("LEGACY_", "") + ", ");
        }

        stringBuilder.append(ChatColor.BOLD + "" + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + ChatColor.BOLD + "'s Death: \n");
        stringBuilder.append("- Location: " + ChatColor.YELLOW + target.getLocation().getX() + " " + target.getLocation().getY() + " " + target.getLocation().getZ() + "\n");
        stringBuilder.append("- XP: " + e.getDroppedExp());
        stringBuilder.append("- Items Dropped: " + ChatColor.GRAY + message.toString());

        String finalMessage = stringBuilder.toString();

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (p.isOp())
            {
                p.sendMessage(finalMessage);
            }
        }
        System.out.println(finalMessage);
    }
}