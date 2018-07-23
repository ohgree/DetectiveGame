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
public class Npc2 extends NpcBase{
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc2() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc2", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhMDU5NjZkYmIzOWY3ODBlN2VhNjNhMjk1NjBkOGViNDhlMGMyNDk3YTgxOGE4OTU2NGE1YTE0YTMzZWYifX19");
        //Link Head
        npcArmour = ColorArmour.getColoredArmour(0x5cff29);//green
        npcArmour[1] = ColorArmour.getColoredChestplate(0x228f00);//darkgreen
        npcArmour[0] = head;

        initNpc(2, "링크(Zelda)", "MHF_Alex", Arrays.asList(npcArmour),
                "난!!! 젤다가!!! 아니야!!!!",
                "난!!! 젤다가!!! 아니야!!!!",
                "난! 젤다가! 아니라고!!!",
                "난! 젤다가! 아니라고!!!",
                "나는 젤다의 전설 주인공 젤ㄷ... 아니 링크다",
                "젤다년이 내 공을 모두 가로채고 있어",
                "(소곤)제작자는 젤다의 전설을 안해봤대",
                "나는! 젤다가! 아니야!!!",
                "생각보다 안은 깨끗한데!");
    }
}
