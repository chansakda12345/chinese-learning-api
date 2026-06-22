package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.LeaderboardDTO;

public interface LeaderboardService {

    List<LeaderboardDTO> getLeaderboard();

    List<LeaderboardDTO> getTop10();

    LeaderboardDTO getCurrentUserRank();
}