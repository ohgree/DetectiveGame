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
import com.icloud.infectedduck.detectivegame.utils.HeadHandler;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class CamViewer extends ItemBase {
    public CamViewer() {
        ItemStack item = HeadHandler.CreateHead(true, " " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "감시카메라 화면",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTI0N2M4NzY0ODgxNjdjNzRjYjk0OGFiMTE4MWU2YmVmOWU0YzhkNzU3M2MxOTFhYWFiNGU1Y2M3NWIwOWQifX19");
        if(item == null) throw new NullPointerException("CamViewer head is null");
        initItem("감시카메라 화면", item, 0, Arrays.asList(ProfessionBase.Profession.ALL),
                Arrays.asList(ItemTarget.SELF), new String[]{
                        ChatColor.GRAY + "감시카메라의 시야를 보여줍니다",
                        ChatColor.DARK_GREEN + "우클릭으로 감시카메라를 해체합니다"
                });
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if(isUserValid(e.getPlayer())) {
                    final Player user = e.getPlayer();
                    ArmorStand camera = null;
                    for(World w: Bukkit.getWorlds()) {
                        for(Entity entity: w.getEntities()) {
                            if(entity instanceof ArmorStand) {
                                if(entity.hasMetadata("DGcamera")) {
                                    camera = (ArmorStand) entity;
                                }
                            }
                        }
                    }
                    if(camera == null) {
                        user.sendMessage(ChatColor.RED + "감시카메라로부터의 신호가 잡히지 않는다");
                        return;
                    }
                    user.playSound(user.getLocation(), Sound.CLICK, 1, 1.4f);
                    user.setAllowFlight(true);
                    user.setFlying(true);
                    user.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2 * 20, 0, true, false), true);
                    user.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2 * 20, 0, true, false), true);

                    final NPC playerNpc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, user.getName());
                    playerNpc.setProtected(false);
                    playerNpc.spawn(user.getLocation());
                    //final Player fakePlayer = (Player)playerNpc.getEntity();
                    playerNpc.getEntity().setMetadata("DGfakepl", new FixedMetadataValue(DetectiveGame.instance, "1"));
                    final ItemStack[] contents = user.getInventory().getContents().clone();
                    final ItemStack[] armorContents = user.getInventory().getArmorContents().clone();
                    final int heldSlot = user.getInventory().getHeldItemSlot();
                    Bukkit.getScheduler().runTask(DetectiveGame.instance, new Runnable() {
                        public void run() {
                            ((Player)playerNpc.getEntity()).getInventory().setContents(contents.clone());
                            ((Player)playerNpc.getEntity()).getInventory().setHelmet(user.getInventory().getHelmet());
                            ((Player)playerNpc.getEntity()).getInventory().setChestplate(user.getInventory().getChestplate());
                            ((Player)playerNpc.getEntity()).getInventory().setLeggings(user.getInventory().getLeggings());
                            ((Player)playerNpc.getEntity()).getInventory().setBoots(user.getInventory().getBoots());
                            ((Player)playerNpc.getEntity()).getInventory().setHeldItemSlot(heldSlot);
                            ((Player)playerNpc.getEntity()).updateInventory();
                        }
                    });
                    DetectiveGame.instance.teamAll.addPlayer(((Player)playerNpc.getEntity()));

                    //if(DetectiveGame.instance.killer == null || !user.getName().equalsIgnoreCase(DetectiveGame.instance.killer.getName())) {
                    //    DetectiveGame.instance.invisibleTeam.addPlayer(user);
                    //}

                    user.setGameMode(GameMode.SPECTATOR);
                    user.sendMessage(ChatColor.BLUE + "감시카메라 화면을 그만 보려면 앉기(Shift) 키를 눌러주세요");


                    user.getInventory().clear();
                    user.getInventory().setArmorContents(new ItemStack[]{
                            new ItemStack(Material.AIR),
                            new ItemStack(Material.AIR),
                            new ItemStack(Material.AIR),
                            new ItemStack(Material.AIR),
                    });
                    user.updateInventory();

                    final Location camLocation = camera.getLocation().clone().add(0, -0.7, 0);
                    camLocation.setPitch((float)Math.toDegrees(camera.getHeadPose().getX()));
                    Vector vector = camLocation.getDirection().normalize();
                    camLocation.setX(camLocation.getX() + vector.getX() * 0.4);
                    camLocation.setY(camLocation.getY() + vector.getY() * 0.4);
                    camLocation.setZ(camLocation.getZ() + vector.getZ() * 0.4);

                    BukkitRunnable br = new BukkitRunnable() {
                        public void run() {
                            if(user.isDead()) this.cancel();
                            if(user.isSneaking()) {
                                user.setGameMode(GameMode.ADVENTURE);
                                user.teleport(playerNpc.getEntity().getLocation());
                                user.playSound(user.getLocation(), Sound.FALL_BIG, 1, 1f);
                                user.setFlying(false);
                                user.setAllowFlight(false);
                                user.removePotionEffect(PotionEffectType.INVISIBILITY);
                                user.removePotionEffect(PotionEffectType.NIGHT_VISION);
                                user.getInventory().setContents(contents);
                                user.getInventory().setArmorContents(armorContents);
                                playerNpc.destroy();
                                if(DetectiveGame.instance.killer == null || !DetectiveGame.instance.killer.getName().equalsIgnoreCase(user.getName()))
                                    DetectiveGame.instance.teamAll.addPlayer(user); //WHY?!?! BUKKIT WHY?!?!
                                //else if(DetectiveGame.instance.killer.getName().equalsIgnoreCase(user.getName()))
                                //    DetectiveGame.instance.killerTeam.addPlayer(user);
                                this.cancel();
                                return;
                            }
                            user.teleport(camLocation);
                            user.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2 * 20, 0, true, false), true);
                            user.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2 * 20, 0, true, false), true);
                            user.setAllowFlight(true);
                            user.setFlying(true);

                            DetectiveGame.instance.teamAll.addPlayer((Player)playerNpc.getEntity());
                            ((Player)playerNpc.getEntity()).getInventory().setContents(contents.clone());
                            ((Player)playerNpc.getEntity()).getInventory().setArmorContents(armorContents.clone());
                            ((Player)playerNpc.getEntity()).getInventory().setHeldItemSlot(heldSlot);
                            ((Player)playerNpc.getEntity()).updateInventory();

                            playerNpc.getEntity().setMetadata("DGfakepl", new FixedMetadataValue(DetectiveGame.instance, "1"));
                        }
                    };
                    br.runTaskTimer(DetectiveGame.instance, 5L, 1L);
                }
            }
        }
    }
}
