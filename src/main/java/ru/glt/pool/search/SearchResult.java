package ru.glt.pool.search;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SearchResult {
    private Integer orderId;
    private Integer clientId;
    private String clientName;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date datetime;

    public SearchResult(Integer orderId, Integer clientId, String clientName, Date datetime) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.datetime = datetime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
