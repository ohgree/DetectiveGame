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
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class Pickaxe extends ItemBase {
    public Pickaxe() {
        initKillItem(KillType.BLADE);
        initItem("곡괭이", new ItemStack(Material.IRON_PICKAXE, 1), 0, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.NPC), new String[] {
                        ChatColor.GRAY + "마크복음 1:17",
                        ChatColor.GRAY + "\"노치께서 가라사대 나를 따라 오너라 내가",
                        ChatColor.GRAY + "너희로 사람을 캐는 광부가 되게 하리라 하시니\"",
                        ChatColor.GRAY + " ",
                        ChatColor.GRAY + "들리는 바에 의하면 별풍선도 캘 수 있다."
                });
        initCorpseMessage("뾰족하고 단단한 물질로 인해 두개골에 큰 구멍이 생겨 즉사한 듯 하다", "시체가 과하다 싶을 정도로 난도질되어 있다");
    }
}
