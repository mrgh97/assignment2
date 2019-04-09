package com.example.jpademo.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "workers")
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id ;
    @Column(length = 32,name = "Name")
    private String name;
    @Column(length = 32,name = "Dependency")
    private String dependency;
    @Column(length = 32,name = "isCome")
    private boolean come;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public boolean isCome() {
        return come;
    }

    public void setCome(boolean come) {
        this.come = come;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dependency='" + dependency + '\'' +
                ", come=" + come +
                '}';
    }
}
