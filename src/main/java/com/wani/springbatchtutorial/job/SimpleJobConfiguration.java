package com.wani.springbatchtutorial.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration // Spring Batch의 모든 Job은 @Configuration으로 등록해서 사용해야 한다.
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob") // simpleJob이란 이름의 BatchJob을 생성한다. // Job의 이름은 별도로 지정하지 않고 Builder를 통해 지정합니다.
                .start(simpleStep1())
                .build();
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1") // simpleStep1 이란 이름의 BatchStep을 생성합니다 // jobBuilderFactory.get("simpleJob")와 마찬가지로 builder를 통해 이름을 지정합니다.
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>>> Tis is Step1");
                    return RepeatStatus.FINISHED;
                })) // Step안에서 수행될 기능들을 명시합니다. Tasklet은 Step안에서 단일로 수행될 커스텀한 기능들을 선언할 때 사용합니다.
                .build();
    }
}
