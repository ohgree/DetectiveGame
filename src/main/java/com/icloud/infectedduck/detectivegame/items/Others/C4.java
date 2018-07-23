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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class C4 extends ItemBase {
    public C4() {
        ItemStack head = HeadHandler.CreateHead(true, " " + ChatColor.RED + ChatColor.BOLD + "C4",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRhMzMyYWJkZTMzM2ExNWE2YzZmY2ZlY2E4M2YwMTU5ZWE5NGI2OGU4ZjI3NGJhZmMwNDg5MmI2ZGJmYyJ9fX0=");
        if(head == null) throw new NullPointerException("C4 head is null");
        initKillItem(KillType.EXPLOSIVE);
        initItem("C4", head, 1, Arrays.asList(ProfessionBase.Profession.NONE),
                Arrays.asList(ItemTarget.BLOCK), new String[]{
                        ChatColor.GRAY + "뇌관이 이미 붙어있는 플라스틱 폭약입니다",
                        ChatColor.GRAY + "원하는 시간에 " +
                                ChatColor.DARK_GREEN + "원격격발장치로 " + ChatColor.GRAY + "격발할 수 있습니다."
                });
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(isUserValid(e.getPlayer())) {
                    Player user = e.getPlayer();
                    Block b = e.getClickedBlock().getRelative(e.getBlockFace());

                    final ArmorStand c4 = (ArmorStand)b.getWorld().spawnEntity(b.getLocation().clone().add(0.5, -0.5, 0.5),
                            EntityType.ARMOR_STAND);

                    c4.setSmall(true);
                    c4.setVisible(false);
                    c4.setHelmet(this.createItem());
                    c4.setGravity(false);
                    c4.setMetadata("DGc4", new FixedMetadataValue(DetectiveGame.instance, 1));

                    user.sendMessage(ChatColor.DARK_AQUA + "C4 폭약을 설치했습니다. "
                            + ChatColor.YELLOW + "원격격발장치" + ChatColor.DARK_AQUA + " 아이템으로 기폭시킬 수 있습니다.");
                    user.playSound(e.getPlayer().getLocation(), Sound.BURP, 1, 0.5f);
                    //final Location c4Loc = c4.getLocation().clone().add(0, 1, 0);
                    reduceItemDurability(user, true);
                }
            }
        }
    }
}
