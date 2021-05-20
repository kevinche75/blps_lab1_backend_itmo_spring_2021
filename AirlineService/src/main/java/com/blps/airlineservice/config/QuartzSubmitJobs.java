package com.blps.airlineservice.config;

import com.blps.airlineservice.jobs.ReportJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzSubmitJobs {
    private static final String CRON_EVERY_FIVE_MINUTES = "0/5 0 * ? * * *";

    @Bean(name = "reportStats")
    public JobDetailFactoryBean jobMemberStats() {
        return QuartzConfig.createJobDetail(ReportJob.class, "Member Statistics Job");
    }

    @Bean(name = "reportStatsTrigger")
    public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("reportStats") JobDetail jobDetail) {
        return QuartzConfig.createTrigger(jobDetail, 30000, "Report Statistics Trigger");
    }


//    @Bean(name = "memberClassStatsTrigger")
//    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("reportStats") JobDetail jobDetail) {
//        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_FIVE_MINUTES, "Class Statistics Trigger");
//    }
}