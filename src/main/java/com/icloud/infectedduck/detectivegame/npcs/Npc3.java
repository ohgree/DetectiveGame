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
public class Npc3 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc3() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc3", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZmY2FlZmExOTY2OWQ4YmUwMmNmNWJhOWE3ZjJjZjZkMjdlNjM2NDEwNDk2ZmZjZmE2MmIwM2RjZWI5ZDM3OCJ9fX0=");
        //Target Head
        npcArmour = ColorArmour.getColoredArmour(0xff8180);//pink
        npcArmour[0] = head;


        initNpc(3, "표적지(Target)", "MHF_Steve", Arrays.asList(npcArmour),
                "어째 뒷통수가 쎄한데?",
                "난 그런 눈으로 보지 말라고",
                "금방이라도 날 때리겠다는 눈빛인데?",
                "나는 눈알이 5곳 있어",
                "황소의 눈이 바닥났는데 어떡하지?",
                "난 왠지 다트가 무서워...",
                "자꾸 쳐다보지마 기분나쁘니까...",
                "남자라면 핑크지!!");
    }
}
