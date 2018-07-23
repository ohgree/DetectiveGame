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

import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class DeathNote extends ItemBase {
    public DeathNote() {
        initKillItem(KillType.MAGIC, 20);
        initItem("데스노트", new ItemStack(Material.SIGN, 1), 1, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.NPC), new String[] {
                        ChatColor.GRAY + "이름을 적으면 죽는다는 데스노트입니다.",
                        ChatColor.GRAY + "NPC의 " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "영어 이름(대소문자 구분)"
                                + ChatColor.RESET + ChatColor.GRAY + "을 적으면 "
                                + ChatColor.DARK_GREEN + "20초 뒤 " + ChatColor.GRAY + "사망합니다.",
                        ChatColor.DARK_GREEN + "흔적이 남지 않습니다."
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent)event;
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(isUserValid(e.getPlayer())) {
                    Player p = e.getPlayer();
                    Block b = e.getClickedBlock().getRelative(e.getBlockFace());
                    b.setType(Material.SIGN_POST);
                    Sign sign = (Sign)b.getState();
                    //
                    sign.update();
                    reduceItemDurability(e.getPlayer(), false);
                }
            }
        } else if(event instanceof EntityDamageByEntityEvent) {
            //set victim...(Left-Clicking)
        }
    }
}
