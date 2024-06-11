package com.technocraft.server.listener;

import com.technocraft.server.Main;
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
import java.util.logging.Level;

public class DeathEvent implements Listener {
    private Main main;

    public DeathEvent(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {

        Player target = e.getEntity();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder itemStringBuilder = new StringBuilder();

        for (ItemStack item : e.getDrops())
        {
            if (!item.getType().equals(Material.AIR))
            {
                String itemSerialized = item.getType().toString().replace("LEGACY_", "").replace("NETHERITE", "N").replace("DIAMOND", "D").replace("IRON", "I");
                itemStringBuilder.append(item.getAmount() + "x " + itemSerialized + (enchantmentSerializer(item.getEnchantments()) != null ? enchantmentSerializer(item.getEnchantments()) : "") + ", ");
            }
        }

        stringBuilder.append(ChatColor.YELLOW + "" + ChatColor.BOLD + target.getName() + ChatColor.WHITE + ChatColor.BOLD + "'s Death: \n");
        stringBuilder.append(ChatColor.WHITE + "- Location: " + ChatColor.YELLOW + String.format("%.1f", target.getLocation().getX()) + " " + String.format("%.1f", target.getLocation().getY()) + " " + String.format("%.1f", target.getLocation().getZ()) + "\n");
        stringBuilder.append(ChatColor.WHITE + "- Server TPS: " + ChatColor.YELLOW + String.format("%.1f", Bukkit.getServer().getTPS()[0]) + " " + String.format("%.1f", Bukkit.getServer().getTPS()[1]) + " " + String.format("%.1f", Bukkit.getServer().getTPS()[2]) + "\n");
        stringBuilder.append(ChatColor.WHITE + "- Ping: " + ChatColor.YELLOW + e.getPlayer().getPing() + "\n");
        stringBuilder.append(ChatColor.BOLD + "" + ChatColor.WHITE + "- XP: " + ChatColor.YELLOW + e.getDroppedExp() + "\n");
        stringBuilder.append(ChatColor.BOLD + "" + ChatColor.WHITE + "- Items Dropped: " + ChatColor.GRAY + itemStringBuilder.toString());

        String finalMessage = stringBuilder.toString();

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (p.isOp())
            {
                p.sendMessage(finalMessage);
            }
        }

        Bukkit.getLogger().log(Level.INFO, finalMessage);

    }

    private String enchantmentSerializer(Map<Enchantment, Integer> enchantmentMap)
    {
        if (enchantmentMap.size() == 0 || enchantmentMap.equals(null))
        {
            return null;
        }

        String enchants = "";
        for (Map.Entry mapElement : enchantmentMap.entrySet())
        {
            Enchantment key = (Enchantment) mapElement.getKey();

            int value = ((int) mapElement.getValue());
            enchants += enchantmentAbbreviate(key) + " " + value + ", ";
        }

        enchants = enchants.trim();
        return " (" + enchants.substring(0, enchants.length() - 1) + ")";
    }

    private String enchantmentAbbreviate(Enchantment enchant)
    {
        if (enchant.equals(Enchantment.PROTECTION))
        {
            return "prot";
        } else if (enchant.equals(Enchantment.FIRE_PROTECTION))
        {
            return "f_prot";
        } else if (enchant.equals(Enchantment.FEATHER_FALLING))
        {
            return "fall";
        } else if (enchant.equals(Enchantment.BLAST_PROTECTION))
        {
            return "b_prot";
        } else if (enchant.equals(Enchantment.PROJECTILE_PROTECTION))
        {
            return "p_prot";
        } else if (enchant.equals(Enchantment.RESPIRATION))
        {
            return "resp";
        } else if (enchant.equals(Enchantment.AQUA_AFFINITY))
        {
            return "aqua";
        } else if (enchant.equals(Enchantment.THORNS))
        {
            return "thorn";
        } else if (enchant.equals(Enchantment.DEPTH_STRIDER))
        {
            return "depth";
        } else if (enchant.equals(Enchantment.FROST_WALKER))
        {
            return "frost";
        } else if (enchant.equals(Enchantment.BINDING_CURSE))
        {
            return "bind";
        } else if (enchant.equals(Enchantment.SHARPNESS))
        {
            return "sharp";
        } else if (enchant.equals(Enchantment.SMITE))
        {
            return "smite";
        } else if (enchant.equals(Enchantment.BANE_OF_ARTHROPODS))
        {
            return "anthr";
        } else if (enchant.equals(Enchantment.KNOCKBACK))
        {
            return "kb";
        } else if (enchant.equals(Enchantment.FIRE_ASPECT))
        {
            return "fire";
        } else if (enchant.equals(Enchantment.LOOTING))
        {
            return "loot";
        } else if (enchant.equals(Enchantment.SWEEPING_EDGE))
        {
            return "sweep";
        } else if (enchant.equals(Enchantment.EFFICIENCY))
        {
            return "effic";
        } else if (enchant.equals(Enchantment.SILK_TOUCH))
        {
            return "silk";
        } else if (enchant.equals(Enchantment.UNBREAKING))
        {
            return "unb";
        } else if (enchant.equals(Enchantment.FORTUNE))
        {
            return "fort";
        } else if (enchant.equals(Enchantment.POWER))
        {
            return "pwr";
        } else if (enchant.equals(Enchantment.PUNCH))
        {
            return "pnch";
        } else if (enchant.equals(Enchantment.FLAME))
        {
            return "flme";
        } else if (enchant.equals(Enchantment.INFINITY))
        {
            return "inf";
        } else if (enchant.equals(Enchantment.LUCK_OF_THE_SEA))
        {
            return "luck_sea";
        } else if (enchant.equals(Enchantment.LURE))
        {
            return "lure";
        } else if (enchant.equals(Enchantment.LOYALTY))
        {
            return "loyl";
        } else if (enchant.equals(Enchantment.IMPALING))
        {
            return "impal";
        } else if (enchant.equals(Enchantment.RIPTIDE))
        {
            return "rip";
        } else if (enchant.equals(Enchantment.CHANNELING))
        {
            return "chan";
        } else if (enchant.equals(Enchantment.MULTISHOT))
        {
            return "mlti";
        } else if (enchant.equals(Enchantment.QUICK_CHARGE))
        {
            return "qck_chg";
        } else if (enchant.equals(Enchantment.PIERCING))
        {
            return "pirc";
        } else if (enchant.equals(Enchantment.DENSITY))
        {
            return "dens";
        } else if (enchant.equals(Enchantment.BREACH))
        {
            return "brch";
        } else if (enchant.equals(Enchantment.WIND_BURST))
        {
            return "wnd";
        } else if (enchant.equals(Enchantment.MENDING))
        {
            return "mnd";
        } else if (enchant.equals(Enchantment.VANISHING_CURSE))
        {
            return "vnsh";
        } else if (enchant.equals(Enchantment.SOUL_SPEED))
        {
            return "soul";
        } else if (enchant.equals(Enchantment.SWIFT_SNEAK))
        {
            return "swft";
        }
        return null;
    }
}
