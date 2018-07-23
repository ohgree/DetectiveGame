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

package com.icloud.infectedduck.detectivegame.types;

import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.event.*;
import com.icloud.infectedduck.detectivegame.lists.ItemList;
import com.icloud.infectedduck.detectivegame.utils.DetectiveGameUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static com.icloud.infectedduck.detectivegame.types.ItemBase.KillType.NO_KILL;

/**
 * Created by InfectedDuck on 15. 9. 19..
 */
abstract public class ItemBase {
    public enum ItemTarget {
        CORPSE,
        BLOCK,
        PLAYER,
        NPC,
        SELF
    }
    public enum KillType {
        NO_KILL,
        POISON,
        BLADE,
        MACE,
        REMOTE_BLADE,
        REMOTE_MACE,
        SUFFOCATION,
        EXPLOSIVE,
        MAGIC
    }

    private List<ItemTarget> itemTarget = new ArrayList<ItemTarget>();
    private KillType killType = NO_KILL;
    private String itemName;
    private ItemStack ingameItem;
    private List<String> explanation = new ArrayList<String>();
    private int maxUse; //if maxUse == 0 -> Unlimited Uses
    private int useCount = 0;
    private int waitTime = 0; //used for poisoning, etc.
    private boolean isKillerItem = false;
    private List<ProfessionBase.Profession> profession = new ArrayList<ProfessionBase.Profession>();
    private UUID itemUUID;
    private List<String> corpseMessage = new ArrayList<String>();

    protected final void initItem(String itemName, ItemStack ingameItem, int maxUse,
                                  List<ProfessionBase.Profession> profession, List<ItemTarget> target, String[] explanation) {
        this.itemName = itemName;
        this.ingameItem = ingameItem;
        this.maxUse = maxUse;
        this.profession = profession;
        this.itemTarget = target;
        this.explanation = new LinkedList<String>(Arrays.asList(explanation));
        this.itemUUID = UUID.randomUUID();
        ItemList.ItemList.add(this);
    }
    protected boolean isUserValid(Player player) {
        if(this.isKillerItem) {
            return player.equals(DetectiveGame.instance.killer);
        }

        if(this.profession.contains(ProfessionBase.Profession.ALL)) return true;
        if(this.profession.contains(ProfessionBase.Profession.NONE)) {
            player.sendMessage(ChatColor.RED + "이 아이템을 사용할 수 없습니다");
            return false;
        }
        for(ProfessionBase.Profession prof: this.profession) {
            if(DetectiveGame.instance.playerJobs.get(player).getProfession().equals(prof)) return true;
        }

        if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            player.sendMessage(ChatColor.RED + "은신상태에서는 아이템을 사용할 수 없습니다");
            return false;
        }

        player.sendMessage(ChatColor.RED + "이 아이템을 사용할 수 없습니다");
        return false;
    }
    protected final void initCorpseMessage(String msgDoctor, String msgDetective) {
        corpseMessage.add(msgDoctor);
        corpseMessage.add(msgDetective);
    }
    public String getCorpseMessage(boolean isDoctor) {
        if(corpseMessage.isEmpty()) {
            if (isDoctor) {
                switch (killType) {
                    case POISON: return "피해자의 외양으로부터 어떠한 특징도 찾을 수 없다";
                    case BLADE: return "흉기의 흔적으로 입은듯한 깊은 상처를 발견했다";
                    case MACE: return "골절로 인한 내장파열, 창상, 자상의 복합적인 상처를 발견했다";
                    case REMOTE_BLADE: return "흉기의 흔적으로 입은듯한 깊은 상처를 발견했다";
                    case REMOTE_MACE: return "골절로 인한 내장파열, 창상, 자상의 복합적인 상처를 발견했다";
                    case SUFFOCATION: return "목에서 눌린 흔적을 발견했다";
                    case EXPLOSIVE: return "피해자는 형체를 알아볼 수 없을 정도로 온 몸이 찢어져있다";
                    case MAGIC: return "피해자의 외양으로부터 어떠한 특징도 찾을 수 없다";
                }
            } else {
                switch (killType) {
                    case POISON: return "주변에서 특별한 물건이나 특징을 찾을 수 없었다";
                    case BLADE: return "시체가 과하다 싶을 정도로 난도질되어 있다";
                    case MACE: return "시체의 골격이 무너져 있다. 단단한 물체로 타격한 듯 하다";
                    case REMOTE_BLADE: return "주변에서 범행에 쓰인 투사체를 발견했다";
                    case REMOTE_MACE: return "시체 주변에서 차갑고 축축한 돌을 발견했다";
                    case SUFFOCATION: return "주변의 물건들이 흐트러져있다. 피해자의 거센 저항이 있었던 것 같다";
                    case EXPLOSIVE: return "주변의 사물이 모두 그을려져 있고 사건현장 바닥이 뜨겁다";
                    case MAGIC: return "주변에서 특별한 물건이나 특징을 찾을 수 없었다";
                }
            }
        } else {
            if (isDoctor) {
                return corpseMessage.get(0);
            } else {
                return corpseMessage.get(1);
            }
        }
        return "알 수 없는 문제가 생겼습니다. 개발자에게 로그를 보내주세요";
    }
    protected final void initKillItem() { this.isKillerItem = true; }
    protected final void initKillItem(KillType killType) {
        initKillItem(killType, 0);
    }
    protected final void initKillItem(KillType killType, int waitTime) {
        this.isKillerItem = true;
        this.killType = killType;
        this.waitTime = waitTime;
    }
    @Deprecated
    protected void reduceItemDurability(Player user) {
        if (maxUse != 0) {//not infinite
            if (++useCount >= maxUse) {//break item
                for (ItemStack item : user.getInventory().getContents()) {
                    if(item == null) continue;
                    if(item.getItemMeta().getLore() == null) continue;
                    if(item.getItemMeta().getLore().containsAll(this.createItem().getItemMeta().getLore())) {
                        user.getInventory().remove(item);
                        user.playSound(user.getLocation(), Sound.ITEM_BREAK, (float) 0.5, 1);
                        break;
                    }
                }
            }
        }
    }
    protected void reduceItemDurability(Player user, boolean silent) {
        if (maxUse != 0) {//not infinite
            if (++useCount >= maxUse) {//break item
                for (ItemStack item : user.getInventory().getContents()) {
                    if(item == null) continue;
                    if(item.getItemMeta().getLore() == null) continue;
                    if(item.getItemMeta().getLore().containsAll(this.createItem().getItemMeta().getLore())) {
                        user.getInventory().remove(item);
                        user.updateInventory();//for good measure
                        if(!silent) user.playSound(user.getLocation(), Sound.ITEM_BREAK, (float) 0.5, 1);
                        break;
                    }
                }
            }
        }
    }
    public String getName() { return this.itemName; }
    public void useItem(Event event) {//only for killing... needs to be overwrited
        if(event instanceof PlayerInteractEntityEvent) {//@ Entity
            PlayerInteractEntityEvent e = (PlayerInteractEntityEvent)event;
            if(!DetectiveGame.instance.killer.equals(e.getPlayer())) return;
            if(!this.getItemTarget().contains(ItemTarget.NPC) && !this.getItemTarget().contains(ItemTarget.PLAYER)) {
                e.getPlayer().sendMessage(ChatColor.RED + "이 대상에 아이템을 사용할 수 없습니다");
                return;
            }
            if(!((e.getRightClicked() instanceof Player))) return;
            if(DetectiveGame.instance.isKillCooltime()) {
                e.getPlayer().sendMessage(ChatColor.RED + "아직 살인을 할 수 없습니다");
                return;
            }

            Player user = e.getPlayer();

            switch (getKillType()) {
                case BLADE:
                    BladeKill.kill(user,
                            (Player) e.getRightClicked(), this);
                    break;
                case MACE:
                    MaceKill.kill(user,
                            (Player) e.getRightClicked(), this);
                    break;
                case SUFFOCATION:
                    SuffocationKill.kill(user,
                            (Player) e.getRightClicked(), this);
                    break;
                case POISON:
                    PoisonKill.kill(user,
                            (Player) e.getRightClicked(), this, waitTime);
                    break;
            }
            reduceItemDurability(user, true);
        /*Projectiles have their own methods...*/
        }
        if(event instanceof PlayerInteractEvent) {//objective:tile
            PlayerInteractEvent e = (PlayerInteractEvent)event;
            if(((PlayerInteractEvent) event).getAction().equals(Action.LEFT_CLICK_BLOCK) ||
                    ((PlayerInteractEvent) event).getAction().equals(Action.LEFT_CLICK_AIR)) {
                Block block = ((PlayerInteractEvent) event).getPlayer().getTargetBlock((Set<Material>) null, 5);
                //bomb etc...
            }
        }
    }
    public KillType getKillType() { return killType; }
    protected final List<ItemTarget> getItemTarget() { return itemTarget; }
    protected final List<String> getExplanation() { return explanation; }
    public UUID getItemUUID() { return itemUUID; }
    public ItemStack createItem() {

        ItemStack item;

        if(!this.ingameItem.getType().equals(Material.SKULL_ITEM)) {
            net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(this.ingameItem.clone());
            NBTTagCompound tag = new NBTTagCompound();
            tag.setBoolean("Unbreakable", true);
            stack.setTag(tag);
            item = CraftItemStack.asCraftMirror(stack);
        }

        else item = this.ingameItem.clone();

        ItemMeta im = item.getItemMeta();

        if(isKillerItem) { im.setDisplayName(" " + ChatColor.RED + ChatColor.BOLD + itemName); }
        else { im.setDisplayName(" " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + itemName); }

        /*==Add Item Status==*/

        String type = "";
        switch (killType) {
            case NO_KILL: type = ChatColor.GREEN + " [도구]"; break;
            case POISON: type = ChatColor.GREEN + " [독극물]"; break;
            case BLADE: type = ChatColor.GREEN + " [날붙이]"; break;
            case SUFFOCATION: type = ChatColor.GREEN + " [질식 무기]"; break;
            case REMOTE_BLADE: type = ChatColor.GREEN + " [원거리 날붙이]"; break;
            case MACE: type = ChatColor.GREEN + " [둔기]"; break;
            case REMOTE_MACE: type = ChatColor.GREEN + " [원거리 둔기]"; break;
            case EXPLOSIVE: type = ChatColor.GREEN + " [폭발성 무기]"; break;
            case MAGIC: type = ChatColor.GREEN + " [마법]"; break;
        }

        String status = ChatColor.GREEN +  " 사용가능: ";
        if (isKillerItem) status += ChatColor.RED + "살인마 ";
        if (profession.contains(ProfessionBase.Profession.ALL)) status += ChatColor.GRAY + "모든직업 ";
        else {
            if (profession.contains(ProfessionBase.Profession.DOCTOR)) status += ChatColor.AQUA + "의사 ";
            if (profession.contains(ProfessionBase.Profession.STUDENT)) status += ChatColor.AQUA + "의대생 ";
            if (profession.contains(ProfessionBase.Profession.POLICE)) status += ChatColor.AQUA + "경찰 ";
            if (profession.contains(ProfessionBase.Profession.DETECTIVE)) status += ChatColor.AQUA + "탐정 ";
            if (profession.contains(ProfessionBase.Profession.SOILDER)) status += ChatColor.GRAY + "군인 ";
            if (profession.contains(ProfessionBase.Profession.DELTA)) status += ChatColor.GRAY + "델타포스 ";
            if (profession.contains(ProfessionBase.Profession.SALARY)) status += ChatColor.GRAY + "회사원 ";
            if (profession.contains(ProfessionBase.Profession.ENGINEER)) status += ChatColor.GRAY + "엔지니어 ";
            if (profession.contains(ProfessionBase.Profession.UNIMPLOYED)) status += ChatColor.GRAY + "백수 ";
        }

        status += ChatColor.GRAY + "| " + ChatColor.GREEN + "대상: ";

        if(itemTarget.contains(ItemTarget.PLAYER)) status += ChatColor.GRAY + "플레이어 ";
        if(itemTarget.contains(ItemTarget.NPC)) status += ChatColor.GRAY + "NPC ";
        if(itemTarget.contains(ItemTarget.CORPSE)) status += ChatColor.GRAY + "시체 ";
        if(itemTarget.contains(ItemTarget.BLOCK)) status += ChatColor.GRAY + "블럭 ";
        if(itemTarget.contains(ItemTarget.SELF)) status += ChatColor.GRAY + "자신 ";

        String maxTimes = ChatColor.GREEN + " 최대 사용횟수: ";
        if(this.maxUse != 0) maxTimes += "" + ChatColor.GRAY + this.maxUse;
        else maxTimes += ChatColor.GRAY + "무제한";

        List<String> exp = new ArrayList<String>();
        exp.addAll(this.explanation);
        exp.add(0, type);
        exp.add(1, status);
        exp.add(2, maxTimes);
        exp.add(ChatColor.DARK_GRAY + itemUUID.toString());
        /*====================*/

        im.setLore(exp);

        //im.spigot().setUnbreakable(true);
        item.setItemMeta(im);
        return item;
    }
}
