package ru.glt.pool.timetable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.glt.pool.client.Client;

public interface TimeTableRepository extends CrudRepository<TimeTable, Integer> {
}