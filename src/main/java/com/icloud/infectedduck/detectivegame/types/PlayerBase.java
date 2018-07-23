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

package com.icloud.infectedduck.detectivegame.types;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

/**
 * Created by InfectedDuck on 2015. 10. 24..
 */
public class PlayerBase {
    private boolean bloodTaintedOnce = false;
    private boolean soakedWithBlood = false;
    private boolean wet = false;
    private boolean panting = false;
    private boolean killedInnocent = false;
    private boolean dead = false;
    private boolean isMarkerSet = false;
    private boolean tradeReady = false;
    private boolean silenced = false;
    private ItemBase killItem;
    private BukkitTask wetTask;
    private BukkitTask pantingTask;
    private int fatigue = 0;
    /**
     * fatigue system:
     * sprint: +1 fatigue/sec
     * stop: -4 fatigue/sec
     * walk: -2 fatigue/sec
     *
     * max: 130
     * min: 0
     *
     * fatigue 50+ -> get wet
     * fatigue 100+ -> totally fatigued, slowness 2 & pant
     *
     * wet -> 30 sec (+ after using tap)
     * pant -> 20 sec (+ after bloody kill & Innocent Kill)
     */
    private Player player;
    public PlayerBase(Player player) {
        this.player = player;
    }
    public Player getPlayer() { return this.player; }
    public void addFatigue(float inc) {
        if(inc > 0) { //runnin'
            if(this.fatigue + inc > 170) {
                this.fatigue = 170;
            } else {
                this.fatigue += inc;
            }
        } else {
            if(killedInnocent) {
                if(this.fatigue + inc < 30) {
                    this.fatigue = 30;
                } else {
                    this.fatigue += inc;
                }
            }
            else {
                if(this.fatigue + inc < 0) {
                    this.fatigue = 0;
                } else {
                    this.fatigue += inc;
                }
            }
        }
    }
    public boolean isSilenced() { return silenced; }
    public void setSilenced(boolean silenced) { this.silenced = silenced; }
    public int getFatigue() { return fatigue; }
    public boolean isWet() { return wet; }
    public boolean isTradeReady() { return this.tradeReady; }
    public void setTradeReady(boolean tradeReady) { this.tradeReady = tradeReady; }
    private void removeWet() {
        this.wet = false;
        getPlayer().sendMessage(ChatColor.DARK_AQUA + "몸의 물기가 모두 말랐다");
    }
    public void setWet() {
        if(getPlayer().getGameMode().equals(GameMode.SPECTATOR) && isDead()) return;
        if(!isWet()) this.getPlayer().sendMessage(ChatColor.RED + "온 몸이 물기로 젖었다...");
        if(isWet()) this.wetTask.cancel();
        this.wet = true;
        wetTask = Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
            public void run() {
                removeWet();
            }
        }, 20*30);//for 30sec
    }
    public boolean isPanting() { return panting; }
    public void removePanting() { this.panting = false; }
    public void setPlayer(Player player) { this.player = player; }
    public void setPanting() {
        if(getPlayer().getGameMode().equals(GameMode.SPECTATOR) && isDead()) return;
        if(!isPanting()) {
            this.getPlayer().sendMessage(ChatColor.RED + "헉..헉.. 숨이 차오른다..");
            getPlayer().playSound(getPlayer().getLocation(), Sound.WOLF_PANT, 1f, 0.5f);
        }
        if(isPanting()) this.pantingTask.cancel();
        this.panting = true;
        pantingTask = Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
            public void run() {
                removePanting();
            }
        }, 20*30);//for 30sec
    }
    public boolean isSoakedWithBlood() { return this.soakedWithBlood; }
    public boolean isBloodTaintedOnce() { return this.bloodTaintedOnce; }
    public void setSoakedWithBlood() {
        if(getPlayer().getGameMode().equals(GameMode.SPECTATOR) && isDead()) return;
        this.soakedWithBlood = true;
        this.bloodTaintedOnce = true;
    }
    public void washBlood() {
        this.soakedWithBlood = false;
        getPlayer().playSound(getPlayer().getLocation(), Sound.WATER, 1, 2f);
        getPlayer().sendMessage(ChatColor.BLUE + "흐르는 물에 손을 깨끗이 씻었다");
        setWet();
    }
    public void setKilledInnocent() {
        setPanting();
        this.killedInnocent = true;
    }
    public void setDead() {
        this.dead = true;
    }
    public boolean isDead() {
        return this.dead;
    }
    public ItemBase getKillItem() { return killItem; }
    public void killPlayer(ItemBase killItem, Player killer) {
        setDead();
        getPlayer().setHealth(0);
        //Vector direction = getPlayer().getLocation().getDirection();
        this.killItem = killItem;
        //killer.getInventory().addItem(getPlayer().getInventory().getContents().clone());
        //getPlayer().getInventory().clear();

        Location loc = getPlayer().getLocation();

        Block b = loc.getWorld().getBlockAt(loc);
        b.setType(Material.SIGN_POST);
        Sign sign = (Sign)b.getState();
        sign.setLine(1, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Rest In");
        sign.setLine(2, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Pepperonis");
        sign.setLine(3, ChatColor.BOLD + "[ " + ChatColor.ITALIC + getPlayer().getName() + ChatColor.RESET + ChatColor.BOLD + " ]");
        sign.update();
        sign.setMetadata("DGcorpse", new FixedMetadataValue(DetectiveGame.instance, "1"));

        loc.add(0, 1, 0);
        Block head = loc.getWorld().getBlockAt(loc);
        head.setType(Material.SKULL);
        Skull skull = (Skull)head.getState();
        skull.getData().setData((byte)1);
        skull.setSkullType(SkullType.PLAYER);
        skull.setOwner(getPlayer().getName());
        skull.setRotation(BlockFace.SOUTH);
        skull.update();
        skull.setMetadata("DGcorpse", new FixedMetadataValue(DetectiveGame.instance, "1"));

        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
            public void run() {
                getPlayer().kickPlayer(ChatColor.RED + "" + ChatColor.BOLD + "당신은 사망했습니다");
            }
        }, 10L);
    }
    public void setMarker() {
        if(this.isMarkerSet) {
            getPlayer().sendMessage(ChatColor.RED + "설치된 마커가 아직 사라지지 않았습니다");
            return;
        }

        isMarkerSet = true;

        Location loc = getPlayer().getLocation();
        final ArmorStand marker = (ArmorStand)loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        marker.setSmall(true);

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        meta.setOwner(getPlayer().getName());
        skull.setItemMeta(meta);

        marker.setHelmet(skull);
        marker.setCustomNameVisible(true);
        marker.setCustomName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[ "
                + ChatColor.YELLOW + "" + ChatColor.BOLD + getPlayer().getName() + "의 마커"
                + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + " ]");
        marker.setMetadata("DGmarker", new FixedMetadataValue(DetectiveGame.instance, "1"));

        BukkitRunnable markerTask = new BukkitRunnable() {
            public void run() {
                marker.remove();
                isMarkerSet = false;
            }
        };
        markerTask.runTaskLater(DetectiveGame.instance, 20 * 20L);
    }
}
