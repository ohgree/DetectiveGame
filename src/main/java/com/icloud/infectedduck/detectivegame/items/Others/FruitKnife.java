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
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class FruitKnife extends ItemBase {
    public FruitKnife() {
        initKillItem(KillType.BLADE);
        initItem("오염된 대형 식칼", new ItemStack(Material.IRON_SWORD, 1), 0, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.NPC), new String[]{
                        ChatColor.GRAY + "사과, 자두, 복숭아와 더불어 사람도 자를 수 있다",
                        ChatColor.GRAY + "줄여서 오대식이라고도 한다 " + ChatColor.BLUE + "카더라"
                });
    }
}
