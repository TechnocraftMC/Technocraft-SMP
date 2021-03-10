package com.technocraft.server.listener;

import com.technocraft.server.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class WizDog implements Listener {
    private Main main;

    public WizDog(Main main)
    {
        this.main = main;
    }


    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e)
    {
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().equals(Material.NAME_TAG))
        {
            String oldName = e.getRightClicked().getCustomName();
            if (oldName.contains("Wiz_Dog") && !p.isOp())
            {
                Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                    @Override
                    public void run()
                    {
                        e.getRightClicked().setCustomName(oldName);
                        p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&c&lWiz_Dog&f: Get fucked bro"));
                    }
                }, 1);
            }
        }
    }
}
