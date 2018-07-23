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
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class Inspection extends ItemBase {
    public Inspection() {
        initItem("몸수색", new ItemStack(Material.FISHING_ROD, 1), 1, Arrays.asList(ProfessionBase.Profession.POLICE,
                        ProfessionBase.Profession.DELTA, ProfessionBase.Profession.UNIMPLOYED),
                Arrays.asList(ItemTarget.PLAYER), new String[] {
                        ChatColor.GRAY + "대상 플레이어의 인벤토리를 10초동안 볼 수 있습니다."
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEntityEvent) {
            PlayerInteractEntityEvent e = (PlayerInteractEntityEvent)event;
            if(!isUserValid(e.getPlayer())) return;
            if(e.getRightClicked() instanceof Player) {
                Player target = (Player)e.getRightClicked();
                if(!target.hasMetadata("NPC") || target.hasMetadata("DGfakepl")) {
                    final Inventory inspectedInv = Bukkit.createInventory(null, InventoryType.PLAYER,
                            ChatColor.DARK_BLUE + "" + ChatColor.BOLD + target.getName() + "의 인벤토리");
                    ItemStack[] inv = target.getInventory().getContents();
                    inspectedInv.setContents(inv);
                    e.getPlayer().openInventory(inspectedInv);
                    final Player user = e.getPlayer();
                    user.playSound(user.getLocation(), Sound.BAT_TAKEOFF, 1, 0.65f);
                    Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                        public void run() {
                            if(inspectedInv.getViewers().contains(user)) {
                                user.closeInventory();
                                user.playSound(user.getLocation(), Sound.BAT_TAKEOFF, 1, 0.65f);
                            }
                        }
                    }, 10 * 20L);
                    reduceItemDurability(user, true);
                }
            }
        }
    }
}
