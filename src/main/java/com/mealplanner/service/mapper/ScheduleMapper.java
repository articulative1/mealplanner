package com.mealplanner.service.mapper;

import com.mealplanner.domain.Schedule;
import com.mealplanner.service.dto.ScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Schedule and its DTO ScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {MealMapper.class})
public interface ScheduleMapper extends EntityMapper<ScheduleDTO, Schedule> {

    @Mapping(source = "meal.id", target = "mealId")
    @Mapping(source = "meal.name", target = "mealName")
    ScheduleDTO toDto(Schedule schedule);

    @Mapping(source = "mealId", target = "meal")
    Schedule toEntity(ScheduleDTO scheduleDTO);

    default Schedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Schedule schedule = new Schedule();
        schedule.setId(id);
        return schedule;
    }
}
