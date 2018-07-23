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
 * Created by InfectedDuck on 15. 9. 26..
 */
public class Npc12 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc12() {
        //ItemStack head = HeadHandler.CreateHead(true, "Npc12", "");
        //주민
        //npcArmour.add(head);
        ItemStack head = new ItemStack(Material.SKULL_ITEM);
        head.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner("MHF_Villager");
        head.setItemMeta(meta);

        npcArmour = ColorArmour.getColoredArmour(0xd0b867);
        npcArmour[0] = head;

        initNpc(12, "주민(Villager)", "MHF_Villager", Arrays.asList(npcArmour),
                "힝~?",
                "히-히이잉~",
                "골렘은 어디갔지?",
                "1.8 버전으로 오면서 농사하는 법을 배웠어",
                "난 물건 교환같은건 안해줘. 우클릭 해도 소용없어",
                "친구들은 대부분 좀비보단 플레이어에게 더 자주 죽더라");
    }
}
