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
public class JobDeltaForce extends ProfessionBase {
    public JobDeltaForce() {
        initProfession("델타포스", Profession.DELTA,
                ChatColor.GRAY + "당신은 은퇴한 특수요원으로서, 수많은 훈련과 실전을 겪었습니다.",
                ChatColor.GRAY + "인터넷상에서 백수로 위장한 상태입니다." + ChatColor.DARK_GREEN + " 구글링에서 백수로 표시됩니다.",
                //ChatColor.LIGHT_PURPLE + "의심되는 플레이어 1명을 근접에서 권총으로 사살할 수 있습니다.",
                ChatColor.LIGHT_PURPLE + "당신은 근접무기로는 살인당하지 않습니다. 살인마가 살인 시도시 게임이 끝납니다.",
                ChatColor.GRAY + "총기에 대한 훈련이 되어있어 "
                        + ChatColor.DARK_GREEN + "일반인보다 먼 거리에서도 총기류를 사용" + ChatColor.GRAY + "할 수 있습니다.",
                ChatColor.DARK_GREEN + "몸수색" + ChatColor.GRAY + "을 사용할 수 있습니다.",
                ChatColor.DARK_GREEN + "플레이어 상태확인" + ChatColor.GRAY + "을 사용할 수 있습니다.");
    }
}
