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
import com.icloud.infectedduck.detectivegame.professions.JobSoldier;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.NpcBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.utils.CountDownTimer;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.TitleAPI;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by InfectedDuck on 15. 9. 19..
 */
public class MaceKill {
    public static void kill(final Player killer, Player victim, ItemBase item) {

        if(!victim.hasMetadata("DGfakepl") && victim.hasMetadata("NPC")) { //NPC 살인
            NpcBase nb = DetectiveGameUtils.getNpcBaseFromEntity(victim, NpcList.NpcList);
            if(nb.isDead()) {
                killer.sendMessage("[ERROR] Already dead - Contact Dev");
                return;
            }
            nb.killNpc(item);
        } else { //플레이어 살인
            PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(victim, DetectiveGame.instance.acceptedPlayerBase);
            if(DetectiveGame.instance.playerJobs.get(pb.getPlayer()) instanceof JobDeltaForce
                    || DetectiveGame.instance.playerJobs.get(pb.getPlayer()) instanceof JobSoldier) {

                Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                    public void run() {
                        for(Player p: DetectiveGame.instance.acceptedPlayers) {
                            TitleAPI.sendTitle(p, 20, 60, 20, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[ 살인마 패배 ]",
                                    ChatColor.BLUE + "" + ChatColor.BOLD + "살인마가 살해당했습니다");
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 0.1f);
                        }
                    }
                }, 60L);

                victim.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + ChatColor.BOLD + killer.getName() +
                        ChatColor.LIGHT_PURPLE + "" + "이 나를 살해하려 하자 나는 반사적으로 무기를 빼앗아 그를 공격했다");
                victim.playSound(victim.getLocation(), Sound.ZOMBIE_METAL, 1.0f, 1.5f);
                DetectiveGame.instance.bloodLocation.add(killer.getLocation());

                PlayerBase moron = DetectiveGameUtils.getPlayerBaseFromPlayer(killer, DetectiveGame.instance.acceptedPlayerBase);
                moron.killPlayer(item, killer);//역관광

                return;
            } else {
                if(victim.hasMetadata("DGfakepl")) {
                    pb.getPlayer().teleport(victim.getLocation());
                    CitizensAPI.getNPCRegistry().getNPC(victim).destroy();
                    victim = pb.getPlayer();
                    victim.setGameMode(GameMode.ADVENTURE);
                }
                pb.killPlayer(item, killer);
            }
        }

        killer.playSound(killer.getLocation(), Sound.ZOMBIE_METAL, 1.0f, 1.5f);

        victim.getWorld().playSound(victim.getLocation(), Sound.WOLF_HOWL, (float)15.0, (float)2);    //Scream(Wayyy over 150 blocks)

        DetectiveGame.instance.bloodLocation.add(victim.getLocation());
        DetectiveGame.instance.addKill();
        DetectiveGame.instance.setKillCooltime(240);
        CountDownTimer.sidebarCountDownWithSound(240);
        PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(killer, DetectiveGame.instance.acceptedPlayerBase);
        pb.setSoakedWithBlood();
        if(!(DetectiveGame.instance.playerJobs.get(killer) instanceof JobDeltaForce)) pb.setPanting();
        killer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60 * 20, 0, true, false), true);

        final Player p = killer;
        BukkitRunnable br = new BukkitRunnable() {
            public void run() {
                if(!p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    this.cancel();
                    return;
                }
                //p.playSound(p.getLocation(), Sound.ZOMBIE_UNFECT, 1, 0.5f);
                TitleAPI.sendTitle(p, 20, 60, 20, "", ChatColor.DARK_RED + "" + ChatColor.BOLD + "[은신상태]");
            }
        };
        br.runTaskTimer(DetectiveGame.instance, 0L, 100L);

        killer.sendMessage(ChatColor.BLUE + "은신상태가 되었습니다. 앉기(기본 Shift키)로 은신을 바로 풀 수 있습니다");
    }
}
