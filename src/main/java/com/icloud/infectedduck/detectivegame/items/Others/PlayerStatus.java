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

import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 2015. 11. 1..
 */
public class PlayerStatus extends ItemBase {
    public PlayerStatus() {
        initItem("플레이어 상태확인", new ItemStack(Material.ITEM_FRAME, 1), 2, Arrays.asList(ProfessionBase.Profession.ALL),
                Arrays.asList(ItemTarget.PLAYER), new String[] {
                        ChatColor.GRAY + "대상 플레이어의 "+ ChatColor.DARK_GREEN + "혈흔 유무를 포함한 외부적인 특징" + ChatColor.GRAY + "을 찾아냅니다"
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractEntityEvent) {
            PlayerInteractEntityEvent e = (PlayerInteractEntityEvent)event;
            if(isUserValid(e.getPlayer())) {
                if (e.getRightClicked() instanceof Player) {
                    Player target = (Player) e.getRightClicked();
                    if (!target.hasMetadata("NPC") || target.hasMetadata("DGfakepl")) {
                        PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(target, DetectiveGame.instance.acceptedPlayerBase);
                        if(pb == null) throw new NotFoundException("PB not found");

                        if(pb.isPanting()) e.getPlayer().sendMessage(" " + ChatColor.DARK_AQUA + target.getName() + "의 숨소리가 거칠다");
                        if(pb.isWet()) e.getPlayer().sendMessage(" " + ChatColor.DARK_AQUA + target.getName() + "의 몸이 물에 젖어있다");
                        if(pb.isSoakedWithBlood()) e.getPlayer().sendMessage(" " + ChatColor.DARK_RED + target.getName() + "의 몸에 피가 묻어있다");
                        if(!(pb.isSoakedWithBlood() || pb.isPanting() || pb.isWet())) e.getPlayer().sendMessage(" " + ChatColor.GOLD + target.getName() + "의 외양으로부터 어떠한 특징도 찾을 수 없었다");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 0.6f, 1.5f);

                        reduceItemDurability(e.getPlayer(), false);
                    }
                }
            }
        }
    }
}
