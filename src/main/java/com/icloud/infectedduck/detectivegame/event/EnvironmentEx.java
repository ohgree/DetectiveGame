/*
 *  DetectiveGame420 - A bukkit plugin for Detective Games - A bukkit plugin for Detective Games
 *  Copyright (C) 2015  InfectedDuck
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Contact: minjooon123@naver.com | Skype: minjooon123
 */

package com.icloud.infectedduck.detectivegame.event;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.TitleAPI;
import net.citizensnpcs.npc.ai.speech.Chat;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 2015. 11. 7..
 */
public class EnvironmentEx {
    private static boolean fuseOn = false;
    private static BukkitTask bellTask;
    public static Inventory ballotGUI;
    private static boolean activatedOnce = false;
    public static boolean isFuseOn() {
        return fuseOn;
    }
    public static List<Player> alivePlayers = new ArrayList<Player>();
    public static void toggleFuse() {
        if(fuseOn) {
            fuseOn = false;
            for(Player p: DetectiveGame.instance.acceptedPlayers) {
                p.removePotionEffect(PotionEffectType.BLINDNESS);
            }
            bellTask.cancel();
        } else {
            fuseOn = true;
            for(Player p: DetectiveGame.instance.acceptedPlayers) {
                TitleAPI.sendTitle(p, 20, 60, 20, "", ChatColor.GRAY + "" + ChatColor.BOLD + "누군가 퓨즈를 내렸습니다");
                if(p.equals(DetectiveGame.instance.killer)) continue;
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, false, false));
            }
            bellTask = Bukkit.getScheduler().runTaskTimerAsynchronously(DetectiveGame.instance, new Runnable() {
                public void run() {
                    for(Player player: DetectiveGame.instance.acceptedPlayers) {
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.8f, 1.7f);
                    }
                }
            }, 2, 2);
        }
    }
    public static void washPlayer(Player player) {
        if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            player.sendMessage(ChatColor.RED + "은신을 해제해야 피를 씻을 수 있습니다");
            return;
        }
        PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(player, DetectiveGame.instance.acceptedPlayerBase);
        pb.washBlood();
    }
    public static void startBallot() {
        alivePlayers.clear();
        for(Player pl: DetectiveGame.instance.acceptedPlayers) {
            PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(pl, DetectiveGame.instance.acceptedPlayerBase);
            if(pb == null) throw new NullPointerException("Could not find player in the playerbase classes");
            if(!pb.isDead()) {
                alivePlayers.add(pl);
            }
        }
        ballotGUI = Bukkit.createInventory(null, (alivePlayers.size()/9 + 1) * 9,
                "" + ChatColor.DARK_RED + ChatColor.BOLD + "살인자는 바로 너야!");
        for(Player p: alivePlayers) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 0, (short) SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta)skull.getItemMeta();
            meta.setOwner(p.getName());
            skull.setItemMeta(meta);
            meta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + p.getName());
            ballotGUI.addItem(skull);
        }
        for(Player p: alivePlayers) {
            p.getInventory().clear();

            ItemStack paper = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = paper.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "투표권");
            meta.setLore(Arrays.asList(ChatColor.GOLD + "\"투표는 탄환보다 강하다\"" + ChatColor.RESET + " - "
                    + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "에이브러햄 링컨"));
            paper.setItemMeta(meta);

            p.getInventory().addItem(paper);
            p.openInventory(ballotGUI);
            activatedOnce = true;
        }
    }
    public static boolean isActivatedAtLeastOnce() {
        return activatedOnce;
    }
}
