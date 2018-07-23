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

package com.icloud.infectedduck.detectivegame.scheduler;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.event.EnvironmentEx;
import com.icloud.infectedduck.detectivegame.items.Others.Inspection;
import com.icloud.infectedduck.detectivegame.items.Others.PlayerStatus;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.ParticleEffect;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by InfectedDuck on 15. 10. 2..
 */
public class GameScheduler {
    private static BukkitTask bloodTask;
    private static BukkitTask tickTask;
    private static BukkitTask weatherTask;
    private static BukkitTask itemTask;
    public static void runSubtitleCommand(long delayTicks) {
        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
            public void run() {
                Random random = new Random();
                String command = "title @a subtitle {text:\"- Within Us -\",color:\"";
                switch(random.nextInt(10)) {
                    case 0 : command += "white"; break;
                    case 1 : command += "red"; break;
                    case 2 : command += "yellow"; break;
                    case 3 : command += "gold"; break;
                    case 4 : command += "light_purple"; break;
                    case 5 : command += "blue"; break;
                    case 6 : command += "green"; break;
                    case 7 : command += "gray"; break;
                    case 8 : command += "dark_blue"; break;
                    case 9 : command += "aqua"; break;
                }
                command += "\",italic:true}";
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            }
        }, delayTicks);
    }
    public static void showTitleWithColor(final Player player, final String message, final String color, long delayTicks) {
        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
            public void run() {
                if (player == null) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                            "title @a title {text:\"" + message + "\",color:\"" + color + "\",bold:true}");
                } else {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "title " + player.getName() +
                            " title {text:\"" + message + "\",color:\"" + color + "\",bold:true}");
                }
            }
        }, delayTicks);
    }
    public static void showSubtitleWithColor(final Player player, final String message, final String color, long delayTicks) {
        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
            public void run() {
                if (player == null) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                            "title @a subtitle {text:\"" + message + "\",color:\"" + color + "\",bold:true}");
                } else {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "title " + player.getName() +
                            " subtitle {text:\"" + message + "\",color:\"" + color + "\",bold:true}");
                }
            }
        }, delayTicks);
    }
    public static void setBloodScheduler() {
        bloodTask = Bukkit.getScheduler().runTaskTimerAsynchronously(DetectiveGame.instance, new Runnable() {
            public void run() {
                for (Location loc : DetectiveGame.instance.bloodLocation) {
                    ParticleEffect.BlockData blockData = new ParticleEffect.BlockData(Material.REDSTONE_BLOCK, (byte)0);
                    ParticleEffect.BLOCK_CRACK.display(blockData,
                            (float)0.7, (float)0, (float)0.7, 1, 700, loc.clone().add(0, 0.3, 0), DetectiveGame.instance.acceptedPlayers);

                    /*Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                            "particle blockcrack_152 " + loc.getBlockX() + " " + loc.add(0, -0.5, 0).getBlockY()
                                    + " " + loc.getBlockZ() + " 0.7 0 0.7 1 700");*/
                }
            }
        }, 20, 20);
    }
    public static void cancelBloodScheduler() {
        bloodTask.cancel();
    }
    public static void setTickTimer() {
        tickTask = Bukkit.getScheduler().runTaskTimer(DetectiveGame.instance, new Runnable() {
            public void run() {
                for(Player pl : DetectiveGame.instance.acceptedPlayers) {
                    PlayerBase playerBase =
                            DetectiveGameUtils.getPlayerBaseFromPlayer(pl, DetectiveGame.instance.acceptedPlayerBase);
                    if(pl.isSprinting()) {
                        playerBase.addFatigue(3);
                    } else if(pl.isSneaking()) {//not moving
                        playerBase.addFatigue(-4);
                    } else { //walking
                        playerBase.addFatigue(-2);
                    }

                    if(playerBase.getFatigue() > 70) playerBase.setPanting();//playerBase.setWet();
                    if(playerBase.getFatigue() > 120) {
                        if(!pl.hasPotionEffect(PotionEffectType.SLOW)) {
                            pl.sendMessage(ChatColor.DARK_RED + "더 이상 뛰는건 무리다...");
                        }
                        pl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2 * 20, 0, true, false), true);
                    }
                }
            }
        }, 20, 20);//for every seconds
    }
    public static void setWeatherTimer() {
        final Random random = new Random();
        weatherTask = Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
            public void run() {
                for (World w : Bukkit.getWorlds()) {
                    if(random.nextBoolean()) {
                        w.setStorm(true);
                        w.setWeatherDuration(20 * 20 + 20 * random.nextInt(500) + 60);
                    }
                }
                setWeatherTimer();
            }
        }, 20 * 20 + 20 * random.nextInt(500));//20sec ~ 8min
    }

    public static void setItemDistTask() {
        final Random random = new Random();
        itemTask = Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
            public void run() {
                if (random.nextInt(3) == 0) {
                    DetectiveGame.instance.spawnedItem.add(0, new PlayerStatus());
                    DetectiveGame.instance.chests.get(random.nextInt(DetectiveGame.instance.chests.size())).getInventory()
                            .addItem(DetectiveGame.instance.spawnedItem.get(0).createItem());
                    Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "[" + ChatColor.BLUE + "공지" + ChatColor.AQUA + "]"
                            + ChatColor.RESET + ChatColor.WHITE + " [플레이어 상태확인] 아이템이 상자 중 하나에 스폰되었습니다");
                } else {
                    DetectiveGame.instance.spawnedItem.add(0, new Inspection());
                    DetectiveGame.instance.chests.get(random.nextInt(DetectiveGame.instance.chests.size())).getInventory()
                            .addItem(DetectiveGame.instance.spawnedItem.get(0).createItem());
                    Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "[" + ChatColor.BLUE + "공지" + ChatColor.AQUA + "]"
                            + ChatColor.RESET + ChatColor.WHITE + " [몸수색] 아이템이 상자 중 하나에 스폰되었습니다");
                }
                setItemDistTask();
            }
        }, 60 * 20 + random.nextInt(9 * 60 * 20));//1 ~ 10 min
    }
}
