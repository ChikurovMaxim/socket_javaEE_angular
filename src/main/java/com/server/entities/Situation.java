package com.server.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Maksym on 1/12/2016.
 */
@Entity
@Table(name="situation")
public class Situation implements Serializable {

    public Situation() {
    }

    public Situation(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "NAME" )
    private String name;

    @Column(name = "DESCRIPTION")
    private String desc;

    public int getId() {
        return id;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "situation")
    private Collection<Metric> metricCollection;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Collection<Metric> getMetricCollection() {
        return metricCollection;
    }

    public void setMetricCollection(Collection<Metric> metricCollection) {
        this.metricCollection = metricCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Situation situation = (Situation) o;

        if (id != situation.id) return false;
        if (name != null ? !name.equals(situation.name) : situation.name != null) return false;
        return metricCollection != null ? metricCollection.equals(situation.metricCollection) : situation.metricCollection == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (metricCollection != null ? metricCollection.hashCode() : 0);
        return result;
    }
}
