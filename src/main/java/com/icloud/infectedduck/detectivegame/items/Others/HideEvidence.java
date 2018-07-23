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

package com.icloud.infectedduck.detectivegame.items.Others;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.professions.JobDetective;
import com.icloud.infectedduck.detectivegame.professions.JobPolice;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class HideEvidence extends ItemBase {
    public HideEvidence() {
        initKillItem(KillType.MAGIC);
        initItem("증거인멸", new ItemStack(Material.BOWL, 1), 1, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.CORPSE), new String[] {
                        ChatColor.GRAY + "시체의 외양상의 모든 증거를 인멸합니다.",
                        ChatColor.DARK_GREEN + "플레이어 시체에는 쓸 수 없습니다."
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractAtEntityEvent) {
            PlayerInteractAtEntityEvent e = (PlayerInteractAtEntityEvent) event;

            if(!(e.getRightClicked() instanceof ArmorStand)) return;
            if(!e.getRightClicked().hasMetadata("DGcorpse")) return;

            if(!this.isUserValid(e.getPlayer())) {
                return;
            }

            int npcNum = (e.getRightClicked().getMetadata("DGcorpse").get(0).asInt());

            if(e.getPlayer().getLocation().distance(NpcList.NpcList.get(npcNum-1).getNpcLocation()) > 1.3) {
                e.getPlayer().sendMessage(ChatColor.RED + "증거인멸을 하려면 시체에 더 다가가야 한다");
                return;
            }
            ItemBase killItem = NpcList.NpcList.get(npcNum-1).getKillItem();
            if(killItem.getKillType().equals(KillType.BLADE) || killItem.getKillType().equals(KillType.MACE)
                    || killItem.getKillType().equals(KillType.REMOTE_BLADE) || killItem.getKillType().equals(KillType.REMOTE_MACE)
                    || killItem.getKillType().equals(KillType.EXPLOSIVE)) {
                Location loc = DetectiveGameUtils.getNearestLocation(e.getRightClicked().getLocation(), DetectiveGame.instance.bloodLocation);
                if (loc != null)
                    DetectiveGame.instance.bloodLocation.remove(loc);
            }
            NpcList.NpcList.get(npcNum - 1).setKillItem(this);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BAT_TAKEOFF, 1, 0.85f);
            e.getPlayer().sendMessage(ChatColor.BLUE + "시체 주변의 증거를 모두 회수했다");
            reduceItemDurability(e.getPlayer(), true);
        }
    }
}
