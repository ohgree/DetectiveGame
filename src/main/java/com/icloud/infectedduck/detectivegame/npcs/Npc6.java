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
public class Npc6 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc6() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc6", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGViNDYxMjY5MDQ0NjNmMDdlY2ZjOTcyYWFhMzczNzNhMjIzNTliNWJhMjcxODIxYjY4OWNkNTM2N2Y3NTc2MiJ9fX0=");
        //Facebook
        npcArmour = ColorArmour.getColoredArmour(0x0a9aff);//vivid blue
        npcArmour[0] = head;


        initNpc(6, "따봉충(Facebook)", "Notch", Arrays.asList(npcArmour),
                "따봉이 더 필요하다! Moar DDabong!",
                "1 LIKE = 1 WIN!",
                "좋아요 1만개 받으면 군대 재입대한다",
                "1억짜리 돈뭉치입니다. 댓글에 \'돈\'이라고 치면 1주일내에 100만원이 들어옵니다",
                "당신은 귀신을 보았습니다. 30초가 지나기 전까지 좋아요를 안누르면 책임 못집니다.",
                "오빠 오늘 화끈하게 ♥ www.unicef.or.kr",
                "오빠 ♥ www.unicef.or.kr ♥ 난민을 도와요",
                "1 LIKE = 1 PRAYERS",
                "(미필) 전쟁나도 걱정마. 오빠가 제일 먼저 지켜줄테니깐 뒤에서 안심하고 있어.");
    }
}
