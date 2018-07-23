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
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 26..
 */
public class Npc18 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc18() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc18", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA3MWE3NmY2NjlkYjVlZDZkMzJiNDhiYjJkYmE1NWQ1MzE3ZDdmNDUyMjVjYjMyNjdlYzQzNWNmYTUxNCJ9fX0=");
        //Elephant Head
        npcArmour = ColorArmour.getColoredArmour(0xbb90a4);
        npcArmour[0] = head;


        initNpc(18, "코끼리(Dick)", "MHF_Spider", Arrays.asList(npcArmour),
                "아 뭐 왜 영어이름이 어때서",
                "내 코는 누구보다 두껍지!",
                "나 정도 크기면 냉장고에 들어갈 수 있지 않을까?",
                "아 뭐 먹을거 없냐",
                "치킨과 피자 둘 다 포기할 수 없다면 답은 피자XX로!",
                "XX알볼X꺼 피자 개맛있더라? 근데 할인 혜택이 하나도 없어...");
    }
}
