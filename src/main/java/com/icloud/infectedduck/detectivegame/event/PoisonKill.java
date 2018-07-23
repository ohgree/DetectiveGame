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
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.professions.JobDeltaForce;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.NpcBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.utils.CountDownTimer;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.TitleAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by InfectedDuck on 15. 9. 19..
 */
public class PoisonKill {
    public static void kill(final Player killer, final Player victim, final ItemBase item, int wait) {

        killer.playSound(killer.getLocation(), Sound.DRINK, 1.0f, 1.4f);
        killer.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + item.getName() + ChatColor.RESET + ChatColor.DARK_AQUA + "을(를) "
                + ChatColor.DARK_GREEN + victim.getName() + "에게"
                + ChatColor.DARK_AQUA + " 주사했습니다. "
                + ChatColor.YELLOW + wait
                + ChatColor.DARK_AQUA + "초 후 사망합니다");

        CountDownTimer.expCountDown(killer, wait);
        DetectiveGame.instance.setKillCooltime(240 + wait);

        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
            public void run() {
                DetectiveGame.instance.addKill();
                CountDownTimer.sidebarCountDownWithSound(240);

                if(!victim.hasMetadata("DGfakepl") && victim.hasMetadata("NPC")) { //NPC 살인
                    NpcBase nb = DetectiveGameUtils.getNpcBaseFromEntity(victim, NpcList.NpcList);
                    if(nb.isDead()) {
                        killer.sendMessage("[ERROR] Already dead - Contact Dev");
                        return;
                    }
                    nb.killNpc(item);
                } else { //플레이어 살인
                    final PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(victim, DetectiveGame.instance.acceptedPlayerBase);//real player
                    for(World w: Bukkit.getWorlds()) {
                        for(Entity e: w.getEntities()) {
                            if(e.hasMetadata("DGfakepl")) {
                                if(e.getName().equalsIgnoreCase(pb.getPlayer().getName())) {
                                    pb.getPlayer().teleport(e.getLocation());
                                    CitizensAPI.getNPCRegistry().getNPC(e).destroy();
                                    pb.getPlayer().setGameMode(GameMode.ADVENTURE);
                                    break;
                                }
                            }
                        }
                    }
                    pb.killPlayer(item, killer);
                }
            }
        }, wait * 20L);
    }
}
