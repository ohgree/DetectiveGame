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
 * Created by InfectedDuck on 2015. 11. 7..
 */
public class Tie extends ItemBase{
    public Tie() {
        initKillItem(KillType.SUFFOCATION);
        initItem("넥타이", new ItemStack(Material.STRING, 1), 1, Arrays.asList(ProfessionBase.Profession.NONE), Arrays.asList(ItemTarget.NPC), new String[] {
                ChatColor.GRAY + "굉장히 얇아 충격에 약한 넥타이"
        });
    }
}
