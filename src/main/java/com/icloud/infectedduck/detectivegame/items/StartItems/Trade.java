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

package com.icloud.infectedduck.detectivegame.items.StartItems;

import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import net.citizensnpcs.npc.ai.speech.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class Trade extends ItemBase {
    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta im = item.getItemMeta();

        im.setDisplayName(" " + ChatColor.AQUA + ChatColor.BOLD + ChatColor.ITALIC + "교환하기");
        List<String> exp;
        exp = this.getExplanation();
        exp.add(0, ChatColor.AQUA + "[기본 아이템(이동불가)]");
        im.setLore(exp);
        item.setItemMeta(im);
        return item;
    }
    public Trade() {
        initItem("아이템 교환", new ItemStack(Material.EMERALD, 1), 0, Arrays.asList(ProfessionBase.Profession.ALL),
                Arrays.asList(ItemTarget.PLAYER), new String[] {
                        ChatColor.GRAY + "이 아이템을 들고 있는 플레이어와",
                        ChatColor.GRAY + "우클릭으로 물물거래가 가능합니다.",
                        ChatColor.DARK_GREEN + "이 아이템은 기본 아이템입니다. 이동이 불가능합니다"
                });
    }
    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerItemHeldEvent) {
            PlayerItemHeldEvent e = (PlayerItemHeldEvent) event;
            if(e.getPlayer().getItemInHand().equals(this.createItem())) {
                PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(e.getPlayer(), DetectiveGame.instance.acceptedPlayerBase);
                if(pb == null) throw new NotFoundException("PB not found");
                pb.setTradeReady(true);
            } else {
                PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(e.getPlayer(), DetectiveGame.instance.acceptedPlayerBase);
                if(pb == null) throw new NotFoundException("PB not found");
                pb.setTradeReady(false);
            }
        }
        if(event instanceof PlayerInteractEntityEvent) {
            PlayerInteractEntityEvent e = (PlayerInteractEntityEvent)event;
            if (e.getRightClicked() instanceof Player) {
                Player target = (Player) e.getRightClicked();
                if (!target.hasMetadata("NPC")) {
                    PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(target, DetectiveGame.instance.acceptedPlayerBase);
                    if(pb == null) throw new NotFoundException("PB not found");
                    if(pb.isTradeReady()) {
                        e.getPlayer().sendMessage("[DEBUG]TRADE>>>>>>>>");
                        target.sendMessage("[DEBUG]TRADE<<<<<<<<<");
                        //trade...
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "상대방이 [교환하기] 아이템을 들고 있어야 합니다");
                    }
                }
            }
        }
    }
}
