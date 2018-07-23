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
public class Npc13 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc13() {
        //ItemStack head = HeadHandler.CreateHead(true, "Npc13", "");
        //
        //npcArmour.add(head);
        ItemStack head = new ItemStack(Material.SKULL_ITEM);
        head.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner("MHF_Chicken");
        head.setItemMeta(meta);

        npcArmour = ColorArmour.getColoredArmour(0xFFFFFF);
        npcArmour[0] = head;
        npcArmour[1] = ColorArmour.getColoredChestplate(0xDDE750);//yellowish


        initNpc(13, "양염(Chicken)", "MHF_Chicken", Arrays.asList(npcArmour),
                "안녕 나는 양염(Chicken)이야",
                "훌아이드 오빠는 나랑 같이 다니는 게 싫다고 안 왔어...",
                "여기 좀 추운거 같아...",
                "윽! 기름진 건 질색이야",
                "뭔 일 있어?",
                "무슨 소리가 난 것 같은데...",
                "계속 서있으려니 좀 피곤하네");
    }
}
