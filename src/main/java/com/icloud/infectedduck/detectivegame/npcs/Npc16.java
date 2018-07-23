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
public class Npc16 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc16() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc16", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjVlOTE1MmVmZDg5MmY2MGQ3ZTBkN2U1MzM2OWUwNDc3OWVkMzExMWUyZmIyNzUyYjZmNGMyNmRmNTQwYWVkYyJ9fX0=");
        //Dropper Head
        npcArmour = ColorArmour.getColoredArmour(0x575757);
        npcArmour[0] = head;


        initNpc(16, "드로퍼(Dispenser)", "MHF_Squid", Arrays.asList(npcArmour),
                "나는 디스펜서가 아니야!",
                "내가 웃는 표정으로 보여?",
                "웃어?",
                "TNT 뱉어버린다",
                "자꾸 나보고 놀리면 모래를 뱉어버리는 수가 있어",
                "레드스톤 배우기 너무 어렵다...");
    }
}
