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
public class Npc19 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc19() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc19", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhhMzRkODZhN2JiMTNkNDVhZmRjNTBkM2RjZTVlZWQ5NWUxODQ0ZmJkZWUwY2NhNzUzYzZkMzM0NmUzMzllIn19fQ==");
        //Sprite Head
        npcArmour = ColorArmour.getColoredArmour(0x259d56);
        npcArmour[0] = head;


        initNpc(19, "습흐라이트(Sprite)", "MHF_Alex", Arrays.asList(npcArmour),
                "왠지 샤워가 하고 싶어졌어",
                "수지 보고싶다...",
                "샤워한 직후에는 몸이 젖어있으니 좀 시간을 들여서 말리는게 좋지",
                "샤워는 몸을 깨끗하게 하지만 화학적으로는 완전히 씻진 못하는가봐",
                "나는 왜 만날 샤워하는데 여자들이 싫어할까?",
                "왠지 샤워가 하고싶어",
                "탄산음료로 샤워하면 정말 끈적해서 끔찍할거같아");
    }
}
