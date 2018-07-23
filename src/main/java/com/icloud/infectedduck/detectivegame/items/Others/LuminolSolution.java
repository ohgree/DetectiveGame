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

import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.professions.JobDoctor;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class LuminolSolution extends ItemBase {
    public LuminolSolution() {
        initItem("루미놀 용액", new ItemStack(Material.POTION, 1), 2, Arrays.asList(ProfessionBase.Profession.DOCTOR),
                Arrays.asList(ItemTarget.PLAYER), new String[] {
                        ChatColor.GRAY + "플레이어에게서 " + ChatColor.DARK_GREEN + "혈흔" + ChatColor.DARK_GREEN + "을 검출합니다.",
                        ChatColor.LIGHT_PURPLE + "씻은 혈흔도 검출할 수 있습니다."
                });
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEntityEvent) {
            PlayerInteractEntityEvent e = (PlayerInteractEntityEvent) event;
            if(e.getRightClicked() instanceof Player) {
                Player target = (Player)e.getRightClicked();
                if(!target.hasMetadata("NPC") || target.hasMetadata("DGfakepl")) {
                    PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(target, DetectiveGame.instance.acceptedPlayerBase);
                    if (!(DetectiveGame.instance.playerJobs.get(e.getPlayer()) instanceof JobDoctor)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "당신은 이 아이템을 사용할 수 없습니다");
                        return;
                    }
                    if (pb == null) throw new NotFoundException("Playerbase not found...somehow");
                    if (pb.isBloodTaintedOnce()) {
                        e.getPlayer().sendMessage(ChatColor.GOLD + " " + ChatColor.BOLD + target.getName()
                                + ChatColor.RESET + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "로부터 혈흔이 검출되었다");
                    } else {
                        e.getPlayer().sendMessage(ChatColor.GOLD + " " + ChatColor.BOLD + target.getName()
                                + ChatColor.RESET + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "로부터 혈흔이 검출되지 않았다");
                    }
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 0.6f, 1.5f);
                    reduceItemDurability(e.getPlayer(), false);
                }
            }
        }
    }
}
