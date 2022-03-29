package com.example.triatlon.domain;

import com.example.triatlon.domain.Entity;

import java.util.Objects;

public class Proba extends Entity<Long> {
    private String name;
    private Long idArbitru;

    public Proba(String name, Long idArbitru) {
        this.name = name;
        this.idArbitru = idArbitru;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdArbitru() {return this.idArbitru;}

    public void setIdArbitru(Long idArbitru) {this.idArbitru = idArbitru;}

    @Override
    public String toString() {
        return "Proba{ " +
                "id = " + id + '\'' +
                "name = " + name + '\'' +
                "id Arbitru = " + idArbitru +'\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proba that)) return false;
        return getName().equals(that.getName()) &&
                getIdArbitru().equals(that.getIdArbitru());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}

