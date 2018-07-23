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

package com.icloud.infectedduck.detectivegame.items.StartItems;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 2015. 11. 1..
 */
public class PlayerMarker extends ItemBase {
    public PlayerMarker() {
        initItem("마커 설치", new ItemStack(Material.NETHER_STAR, 1), 0, Arrays.asList(ProfessionBase.Profession.ALL),
                Arrays.asList(ItemTarget.SELF), new String[] {
                        ChatColor.GRAY + "우클릭 시 모든 플레이어가 볼 수 있는 마커를 설치합니다",
                        ChatColor.GRAY + "마커는 1개씩만 설치할 수 있으며, 20초 후 사라집니다",
                        ChatColor.DARK_AQUA + "이 아이템은 기본 아이템입니다. 이동이 불가능합니다"
                });
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            DetectiveGameUtils.getPlayerBaseFromPlayer(((PlayerInteractEvent) event).getPlayer(),
                    DetectiveGame.instance.acceptedPlayerBase).setMarker();
        }
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        item.addUnsafeEnchantment(Enchantment.LURE, 1);
        ItemMeta im = item.getItemMeta();

        im.setDisplayName(" " + ChatColor.AQUA + ChatColor.BOLD + ChatColor.ITALIC + "마커 설치");

        /*==Add Item Status==*/

        String type = ChatColor.AQUA + " [기본 아이템(이동불가)]";

        String status = ChatColor.GREEN + " 대상: ";
        status += ChatColor.GRAY + "자신의 현재 위치";

        String maxTimes = ChatColor.GREEN + " 사용가능 횟수: " + ChatColor.AQUA + "무제한";

        List<String> explanation = new ArrayList<String>();
        explanation.addAll(this.getExplanation());
        explanation.add(0, type);
        explanation.add(1, status);
        explanation.add(2, maxTimes);
        /*====================*/

        im.setLore(explanation);
        item.setItemMeta(im);
        return item;
    }
}
