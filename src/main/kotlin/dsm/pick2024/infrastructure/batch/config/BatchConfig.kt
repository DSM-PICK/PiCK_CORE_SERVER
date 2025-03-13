package dsm.pick2024.infrastructure.batch.config

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.infrastructure.batch.processor.NeisMealProcessor
import dsm.pick2024.infrastructure.batch.reader.NeisMealReader
import dsm.pick2024.infrastructure.batch.reader.NeisScheduleReader
import dsm.pick2024.infrastructure.batch.writer.MealItemWriter
import dsm.pick2024.infrastructure.batch.writer.NeisScheduleItemWriter
import dsm.pick2024.infrastructure.feign.client.dto.response.NeisMealRow
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
class BatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val neisMealReader: NeisMealReader,
    private val neisMealProcessor: NeisMealProcessor,
    private val mealItemWriter: MealItemWriter,
    private val neisScheduleReader: NeisScheduleReader,
    private val neisScheduleItemWriter: NeisScheduleItemWriter
) {

    @Bean
    fun neisScheduleJob(): Job {
        return jobBuilderFactory.get("neisScheduleJob")
            .incrementer(RunIdIncrementer())
            .flow(neisScheduleStep())
            .end()
            .build()
    }

    @Bean
    fun neisScheduleStep(): Step {
        return stepBuilderFactory.get("neisScheduleStep")
            .chunk<List<Schedule>, List<Schedule>>(100)
            .reader(neisScheduleReader)
            .writer(neisScheduleItemWriter)
            .build()
    }

    @Bean
    fun neisMealJob(): Job {
        return jobBuilderFactory.get("neisMealJob")
            .incrementer(RunIdIncrementer())
            .flow(neisMealStep())
            .end()
            .build()
    }

    @Bean
    fun neisMealStep(): Step {
        return stepBuilderFactory.get("neisMealStep")
            .chunk<NeisMealRow, Meal>(10)
            .reader(neisMealReader)
            .processor(neisMealProcessor)
            .writer(mealItemWriter)
            .build()
    }
}
