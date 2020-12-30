package com.technocraft.server.commands;

import com.connorlinfoot.titleapi.TitleAPI;
import com.technocraft.server.Main;
import com.technocraft.server.util.Chat;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.Random;

public class ChristmasManager implements CommandExecutor {

    private Main main;

    private static ArrayList<Player> cooldown = new ArrayList<>();

    public ChristmasManager(Main main)
    {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        Player player;
        if (commandSender instanceof Player)
        {
            player = (Player) commandSender;
        } else
        {
            return true;
        }

        if (!cooldown.contains(player))
        {
            player.setPlayerTime(14000, false);

            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {
                    TitleAPI.sendTitle(player, 10, 50, 20, ChatColor.translateAlternateColorCodes('&', "&d&lGET READY FOR 2021!"), ChatColor.translateAlternateColorCodes('&', "&82020, you sucked"));
                    effects(main, player);
                    resetTime(main, player);
                }
            }, 20L);

        } else
        {
            player.sendMessage(Chat.error("Holiday", "This command is on cooldown."));
        }

        if (!player.isOp())
        {
            cooldown.add(player);
            removeFromCooldown(main, player);
        }

        return false;
    }

    private void effects(Main main, Player player)
    {
        Random random = new Random();

        for (int i = 0; i < 30; i++)
        {
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run()
                {

                    int xOffset = random.nextInt(8);
                    int zOffset = random.nextInt(8);

                    double xCord = player.getLocation().getX();
                    double yCord = player.getLocation().getY();
                    double zCord = player.getLocation().getZ();


                    if (random.nextBoolean())
                    {
                        xCord += xOffset;
                        zCord += zOffset;

                        if (random.nextBoolean())
                        {
                            xCord -= xOffset;
                            zCord += zOffset;

                            if (random.nextBoolean())
                            {
                                xCord -= xOffset;
                                zCord -= zOffset;

                                if (random.nextBoolean())
                                {
                                    xCord += xOffset;
                                    zCord -= zOffset;
                                }
                            }
                        }
                    }

                    Location location = new Location(player.getWorld(), xCord, yCord, zCord);


                    Firework firework = player.getWorld().spawn(location, Firework.class);
                    FireworkMeta meta = firework.getFireworkMeta();
                    FireworkEffect.Builder builder = FireworkEffect.builder();

                    meta.addEffect(builder
                            .flicker(true)
                            .trail(true)
                            .with(FireworkEffect.Type.STAR)
                            .withColor(Color.RED, Color.FUCHSIA, Color.PURPLE, Color.TEAL, Color.AQUA)
                            .withFade(Color.BLUE, Color.AQUA, Color.YELLOW, Color.SILVER)
                            .build());

                    meta.setPower(random.nextInt(10));


                    firework.setFireworkMeta(meta);

                }
            }, 3L * i);
        }
    }

    private void resetTime(Main main, Player player)
    {
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run()
            {
                player.resetPlayerTime();
            }
        }, 6L * 20);
    }

    private void removeFromCooldown(Main main, Player player)
    {
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run()
            {
                cooldown.remove(player);
            }
        }, 10L * 20);
    }
}
