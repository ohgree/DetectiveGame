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
public class Npc17 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc17() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc17", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNjNTdmYWNiYjNhNGRiN2ZkNTViNWMwZGM3ZDE5YzE5Y2IwODEzYzc0OGNjYzk3MTBjNzE0NzI3NTUxZjViOSJ9fX0=");
        //Penguin Head
        npcArmour = ColorArmour.getColoredArmour(0x7dd9d6);
        npcArmour[0] = head;


        initNpc(17, "펭귄(Penguin)", "MHF_Spider", Arrays.asList(npcArmour),
                "아 여기 너무 더워...",
                "야 얼음 없냐? 너무 덥다",
                "치킨도 맛있지만 가끔은 피자도 먹고 그래",
                "나도 새야! 날지 못해서 그렇지...",
                "\"아 병원입니다 안ㅅ..\" 아 대사를 잘못 가져왔네",
                "?");
    }
}
