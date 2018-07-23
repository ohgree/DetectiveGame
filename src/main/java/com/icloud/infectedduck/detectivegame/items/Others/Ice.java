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
import com.icloud.infectedduck.detectivegame.event.EnvironmentEx;
import com.icloud.infectedduck.detectivegame.professions.JobEngineer;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.HeadHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class Ice extends ItemBase {
    public Ice() {
        ItemStack head = HeadHandler.CreateHead(true, "얼음",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODcyZDliOThhNmIzNGEyNzYyYWFjMWFmOTE1ODczYzA2NmM0M2MyYjJiOGQ2ODlkMjc2MjZjYzVhZmNiMTEifX19");
        initItem("얼음", head, 1, Arrays.asList(ProfessionBase.Profession.ENGINEER),
                Arrays.asList(ItemTarget.BLOCK), new String[] {
                        ChatColor.GRAY + "무더운 실내에서 곧 녹을듯한 얼음입니다.",
                        ChatColor.DARK_GREEN + "퓨즈에 설치하면 30초 후 퓨즈가 내려갑니다."
                });
    }

    public void useItem(Event event) {
        if(event instanceof PlayerInteractAtEntityEvent) {
            PlayerInteractAtEntityEvent e = (PlayerInteractAtEntityEvent) event;

            if(DetectiveGame.instance.playerJobs.get(e.getPlayer()) instanceof JobEngineer) {
                Block block = e.getPlayer().getTargetBlock((Set<Material>) null, 5);
                for(Block fuse: DetectiveGame.instance.fuse) {
                    if (fuse.equals(block)) {
                        final ArmorStand ice = (ArmorStand)fuse.getWorld().spawnEntity(fuse.getLocation().clone().add(0.5, -1, 0.5),
                                EntityType.ARMOR_STAND);

                        ice.setSmall(true);
                        ice.setVisible(false);
                        ice.setHelmet(this.createItem());

                        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                            public void run() {
                                ice.remove();
                                if(!EnvironmentEx.isFuseOn()) {
                                    EnvironmentEx.toggleFuse();
                                }
                            }
                        }, 30 * 20L);

                        e.getPlayer().sendMessage(ChatColor.BLUE + "얼음을 설치했습니다. 30초 후 퓨즈가 내려갑니다.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BURP, 1, 0.5f);
                        reduceItemDurability(e.getPlayer(), true);
                        break;
                    }
                }
            }

        } else if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                for(Block fuse: DetectiveGame.instance.fuse) {
                    if (fuse.equals(e.getClickedBlock())) {
                        final ArmorStand ice = (ArmorStand)fuse.getWorld().spawnEntity(fuse.getLocation().clone().add(0.5, -1, 0.5),
                                EntityType.ARMOR_STAND);

                        ice.setSmall(true);
                        ice.setVisible(false);
                        ice.setHelmet(this.createItem());

                        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                            public void run() {
                                ice.remove();
                                if(!EnvironmentEx.isFuseOn()) {
                                    EnvironmentEx.toggleFuse();
                                }
                            }
                        }, 30 * 20L);

                        e.getPlayer().sendMessage(ChatColor.BLUE + "얼음을 설치했습니다. 30초 후 퓨즈가 내려갑니다.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BURP, 1, 0.5f);
                        reduceItemDurability(e.getPlayer(), true);
                        break;
                    }
                }
            }
        }
    }
}
