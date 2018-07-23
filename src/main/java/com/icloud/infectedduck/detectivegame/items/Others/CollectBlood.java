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
import com.icloud.infectedduck.detectivegame.lists.ItemList;
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class CollectBlood extends ItemBase {
    public CollectBlood() {
        initItem("피 수집", new ItemStack(Material.GLASS_BOTTLE, 1), 1, Arrays.asList(ProfessionBase.Profession.ALL),
                Arrays.asList(ItemTarget.CORPSE), new String[] {
                        ChatColor.GRAY + "시체의 혈흔을 소량 채취합니다."
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractAtEntityEvent) {
            PlayerInteractAtEntityEvent e = (PlayerInteractAtEntityEvent) event;
            if(!isUserValid(e.getPlayer())) return;
            if(e.getRightClicked().hasMetadata("DGcorpse")) {
                ArmorStand corpse = (ArmorStand) e.getRightClicked();
                int npcNum = corpse.getMetadata("DGcorpse").get(0).asInt();

                if(DetectiveGameUtils.getNearestLocation(corpse.getLocation(), DetectiveGame.instance.bloodLocation) == null) {
                    e.getPlayer().sendMessage(ChatColor.RED + "이 시체에선 피를 수집할 수 없다");
                    return;
                }
                if(corpse.getLocation().distance(
                        DetectiveGameUtils.getNearestLocation(corpse.getLocation(), DetectiveGame.instance.bloodLocation)) > 2) {
                    e.getPlayer().sendMessage(ChatColor.RED + "이 시체에선 피를 수집할 수 없다");
                    return;
                }
                if(NpcList.NpcList.get(npcNum - 1).getNpcLocation().distance(e.getPlayer().getLocation()) > 1.3) {
                    e.getPlayer().sendMessage(ChatColor.RED + "더 가까이 가야 피를 수집할 수 있을 것 같다");
                    return;
                }

                for (ItemStack item : e.getPlayer().getInventory().getContents()) {
                    if(item == null) continue;
                    if(item.getItemMeta().getLore() == null) continue;
                    if(item.getItemMeta().getLore().containsAll(this.createItem().getItemMeta().getLore())) {
                        e.getPlayer().getInventory().remove(item);
                        e.getPlayer().updateInventory();//for good measure
                        break;
                    }
                }
                e.getPlayer().getInventory().addItem(ItemList.bloodBottle.createItem());
                DetectiveGame.instance.spawnedItem.add(ItemList.bloodBottle);
                e.getPlayer().sendMessage(ChatColor.BLUE + "시체의 피를 약간 수집해 피가 담긴 병을 얻었다");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
            }
        }
    }
}
