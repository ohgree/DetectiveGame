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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 22..
 */
public class Npc1 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc1() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc1", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FhYjI3Mjg0MGQ3OTBjMmVkMmJlNWM4NjAyODlmOTVkODhlMzE2YjY1YzQ1NmZmNmEzNTE4MGQyZTViZmY2In19fQ==");
        //QuestionMark Stone
        npcArmour = ColorArmour.getColoredArmour(0xFFFFFF);//white
        npcArmour[0] = head;
        //https://api.mojang.com/user/profiles/<uuid>/names

        initNpc(1, "하루살이(Mayfly)", "MHF_Steve", Arrays.asList(npcArmour),
                "제작자가 감염오리라는 사람인데, 어지간히 띨띨해야지...[온기만 남은 대사입니다]",
                "[이미 제작자에 의해 숙청된 NPC입니다]",
                "어? 당신 누구야? 읍읍!",
                "어 택배왔나보네 잠깐 나가볼게",
                "플러그인 만드는 거 개나소나 하던데?",
                "야 나 컴퓨터 고장났는데 내일 좀 고쳐줘라",
                "힘내자! 더 밝은 내일이 기다리고 있어!",
                "난 내일 그녀와 결혼하기로 했어",
                "아 귀찮아 내일 해야지",
                "내일부턴 운동도 하고 앞으로 열심히 살려고 마음먹었어",
                "여태까지 너무 쓰레기로 살아온거 같아... 내일부터 새 사람이 되겠어",
                "\"오늘 할 일은 내일로 미루지 말자\"... 나에게 딱 필요한 마음가짐인거 같아",
                "제작자도 귀찮았는지 난 스킨이 없어");
    }
}
