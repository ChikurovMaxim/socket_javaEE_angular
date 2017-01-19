package com.server.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Maksym on 19.01.2017.
 */
@Entity
@Table(name = "Plains")
public class Plains implements Serializable{

    public Plains(){

    }

    public Plains(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private
    int id;

    @Column(name = "name")
    private
    String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plains plains = (Plains) o;

        return id == plains.id && (name != null ? name.equals(plains.name) : plains.name == null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
