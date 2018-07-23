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
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.professions.JobDoctor;
import com.icloud.infectedduck.detectivegame.professions.JobStudent;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
public class Autopsy extends ItemBase {
    public Autopsy() {
        initItem("부검", new ItemStack(Material.FEATHER), 0, Arrays.asList(ProfessionBase.Profession.DOCTOR, ProfessionBase.Profession.STUDENT),
                Arrays.asList(ItemTarget.CORPSE), new String[] {ChatColor.GRAY + "시체의 자세한 사인을 밝혀냅니다."});
    }

    @Override
    public void useItem(Event event) {
        if(event instanceof PlayerInteractAtEntityEvent) {
            PlayerInteractAtEntityEvent e = (PlayerInteractAtEntityEvent) event;
            if(!(e.getRightClicked() instanceof ArmorStand)) return;
            if(!e.getRightClicked().hasMetadata("DGcorpse")) return;

            ProfessionBase profession = DetectiveGame.instance.playerJobs.get(e.getPlayer());
            if(!(profession instanceof JobDoctor || profession instanceof JobStudent)) return;

            int npcNum = (e.getRightClicked().getMetadata("DGcorpse").get(0).asInt());
            ItemBase killItem = NpcList.NpcList.get(npcNum-1).getKillItem();

            if(e.getPlayer().getLocation().distance(NpcList.NpcList.get(npcNum-1).getNpcLocation()) > 1.3) {
                e.getPlayer().sendMessage(ChatColor.RED + "시체로 더 가까이 가야 부검을 시작할 수 있을 것 같다");
                return;
            }

            e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + killItem.getCorpseMessage(true));
        } else if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(!isUserValid(e.getPlayer())) return;
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(e.getClickedBlock().getState() instanceof Skull) {
                    Skull corpse = (Skull)e.getClickedBlock().getState();
                    if (corpse.hasMetadata("DGcorpse")) {

                        if (e.getPlayer().getLocation().distance(corpse.getLocation().clone().add(0.5, 0, 0.5)) > 1.3) {
                            e.getPlayer().sendMessage(ChatColor.RED + "시체로 더 가까이 가야 부검을 시작할 수 있을 것 같다");
                            return;
                        }

                        for(Player p: DetectiveGame.instance.acceptedPlayers) {
                            if(p.getName().equalsIgnoreCase(corpse.getOwner())) {
                                e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD
                                        + DetectiveGameUtils.getPlayerBaseFromPlayer(p, DetectiveGame.instance.acceptedPlayerBase)
                                        .getKillItem().getCorpseMessage(true));
                                return;
                            }
                        }

                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "내부적 오류가 생겼습니다. [DEBUG INFO]: " + corpse.getOwner() + " " + DetectiveGame.instance.acceptedPlayers);
                    }
                }
            }
        }
    }
}
