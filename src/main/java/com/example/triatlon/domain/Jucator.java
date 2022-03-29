package com.example.triatlon.domain;

import java.util.Objects;

public class Jucator extends Entity<Long> {
    private String firstName;
    private String lastName;
    private int totalPuncte;

    public Jucator(String firstName, String lastName, Integer total_puncte) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalPuncte = total_puncte;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public Integer getTotalPuncte() {
        return this.totalPuncte;
    }
    public void setFirstName(String firstName) {
        this.firstName =firstName;
    }
    public void setLastName(String lastName) {
        this.lastName =lastName;
    }
    public void setTotalPuncte(int totalPuncte) {
        this.totalPuncte =totalPuncte;
    }

    @Override
    public String toString() {
        return "Jucator{" +
                "id = " + id + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", totalPuncte=" + totalPuncte +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Jucator that)) return false;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getTotalPuncte().equals(that.getTotalPuncte());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getTotalPuncte());
    }
}
