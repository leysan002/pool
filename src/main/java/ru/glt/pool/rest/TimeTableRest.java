package ru.glt.pool.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glt.pool.timetable.ReservedTimeInfo;
import ru.glt.pool.timetable.TimeTable;
import ru.glt.pool.timetable.TimeTableService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path="/timetable")
public class TimeTableRest {

    @Autowired
    private TimeTableService timeTableService;

    @GetMapping(path = "all")
    public ResponseEntity<List<ReservedTimeInfo>> getAll(@RequestParam(name="date")@DateTimeFormat(pattern="dd/MM/yyyy") Date date) {
        return new ResponseEntity(timeTableService.getAll(date), HttpStatus.OK);
    }

    @GetMapping(path = "available")
    public ResponseEntity<List<ReservedTimeInfo>> getAvailable(@RequestParam(name="date") @DateTimeFormat(pattern="dd/MM/yyyy") Date date) {
        return new ResponseEntity(timeTableService.getAvailable(date), HttpStatus.OK);
    }

    @PostMapping(path = "reserve")
    public ResponseEntity<Integer> reserve(@RequestBody TimeTable timeTable) {
        try {
            return new ResponseEntity(timeTableService.reserve(timeTable), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "cancel")
    public ResponseEntity<Integer> cancel(@RequestBody TimeTable timeTable) {
        try {
            timeTableService.cancel(timeTable);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
