package com.server.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Maksym on 27.11.2016.
 */

@Entity
@Table(name="metrics")
public class Metric implements Serializable {

    public Metric() {
    }

    public Metric(String name, Double value, Situation situation) {
        this.name = name;
        this.value = value;
        this.situation = situation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name="NAME" )
    private String name;

    @Column(name = "VALUE")
    private Double value;

    @ManyToOne(targetEntity = Situation.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "SI", referencedColumnName = "ID")
    private Situation situation;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metric metric = (Metric) o;

        if (id != metric.id) return false;
        if (name != null ? !name.equals(metric.name) : metric.name != null) return false;
        if (value != null ? !value.equals(metric.value) : metric.value != null) return false;
        return situation != null ? situation.equals(metric.situation) : metric.situation == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (situation != null ? situation.hashCode() : 0);
        return result;
    }
}
