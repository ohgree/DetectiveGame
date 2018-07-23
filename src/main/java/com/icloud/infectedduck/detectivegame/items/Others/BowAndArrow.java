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
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
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
public class BowAndArrow extends ItemBase {
    public BowAndArrow() {
        initKillItem(KillType.REMOTE_BLADE);
        initItem("활", new ItemStack(Material.BOW, 1), 1, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.NPC), new String[]{
                        ChatColor.GRAY + "조준 후 발사해야 하는 활입니다.",
                        ChatColor.GRAY + "화살이 없어도 사용가능하다는 설정입니다.",
                        ChatColor.DARK_GREEN + "소리가 나지 않습니다."
                });
        initCorpseMessage("날카로운 투사체가 몸을 뚫으면서 깊은 상처를 남겼다", "사건 현장 주위에서 피가 묻은 화살을 발견했다");
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(isUserValid(e.getPlayer())) {
                    final Player user = e.getPlayer();
                    if(DetectiveGameUtils.getTarget(user) instanceof Player) {
                        Player target = (Player) DetectiveGameUtils.getTarget(user);
                        if(target.getGameMode().equals(GameMode.SPECTATOR)) return;

                        HashSet<Material> trans = new HashSet<Material>();
                        trans.add(Material.AIR);
                        trans.add(Material.SKULL);
                        trans.add(Material.SIGN_POST);
                        trans.add(Material.WALL_SIGN);
                        trans.add(Material.FENCE);
                        trans.add(Material.FENCE_GATE);
                        trans.add(Material.TRAP_DOOR);
                        trans.add(Material.BARRIER);
                        Block targetBlock = user.getTargetBlock(trans, 100);

                        if(user.getLocation().distance(targetBlock.getLocation()) < user.getLocation().distance(target.getLocation())) return;

                        if(DetectiveGame.instance.isKillCooltime()) {
                            e.getPlayer().sendMessage(ChatColor.RED + "아직 살인을 할 수 없습니다");
                            return;
                        }
                        if(user.getLocation().distance(target.getLocation()) > 8) {
                            user.sendMessage(ChatColor.RED + "상대방을 맞추기에는 거리가 너무 멉니다");
                            return;
                        }
                        RemoteBloodKill.kill(user, target, this);
                        user.playSound(user.getLocation(), Sound.SHOOT_ARROW, 1, 1);
                        Bukkit.getScheduler().runTask(DetectiveGame.instance, new Runnable() {
                            public void run() {
                                reduceItemDurability(user, true);// for some reason this does not work wtf?
                            }
                        });
                    }
                }
            }
        }
    }
}
