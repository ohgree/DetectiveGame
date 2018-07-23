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
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 26..
 */
public class Npc5 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc5() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc5", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQzZDRiN2FjMjRhMWQ2NTBkZGY3M2JkMTQwZjQ5ZmMxMmQyNzM2ZmMxNGE4ZGMyNWMwZjNmMjlkODVmOGYifX19");
        //Pokeball Head
        npcArmour = ColorArmour.getColoredArmour(0xFFFFFF);//white
        npcArmour[0] = head;
        npcArmour[1] = ColorArmour.getColoredChestplate(0xff1f5d);//red shirt


        initNpc(5, "포켓볼(Pokeball)", "MHF_Steve", Arrays.asList(npcArmour),
                "나는 전설의 포켓몬이야",
                "누가 나 좀 꺼내줄래?",
                "앞에 있는 버튼만 누르면 나올 수 있는데...",
                "누가 나 좀 꺼내줘...",
                "여기 안은 너무 답답해",
                "포켓볼에 들어갈 때 실수로 머리만 들어갔지 뭐야",
                "포켓볼에 붙어있는 하체는 신경쓰지 마",
                "여기 너무 어둡고 무섭다...");
    }
}
