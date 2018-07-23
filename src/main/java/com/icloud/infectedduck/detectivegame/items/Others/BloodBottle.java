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
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class BloodBottle extends ItemBase {
    public BloodBottle() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        Potion potion = new Potion(PotionType.INSTANT_HEAL);
        potion.apply(item);
        initItem("피가 담긴 병", item, 1, Arrays.asList(ProfessionBase.Profession.ALL),
                Arrays.asList(ItemTarget.PLAYER), new String[]{
                        ChatColor.GRAY + "플레이어에게 병 안의 피를 묻힐 수 있습니다.",
                        ChatColor.DARK_GREEN + "사용 후, 빈 병은 다시 사용할 수 있습니다."
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEntityEvent) {
            PlayerInteractEntityEvent e = (PlayerInteractEntityEvent) event;
            if(e.getRightClicked() instanceof Player) {
                if(!isUserValid(e.getPlayer())) return;
                Player target = (Player) e.getRightClicked();
                if(!target.hasMetadata("NPC") || target.hasMetadata("DGfakepl")) {
                    PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(target, DetectiveGame.instance.acceptedPlayerBase);
                    pb.setSoakedWithBlood();

                    for (ItemStack item : e.getPlayer().getInventory().getContents()) {
                        if(item == null) continue;
                        if(item.getItemMeta().getLore() == null) continue;
                        if(item.getItemMeta().getLore().containsAll(this.createItem().getItemMeta().getLore())) {
                            e.getPlayer().getInventory().remove(item);
                            e.getPlayer().updateInventory();//for good measure
                            break;
                        }
                    }
                    e.getPlayer().getInventory().addItem(ItemList.collectBlood.createItem());
                    DetectiveGame.instance.spawnedItem.remove(ItemList.bloodBottle);
                    e.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + target.getName()
                            + ChatColor.RESET + ChatColor.BLUE + "에게 몰래 피를 묻혔다");
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BURP, 1, 0.5f);
                }
            }
        }
    }
}//custom potions
