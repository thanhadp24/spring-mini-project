package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskService {
    private static final Logger logger = LoggerFactory.getLogger("SCHEDULE_TASK_SERVICE");
    @Scheduled(fixedRate = 30000)
    public void performScheduledTask() {
        logger.info("System running");
    }
}
