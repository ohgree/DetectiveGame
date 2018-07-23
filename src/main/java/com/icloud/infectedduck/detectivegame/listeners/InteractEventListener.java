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

package com.icloud.infectedduck.detectivegame.listeners;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.event.EnvironmentEx;
import com.icloud.infectedduck.detectivegame.items.Others.*;
import com.icloud.infectedduck.detectivegame.lists.ItemList;
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sun.net.www.content.text.Generic;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by InfectedDuck on 15. 9. 19..
 */
public class InteractEventListener implements Listener{
    public static DetectiveGame plugin;
    public InteractEventListener(DetectiveGame instance) { plugin = instance; }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) return;

        if(DetectiveGameUtils.getPlayerBaseFromPlayer(event.getPlayer(), DetectiveGame.instance.acceptedPlayerBase).isDead()) return;
        if(event.getPlayer().getItemInHand().getType().equals(Material.SNOW_BALL)) {
            event.setCancelled(true);
            final Player thrower = event.getPlayer();
            thrower.getPlayer().updateInventory();
        }

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack paper = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = paper.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "투표권");
            meta.setLore(Arrays.asList(ChatColor.GOLD + "\"투표는 탄환보다 강하다\"" + ChatColor.RESET + " - "
                    + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "에이브러햄 링컨"));
            paper.setItemMeta(meta);

            if(event.getPlayer().getItemInHand().equals(paper)) event.getPlayer().openInventory(EnvironmentEx.ballotGUI);

            Block block = event.getPlayer().getTargetBlock((Set<Material>) null, 5);

            for(Block fuse: plugin.fuse) {
                if (fuse.equals(block)) {
                    ItemBase ib = DetectiveGameUtils.getItemBaseFromItemstack(event.getPlayer().getItemInHand(), plugin.spawnedItem);
                    if (ib instanceof Ice) {
                        ib.useItem(event);
                        event.setCancelled(true);//for good measure
                        return;
                    }
                    EnvironmentEx.toggleFuse();
                    break;
                }
            }

            for(Block tap: plugin.tapWater) {
                if (tap.equals(block)) {
                    EnvironmentEx.washPlayer(event.getPlayer());
                    break;
                }
            }
            if(event.getPlayer().getItemInHand().getType().equals(Material.AIR)) return;

            if(ItemList.playerMarker.createItem().equals(event.getPlayer().getItemInHand())) ItemList.playerMarker.useItem(event);
            if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
                    .equals(ItemList.myStatus.createItem(event.getPlayer().getName()).getItemMeta().getDisplayName())) ItemList.myStatus.useItem(event);

            ItemBase ib = DetectiveGameUtils.getItemBaseFromItemstack(event.getPlayer().getItemInHand(), plugin.spawnedItem);
            if(ib != null) {
                if (ib instanceof Googling) ib.useItem(event);
                else if (ib instanceof Suppressor) ib.useItem(event);
                else if (ib instanceof Investigation) ib.useItem(event);
                else if (ib instanceof Autopsy) ib.useItem(event);
                else if (ib instanceof Detonator) ib.useItem(event);
                else if (ib instanceof CCTV) ib.useItem(event);
                else if (ib instanceof CamViewer) ib.useItem(event);
            }
            ItemBase ibKiller = DetectiveGameUtils.getItemBaseFromItemstack(event.getPlayer().getItemInHand(), plugin.spawnedKillerItem);

            if(ibKiller != null) {
                if (ibKiller.getKillType().equals(ItemBase.KillType.EXPLOSIVE)
                        || ibKiller.getKillType().equals(ItemBase.KillType.REMOTE_BLADE)
                        || ibKiller.getKillType().equals(ItemBase.KillType.REMOTE_MACE)) {
                    ibKiller.useItem(event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) return;
        if(event.getRightClicked() instanceof Player) {
            if(((Player) event.getRightClicked()).getGameMode().equals(GameMode.SPECTATOR)) return;
        }

        if(event.getPlayer().getItemInHand() == null) return;
        if(DetectiveGameUtils.getPlayerBaseFromPlayer(event.getPlayer(), DetectiveGame.instance.acceptedPlayerBase).isDead()) return;
        ItemBase ib = DetectiveGameUtils.getItemBaseFromItemstack(event.getPlayer().getItemInHand(), plugin.spawnedKillerItem);
        if (ib == null) {
            ib = DetectiveGameUtils.getItemBaseFromItemstack(event.getPlayer().getItemInHand(), plugin.spawnedItem);
            if (ib != null) ib.useItem(event);
        } else ib.useItem(event);
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) return;

        if(DetectiveGameUtils.getPlayerBaseFromPlayer(event.getPlayer(), DetectiveGame.instance.acceptedPlayerBase).isDead()) return;
        if(event.getRightClicked() instanceof ArmorStand) {
            ArmorStand as = (ArmorStand)event.getRightClicked();
            if (event.getRightClicked().hasMetadata("DGfuse")) {
                ItemBase ib = DetectiveGameUtils.getItemBaseFromItemstack(event.getPlayer().getItemInHand(), plugin.spawnedItem);
                if (ib != null) {
                    ib.useItem(event);
                    return;
                }
                EnvironmentEx.toggleFuse();
            } else if (event.getRightClicked().hasMetadata("DGtap")) {
                EnvironmentEx.washPlayer(event.getPlayer());
            } else if (event.getRightClicked().hasMetadata("DGcorpse")) {
                ItemBase ib = DetectiveGameUtils.getItemBaseFromItemstack(event.getPlayer().getItemInHand(), plugin.spawnedItem);
                if(ib != null) ib.useItem(event);
                else {
                    ItemBase ibKiller = DetectiveGameUtils.getItemBaseFromItemstack(event.getPlayer().getItemInHand(), plugin.spawnedKillerItem);
                    if(ibKiller != null) ibKiller.useItem(event);
                }
            } else if (event.getRightClicked().hasMetadata("DGballot")) {
                if (DetectiveGame.instance.getKillCount() < 1) {
                    event.getPlayer().sendMessage(ChatColor.RED + "적어도 1번의 살인 후 투표가 가능합니다");
                } else if (EnvironmentEx.isActivatedAtLeastOnce()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "이미 투표가 진행중입니다");
                } else {
                    boolean isTogether = false;
                    for(PlayerBase pb: plugin.acceptedPlayerBase) {
                        if(!pb.isDead() && !pb.getPlayer().equals(event.getPlayer())) {
                            if(pb.getPlayer().getLocation().distance(event.getRightClicked().getLocation()) < 2) {
                                isTogether = true;
                                break;
                            }
                        }
                    }
                    if(isTogether) EnvironmentEx.startBallot();
                    else event.getPlayer().sendMessage(ChatColor.RED + "본인 포함 최소 두 명의 플레이어가 투표장소에 있어야 합니다");
                }
            }
        }
    }
}
