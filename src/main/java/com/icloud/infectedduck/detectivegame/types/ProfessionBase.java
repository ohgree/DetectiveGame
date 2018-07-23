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

import com.icloud.infectedduck.detectivegame.lists.ProfessionList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by InfectedDuck on 15. 9. 19..
 */
abstract public class ProfessionBase {
    public static enum Profession {
        ALL,
        NONE,
        DOCTOR,
        STUDENT,
        POLICE,
        DETECTIVE,
        SOILDER,
        DELTA,
        SALARY,
        ENGINEER,
        UNIMPLOYED
    }
    private String name;
    private Profession profession;
    private String[] explanation;
    protected final void initProfession(String name, Profession profession, String ... explanation) {
        this.profession = profession;
        this.name = name;
        this.explanation = new String[explanation.length];
        this.explanation = explanation.clone();
        ProfessionList.ProfessionList.add(this);
    }
    public void showJobExplanation(Player player) {

        String name;
        name = ChatColor.GREEN + " 당신의 직업은 ";
        if (profession.equals(Profession.DOCTOR) || profession.equals(Profession.STUDENT)
                || profession.equals(Profession.POLICE) || profession.equals(Profession.DETECTIVE)) {
            name += ChatColor.AQUA + "" + ChatColor.BOLD + this.name;
        } else { name += ChatColor.DARK_GRAY + "" + ChatColor.BOLD + this.name; }

        name += ChatColor.GREEN + "입니다.";

        player.sendMessage(name);
        for(String exp : this.explanation) {
            player.sendMessage(" " + exp);
        }
    }
    public String getProfessionName() { return name; }
    protected Profession getProfession() { return profession; }
}
