package com.technocraft.server.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DamageListener implements Listener {

    private File logs;

    public DamageListener(File logs)
    {
        this.logs = logs;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) throws IOException
    {
        if (e.getEntityType().equals(EntityType.ENDER_DRAGON))
        {

            Player player = null;
            if (e.getDamager().getType().equals(EntityType.PLAYER))
            {
                player = (Player) e.getDamager();
                log(player.getName() + " " + e.getFinalDamage());
            } else if (e.getDamager().getType().equals(EntityType.ARROW))
            {
                player = (Player) (((Arrow) e.getDamager()).getShooter());
            }

            if (player != null)
            {
                log(player.getName() + " " + e.getFinalDamage());
            }
        }
    }

    public void log(String value) throws IOException
    {
        value = value + "\n";
        //BufferedWriter writer = new BufferedWriter(new FileWriter(logs));
        Files.write(Paths.get(logs.toURI()), value.getBytes(), StandardOpenOption.APPEND);
    }
}
