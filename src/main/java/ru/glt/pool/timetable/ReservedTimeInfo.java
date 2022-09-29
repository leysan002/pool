package ru.glt.pool.timetable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReservedTimeInfo {
    @JsonFormat(pattern = "HH:mm")
    private Date time;
    private Long count;

    public ReservedTimeInfo(Date time, Long count) {
        this.time = time;
        this.count = count;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
