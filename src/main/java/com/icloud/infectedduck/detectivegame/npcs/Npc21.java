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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 29..
 */
public class Npc21 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc21() {
        ItemStack head = new ItemStack(Material.SKULL_ITEM);
        head.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner("SuCuSIN");
        head.setItemMeta(meta);
        //SuCuSIN

        npcArmour = ColorArmour.getColoredArmour(0x5b78e1);
        npcArmour[0] = head;
        npcArmour[2] = ColorArmour.getColoredLeggings(0x000000);


        initNpc(21, "수쿠신(SuCuSIN)", "SuCuSIN", Arrays.asList(npcArmour),
                "오그리",
                "시향은 돈을 마셔버리는 재주가 있어",
                "오로",
                "내 빌딩에 내 얼굴 박겠다는데 문제있어?",
                "수-쿠신 술래잡기 많이 다운받아주세요. 근데 배포는 안함",
                "물약변태 전문 수쿠신입니다",
                "시향느무시끼!",
                "내가 내 집에서 세탁기 돌리겠다는데 문제있어?",
                "아 추워, 너무 추워",
                "난 자유롭고 싶어 지금 전투력 수치 111퍼 입고싶은 옷 입고 싶어... (박자를 놓친다)",
                "딱히?",
                "아몰랑",
                "오지네~",
                "그→렇↗구↘만→");
    }
}
