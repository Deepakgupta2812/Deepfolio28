package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.VisitorStats;
import com.portfolio.deepak.repository.VisitorStatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitorStatsService {

    private static final long STATS_ID = 1L;
    private final VisitorStatsRepository visitorStatsRepository;

    public VisitorStatsService(VisitorStatsRepository visitorStatsRepository) {
        this.visitorStatsRepository = visitorStatsRepository;
    }

    private VisitorStats getStats() {
        return visitorStatsRepository.findById(STATS_ID)
                .orElseGet(() -> visitorStatsRepository.save(new VisitorStats()));
    }

    /**
     * Increments the visit counter by one every time the public portfolio
     * home page is loaded, and returns the updated total.
     */
    @Transactional
    public synchronized long recordVisit() {
        VisitorStats stats = getStats();
        stats.setVisitCount(stats.getVisitCount() + 1);
        visitorStatsRepository.save(stats);
        return stats.getVisitCount();
    }

    public long getVisitCount() {
        return getStats().getVisitCount();
    }
}
