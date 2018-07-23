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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class Suppressor extends ItemBase { //WIP
    public Suppressor() {
        initKillItem(KillType.NO_KILL);
        initItem("소음기", new ItemStack(Material.BLAZE_ROD, 1), 1, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.SELF), new String[]{
                        ChatColor.GRAY + "사용 후 다음 총소리가 나지 않습니다."
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(isUserValid(e.getPlayer())) {
                    Player user = e.getPlayer();
                    PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(user, DetectiveGame.instance.acceptedPlayerBase);
                    if(pb.isSilenced()) {
                        user.sendMessage(ChatColor.RED + "이미 소음기를 사용한 상태입니다");
                        return;
                    }
                    pb.setSilenced(true);
                    user.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "소음기를 사용했습니다. 다음 총살은 소리가 나지 않습니다.");
                    user.playSound(user.getLocation(), Sound.BURP, 1, 1);
                    reduceItemDurability(user, true);
                }
            }
        }
    }
}
