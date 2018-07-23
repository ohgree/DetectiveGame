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

package com.icloud.infectedduck.detectivegame.listeners;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.event.EnvironmentEx;
import com.icloud.infectedduck.detectivegame.items.StartItems.PlayerMarker;
import com.icloud.infectedduck.detectivegame.lists.ItemList;
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.types.NpcBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.CountDownTimer;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import com.icloud.infectedduck.detectivegame.utils.TitleAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCPushEvent;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by InfectedDuck on 15. 9. 19..
 */
public class BasicEventListener implements Listener {

    public static DetectiveGame plugin;
    public BasicEventListener(DetectiveGame instance) {
        plugin = instance;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
        if(event.getEntity() instanceof Player) {
            if (event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
                if (e.getDamager() instanceof Player) {
                    Player pl = (Player)e.getDamager();
                    if(((Player)event.getEntity()).hasPotionEffect(PotionEffectType.INVISIBILITY)) return;

                    if(event.getEntity().hasMetadata("NPC") && !event.getEntity().hasMetadata("DGfakepl")) {
                        pl.sendMessage(ChatColor.GREEN + event.getEntity().getName() + "이다");
                    } else pl.sendMessage(ChatColor.AQUA + event.getEntity().getName() + "이다");
                }
            }

            if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                if(event.getDamage() > 8) {
                    Player pl = (Player)event.getEntity();
                    pl.sendMessage(ChatColor.DARK_RED + "너무 높은곳에서 떨어져 발목을 다쳤다...");
                    pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (3 + (((int)event.getDamage())-10) * 2)*20, 128, false, false));
                    pl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (3 + (((int)event.getDamage())-10) * 2)*20, 3, false, false));
                }
            }

            else if(event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
                    || event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
                if(event.getDamage() > 10) {
                    Player pl = (Player) event.getEntity();
                    DetectiveGame.instance.bloodLocation.add(pl.getLocation());
                    if (!pl.hasMetadata("DGfakepl") && pl.hasMetadata("NPC")) { //NPC 살인
                        NpcBase nb = DetectiveGameUtils.getNpcBaseFromEntity(pl, NpcList.NpcList);
                        if (nb.isDead()) {
                            pl.sendMessage("[ERROR] Already dead - Contact Dev");
                            return;
                        }
                        nb.killNpc(ItemList.tnt);
                    } else { //플레이어 살인
                        PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(pl, DetectiveGame.instance.acceptedPlayerBase);
                        if(pb.getPlayer().getName().equalsIgnoreCase(DetectiveGame.instance.killer.getName())) {
                            Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                                public void run() {
                                    for(Player p: DetectiveGame.instance.acceptedPlayers) {
                                        TitleAPI.sendTitle(p, 20, 60, 20, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[ 살인마 패배 ]",
                                                ChatColor.BLUE + "" + ChatColor.BOLD + "살인마가 살해당했습니다");
                                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 0.1f);
                                    }
                                }
                            }, 60L);
                        }//설마....

                        if(pl.hasMetadata("DGfakepl")) {
                            pb.getPlayer().teleport(pl.getLocation());
                            CitizensAPI.getNPCRegistry().getNPC(pl).destroy();
                            pb.getPlayer().setGameMode(GameMode.ADVENTURE);
                        }
                        pb.killPlayer(ItemList.tnt, DetectiveGame.instance.killer);
                    }

                    DetectiveGame.instance.addKill();
                    DetectiveGame.instance.setKillCooltime(240);
                    CountDownTimer.sidebarCountDownWithSound(240);
                }
            }
        }
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) { event.setCancelled(true); }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if(event.getItem().getType().equals(Material.POTION)) event.setCancelled(true);
        if(event.getItem().getType().equals(Material.RAW_FISH)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
            if(event.getNewSlot() == 1) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) { event.setCancelled(true); }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(plugin.board);

        if(event.getPlayer().getName().equalsIgnoreCase(DetectiveGame.instance.killer.getName())) {
            DetectiveGame.instance.killer = event.getPlayer();
            DetectiveGame.instance.killerTeam.addPlayer(event.getPlayer());
        } else DetectiveGame.instance.teamAll.addPlayer(event.getPlayer());

        for(Player pl: plugin.acceptedPlayers) {
            if(pl.getName().equalsIgnoreCase(event.getPlayer().getName())) {
                plugin.acceptedPlayers.remove(pl);
                plugin.acceptedPlayers.add(event.getPlayer());
                break;
            }
        }
        for(Player pl: plugin.playerJobs.keySet()) {
            if(pl.getName().equalsIgnoreCase(event.getPlayer().getName())) {
                ProfessionBase prof = plugin.playerJobs.get(pl);
                plugin.playerJobs.remove(pl);
                plugin.playerJobs.put(event.getPlayer(), prof);
                break;
            }
        }
        for(PlayerBase pb: plugin.acceptedPlayerBase) {
            if(pb.getPlayer().getName().equalsIgnoreCase(event.getPlayer().getName())) {
                pb.setPlayer(event.getPlayer());
                if(pb.isDead()) {
                    event.getPlayer().setGameMode(GameMode.SPECTATOR);
                    event.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "당신은 죽었습니다. 관전자 모드로 진행합니다");
                }
                return;
            }
        }
        event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "경고: 당신은 등록되지 않은 플레이어입니다");
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event) {
        if(event.getInventory().getName().contains("인벤토리")) {
            if(event.getPlayer() instanceof Player) {
                ((Player) event.getPlayer()).playSound(event.getPlayer().getLocation(), Sound.BAT_TAKEOFF, 1, 0.65f);
            }
        }
    }

    /*@EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if(event.getDestination() instanceof PlayerInventory) {
            if(DetectiveGameUtils.getItemCount(event.getDestination()) >= 5) {
                ((Player)event.getDestination().getHolder()).sendMessage(ChatColor.RED + "아이템을 너무 많이 지니고 있어 더 이상은 들 수 없다");
                event.setCancelled(true);
            }
        }
    }*/
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            event.setCancelled(true);
            return;
        }

        //event.getWhoClicked().sendMessage("debug getInv:" + event.getInventory());

        if((!(event.isLeftClick() || event.isRightClick())) || event.isShiftClick()) {
            event.getWhoClicked().sendMessage(ChatColor.RED + "해당 동작은 금지되어 있습니다");
            event.setCancelled(true);
            return;
        }

        if(event.getRawSlot() == event.getSlot()) { //상자 클릭
            //if (event.getInventory() instanceof PlayerInventory) {
            PlayerInventory pi = event.getWhoClicked().getInventory();
            if (DetectiveGameUtils.getItemCount(pi) >= 5) {
                pi.getHolder().sendMessage(ChatColor.RED + "아이템을 너무 많이 지니고 있어 더 이상은 들 수 없다");
                event.setCancelled(true);
            }
        }

        /*
        if(event.getAction().equals(InventoryAction.PLACE_ALL) || event.getAction().equals(InventoryAction.PLACE_ONE)
                || event.getAction().equals(InventoryAction.PLACE_SOME)) {
            if(event.getClickedInventory() instanceof PlayerInventory) {
                PlayerInventory playerInv = (PlayerInventory) event.getClickedInventory();
                if (DetectiveGameUtils.getItemCount(playerInv) >= 5) {
                    playerInv.getHolder().sendMessage(ChatColor.RED + "아이템들이 너무 무거워 더 이상은 들 수 없다");
                    event.setCancelled(true);
                }
            } else if(!event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
                event.setCancelled(true);
            }
        } else if(event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)
                || event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD)
                || event.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
            if(event.getWhoClicked() instanceof Player) {
                if(!(event.getClickedInventory() instanceof PlayerInventory)) {
                    if (DetectiveGameUtils.getItemCount(event.getWhoClicked().getInventory()) >= 5) {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "아이템들이 너무 무거워 더 이상은 들 수 없다");
                        event.setCancelled(true);
                    }
                }
            }
        } else if(event.isLeftClick()) {
            if(!(event.getClickedInventory() instanceof PlayerInventory)) {
                if(DetectiveGameUtils.getItemCount(event.getWhoClicked().getInventory()) >= 5) {
                    event.getWhoClicked().sendMessage(ChatColor.RED + "아이템들이 너무 무거워 더 이상은 들 수 없다");
                    event.setCancelled(true);
                }
            }
        }*/


        if(event.getCurrentItem() != null) {
            ItemStack paper = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = paper.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "투표권");
            meta.setLore(Arrays.asList(ChatColor.GOLD + "\"투표는 탄환보다 강하다\"" + ChatColor.RESET + " - "
                    + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "에이브러햄 링컨"));
            paper.setItemMeta(meta);
            if (event.getCurrentItem().equals((new PlayerMarker()).createItem())
                    || event.getCurrentItem().equals(paper)) {
                event.setCancelled(true);
                return;
            }
            if (event.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                SkullMeta skullMeta = (SkullMeta)event.getCurrentItem().getItemMeta();
                if (event.getWhoClicked().getName().equals(skullMeta.getOwner())) {
                    if(event.getCurrentItem().containsEnchantment(Enchantment.DURABILITY)) {
                        if (event.getInventory() instanceof PlayerInventory) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }

        if(event.getInventory().getName().contains("인벤토리")) {
            event.setCancelled(true);
        } else if(event.getInventory().getName().contains("살인자는")) {
            event.setCancelled(true);
            if(event.getCurrentItem().getType().equals(Material.AIR)) return;
            if(event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                return;
            }

            ItemStack paper = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = paper.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "투표권");
            meta.setLore(Arrays.asList(ChatColor.GOLD + "\"투표는 탄환보다 강하다\"" + ChatColor.RESET + " - "
                    + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "에이브러햄 링컨"));
            paper.setItemMeta(meta);

            if(event.getWhoClicked().getInventory().contains(paper)) {
                event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() + 1);
                event.getWhoClicked().getInventory().remove(paper);
            } else return;

            boolean isFinished = true;
            for(Player p: EnvironmentEx.alivePlayers) {
                if(p.getInventory().contains(paper)) isFinished = false;
            }
            if(isFinished) {
                ItemStack[] itemStack = event.getInventory().getContents();
                ItemStack election = itemStack[0];
                boolean hasDraw = false;
                for(int i=1 ; i<itemStack.length ; i++) {
                    if(itemStack[i] == null) continue;
                    if(election.getAmount() < itemStack[i].getAmount()) {
                        hasDraw = false;
                        election = itemStack[i];
                    } else if(election.getAmount() == itemStack[i].getAmount()) {
                        hasDraw = true;
                    }
                }

                if(hasDraw) {
                    Bukkit.getScheduler().runTask(plugin, new Runnable() {
                        public void run() {
                            for (Player p : EnvironmentEx.alivePlayers) {
                                p.closeInventory();
                                TitleAPI.sendTitle(p, 20, 60, 20, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "투표가 부결되었습니다", "잠시후 재투표를 진행합니다");
                            }
                        }
                    });
                    Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                        public void run() {
                            EnvironmentEx.startBallot();
                        }
                    }, 120L);
                    return;
                }

                final SkullMeta smeta = (SkullMeta)election.getItemMeta();

                for(final Player p: DetectiveGame.instance.acceptedPlayers) {
                    Bukkit.getScheduler().runTask(DetectiveGame.instance, new Runnable() {
                        public void run() {
                            p.closeInventory();
                        }
                    });
                    TitleAPI.sendTitle(p, 20, 60, 20, ChatColor.GRAY + "" + ChatColor.BOLD + "투표가 완료되었습니다", "");
                    Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                        public void run() {
                            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "    우리는 모든 것을 다 알게 되었어!");
                            p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1.7f);
                        }
                    }, 100L);
                    Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                        public void run() {
                            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "    이 모든 일은 "
                                    + ChatColor.RED + ChatColor.BOLD + smeta.getOwner() +
                                    ChatColor.RESET + ChatColor.GOLD + ChatColor.BOLD + ", 네가 꾸민 짓이 틀림없다!");
                            p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1.7f);
                        }
                    }, 160L);
                    if(smeta.getOwner().equalsIgnoreCase(DetectiveGame.instance.killer.getName())) {
                        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                            public void run() {
                                p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "    아니 어떻게 알아낸거지? 내 계획은 완벽했다고!");
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1.7f);
                            }
                        }, 320L);
                        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                            public void run() {
                                TitleAPI.sendTitle(p, 20, 60, 20, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[ 살인마 패배 ]",
                                        ChatColor.BLUE + "" + ChatColor.BOLD + "살인마가 투표로 처형되었습니다");
                                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 0.1f);
                            }
                        }, 360L);
                    } else {
                        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                            public void run() {
                                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "    말도 안돼! 난 범인이 아니야!");
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1.7f);
                            }
                        }, 320L);

                        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                            public void run() {
                                p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "    범인은 저기 있는 "
                                        + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + DetectiveGame.instance.killer.getName()
                                        + ChatColor.RESET + ChatColor.DARK_RED + ChatColor.BOLD + " 라고!");
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1.7f);
                            }
                        }, 360L);

                        Bukkit.getScheduler().runTaskLater(DetectiveGame.instance, new Runnable() {
                            public void run() {
                                TitleAPI.sendTitle(p, 20, 60, 20, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[ 살인마 승리 ]",
                                        ChatColor.BLUE + "" + ChatColor.BOLD + "투표에서 살인마가 지목되지 않았습니다");
                                p.playSound(p.getLocation(), "mob.horse.zombie.death", 1, 0.8f);
                            }
                        }, 400L);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMoveBlock(PlayerMoveEvent event) {
        if((int)event.getFrom().getX() != (int)event.getTo().getX()
                || (int)event.getFrom().getY() != (int)event.getTo().getY()
                || (int)event.getFrom().getZ() != (int)event.getTo().getZ()) {
            Location loc = event.getPlayer().getLocation();
            if(loc.getWorld().hasStorm()) {
                Biome biome = loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ());
                if(!biome.equals(Biome.DESERT) &&
                        !biome.equals(Biome.DESERT_HILLS) &&
                        !biome.equals(Biome.DESERT_MOUNTAINS) &&
                        !biome.equals(Biome.COLD_BEACH) &&
                        !biome.equals(Biome.COLD_TAIGA) &&
                        !biome.equals(Biome.COLD_TAIGA_HILLS) &&
                        !biome.equals(Biome.COLD_TAIGA_MOUNTAINS) &&
                        !biome.equals(Biome.FROZEN_OCEAN) &&
                        !biome.equals(Biome.FROZEN_RIVER) &&
                        !biome.equals(Biome.HELL) &&
                        !biome.equals(Biome.ICE_MOUNTAINS) &&
                        !biome.equals(Biome.ICE_PLAINS) &&
                        !biome.equals(Biome.ICE_PLAINS_SPIKES)) {
                    if(loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()) <= loc.getBlockY()) {
                        PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(event.getPlayer(), plugin.acceptedPlayerBase);
                        if(!pb.isDead() && pb.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) pb.setWet();
                    }
                }
            }

            for(Location blood: plugin.bloodLocation) {
                if(blood.distance(loc) < 2 + (new Random()).nextInt(2)) {
                    PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(event.getPlayer(), plugin.acceptedPlayerBase);
                    if(!pb.isDead() && pb.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) pb.setSoakedWithBlood();
                }
            }

            if(event.getTo().getBlock().getType().equals(Material.WATER) || event.getTo().getBlock().getType().equals(Material.STATIONARY_WATER)) {
                PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(event.getPlayer(), plugin.acceptedPlayerBase);
                if(!pb.isDead() && pb.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) pb.setWet();
            }
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {//sneak -> All nametag, not sneaking -> See player nametag only
        if(event.isSneaking()) {
            if(event.getPlayer().equals(plugin.killer)) {
                plugin.npcTeam.addPlayer(plugin.killer);
            }
            if(event.getPlayer().hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                if(event.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)) return;

                event.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ZOMBIE_UNFECT, 1, 1.5f);
                TitleAPI.sendTitle(event.getPlayer(), 20, 60, 20, "", ChatColor.AQUA + "" + ChatColor.BOLD + "[은신해제]");
                event.getPlayer().sendMessage(ChatColor.BLUE + "은신을 해제했습니다");
            }
        } else {//player nametag only
            if(event.getPlayer().equals(plugin.killer)) {
                plugin.killerTeam.addPlayer(plugin.killer);
            }
        }
    }

    @EventHandler
    public void onNpcPush(NPCPushEvent event) { event.setCancelled(true); }

    @EventHandler
    public void onPlayerManipulateArmorStand(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerGameModeToggle(PlayerGameModeChangeEvent event) {
        if(event.getNewGameMode().equals(GameMode.SPECTATOR)) {//Disables spectator's team view
            event.getPlayer().setScoreboard(DetectiveGame.instance.manager.getNewScoreboard());
        } else {
            event.getPlayer().setScoreboard(DetectiveGame.instance.board);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.setKeepInventory(true);
    }
}
