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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class CCTV extends ItemBase {
    public CCTV() {
        ItemStack item = HeadHandler.CreateHead(true, " " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "감시카메라",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE3ZDJhN2ZiYjRkMzdiNGQ1M2ZlODc3NTcxMjhlNWVmNjZlYzIzZDdmZjRmZTk5NDQ1NDZkYmM4Y2U3NzcifX19");
        if(item == null) throw new NullPointerException("Camera head is null");
        initItem("감시카메라", item, 1, Arrays.asList(ProfessionBase.Profession.ENGINEER),
                Arrays.asList(ItemTarget.BLOCK), new String[] {
                        ChatColor.GRAY + "휴대용 벽걸이형 감시카메라입니다",
                        ChatColor.DARK_GREEN + "감시카메라 화면" + ChatColor.GRAY + " 아이템으로 해당 장소를 볼 수 있습니다"
                        //ChatColor.DARK_GREEN + "설치된 카메라를 다른 플레이어가 망가뜨릴 수 있습니다",
                        //"TEST VERSION--"
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
                    Location camLoc = b.getLocation().clone().add(0.5, -0.5, 0.5);
                    camLoc.setYaw(180 + user.getLocation().getYaw());

                    final ArmorStand cam = (ArmorStand)b.getWorld().spawnEntity(camLoc, EntityType.ARMOR_STAND);

                    cam.setSmall(true);
                    cam.setVisible(false);
                    cam.setHelmet(this.createItem());
                    cam.setGravity(false);
                    cam.setHeadPose(new EulerAngle(Math.toRadians(-user.getLocation().getPitch()), 0f, 0f));
                    cam.setMetadata("DGcamera", new FixedMetadataValue(DetectiveGame.instance, "1"));

                    user.sendMessage(ChatColor.BLUE + "감시카메라를 설치했습니다. "
                            + ChatColor.YELLOW + "감시카메라 화면" + ChatColor.BLUE + " 아이템으로 화면을 볼 수 있습니다.");
                    user.playSound(e.getPlayer().getLocation(), Sound.BURP, 1, 0.5f);

                    reduceItemDurability(user, true);
                }
            }
        }
    }
}
