package com.eParking.Egesha.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Rent")
public class Rent {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "AgentId", nullable = false)
    private Integer agentId;

    @Column(name = "Location", nullable = false)
    private String location;

    @Column(name = "dates", nullable = false)
    private LocalDate dates;

    @Column(name = "operator", nullable = false)
    private String operator;

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDates() {
        return dates;
    }

    public void setDates(LocalDate dates) {
        this.dates = dates;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
