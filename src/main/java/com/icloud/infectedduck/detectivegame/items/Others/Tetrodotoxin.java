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
public class Tetrodotoxin extends ItemBase {
    public Tetrodotoxin() {
        ItemStack item = new ItemStack(Material.RAW_FISH, 1, (short)3);
        initKillItem(KillType.POISON, 10);
        initItem("테트로도톡신", item, 1, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.NPC), new String[]{
                        ChatColor.GRAY + "치명적인 복어의 독입니다.",
                        ChatColor.DARK_GREEN + "주사 10초 후" + ChatColor.GRAY + " 대상이 사망합니다."
                });
    }//data value ItemStack.setData(... byte value ...)
}
