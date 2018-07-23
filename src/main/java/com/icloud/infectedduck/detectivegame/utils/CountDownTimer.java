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

package com.icloud.infectedduck.detectivegame.utils;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

/**
 * Created by InfectedDuck on 2015. 10. 26..
 */
public class CountDownTimer {
    private static BukkitRunnable task;
    private static boolean sidebarCountDownRunning = false;
    public static void expCountDown(final Player player, final int count) {
        BukkitRunnable task = new BukkitRunnable() {
            int countdown = count;

            public void run(){
                player.setLevel(countdown);

                if (--countdown < 0) {
                    this.cancel();
                }
            }
        };
        task.runTaskTimer(DetectiveGame.instance, 0L, 20L);
    }
    public static void sidebarCountDownWithSound(final int count) {
        if(sidebarCountDownRunning) {
            task.cancel();
        }
        sidebarCountDownRunning = true;

        Scoreboard sb = DetectiveGame.instance.board;
        final Objective o = sb.registerNewObjective(UUID.randomUUID().toString().substring(0, 15), "dummy");
        o.setDisplayName(ChatColor.GOLD + "남은시간");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        final Score score = o.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + ""));

        task = new BukkitRunnable() {
            int countdown = count;
            public void run() {

                score.setScore(countdown);
                if(countdown < 4) {
                    for (Player p : DetectiveGame.instance.acceptedPlayers) {
                        if (countdown == 0) p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 0.7f);
                        else p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 0.8f);
                    }
                }

                if (countdown-- == 0) { //If countdown == 0.
                    sidebarCountDownRunning = false;
                    o.unregister();
                    this.cancel();
                }
            }
        };
        task.runTaskTimer(DetectiveGame.instance, 0L, 20L);
    }
}
