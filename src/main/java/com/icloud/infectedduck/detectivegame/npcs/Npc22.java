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
public class Npc22 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc22() {
        ItemStack head = new ItemStack(Material.SKULL_ITEM);
        head.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner("Sihyang");
        head.setItemMeta(meta);
        //Sihyang

        npcArmour[1] = new ItemStack(Material.IRON_CHESTPLATE, 1);
        npcArmour[0] = head;
        npcArmour[2] = new ItemStack(Material.IRON_LEGGINGS, 1);
        npcArmour[3] = new ItemStack(Material.GOLD_BOOTS, 1);


        initNpc(22, "시향(Sihyang)", "Sihyang", Arrays.asList(npcArmour),
                "수쿠신 오리랑 함께 게임한지 5년이나 됬어 *발",
                "아 케이크를 먹었는데 헛배불러",
                "난 진짜 앞으로 두명이서 마크는 안할거야",
                "멤버 구하자",
                "돈을 마셔버리는 방법을 알려줄까?",
                "졸려",
                "나주량쌔다잉",
                "인생은 개썅 마이웨이야",
                "아몰랑",
                "[이 어록은 당사자의 문제제기로 인해 삭제되었습니다]",
                "[이 어록은 당사자의 문제제기로 인해 삭제되었습니다]",
                "[이 어록은 당사자의 문제제기로 인해 삭제되었습니다]",
                "[이 어록은 당사자의 문제제기로 인해 삭제되었습니다]",
                //"야 술 배틀 1:1로 뜨자",
                //"나는 성현 죽음이고 지옥이 나를 불렀다. 너희들이 밤에 편안히 잠들수 있는 것은 내가 너희들을 대신해 폭력을 저지르기 때문이다. 나를 본다면 최대한 멀리 도망쳐라 피와 고기에 굶주린 악마가 거리를 활보하고 있다.",
                //"교복이좋구나...",
                //"오늘 고3들 학교왔는데 여자애들 진짜 귀엽더라",
                "* 이 NPC의 대사는 당사자가 실제로 한 말들을 그대로 가져왔습니다",
                "* 이 NPC의 대사는 당사자가 실제로 한 말들을 그대로 가져왔습니다",
                "오~~~~~지구요~~~, 쥐~~~~~~리구요~~~",
                "코빅 추천 꾸우우욱~~",
                "[이 어록은 당사자의 문제제기로 인해 삭제되었습니다]",
                "[이 어록은 당사자의 문제제기로 인해 삭제되었습니다]",
                //"나취했떠ㅠ",
                //"야 오빷취함(to. 고1) - by. 반오십",
                "또어록같은거하면디진다",
                "[이 어록은 당사자의 문제제기로 인해 삭제되었습니다]"
                //"나 취해요 살려주세요"
        );
    }
}
