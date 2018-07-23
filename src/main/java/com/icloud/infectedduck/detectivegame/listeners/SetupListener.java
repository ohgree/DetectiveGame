/*
 *  DetectiveGame - A bukkit plugin for Detective Games
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
import com.icloud.infectedduck.detectivegame.lists.ItemList;
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by InfectedDuck on 2015. 12. 23..
 */
public class SetupListener implements Listener {
    public static DetectiveGame plugin;
    public SetupListener(DetectiveGame instance) { plugin = instance; }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!event.getPlayer().isOp()) return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!event.getPlayer().getItemInHand().equals(ItemList.setupWizard.createItem())) return;
        event.setCancelled(true);
        Player pl = event.getPlayer();
        if(event.getPlayer().isSneaking()) {
            if(plugin.spawnedNpcNum.size() >= 20) {
                pl.sendMessage(ChatColor.LIGHT_PURPLE + "더 이상 NPC를 설치할 수 없습니다");
                return;
            }
            List<Location> locs = new ArrayList<Location>();
            for(int n: plugin.spawnedNpcNum) {
                if(NpcList.NpcList.get(n-1).getNpc().isSpawned()) {
                    locs.add(NpcList.NpcList.get(n-1).getNpcLocation());
                }
            }
            Integer rand;
            while(true) {
                rand = (new Random()).nextInt(20); //0~19
                if(!plugin.spawnedNpcNum.contains(rand + 1)) break;
            }
            Location closeLoc = DetectiveGameUtils
                    .getNearestLocation(event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().clone().add(0.5, 0, 0.5), locs);

            if(closeLoc != null) {
                if(closeLoc.distance(event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().clone().add(0.5, 0, 0.5)) < 3) {
                    pl.sendMessage(ChatColor.LIGHT_PURPLE + "이미 가까운 곳에 NPC가 존재합니다");
                    return;
                }
            }
            if(!event.getClickedBlock().getRelative(event.getBlockFace()).getType().equals(Material.AIR)
                    || event.getClickedBlock().getRelative(event.getBlockFace()).getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
                pl.sendMessage(ChatColor.LIGHT_PURPLE + "이 장소에는 NPC를 소환할 수 없습니다");
                return;
            }
            plugin.spawnedNpcNum.add(rand + 1);
            NpcList.NpcList.get(rand).spawnNpc(event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().clone().add(0.5, 0, 0.5));
            pl.sendMessage(ChatColor.GREEN + "" + (rand + 1) + "번 NPC를 성공적으로 생성했습니다");

        } else {
            Block block = event.getClickedBlock();
            if (block.getType().equals(Material.TRIPWIRE_HOOK)) {
                if (plugin.fuse.contains(block)) {
                    pl.sendMessage(ChatColor.LIGHT_PURPLE + "이미 등록된 퓨즈입니다");
                    return;
                }
                plugin.fuse.add(block);
                pl.sendMessage(ChatColor.GREEN + "퓨즈를 성공적으로 추가했습니다");
            } else if (block.getType().equals(Material.CAULDRON)) {
                if (plugin.tapWater.contains(block)) {
                    pl.sendMessage(ChatColor.LIGHT_PURPLE + "이미 등록된 세면대입니다");
                    return;
                }
                plugin.tapWater.add(block);
                pl.sendMessage(ChatColor.GREEN + "세면대를 성공적으로 추가했습니다");
            } else if (block.getType().equals(Material.ENCHANTMENT_TABLE)) {
                plugin.ballotPlace = block;
                pl.sendMessage(ChatColor.GREEN + "투표소를 성공적으로 설정했습니다. 투표소는 하나만 설정가능합니다.");
            } else {
                pl.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "올바른 블록이 아닙니다");
                pl.sendMessage(ChatColor.LIGHT_PURPLE + "필수블록 목록을 보기 위해선 인벤토리를 비우고 /dg setup을 다시 입력하세요");
            }
        }
    }
}
