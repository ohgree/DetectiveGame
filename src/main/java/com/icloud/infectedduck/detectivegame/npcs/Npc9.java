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
public class Npc9 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc9() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc9", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTViZWM3NmQ2NWE4NjhhNWJlNTE3M2QzYjllMTc3NWI1NDA0NmY2MjAzNWMxNTUyNDQwZWRlOTk3M2E5MGUxIn19fQ==");
        //Glados Head
        npcArmour = ColorArmour.getColoredArmour(0xa8a8a8);//Gray
        npcArmour[0] = head;


        initNpc(9, "글라도스(Glados)", "MHF_Ghast", Arrays.asList(npcArmour),
                "안녕",
                "난 니가 진짜 싫어",
                "아악! 새다! 새! 죽여! 악마다!",
                "과학을 위해서 말이지... 이 괴물아",
                "나 음모좀 꾸밀게, 조심하는 게 좋을 걸",
                //"\"난 절대! 얼간이가!...\" 아 대사를 잘못 가져왔군...",
                "첼은 어딨지?",
                "블랙메사... 이름부터 암흑의 다크니스같이 구리네");
    }
}
