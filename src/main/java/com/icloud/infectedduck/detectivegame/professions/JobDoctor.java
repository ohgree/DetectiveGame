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
public class JobDoctor extends ProfessionBase {
    public JobDoctor() {
        initProfession("의사", Profession.DOCTOR,
                ChatColor.GRAY + "큰 병원을 운영하면서 생긴 돈으로 휴가를 왔습니다.",
                ChatColor.GRAY + "당신은 " + ChatColor.DARK_GREEN + "부검" + ChatColor.GREEN + "으로 시체의 사인을 밝혀낼 수 있으며,",
                ChatColor.DARK_GREEN + "루미놀 용액" + ChatColor.GRAY + "을 사용해 혈흔을 검출할 수 있습니다.",
                ChatColor.DARK_GREEN + "플레이어 상태확인" + ChatColor.GRAY + "을 사용할 수 있습니다.");
    }
}
