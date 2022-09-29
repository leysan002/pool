package ru.glt.pool.search;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SearchConditions {
    private String clientName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date from;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date to;

    public SearchConditions(String clientName, @JsonFormat(pattern = "dd/MM/yyyy") Date from, @JsonFormat(pattern = "dd/MM/yyyy") Date to) {
        this.clientName = clientName;
        this.from = from;
        this.to = to;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
