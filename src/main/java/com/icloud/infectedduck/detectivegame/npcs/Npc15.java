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
public class Npc15 extends NpcBase {
    private ItemStack[] npcArmour = new ItemStack[4];
    public Npc15() {
        //ItemStack head = HeadHandler.CreateHead(true, "Npc15", "");
        //
        //npcArmour.add(head);
        ItemStack head = new ItemStack(Material.SKULL_ITEM);
        head.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner("MHF_Herobrine");
        head.setItemMeta(meta);

        //npcArmour = ColorArmour.getColoredArmour(0x32223F);
        npcArmour[0] = head;
        npcArmour[1] = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        npcArmour[2] = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        npcArmour[3] = new ItemStack(Material.DIAMOND_BOOTS, 1);

        initNpc(15, "히로빈(Herobrine)", "", Arrays.asList(npcArmour),
                "내 직장이 마이크로소프트에 인수됬대...",
                "직장을 잃었어...",
                "이번에 마인크래프트 주인공이 바뀌었다면서?",
                "알렉스 버전의 또다른 히로빈은 없는걸까...",
                "나? 난 그냥 놀러왔는데?",
                "나 장난치는거 별로 안 좋아해",
                "야 양념치킨이 치킨중에 진짜 제일 맛있어(소근)",
                "아 YESYES치킨 맛있더라",
                "나는 원래 히로-브륀이라고 발음하는게 맞아. 뭐 상관없긴 한데...");
    }
}
