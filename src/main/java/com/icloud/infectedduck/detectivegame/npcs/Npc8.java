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
public class Npc8 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc8() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc8", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThjMjA2ZTI5OTI0Yjk5MTZkNGQyNGRmYmJjMzhmMjhiNDRkNmQzY2ZhMjNhZGVjOWVkM2E4ZmNlMWI3YjIifX19");
        //Green Ore Head
        npcArmour = ColorArmour.getColoredArmour(0x00FF00); //ol' green
        npcArmour[0] = head;


        initNpc(8, "우라늄(Uranium)", "MHF_LavaSlime", Arrays.asList(npcArmour),
                "나는 유-레이니엄이라고 읽는게 맞아",
                "김정은이 자꾸 내 친구들을 괴롭혀",
                "가까이 오지 마... 방사선으로 죽이 되버릴 수도 있어",
                "알파선! 슈슉!",
                "감마선! 슈슉!",
                "아...안돼 중성자만은...",
                "얍! 고자가 되어라!",
                "얍! 고자가 되어라!",
                "얍! 고자가 되어라!");
    }
}
