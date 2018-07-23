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
public class Npc4 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc4() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc4", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzFjM2UxZjIyNGI0NDZjY2FjNmE2Y2MzY2Q5ODkxMDE5YTEyMmY5OTY5MWMzOTA3OTkyYTNhZjk5YTIxYjAifX19");
        //Darth Vader Head
        npcArmour = ColorArmour.getColoredArmour(0x000000);//black(Completely)
        npcArmour[0] = head;


        initNpc(4, "다스베이더(DV)", "MHF_Enderman", Arrays.asList(npcArmour),
                "판사님 저는 이 아이의 아버지입니다",
                "제작자년이 스타워즈 안봐서 내가 뭐하는 놈인지 모른다더라",
                "EA 개객기 해봐",
                "나 신작나온다 ㅎㅎ 거기서 아들놈도 만날 예정이야",
                "살인자라니... 그런게 있을리가 없잖아 과학적으로 생각해봐",
                "저도 가정이 있고... 아들도 있습니다... 선처 부탁드립니다");
    }
}
