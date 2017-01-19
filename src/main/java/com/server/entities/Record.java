package com.server.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Maksym on 14.12.2016.
 */
@Entity
@Table(name = "records")
public class Record implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int Id;

    @ManyToOne(targetEntity = Users.class,fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID",referencedColumnName = "ID",insertable = false)
    private Users user;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "SIM_DATA")
    private String simData;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="SITUATION_ID", referencedColumnName = "ID",insertable = false)
    private Situation situation;

    @OneToOne
    @JoinColumn(name="Plain_ID", referencedColumnName = "ID", insertable = false)
    private Plains plains;

    public Record() {
    }

    public Record(Date date, String simData, Users user, Situation situation) {
        this.date = date;
        this.user = user;
        this.situation = situation;
    }

    public int getId() {
        return Id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSimData() {
        return simData;
    }

    public void setSimData(String simData) {
        this.simData = simData;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public Plains getPlains() {
        return plains;
    }

    public void setPlains(Plains plains) {
        this.plains = plains;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (Id != record.Id) return false;
        if (user != null ? !user.equals(record.user) : record.user != null) return false;
        if (date != null ? !date.equals(record.date) : record.date != null) return false;
        if (simData != null ? !simData.equals(record.simData) : record.simData != null) return false;
        return situation != null ? situation.equals(record.situation) : record.situation == null;

    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (simData != null ? simData.hashCode() : 0);
        result = 31 * result + (situation != null ? situation.hashCode() : 0);
        return result;
    }
}
