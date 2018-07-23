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
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.NpcBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.utils.CountDownTimer;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.TitleAPI;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by InfectedDuck on 2015. 11. 19..
 */
public class RemoteBloodKill {
    public static void kill(Player killer, Player victim, ItemBase item) {

        if(!victim.hasMetadata("DGfakepl") && victim.hasMetadata("NPC")) { //NPC 살인
            NpcBase nb = DetectiveGameUtils.getNpcBaseFromEntity(victim, NpcList.NpcList);
            if(nb.isDead()) {
                killer.sendMessage("[ERROR] Already dead - Contact Dev");
                return;
            }
            nb.killNpc(item);
        } else { //플레이어 살인
            PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(victim, DetectiveGame.instance.acceptedPlayerBase);
            if(victim.hasMetadata("DGfakepl")) {
                pb.getPlayer().teleport(victim.getLocation());
                CitizensAPI.getNPCRegistry().getNPC(victim).destroy();
                victim = pb.getPlayer();
                victim.setGameMode(GameMode.ADVENTURE);
            }
            pb.killPlayer(item, killer);
        }

        /*
        killer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60 * 20, 0, true, false), true);
        final Player p = killer;
        BukkitRunnable br = new BukkitRunnable() {
            public void run() {
                if(!p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    this.cancel();
                    return;
                }
                TitleAPI.sendTitle(p, 20, 60, 20, "", ChatColor.DARK_RED + "" + ChatColor.BOLD + "[은신상태]");
            }
        };
        br.runTaskTimer(DetectiveGame.instance, 0L, 100L);*/


        DetectiveGame.instance.bloodLocation.add(victim.getLocation());
        DetectiveGame.instance.addKill();
        DetectiveGame.instance.setKillCooltime(240);
        CountDownTimer.sidebarCountDownWithSound(240);
    }
}
