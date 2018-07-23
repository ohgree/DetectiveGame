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

package com.icloud.infectedduck.detectivegame.items.Others;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.HeadHandler;
import com.icloud.infectedduck.detectivegame.utils.TitleAPI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 2015. 11. 25..
 */
public class Cloaking extends ItemBase{
    public Cloaking() {
        ItemStack head = HeadHandler.CreateHead(true, "은신", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ2ZGE3ZTg0OWU1OTZlYzAyYzM2Yjk3MDMzZWE1M2Q5NTU2ODZlNzVmNDFlNWY2MjZjY2UzMjk2ZWQifX19");
        initItem("은신", head, 1, Arrays.asList(ProfessionBase.Profession.DELTA, ProfessionBase.Profession.UNIMPLOYED),
                Arrays.asList(ItemTarget.SELF), new String[] {
                        ChatColor.GRAY + "사용 후 " + ChatColor.DARK_GREEN + "2분 동안 은신상태" + ChatColor.GRAY + "가 됩니다",
                        ChatColor.GRAY + "은신 중 앉기(Shift) 키를 눌러 바로 은신을 해제할 수 있습니다"
        });
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(isUserValid(e.getPlayer())) {
                if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    final Player user = e.getPlayer();
                    if(user.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                        user.sendMessage(ChatColor.RED + "이미 은신상태입니다");
                        return;
                    }
                    user.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 120 * 20, 0, false, false), true);
                    user.playSound(user.getLocation(), Sound.ZOMBIE_UNFECT, 1, 0.5f);
                    BukkitRunnable br = new BukkitRunnable() {
                        public void run() {
                            if(!user.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                                this.cancel();
                                return;
                            }
                            TitleAPI.sendTitle(user, 20, 60, 20, "", ChatColor.DARK_RED + "" + ChatColor.BOLD + "[은신상태]");
                        }
                    };
                    br.runTaskTimer(DetectiveGame.instance, 0L, 100L);
                    user.sendMessage(ChatColor.BLUE + "은신상태가 되었습니다. Sneak키(기본 Shift)를 눌러 은신을 바로 해제할 수 있습니다");
                }
            }
        }
    }
}
