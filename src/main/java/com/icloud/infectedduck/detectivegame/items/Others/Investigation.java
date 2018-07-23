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
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.professions.JobDetective;
import com.icloud.infectedduck.detectivegame.professions.JobDoctor;
import com.icloud.infectedduck.detectivegame.professions.JobPolice;
import com.icloud.infectedduck.detectivegame.professions.JobStudent;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Skull;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class Investigation extends ItemBase {
    public Investigation() {
        initItem("현장조사", new ItemStack(Material.WATCH, 1), 0, Arrays.asList(ProfessionBase.Profession.POLICE, ProfessionBase.Profession.DETECTIVE),
                Arrays.asList(ItemTarget.CORPSE),
                new String[] { ChatColor.GRAY + "주변 현장을 샅샅히 관찰합니다.",
                        ChatColor.DARK_GREEN + "투사체를 찾을 수도 있습니다." });
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractAtEntityEvent) {
            PlayerInteractAtEntityEvent e = (PlayerInteractAtEntityEvent) event;
            if(!(e.getRightClicked() instanceof ArmorStand)) return;
            if(!e.getRightClicked().hasMetadata("DGcorpse")) return;

            ProfessionBase profession = DetectiveGame.instance.playerJobs.get(e.getPlayer());
            if(!(profession instanceof JobPolice || profession instanceof JobDetective)) return;

            int npcNum = (e.getRightClicked().getMetadata("DGcorpse").get(0).asInt());
            ItemBase killItem = NpcList.NpcList.get(npcNum-1).getKillItem();

            if(e.getPlayer().getLocation().distance(NpcList.NpcList.get(npcNum-1).getNpcLocation()) > 1.3) {
                e.getPlayer().sendMessage(ChatColor.RED + "시체로 더 가까이 가야 현장을 조사할 수 있을 것 같다");
                return;
            }

            e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + killItem.getCorpseMessage(false));
        } else if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(!isUserValid(e.getPlayer())) return;
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(e.getClickedBlock().getState() instanceof Skull) {
                    Skull corpse = (Skull)e.getClickedBlock().getState();
                    if (corpse.hasMetadata("DGcorpse")) {

                        if (e.getPlayer().getLocation().distance(corpse.getLocation().clone().add(0.5, 0, 0.5)) > 1.3) {
                            e.getPlayer().sendMessage(ChatColor.RED + "시체로 더 가까이 가야 현장을 조사할 수 있을 것 같다");
                            return;
                        }

                        for(Player p: DetectiveGame.instance.acceptedPlayers) {
                            if(p.getName().equalsIgnoreCase(corpse.getOwner())) {
                                e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD
                                        + DetectiveGameUtils.getPlayerBaseFromPlayer(p, DetectiveGame.instance.acceptedPlayerBase)
                                        .getKillItem().getCorpseMessage(false));
                                return;
                            }
                        }

                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "내부적 오류가 생겼습니다. [DEBUG INFO]: " + corpse.getOwner() + " | " + DetectiveGame.instance.acceptedPlayers);
                    }
                }
            }
        }
    }
}
