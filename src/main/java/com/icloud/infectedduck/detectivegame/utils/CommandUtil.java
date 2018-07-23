/*
 *  DetectiveGame420 - A bukkit plugin for Detective Games
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

package com.icloud.infectedduck.detectivegame.utils;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.items.Others.*;
import com.icloud.infectedduck.detectivegame.items.StartItems.*;
import com.icloud.infectedduck.detectivegame.lists.ItemList;
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.professions.*;
import com.icloud.infectedduck.detectivegame.scheduler.GameScheduler;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.NpcBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.NameTagVisibility;

import java.util.*;

/**
 * Created by InfectedDuck on 2015. 10. 6..
 */
public class CommandUtil implements CommandExecutor {
    DetectiveGame plugin;
    public CommandUtil(DetectiveGame plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ChatColor.GREEN +  "" + ChatColor.BOLD + "  추리게임[420] 명령어 도움말");
            sender.sendMessage(ChatColor.YELLOW + "/dg start" + ChatColor.GRAY + " - 추리게임[420]을 시작합니다");
            sender.sendMessage(ChatColor.YELLOW + "/dg setup" + ChatColor.GRAY + " - 추리게임[420]의 옵션을 설정합니다(필수)");
            sender.sendMessage(ChatColor.YELLOW + "/dg info" + ChatColor.GRAY + " - 플러그인 정보를 표시합니다");
            sender.sendMessage(ChatColor.YELLOW + "/dg license" + ChatColor.GRAY + " - 플러그인 라이센스 정보");
            sender.sendMessage(ChatColor.GRAY + "* 재시작을 위해선 맵과 서버를 초기화 해야 합니다 *");
            return true;
        } else {
            if (!(sender instanceof Player || sender instanceof BlockCommandSender)) {
                sender.sendMessage("터미널에서는 명령어를 사용할 수 없습니다.");
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "추리게임[420] - 제작자: 감염오리");
                sender.sendMessage(ChatColor.GRAY + "살인마는 맵의 NPC를 살인하고, 플레이어들은 자신의 직업에 맞는");
                sender.sendMessage(ChatColor.GRAY + "특성을 이용해 살인마를 추리해내는 플러그인입니다");
                sender.sendMessage(ChatColor.GRAY + "플레이어 살인도 기본적으로 자유롭게 가능합니다");
                sender.sendMessage(ChatColor.GRAY + "설정 > 언어 > 유니코드 글꼴 강제 사용을 켜주시길 바랍니다");
                return true;
            } else if (args[0].equalsIgnoreCase("license")) {
                sender.sendMessage(new String[] {
                        "DetectiveGame420 - A bukkit plugin for Detective Games",
                        "Copyright (C) 2015  InfectedDuck",
                        "",
                        "This program is free software: you can redistribute it and/or modify",
                        "it under the terms of the GNU General Public License as published by",
                        "the Free Software Foundation, either version 3 of the License, or",
                        "(at your option) any later version.",
                        "",
                        "This program is distributed in the hope that it will be useful,",
                        "but WITHOUT ANY WARRANTY; without even the implied warranty of",
                        "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the",
                        "GNU General Public License for more details.",
                        "",
                        "You should have received a copy of the GNU General Public License",
                        "along with this program.  If not, see <http://www.gnu.org/licenses/>.",
                        "",
                        "Contact: minjooon123@naver.com | Skype: minjooon123"});
                return true;
            } else if (args[0].equalsIgnoreCase("stat")) {
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "디버깅용이다. 제발 /dg stat 쓰지마...");//for debug purpose only
                if (plugin.isEnabled) {
                    Player pl = (Player)sender;
                    plugin.playerJobs.get(pl).showJobExplanation(pl);
                    if(plugin.killer == null) {
                        pl.sendMessage(ChatColor.LIGHT_PURPLE + " 아직 살인마가 정해지지 않았습니다");
                    } else if(plugin.killer.equals(sender)) {
                        pl.sendMessage(ChatColor.RED + " 당신은 살인마입니다. 모든 무기를 사용할 수 있습니다");
                        pl.sendMessage(ChatColor.RED + " " + (4 - plugin.getKillCount()) + "번의 살인이 더 필요합니다");
                    }

                    PlayerBase pb = DetectiveGameUtils.getPlayerBaseFromPlayer(pl, plugin.acceptedPlayerBase);
                    if(pb.isWet()) pl.sendMessage(ChatColor.GOLD + " 몸이 물에 젖어있습니다");
                    if(pb.isPanting()) pl.sendMessage(ChatColor.GOLD + " 숨소리가 거칩니다. 잠시 쉴 필요가 있습니다");
                    if(pb.isSoakedWithBlood()) pl.sendMessage(ChatColor.DARK_RED + " 피가 묻어있습니다");

                    //show player profiles
                } else {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "아직 게임이 시작하지 않았습니다");
                }
                return true;
            } else if (args[0].equalsIgnoreCase("setup")) {
                if (!sender.isOp()) {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "당신은 이 명령어에 권한이 없습니다");
                    return true;
                }
                if (plugin.isEnabled) {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "이미 게임이 시작되었습니다");
                    return true;
                }

                if (args.length == 1) {
                    String npcString = "NPC(" + plugin.spawnedNpcNum.size() + ") - ";
                    for (int i = 0; i < plugin.spawnedNpcNum.size(); i++) {
                        npcString += plugin.spawnedNpcNum.get(i) + " ";
                    }
                    sender.sendMessage(ChatColor.GRAY + npcString);
                    String fuseString = "퓨즈 위치(" + plugin.fuse.size() + ") - ";
                    for (int i = 0; i < plugin.fuse.size(); i++) {
                        if (plugin.fuse.get(i) != null) {
                            fuseString += (i+1) +
                                    ": x" + plugin.fuse.get(i).getLocation().getBlockX() +
                                    " y" + plugin.fuse.get(i).getLocation().getBlockY() +
                                    " z" + plugin.fuse.get(i).getLocation().getBlockZ() + " | ";
                        }
                    }
                    sender.sendMessage(ChatColor.GRAY + fuseString);
                    String waterString = "세면대 위치(" + plugin.tapWater.size() + ") - ";
                    for (int i = 0; i < plugin.tapWater.size(); i++) {
                        if (plugin.tapWater.get(i) != null) {
                            waterString += (i+1) +
                                    ": x" + plugin.tapWater.get(i).getLocation().getBlockX() +
                                    " y" + plugin.tapWater.get(i).getLocation().getBlockY() +
                                    " z" + plugin.tapWater.get(i).getLocation().getBlockZ() + " | ";
                        }
                    }
                    sender.sendMessage(ChatColor.GRAY + waterString);
                    if (plugin.ballotPlace != null) {
                        sender.sendMessage(ChatColor.GRAY + "투표소 위치 - " +
                                " x" + plugin.ballotPlace.getLocation().getBlockX() +
                                " y" + plugin.ballotPlace.getLocation().getBlockY() +
                                " z" + plugin.ballotPlace.getLocation().getBlockZ());
                    } else {
                        sender.sendMessage(ChatColor.GRAY + "투표소 지정되지 않음");
                    }

                    ItemStack setupWizard = ItemList.setupWizard.createItem();
                    Player player = (Player) sender;
                    if(!player.getInventory().containsAtLeast(setupWizard, 1)) {
                        player.getInventory().addItem(setupWizard);
                        player.sendMessage(ChatColor.BLUE + " " + ChatColor.BOLD + "필수블록을 설정하는 아이템을 드렸습니다. Shift + 우클릭: 랜덤 NPC배치, 그냥 우클릭: 선택한 블록에 따라 블록을 등록");
                        player.sendMessage(ChatColor.GOLD + " " + "필수블록: 퓨즈 - 철사덫 갈고리(Tripwire Hook), 세면대 - 가마솥(Cauldron), 투표소 - 인첸트 테이블(Enchantment Table)");
                        player.sendMessage(ChatColor.GOLD + " " + "선택블록(지정해주지 않으면 맵의 모든 상자를 등록합니다): 상자 - 모든 나무상자(Chest, Trapped Chest)");
                    }
                    sender.sendMessage(ChatColor.YELLOW + "추가 명령어(고급 사용자용): /dg setup [npc|fuse|tap|chest|ballot]");
                } else {
                    if (args[1].equalsIgnoreCase("npc")) {

                        if(args.length == 2) {
                            sender.sendMessage(ChatColor.YELLOW + "/dg setup npc [add|clear]");
                            return true;
                        }

                        if (args[2].equalsIgnoreCase("add")) {
                            if(args.length == 3) {
                                sender.sendMessage(ChatColor.YELLOW + "/dg setup npc add <1~20>");
                                return true;
                            } else if ((((((Player) sender).getLocation().clone().add(0, -1, 0)).getBlock()).getType() == Material.AIR)) {
                                //Sry for Long Code... It examines whether the block below the player is air.
                                sender.sendMessage(ChatColor.LIGHT_PURPLE + "이 장소에는 NPC를 만들 수 없습니다");
                            } else if (0 < Integer.parseInt(args[3]) && Integer.parseInt(args[3]) < 23) {
                                int npcNum = Integer.parseInt(args[3]);
                                if (plugin.spawnedNpcNum.contains(npcNum)) {
                                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "이미 해당 NPC가 존재합니다");
                                } else {
                                    //Spagetti Feast
                                    List<Location> locs = new ArrayList<Location>();
                                    for(int n: plugin.spawnedNpcNum) {
                                        if(NpcList.NpcList.get(n-1).getNpc().isSpawned()) {
                                            locs.add(NpcList.NpcList.get(n-1).getNpcLocation());
                                        }
                                    }
                                    Location closeLoc = DetectiveGameUtils.getNearestLocation(((Player)sender).getLocation(), locs);

                                    if(closeLoc != null) {
                                        if(closeLoc.distance(((Player)sender).getLocation()) < 3) {
                                            sender.sendMessage(ChatColor.LIGHT_PURPLE + "이미 가까운 곳에 NPC가 존재합니다");
                                            return true;
                                        }
                                    }

                                    plugin.spawnedNpcNum.add(npcNum);
                                    NpcList.NpcList.get(npcNum - 1).spawnNpc(((Player) sender).getLocation());
                                    sender.sendMessage(ChatColor.GREEN + "NPC를 성공적으로 생성했습니다");
                                }
                            } else {
                                sender.sendMessage(ChatColor.LIGHT_PURPLE + "올바른 NPC번호가 아닙니다");
                            }
                        } else if (args[2].equalsIgnoreCase("clear")) {
/**/
                            sender.sendMessage("아직 이 기능을 안만들었음. 그냥 맵 새로 열어서 서버 다시 파라");
                        } else sender.sendMessage(ChatColor.LIGHT_PURPLE + "명령어를 제대로 입력해주세요");
                    } else if (args[1].equalsIgnoreCase("chest")) {
                        ItemStack chestIndicator = new ItemStack(Material.DRAGON_EGG, 64);
                        ItemMeta mcMeta = chestIndicator.getItemMeta();
                        mcMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "추리게임 전용상자");
                        chestIndicator.setItemMeta(mcMeta);
                        ((Player) sender).getInventory().addItem(chestIndicator);
                        sender.sendMessage(ChatColor.GRAY + "인벤토리로 드린 용의 알을 맵의 상자안에 하나씩 넣어주세요");
                    } else if (args[1].equalsIgnoreCase("fuse")) {
                        if(args.length == 2) {
                            sender.sendMessage(ChatColor.YELLOW + "/dg setup fuse [add|clear]");
                            return true;
                        }
                        if (args[2].equalsIgnoreCase("add")) {
                            Block block = ((Player) sender).getTargetBlock((Set<Material>) null, 5);
                            if (block.getType() == Material.TRIPWIRE_HOOK) {
                                if (plugin.fuse.contains(block)) {
                                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "이미 등록된 퓨즈입니다");
                                    return true;
                                }
                                plugin.fuse.add(block);
                                sender.sendMessage(ChatColor.GREEN + "퓨즈를 성공적으로 추가했습니다");
                            } else {
                                sender.sendMessage(ChatColor.LIGHT_PURPLE + "철사덫 갈고리(Tripwire Hook)가 눈앞에 있어야합니다");
                            }
                        } else if (args[2].equalsIgnoreCase("clear")) {
                            plugin.fuse.clear();
                            sender.sendMessage(ChatColor.GREEN + "모든 퓨즈가 지정해제되었습니다");
                        } else sender.sendMessage(ChatColor.LIGHT_PURPLE + "명령어를 제대로 입력해주세요");
                    } else if (args[1].equalsIgnoreCase("tap")) {
                        if(args.length == 2) {
                            sender.sendMessage(ChatColor.YELLOW + "/dg setup tap [add|clear]");
                            return true;
                        }
                        if (args[2].equalsIgnoreCase("add")) {
                            Block block = ((Player) sender).getTargetBlock((Set<Material>) null, 5);
                            if (block.getType() == Material.CAULDRON) {
                                if (plugin.tapWater.contains(block)) {
                                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "이미 등록된 세면대입니다");
                                    return true;
                                }
                                plugin.tapWater.add(block);
                                sender.sendMessage(ChatColor.GREEN + "세면대를 성공적으로 추가했습니다");
                            } else {
                                sender.sendMessage(ChatColor.LIGHT_PURPLE + "가마솥(Cauldron)이 눈앞에 있어야 합니다");
                            }
                        } else if (args[2].equalsIgnoreCase("clear")) {
                            plugin.tapWater.clear();
                            sender.sendMessage(ChatColor.GREEN + "모든 세면대가 지정해제되었습니다");
                        } else sender.sendMessage(ChatColor.LIGHT_PURPLE + "명령어를 제대로 입력해주세요");
                    } else if (args[1].equalsIgnoreCase("ballot")) {
                        Block block = ((Player) sender).getTargetBlock((Set<Material>) null, 5);
                        if (block.getType() == Material.ENCHANTMENT_TABLE) {
                            plugin.ballotPlace = block;
                            sender.sendMessage(ChatColor.GREEN + "투표소를 성공적으로 설정했습니다. 투표소는 하나만 설정가능합니다.");
                        } else {
                            sender.sendMessage(ChatColor.LIGHT_PURPLE + "인첸트 테이블(Enchantment Table)가 눈앞에 있어야합니다");
                        }
                    } else sender.sendMessage(ChatColor.LIGHT_PURPLE + "명령어를 제대로 입력해주세요");
                }
                return true;
            }

            /********************* Start ***********************/

            else if (args[0].equalsIgnoreCase("start")) {
                if (!sender.isOp()) {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "당신은 이 명령어에 권한이 없습니다");
                    return true;
                }
                if (plugin.isEnabled) {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "이미 플러그인이 실행중입니다");
                } else if (plugin.spawnedNpcNum.isEmpty() || plugin.fuse.isEmpty()
                        || plugin.tapWater.isEmpty() || plugin.ballotPlace == null) {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "아직 옵션들이 충분히 설정되지 않았습니다");
                } else if (Bukkit.getServer().getOnlinePlayers().size() < 4) {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "플레이어들이 적어도 4명은 필요합니다");
                } else {
                    ItemStack chestIndicator = new ItemStack(Material.DRAGON_EGG, 1);
                    ItemMeta mcMeta = chestIndicator.getItemMeta();
                    mcMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "추리게임 전용상자");
                    chestIndicator.setItemMeta(mcMeta);
                    World world = ((Player) sender).getWorld();//no multiworld support :(
                    Chunk[] chunks = world.getLoadedChunks();
                    boolean isNotMarked = true;
                    for (Chunk ch : chunks) {
                        BlockState[] tileEntities = ch.getTileEntities();
                        for (BlockState te : tileEntities) {
                            if (te instanceof Chest) {
                                if (((Chest) te).getBlockInventory().containsAtLeast(chestIndicator, 1)) {
                                    plugin.chests.add((Chest)te.getBlock().getState());
                                    isNotMarked = false;
                                }
                                ((Chest) te).getInventory().clear();
                            }
                        }
                    }//gets all marked chests in the world
                    if(isNotMarked) {
                        for (Chunk ch : chunks) {
                            BlockState[] tileEntities = ch.getTileEntities();
                            for (BlockState te : tileEntities) {
                                if (te instanceof Chest) {
                                    plugin.chests.add((Chest) te.getBlock().getState());
                                }
                            }
                        }
                    }

                    for(World w: Bukkit.getWorlds()) {
                        w.setDifficulty(Difficulty.PEACEFUL);
                        w.setGameRuleValue("doMobSpawning", "false");
                        w.setGameRuleValue("doEntityDrops", "false");
                        final World tmp = w;
                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                            public void run() {
                                tmp.setDifficulty(Difficulty.EASY);
                            }
                        }, 5);

                    }//remove all mobs and set difficulty to EASY

                    for(Block b: plugin.fuse) {//블락비
                        for(World w : Bukkit.getWorlds()) {
                            ArmorStand marker = (ArmorStand)w.spawnEntity(b.getLocation().clone().add(0.5, -0.3, 0.5), EntityType.ARMOR_STAND);
                            marker.setSmall(true);
                            marker.setGravity(false);
                            marker.setVisible(false);
                            marker.setBasePlate(false);
                            marker.setCustomNameVisible(true);
                            marker.setCustomName(ChatColor.GOLD + "퓨즈");
                            marker.setMetadata("DGfuse", new FixedMetadataValue(plugin, 1));
                        }
                    }
                    for(Block b: plugin.tapWater) {//블락비
                        for(World w : Bukkit.getWorlds()) {
                            ArmorStand marker = (ArmorStand)w.spawnEntity(b.getLocation().clone().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
                            marker.setSmall(true);
                            marker.setGravity(false);
                            marker.setVisible(false);
                            marker.setBasePlate(false);
                            marker.setCustomNameVisible(true);
                            marker.setCustomName(ChatColor.GOLD + "세면대");
                            marker.setMetadata("DGtap", new FixedMetadataValue(plugin, 1));
                        }
                    }

                    ArmorStand marker = (ArmorStand)plugin.ballotPlace.getWorld()
                            .spawnEntity(plugin.ballotPlace.getLocation().clone().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
                    marker.setSmall(true);
                    marker.setGravity(false);
                    marker.setVisible(false);
                    marker.setBasePlate(false);
                    marker.setCustomNameVisible(true);
                    marker.setCustomName(ChatColor.GOLD + "투표소");
                    marker.setMetadata("DGballot", new FixedMetadataValue(plugin, 1));


                    plugin.board = plugin.manager.getNewScoreboard();
                    plugin.teamAll = plugin.board.registerNewTeam("DGallT");
                    plugin.npcTeam = plugin.board.registerNewTeam("DGnpcT");
                    plugin.killerTeam = plugin.board.registerNewTeam("DGkillerT");
                    //plugin.invisibleTeam = plugin.board.registerNewTeam("DGinvT");
                    plugin.teamAll.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OWN_TEAM);
                    plugin.npcTeam.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
                    plugin.killerTeam.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
                    //plugin.invisibleTeam.setNameTagVisibility(NameTagVisibility.NEVER);
                    plugin.teamAll.setCanSeeFriendlyInvisibles(false);

                    for(NpcBase nb: NpcList.NpcList) {
                        if(nb.getNpc() != null) {
                            plugin.npcTeam.addPlayer((Player) nb.getNpc().getEntity());
                        }
                    }

                    for(int npcNum : plugin.spawnedNpcNum) {
                        NpcList.NpcList.get(npcNum-1).getNpc().setProtected(false);
                    }//make NPCs Vulnerable

                    PluginManager pm = plugin.getServer().getPluginManager();
                    pm.registerEvents(plugin.basicListener, plugin);
                    pm.registerEvents(plugin.interactListener, plugin);
                    //register listeners

                    plugin.acceptedPlayers.addAll(Bukkit.getServer().getOnlinePlayers());
                    for(Player pl: plugin.acceptedPlayers) {
                        plugin.acceptedPlayerBase.add(new PlayerBase(pl));
                        pl.setGameMode(GameMode.ADVENTURE);

                        pl.setScoreboard(plugin.board);
                        plugin.teamAll.addPlayer(pl);

                        pl.getInventory().clear();
                        pl.getInventory().addItem(ItemList.myStatus.createItem(pl.getName()));
                        pl.getInventory().addItem(ItemList.playerMarker.createItem());
                        final Player player = pl;
                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                            public void run() {
                                player.updateInventory();
                            }
                        }, 10L);
                        pl.setHealth(20);
                        pl.setFoodLevel(20);
                    }

                    Random random = new Random();

                    if (plugin.acceptedPlayers.size() < 6) {
                        if (random.nextBoolean()) {// doctor jobs
                            if (random.nextInt(100) < 30) { //doctor
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(random.nextInt(plugin.acceptedPlayers.size())),
                                        new JobDoctor());
                            } else { //student
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(random.nextInt(plugin.acceptedPlayers.size())),
                                        new JobStudent());
                            }
                        } else { //detectivejobs
                            if (random.nextBoolean()) {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(random.nextInt(plugin.acceptedPlayers.size())),
                                        new JobDetective());
                            } else {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(random.nextInt(plugin.acceptedPlayers.size())),
                                        new JobPolice());
                            }
                        }
                    } else { // 2 jobs

                        int randomInt1 = random.nextInt(plugin.acceptedPlayers.size());
                        int randomInt2 = random.nextInt(plugin.acceptedPlayers.size());
                        while (randomInt1 != randomInt2) randomInt2 = random.nextInt(plugin.acceptedPlayers.size());

                        if (random.nextBoolean()) { //doc
                            if (random.nextInt(100) < 30) {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(randomInt1), new JobDoctor());
                            } else {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(randomInt1), new JobStudent());
                            }
                        } else {
                            if (random.nextBoolean()) {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(randomInt2), new JobDetective());
                            } else {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(randomInt2), new JobPolice());
                            }
                        }
                    }
                    //~ 8 player -> 1 person
                    //8+ player -> 1 doctor/student + 1 detective/police MAX

                    int unemployedNum = random.nextInt(((plugin.acceptedPlayers.size() - 2) / 2));

                    for (int i = 0; i < unemployedNum; i++) { //unemployment train choo choo!
                        int unemployedPlayer = random.nextInt(plugin.acceptedPlayers.size());
                        while (plugin.playerJobs.containsKey(plugin.acceptedPlayers.get(unemployedPlayer))) {
                            unemployedPlayer = random.nextInt(plugin.acceptedPlayers.size());
                        }
                        plugin.playerJobs.put(plugin.acceptedPlayers.get(unemployedPlayer), new JobUnemployed());
                    }

                    for (int i = 0; i < plugin.acceptedPlayers.size(); i++) {
                        if (plugin.playerJobs.containsKey(plugin.acceptedPlayers.get(i))) continue;

                        int randomNum = random.nextInt(6); //0: soldier/delta 1: engineer 2:salary
                        if (randomNum == 0) {
                            if (DetectiveGameUtils.doesContainJob(plugin.playerJobs.values(), new JobSoldier()) ||
                            DetectiveGameUtils.doesContainJob(plugin.playerJobs.values(), new JobDeltaForce())) {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(i), new JobUnemployed());
                                continue;
                            }

                            if (random.nextInt(100) < 50) {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(i), new JobSoldier());
                            } else {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(i), new JobDeltaForce());
                            }
                        } else if (randomNum == 1) {//engineer
                            if (DetectiveGameUtils.doesContainJob(plugin.playerJobs.values(), new JobEngineer())) {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(i), new JobUnemployed());
                                continue;
                            }

                            plugin.playerJobs.put(plugin.acceptedPlayers.get(i), new JobEngineer());
                        } else if (randomNum == 2) {//salary
                            if (DetectiveGameUtils.doesContainJob(plugin.playerJobs.values(), new JobSalary())) {
                                plugin.playerJobs.put(plugin.acceptedPlayers.get(i), new JobUnemployed());
                                continue;
                            }

                            plugin.playerJobs.put(plugin.acceptedPlayers.get(i), new JobSalary());
                        } else {
                            plugin.playerJobs.put(plugin.acceptedPlayers.get(i), new JobUnemployed());
                        }
                    }

                    plugin.spawnedItem.add(new Autopsy());
                    plugin.spawnedItem.add(new Investigation());

                    if (random.nextInt(100) < 50) {
                        plugin.spawnedItem.add(new CCTV());
                        plugin.spawnedItem.add(new CamViewer());
                    }
                    if (random.nextInt(100) < 70) plugin.spawnedItem.add(new Ice());
                    if (random.nextInt(100) < 50) plugin.spawnedItem.add(new LuminolSolution());
                    if (random.nextInt(100) < 50) plugin.spawnedItem.add(new LuminolSolution());
                    if (random.nextInt(100) < 70) plugin.spawnedItem.add(new Googling());

                    plugin.spawnedItem.add(new Inspection());
                    if(random.nextBoolean()) plugin.spawnedItem.add(new Inspection());
                    //plugin.spawnedItem.add(new Inspection());
                    //if(random.nextBoolean()) plugin.spawnedItem.add(new Inspection());

                    plugin.spawnedItem.add(new PlayerStatus());
                    //plugin.spawnedItem.add(new PlayerStatus());
                    //if(random.nextBoolean()) plugin.spawnedItem.add(new PlayerStatus());
                    //if(random.nextBoolean()) plugin.spawnedItem.add(new PlayerStatus());

                    plugin.spawnedItem.add(new Suppressor());
                    if(random.nextBoolean()) plugin.spawnedItem.add(new Suppressor());

                    List<ItemBase> normalItems = new ArrayList<ItemBase>();
                    List<ItemBase> killItems = new ArrayList<ItemBase>();

                    normalItems.add(ItemList.collectBlood); //ㅠㅠㅠㅠㅠ

                    for (int i = 0; i < ItemList.ItemList.size(); i++) {
                        if (ItemList.ItemList.get(i) instanceof Autopsy || ItemList.ItemList.get(i) instanceof Investigation ||
                                ItemList.ItemList.get(i) instanceof CCTV || ItemList.ItemList.get(i) instanceof CamViewer ||
                                ItemList.ItemList.get(i) instanceof Ice || ItemList.ItemList.get(i) instanceof LuminolSolution ||
                                ItemList.ItemList.get(i) instanceof Googling || ItemList.ItemList.get(i) instanceof BloodBottle ||
                                ItemList.ItemList.get(i) instanceof PlayerMarker || ItemList.ItemList.get(i) instanceof PlayerStatus ||
                                ItemList.ItemList.get(i) instanceof MyStatus || ItemList.ItemList.get(i) instanceof Inspection ||
                                ItemList.ItemList.get(i) instanceof Suppressor || ItemList.ItemList.get(i) instanceof CollectBlood ||
                                ItemList.ItemList.get(i) instanceof SetupWizard) {
                            continue;
                        }

                        if(ItemList.ItemList.get(i).getKillType() == ItemBase.KillType.NO_KILL) {
                            normalItems.add(ItemList.ItemList.get(i));
                        } else {
                            killItems.add(ItemList.ItemList.get(i));
                        }
                    }

                    long seed = System.nanoTime();
                    Collections.shuffle(killItems, new Random(seed));
                    Collections.shuffle(killItems, new Random(seed));

                    plugin.spawnedItem.addAll(normalItems);


                    /**
                     * The values need tweaking
                     */
                    for(int i=0 ; i < (killItems.size() / 3) * 2 + (random.nextInt(killItems.size() / 3)) ; i++) {
                        plugin.spawnedKillerItem.add(killItems.get(i));
                    }

                    Collections.shuffle(plugin.spawnedItem, new Random(seed));
                    Collections.shuffle(plugin.spawnedItem, new Random(seed));

                    /** Distribute items **/

                    for(ItemBase item : plugin.spawnedKillerItem) {
                        if(random.nextInt(3)==0) {
                            Player player = plugin.acceptedPlayers.get(random.nextInt(plugin.acceptedPlayers.size()));
                            if(!(DetectiveGameUtils.getItemCount(player.getInventory()) >= 5)) {
                                player.getInventory().addItem(item.createItem());
                                continue;
                            }
                        }

                        plugin.chests.get(random.nextInt(plugin.chests.size())).getInventory().addItem(item.createItem());
                    }
                    for(ItemBase item : plugin.spawnedItem) {
                        if(random.nextInt(4) == 0) {
                            Player player = plugin.acceptedPlayers.get(random.nextInt(plugin.acceptedPlayers.size()));
                            if(!(DetectiveGameUtils.getItemCount(player.getInventory()) >= 5)) {
                                player.getInventory().addItem(item.createItem());
                                continue;
                            }
                        }

                        plugin.chests.get(random.nextInt(plugin.chests.size())).getInventory().addItem(item.createItem());
                    }

                    for(Player pl: plugin.acceptedPlayers) {
                        TitleAPI.sendTitle(pl, 20, 80, 20, ChatColor.BOLD + "DetectiveGame[420]", ChatColor.ITALIC + "- Within Us -");
                    }

                    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        public void run() {
                            for (Player pl : plugin.acceptedPlayers) {
                                pl.playSound(pl.getLocation(), "mob.guardian.curse", 1, 0.1f);
                            }
                        }
                    }, (long) 57);

                    for (int i = 0; i < 10; i++) {
                        Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                            public void run() {
                                Random random = new Random();
                                String color = "";
                                switch(random.nextInt(10)) {
                                    case 0 : color += ChatColor.WHITE; break;
                                    case 1 : color += ChatColor.RED; break;
                                    case 2 : color += ChatColor.YELLOW; break;
                                    case 3 : color += ChatColor.GREEN; break;
                                    case 4 : color += ChatColor.LIGHT_PURPLE; break;
                                    case 5 : color += ChatColor.BLUE; break;
                                    case 6 : color += ChatColor.DARK_GREEN; break;
                                    case 7 : color += ChatColor.GRAY; break;
                                    case 8 : color += ChatColor.DARK_BLUE; break;
                                    case 9 : color += ChatColor.DARK_AQUA; break;
                                }
                                for(Player pl: plugin.acceptedPlayers) {
                                    TitleAPI.sendTitle(pl, 0, 5, 0, null, color + ChatColor.ITALIC + "- Within Us -");
                                }
                            }
                        }, 57 + (i * 3));
                    }
                    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        public void run() {
                            for (Player pl : plugin.acceptedPlayers) {
                                TitleAPI.sendTitle(pl, 20, 60, 20, ChatColor.WHITE + "" + ChatColor.BOLD + "직업이 설정되었습니다",
                                        ChatColor.GRAY + "" + ChatColor.BOLD + "[내 상태확인] 아이템으로 직업을 확인하세요");
                            }
                        }
                    }, 120);

                    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        public void run() {
                            for (Player pl : plugin.acceptedPlayers) {
                                TitleAPI.sendTitle(pl, 20, 60, 20, "", ChatColor.WHITE + "" + ChatColor.BOLD + "잠시 후 살인마가 결정됩니다");
                            }
                        }
                    }, 250);

                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        public void run() {
                            Random random = new Random();
                            plugin.killer = plugin.acceptedPlayers.get(random.nextInt(plugin.acceptedPlayers.size()));
                            plugin.killerTeam.addPlayer(plugin.killer);
                            TitleAPI.sendTitle(plugin.killer, 20, 60, 20, ChatColor.RED + "" + ChatColor.BOLD + "당신은 살인마입니다",
                                    ChatColor.GRAY + "" + ChatColor.BOLD + "사람들에게 들키지 않고 6명을 죽이세요");

                        }
                    }, 500);

                    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        public void run() {
                            for (Player pl : plugin.acceptedPlayers) {
                                TitleAPI.sendTitle(pl, 20, 60, 20, "", ChatColor.WHITE + "" + ChatColor.BOLD + "살인마가 결정되었습니다");
                            }
                        }
                    }, 650);

                    GameScheduler.setBloodScheduler();
                    GameScheduler.setTickTimer();
                    GameScheduler.setWeatherTimer();
                    GameScheduler.setItemDistTask();

                    plugin.isEnabled = true;
                }
                return true;
            }
        }
        return false;
    }
}
