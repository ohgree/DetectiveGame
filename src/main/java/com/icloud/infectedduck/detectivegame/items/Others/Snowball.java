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

package com.icloud.infectedduck.detectivegame.items.Others;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.event.RemoteBloodKill;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.ParticleEffect;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class Snowball extends ItemBase {
    public Snowball() {
        initKillItem(KillType.REMOTE_MACE);
        initItem("눈덩이", new ItemStack(Material.SNOW_BALL, 1), 1, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.NPC), new String[]{
                        ChatColor.GRAY + "돌 넣은 눈덩이."
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (isUserValid(e.getPlayer())) {
                    final Player user = e.getPlayer();
                    PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(user, DetectiveGame.instance.acceptedPlayerBase);
                    if (DetectiveGameUtils.getTarget(user) instanceof Player) {
                        final Player target = (Player) DetectiveGameUtils.getTarget(user);
                        if(target.getGameMode().equals(GameMode.SPECTATOR)) return;

                        if (DetectiveGame.instance.isKillCooltime()) {
                            e.getPlayer().sendMessage(ChatColor.RED + "아직 살인을 할 수 없습니다");
                            return;
                        }

                        if (user.getLocation().distance(target.getLocation()) > 6) {
                            user.sendMessage(ChatColor.RED + "상대방을 맞추기에는 거리가 너무 멉니다");
                            return;
                        }

                        user.playSound(user.getLocation(), Sound.SHOOT_ARROW, 1, 0.7f);
                        RemoteBloodKill.kill(user, target, this);
                        reduceItemDurability(user, true);
                    }
                }
            }
        }
    }
}
