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
import com.icloud.infectedduck.detectivegame.event.RemoteBloodKill;
import com.icloud.infectedduck.detectivegame.professions.JobDeltaForce;
import com.icloud.infectedduck.detectivegame.professions.JobSoldier;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.ParticleEffect;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class RifledRifle extends ItemBase {
    public RifledRifle() {
        initKillItem(KillType.REMOTE_BLADE);
        initItem("소총", new ItemStack(Material.ARMOR_STAND, 1), 3, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.NPC, ItemTarget.PLAYER), new String[]{
                        ChatColor.GRAY + "낙차가 없는 총알을 사용합니다.",
                        ChatColor.DARK_GREEN + "사용시 큰 소리가 납니다."
                });
        initCorpseMessage("몸에서 깊은 총상을 발견할 수 있었다", "현장 주변에서 5.56mm 소총 탄두를 다수 발견했다");
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (isUserValid(e.getPlayer())) {
                    final Player user = e.getPlayer();
                    PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(user, DetectiveGame.instance.acceptedPlayerBase);
                    if (DetectiveGameUtils.getTarget(user) instanceof Player) {
                        final Player target = (Player) DetectiveGameUtils.getTarget(user);
                        if(target.getGameMode().equals(GameMode.SPECTATOR)) return;

                        HashSet<Material> trans = new HashSet<Material>();
                        trans.add(Material.AIR);
                        trans.add(Material.SKULL);
                        trans.add(Material.SIGN_POST);
                        trans.add(Material.WALL_SIGN);
                        trans.add(Material.FENCE);
                        trans.add(Material.FENCE_GATE);
                        trans.add(Material.TRAP_DOOR);
                        trans.add(Material.WOOD_DOOR);
                        trans.add(Material.WOODEN_DOOR);
                        trans.add(Material.BARRIER);
                        Block targetBlock = user.getTargetBlock(trans, 100);

                        if(user.getLocation().distance(targetBlock.getLocation()) < user.getLocation().distance(target.getLocation())) return;


                        if(DetectiveGame.instance.isKillCooltime()) {
                            e.getPlayer().sendMessage(ChatColor.RED + "아직 살인을 할 수 없습니다");
                            return;
                        }

                        if(DetectiveGame.instance.playerJobs.get(user) instanceof JobSoldier
                                || DetectiveGame.instance.playerJobs.get(user) instanceof JobDeltaForce) {
                            if (user.getLocation().distance(target.getLocation()) > 25) {
                                user.sendMessage(ChatColor.RED + "상대방을 맞추기에는 거리가 너무 멉니다");
                                return;
                            }
                        } else if (user.getLocation().distance(target.getLocation()) > 8) {
                            user.sendMessage(ChatColor.RED + "상대방을 맞추기에는 거리가 너무 멉니다");
                            return;
                        }

                        RemoteBloodKill.kill(user, target, this);

                        if(!pb.isSilenced()) {
                            target.getWorld().playSound(target.getLocation(), Sound.FIREWORK_TWINKLE, 10000.0f, 0.1f);
                            Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                                public void run() {
                                    target.getWorld().playSound(user.getLocation(), Sound.FIREWORK_BLAST, 10000.0f, 0.5f);
                                }
                            }, 5L);
                            Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                                public void run() {
                                    target.getWorld().playSound(user.getLocation(), Sound.FIREWORK_BLAST, 10000.0f, 0.5f);
                                }
                            }, 10L);
                            Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                                public void run() {
                                    target.getWorld().playSound(user.getLocation(), Sound.FIREWORK_BLAST, 10000.0f, 0.5f);
                                }
                            }, 15L);
                            Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                                public void run() {
                                    target.getWorld().playSound(user.getLocation(), Sound.FIREWORK_BLAST, 10000.0f, 0.5f);
                                }
                            }, 20L);
                            Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                                public void run() {
                                    target.getWorld().playSound(user.getLocation(), Sound.FIREWORK_BLAST, 10000.0f, 0.5f);
                                }
                            }, 25L);//두두두두
                        } else {
                            user.playSound(user.getLocation(), Sound.WITHER_SHOOT, 0.7f, 1.9f);
                            pb.setSilenced(false);
                        }
                        ParticleEffect.SMOKE_NORMAL.display(0.05f, 0.05f, 0.05f, 0.01f, 100, user.getEyeLocation(), DetectiveGame.instance.acceptedPlayers);

                        reduceItemDurability(user, false);
                    }
                }
            }
        }
    }
}
