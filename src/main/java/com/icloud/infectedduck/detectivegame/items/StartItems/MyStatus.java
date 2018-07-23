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
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 2015. 11. 23..
 */
public class MyStatus extends ItemBase {
    public MyStatus() {
        initItem("내 상태확인", new ItemStack(Material.SKULL_ITEM, 1), 0, Arrays.asList(ProfessionBase.Profession.ALL),
                Arrays.asList(ItemTarget.SELF), new String[] {
                        ChatColor.GRAY + "우클릭 시 자신의 직업과 현재 상태를 확인합니다",
                        ChatColor.DARK_GREEN + "혈흔 유무는 알아낼 수 없습니다",
                        ChatColor.DARK_AQUA + "이 아이템은 기본 아이템입니다. 이동이 불가능합니다"
                });
    }

    public ItemStack createItem(String playerName) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM);
        item.setDurability((short)3);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        SkullMeta skull = (SkullMeta)item.getItemMeta();
        skull.setOwner(playerName);

        skull.setDisplayName(" " + ChatColor.AQUA + ChatColor.BOLD + ChatColor.ITALIC + "내 상태 확인");

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

        skull.setLore(explanation);
        item.setItemMeta(skull);
        return item;
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Player user = e.getPlayer();
                DetectiveGame.instance.playerJobs.get(user).showJobExplanation(user);
                if(DetectiveGame.instance.killer == null) {
                    user.sendMessage(ChatColor.LIGHT_PURPLE + " 아직 살인마가 정해지지 않았습니다");
                } else if(DetectiveGame.instance.killer.equals(user)) {
                    user.sendMessage(ChatColor.RED + " 당신은 살인마입니다. 모든 무기를 사용할 수 있습니다");
                    user.sendMessage(ChatColor.RED + " " + (4 - DetectiveGame.instance.getKillCount()) + "번의 살인이 더 필요합니다");
                }

                PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(user, DetectiveGame.instance.acceptedPlayerBase);
                if(pb.isWet()) user.sendMessage(ChatColor.GOLD + " 몸이 물에 젖어있습니다");
                if(pb.isPanting()) user.sendMessage(ChatColor.GOLD + " 숨소리가 거칩니다. 잠시 쉴 필요가 있습니다");
                //if(pb.isSoakedWithBlood()) user.sendMessage(ChatColor.DARK_RED + " 피가 묻어있습니다");

                user.playSound(user.getLocation(), Sound.CHICKEN_EGG_POP, 1, 2);
            }
        }
    }
}
