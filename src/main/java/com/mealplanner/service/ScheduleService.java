package com.mealplanner.service;

import com.mealplanner.domain.Schedule;
import com.mealplanner.repository.ScheduleRepository;
import com.mealplanner.service.dto.ScheduleDTO;
import com.mealplanner.service.mapper.ScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Schedule.
 */
@Service
@Transactional
public class ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    /**
     * Save a schedule.
     *
     * @param scheduleDTO the entity to save
     * @return the persisted entity
     */
    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        log.debug("Request to save Schedule : {}", scheduleDTO);

        Schedule schedule = scheduleMapper.toEntity(scheduleDTO);
        schedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    /**
     * Get all the schedules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Schedules");
        return scheduleRepository.findAll(pageable)
            .map(scheduleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ScheduleDTO> findByDateBetween(LocalDate firstDate, LocalDate secondDate) {
        List<Schedule> schedules = scheduleRepository.findAllByDateBetween(firstDate, secondDate);
        return schedules.stream().map(scheduleMapper::toDto).collect(Collectors.toList());
    }


    /**
     * Get one schedule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ScheduleDTO> findOne(Long id) {
        log.debug("Request to get Schedule : {}", id);
        return scheduleRepository.findById(id)
            .map(scheduleMapper::toDto);
    }

    /**
     * Delete the schedule by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Schedule : {}", id);
        scheduleRepository.deleteById(id);
    }
}
