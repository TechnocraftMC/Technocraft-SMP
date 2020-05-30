package com.technocraft.server.listener;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.earth2me.essentials.Essentials;
import com.technocraft.server.commands.AnnounceCommand;
import com.technocraft.server.util.Chat;
import net.ess3.api.MaxMoneyException;
import net.ess3.api.events.KitClaimEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class KitListener implements Listener {
    @EventHandler
    public void onKitClaim(KitClaimEvent e) throws MaxMoneyException
    {
        Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

        Player player = Bukkit.getPlayer(e.getUser().getName());
        if (e.getKit().getName().equals("donator"))
        {
            player.giveExpLevels(5);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:give " + player.getName() + " minecraft:splash_potion{display:{Name:'{\"text\":\"Legendary Potion of Speed\",\"color\":\"gold\",\"bold\":true}',Lore:['{\"text\":\"Only legends have spoken of such power...\"}','{\"text\":\"Use it wisely!\"}']},CustomPotionEffects:[{Id:1b,Amplifier:2b,Duration:12000,ShowParticles:0b}],CustomPotionColor:8254207} 1");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent addtemp 3 1mo accumulate");
            e.getUser().giveMoney(BigDecimal.valueOf(100, 0));
            kitCommands(player.getName(), "donator");
        } else if (e.getKit().getName().equals("benefactor"))
        {
            player.giveExpLevels(15);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:give " + player.getName() + " minecraft:splash_potion{display:{Name:'{\"text\":\"Super Legendary Potion of Speed\",\"color\":\"gold\",\"bold\":true}',Lore:['{\"text\":\"Only legends have spoken of such power...\"}','{\"text\":\"Use it wisely!\"}']},CustomPotionEffects:[{Id:1b,Amplifier:2b,Duration:12000,ShowParticles:0b}],CustomPotionColor:8254207} 3");
            e.getUser().giveMoney(BigDecimal.valueOf(300, 0));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent addtemp 8 1mo accumulate");
            kitCommands(player.getName(), "benefactor");
        }
    }

    private void kitCommands(String playerName, String kitName)
    {
        String rankName = kitName.equals("benefactor") ? "Benefactor" : "Donator";
        ChatColor rankColor = kitName.equals("benefactor") ? ChatColor.AQUA : ChatColor.DARK_PURPLE;
        AnnounceCommand.announceMessage(ChatColor.BOLD + "Donation", rankColor + playerName + " has donated!", new ArrayList<>(Bukkit.getOnlinePlayers()));
        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 2F, 1F);
        }
        Bukkit.broadcastMessage(Chat.getminiAnnColor() + "Thanks to " + Chat.getValueColor() + playerName + Chat.getminiAnnColor() + " for donating. With these donations, we are able to keep the server afloat!");

        //Initialize
        WebhookClient client = new WebhookClientBuilder("https://canary.discordapp.com/api/webhooks/633100858174996493/Cpny_xCyVkqLKBl21Bv97_VNKfnVgOFtlrBPilQ8_VnvxRbPag9k-LTz9-YkggjSrCP2").build();
        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
        WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();

        //Set embed message
        embedBuilder.setTitle(new WebhookEmbed.EmbedTitle("RANK PURCHASE", null));
        embedBuilder.setDescription("Thank you to **" + playerName + "** for purchasing **" + rankName + "**.\n" +
                "Through generous donations like theirs, this server is able to stay afloat.");
        embedBuilder.setFooter(new WebhookEmbed.EmbedFooter("This is an automated action when a user receives their rank in-game", "http://cdn.discordapp.com/icons/601804017986830337/17a532e4d925ebd3e0a9dbbfc6084e02.png"));
        embedBuilder.setColor((kitName.equals("benefactor") ? Color.CYAN.getRGB() : Color.MAGENTA.getRGB()));

        //Send
        messageBuilder.addEmbeds(embedBuilder.build());

        client.send(messageBuilder.build());

        client.close();

    }
}
