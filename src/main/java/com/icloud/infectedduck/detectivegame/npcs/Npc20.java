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

package com.icloud.infectedduck.detectivegame.npcs;

import com.icloud.infectedduck.detectivegame.types.NpcBase;
import com.icloud.infectedduck.detectivegame.utils.ColorArmour;
import com.icloud.infectedduck.detectivegame.utils.HeadHandler;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 26..
 */
public class Npc20 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc20() {
        ItemStack head = new ItemStack(Material.SKULL_ITEM);
        head.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner("5Gree");
        head.setItemMeta(meta);
        //5Gree
        npcArmour = ColorArmour.getColoredArmour(0x32223F);
        npcArmour[0] = head;
        //32223F Color(Purple + Black + Black) -> 50, 34, 63
        initNpc(20, "감염오리(5Gree)", "5Gree", Arrays.asList(npcArmour),
                "제작자다",
                "배우라는 자바는 안배우고 야매로 플러그인을 만들었어",
                "띠용! 왠지 21번과 22번 NPC가 숨겨져 있을 것 같은 기분이 드는데?",
                "Requiescat In Pace",
                "근본없는 플러그인 장인 감염오리입니다",
                "아몰랑",
                "컨텐츠 자!급!자!족!",
                "제 플러그인의 모든 명령어는 당연히 영어입니다. 당연히 내가 한패하기 귀찮아서.",
                "객체지향 우걱우걱");
    }
}
