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
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.CountDownTimer;
import com.icloud.infectedduck.detectivegame.utils.HeadHandler;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class TNT extends ItemBase {
    public TNT() {
        ItemStack item = HeadHandler.CreateHead(true, "TNT",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWI5OTRiNDFmMDdmODdiMzI4MTg2YWNmY2JkYWJjNjk5ZDViMTg0N2ZhYmIyZTQ5ZDVhYmMyNzg2NTE0M2E0ZSJ9fX0=");

        initKillItem(KillType.EXPLOSIVE, 10);
        initItem("TNT", item, 1, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.BLOCK), new String[]{
                        ChatColor.GRAY + "마인크래프트의 자랑 TNT입니다.",
                        ChatColor.DARK_GREEN + "설치 10초 후 " + ChatColor.GRAY + "자동으로 폭발합니다"
                });
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(isUserValid(e.getPlayer())) {

                    if(DetectiveGame.instance.isKillCooltime()) {
                        e.getPlayer().sendMessage(ChatColor.RED + "아직 살인을 할 수 없습니다");
                        return;
                    }

                    Player user = e.getPlayer();
                    Block b = e.getClickedBlock().getRelative(e.getBlockFace());
                    //Block b = user.getLocation().getBlock();

                    final ArmorStand tnt = (ArmorStand)b.getWorld().spawnEntity(b.getLocation().clone().add(0.5, -0.5, 0.5),
                            EntityType.ARMOR_STAND);

                    tnt.setSmall(true);
                    tnt.setVisible(false);
                    tnt.setHelmet(this.createItem());
                    tnt.setGravity(false);

                    user.sendMessage(ChatColor.DARK_AQUA + "TNT를 설치했습니다. "
                            + ChatColor.YELLOW + "10초" + ChatColor.DARK_AQUA + " 후 폭발합니다.");
                    user.playSound(e.getPlayer().getLocation(), Sound.BURP, 1, 0.5f);

                    DetectiveGame.instance.setKillCooltime(10);
                    CountDownTimer.expCountDown(user, 10);

                    final Location tntLoc = tnt.getLocation().clone().add(0, 1, 0);
                    Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                        public void run() {
                            tnt.remove();
                            tntLoc.getWorld().createExplosion(tntLoc.getX(), tntLoc.getY(), tntLoc.getZ(), 5f, false, false);
                            tntLoc.getWorld().playSound(tntLoc, Sound.EXPLODE, 1000000, 0.1f);
                        }
                    }, 10 * 20L);

                    reduceItemDurability(user, true);
                }
            }
        }
    }
}
