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
public class Npc11 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc11() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc11", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmViOTMzMWFjZGYzY2I3NmJkM2E1NmRkZDU5YjhiMTM5OGE1NDk1MmY1MzQ1NTFkZjgxNDIyYWJiZTk0ZiJ9fX0=");
        //Pricess Peach
        npcArmour = ColorArmour.getColoredArmour(0xfda1fa);
        npcArmour[0] = head;


        initNpc(11, "피치공주(Bitch)", "MHF_Villager", Arrays.asList(npcArmour),
                "안녕하세요 납치중독 공주 피치예요!",
                "쿠파가 정말 좋은데... 마리오는 제게 너무 집착해요",
                "마리오는 정말 극악무도한 범죄자에요. 죽인 굼바수만 몇명인지 아시나요?",
                "돈 못버는 마리오보단 자수성가한 쿠파가 더 좋아요",
                "쿠파님께 납치당하고 싶다...",
                "쿠파 성 참 좋던데요? 거기서 그냥 오손도손 살고싶어요",
                "마리오가 자꾸 집착해서 여행을 핑계로 도망왔어요",
                "루..이지? 그게 누구죠?",
                "저는 분홍색이 좋아요. 그래서 맨날 분홍색 옷만 입고다니죠");
    }
}
