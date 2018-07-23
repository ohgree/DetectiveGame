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

package com.icloud.infectedduck.detectivegame.professions;

import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import org.bukkit.ChatColor;

/**
 * Created by InfectedDuck on 15. 9. 21..
 */
public class JobSalary extends ProfessionBase {
    public JobSalary() {
        initProfession("회사원", Profession.SALARY,
                ChatColor.GRAY + "지긋지긋한 나날에 지쳐 휴식을 위해 이곳에 왔습니다.",
                ChatColor.GRAY + "당신은 직장에서 터득한 " + ChatColor.DARK_GREEN + "구글링"
                        + ChatColor.GRAY + "으로 모든 플레이어의" +
                ChatColor.DARK_GREEN + " 직업을 알아낼 수 있습니다.",
                ChatColor.DARK_GREEN + "플레이어 상태확인" + ChatColor.GRAY + "을 사용할 수 있습니다."
                //ChatColor.DARK_GREEN + "몸수색" + ChatColor.GRAY + "을 사용할 수 있습니다."
        );
    }
}
