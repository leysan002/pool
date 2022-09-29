package ru.glt.pool.timetable;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "timetable")
public class TimeTable {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "client_id")
    private Integer clientId;
    @Column(name = "datetime")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date datetime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setUser(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
