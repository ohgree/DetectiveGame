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
import com.icloud.infectedduck.detectivegame.lists.ProfessionList;
import com.icloud.infectedduck.detectivegame.professions.JobDeltaForce;
import com.icloud.infectedduck.detectivegame.professions.JobSalary;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.HeadHandler;
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
public class Googling extends ItemBase {
    public Googling() {
        ItemStack item = HeadHandler.CreateHead(true, "구글링",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQxZTk5NzkyODlmMDMwOTlhN2M1ODdkNTJkNDg4ZTI2ZTdiYjE3YWI1OTRiNjlmOTI0MzhkNzdlYWJjIn19fQ==");
        initItem("구글링", item, 1, Arrays.asList(ProfessionBase.Profession.SALARY),
                Arrays.asList(ItemTarget.SELF), new String[] {
                        ChatColor.GRAY + "직장에서 터득한 구글신의 힘을 사용합니다.",
                        ChatColor.DARK_GREEN + "모든 플레이어들의 직업을 알 수 있습니다."
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(!(DetectiveGame.instance.playerJobs.get(e.getPlayer()) instanceof JobSalary)) {
                    e.getPlayer().sendMessage(ChatColor.RED + "당신은 이 아이템을 사용할 수 없습니다");
                    return;
                }

                e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + " ========= 구글링 결과 =========");
                for(Player pl: DetectiveGame.instance.acceptedPlayers) {
                    if(DetectiveGame.instance.playerJobs.get(pl) instanceof JobDeltaForce) {
                        e.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + ChatColor.BOLD + " " + pl.getName() + " - "
                                + ChatColor.GRAY + "" + ChatColor.BOLD + ProfessionList.unemployed.getProfessionName());
                    } else {
                        e.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + ChatColor.BOLD + " " + pl.getName() + " - "
                                + ChatColor.GRAY + "" + ChatColor.BOLD + DetectiveGame.instance.playerJobs.get(pl).getProfessionName());
                    }
                }
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                reduceItemDurability(e.getPlayer(), true);
            }
        }
    }
}
