package com.server.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Maksym on 1/12/2016.
 */
@Entity
@Table(name="plain_model")
public class PlainModel implements Serializable {

    public PlainModel() {
    }

    public PlainModel(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "NAME" ,unique = true)
    private String name;

    @OneToMany (mappedBy = "plainModel")
    private Collection<Metric> metrics;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(Collection<Metric> metrics) {
        this.metrics = metrics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlainModel that = (PlainModel) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return metrics != null ? metrics.equals(that.metrics) : that.metrics == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (metrics != null ? metrics.hashCode() : 0);
        return result;
    }
}
