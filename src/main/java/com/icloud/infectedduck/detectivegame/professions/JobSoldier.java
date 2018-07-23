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
public class JobSoldier extends ProfessionBase {
    public JobSoldier() {
        initProfession("군인", Profession.SOILDER,
                ChatColor.GRAY + "군복무중 간첩을 때려잡아 공로로 포상휴가를 나온 현역군인입니다.",
                //ChatColor.GRAY + "총기를 사용해 " + ChatColor.DARK_GREEN + "의심되는 플레이어 한명을 근접에서 사살할 수 있습니다.",
                //ChatColor.LIGHT_PURPLE + "단, 결백한 플레이어를 죽이면 PTSD에 걸려 심각한 제약이 생깁니다.",
                ChatColor.LIGHT_PURPLE + "당신은 근접무기로 살인당하지 않습니다. 살인마가 살인시도시 게임이 끝납니다.",
                ChatColor.GRAY + "총기에 대한 훈련이 되어있어 "
                        + ChatColor.DARK_GREEN + "일반인보다 먼 거리에서도 총기류를 사용" + ChatColor.GRAY + "할 수 있습니다",
                ChatColor.DARK_GREEN + "플레이어 상태확인" + ChatColor.GRAY + "을 사용할 수 있습니다.");
    }
}
