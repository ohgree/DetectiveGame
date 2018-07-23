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
import com.icloud.infectedduck.detectivegame.utils.CountDownTimer;
import com.icloud.infectedduck.detectivegame.utils.HeadHandler;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class Detonator extends ItemBase {
    public Detonator() {
        ItemStack item = HeadHandler.CreateHead(true, " " + ChatColor.RED + ChatColor.BOLD + "원격격발장치",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM5ZTM3ZjU5OWM1NTY5OGQzODczYzg3MTRkOWYzYTQwODc1MWMwMTI3MWE3ODllMmNlM2U3ZTlhMmRjNCJ9fX0");
        if(item == null) throw new NullPointerException("Detonator head is null");
        initKillItem();
        initItem("원격격발장치", item, 1, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.SELF), new String[]{
                        ChatColor.DARK_GREEN + "설치된 C4를 터뜨릴 수 있는 격발장치입니다"
                });
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if(isUserValid(e.getPlayer())) {

                    Player user = e.getPlayer();
                    ArmorStand c4 = null;
                    for(World w: Bukkit.getWorlds()) {
                        for(Entity entity: w.getEntities()) {
                            if(entity instanceof ArmorStand) {
                                if(entity.hasMetadata("DGc4")) {
                                    c4 = (ArmorStand)entity;
                                }
                            }
                        }
                    }
                    if(c4 == null) {
                        user.sendMessage(ChatColor.RED + "C4가 설치되지 않았습니다");
                        return;
                    }
                    if(DetectiveGame.instance.isKillCooltime()) {
                        user.sendMessage(ChatColor.RED + "아직 살인을 할 수 없습니다");
                        return;
                    }
                    final ArmorStand as = c4;
                    user.playSound(user.getLocation(), Sound.CLICK, 1, 0.75f);
                    DetectiveGame.instance.setKillCooltime(1);
                    Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                        public void run() {
                            as.remove();
                            as.getWorld().createExplosion(as.getLocation().getX(), as.getLocation().getY() + 1, as.getLocation().getZ(), 5f, false, false);
                        }
                    }, 10);}
            }
        }
    }
}
