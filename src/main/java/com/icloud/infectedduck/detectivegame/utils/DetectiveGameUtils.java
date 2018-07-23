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

package com.icloud.infectedduck.detectivegame.utils;

import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.NpcBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by InfectedDuck on 2015. 10. 25..
 */
public class DetectiveGameUtils {
    public static PlayerBase getPlayerBaseFromPlayer(Player player, Collection<? extends PlayerBase> playerBases) {
        if(player.hasMetadata("DGfakepl")) {
            for(PlayerBase pb: playerBases) {
                if(pb.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                    player = pb.getPlayer();
                    break;
                }
            }
        }
        for(PlayerBase pb : playerBases) {
            if(pb.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                return pb;
            }
        }
        throw new NotFoundException("The specified player's PlayerBase is not found");
    }
    public static NpcBase getNpcBaseFromEntity(Entity npc, Collection<? extends NpcBase> npcBases) {
        for(NpcBase nb : npcBases) {
            if(!nb.isDead()) {
                if (nb.getNpc() != null) {
                    if (nb.getNpc().getEntity().equals(npc)) {
                        return nb;
                    }
                }
            }
        }
        throw new NotFoundException("The specified entity's NpcBase is not found");
    }
    public static ItemBase getItemBaseFromItemstack(ItemStack item, Collection<? extends ItemBase> itemBases) {
        if(item == null) return null;
        if(item.getType().equals(Material.AIR)) return null;
        if(item.getItemMeta().getLore() == null) return null;
        for(ItemBase ib : itemBases) {
            if (item.getItemMeta().getLore().containsAll(ib.createItem().getItemMeta().getLore())) {
                return ib;
            }
        }
        return null;
    }
    public static boolean doesContainJob(Collection<? extends ProfessionBase> profList, ProfessionBase profValue) {
        for(ProfessionBase prof : profList) {
            if(prof.getClass().equals(profValue.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method from https://bukkit.org/threads/getting-first-entity-in-crosshair-targeting-question.109766/
     * Thanks to whoever made this
     * @param player
     * @return
     */
    public static Entity getTarget(final Player player) {
        assert player != null;
        Entity target = null;
        double targetDistanceSquared = 0;
        final double radiusSquared = 1;
        final Vector l = player.getEyeLocation().toVector(), n = player.getLocation().getDirection().normalize();
        final double cos45 = Math.cos(Math.PI / 4);
        for (final LivingEntity other : player.getWorld().getEntitiesByClass(LivingEntity.class)) {
            if (other == player)
                continue;
            if (target == null || targetDistanceSquared > other.getLocation().distanceSquared(player.getLocation())) {
                final Vector t = other.getLocation().add(0, 1, 0).toVector().subtract(l);
                if (n.clone().crossProduct(t).lengthSquared() < radiusSquared && t.normalize().dot(n) >= cos45) {
                    target = other;
                    targetDistanceSquared = target.getLocation().distanceSquared(player.getLocation());
                }
            }
        }
        return target;
    }

    public static int getItemCount(Inventory inventory) {
        int itemNum = 0;
        for(ItemStack item: inventory.getContents()) {
            if(item != null) {
                itemNum += item.getAmount();
            }
        }
        return itemNum;
    }

    public static Location getNearestLocation(Location origin, List<Location> locations) {
        if(locations.isEmpty()) return null;
        Location closeLoc = locations.get(0);
        for(Location l: locations) {
            if(l.distance(origin) < closeLoc.distance(origin)) {
                closeLoc = l;
            }
        }
        return closeLoc;
    }

    public static void setNoAI(Entity bukkitEntity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }
}
