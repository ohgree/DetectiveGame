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
public class Npc10 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc10() {
        ItemStack head = HeadHandler.CreateHead(true, "Npc10", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWVjZjk0ZjRiY2JiZjZlYWRjYjI1YWEzZDA2OWFhNjc4ZWJkYjUyNDFlYjgyZThlMjY4ODljYWYzMjc1NTcwIn19fQ==");
        //Goomba Head
        npcArmour = ColorArmour.getColoredArmour(0xffaa6b);//light brown
        npcArmour[0] = head;


        initNpc(10, "굼바(Goomba)", "MHF_MushroomCow", Arrays.asList(npcArmour),
                "마리오놈은 맨날 날 점프대 마냥 밟아대더라",
                "잡몹이라고 무시하지 마라",
                "내가 임마! 세계의 마리오들을 셀수없이 죽였어!",
                "마리오 한명 제거하니까 포상휴가 줘서 여행왔지",
                "아래에 있는 몸? 이건 그냥 외출용으로 준 의족같은거라고 보면 돼",
                "이 의족 비싼거다");
    }
}
