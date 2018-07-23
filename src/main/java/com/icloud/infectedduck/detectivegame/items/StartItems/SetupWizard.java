/*
 *  DetectiveGame - A bukkit plugin for Detective Games
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

import com.icloud.infectedduck.detectivegame.types.ItemBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by InfectedDuck on 2015. 12. 23..
 */
public class SetupWizard extends ItemBase {
    public SetupWizard() {
        initItem("플러그인 옵션설정", new ItemStack(Material.PRISMARINE_SHARD, 1), 0, null, null, new String[]{
                ChatColor.GRAY + "" + ChatColor.BOLD + "추리게임420의 옵션을 간편하게 설정하는 아이템입니다",
                ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "우클릭한 블럭에 따라 퓨즈, 세면대, 투표소를 설정합니다",
                ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "앉은 상태(Shift 키)에서 사용 시 NPC를 랜덤하게 배치합니다"
        });
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 420);
        ItemMeta im = item.getItemMeta();

        im.setDisplayName(" " + ChatColor.AQUA + ChatColor.BOLD + ChatColor.ITALIC + "플러그인 옵션설정");

        /*==Add Item Status==*/

        String type = ChatColor.AQUA + " [플러그인 설정 아이템]";

        List<String> explanation = new ArrayList<String>();
        explanation.addAll(this.getExplanation());
        explanation.add(0, type);
        /*====================*/

        im.setLore(explanation);
        item.setItemMeta(im);
        return item;
    }
}
