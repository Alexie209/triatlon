package com.example.triatlon.domain;

import com.example.triatlon.domain.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParticipantProba extends Entity<Long> {
    private Long idJucator;
    private Long idProba;
    private int puncteStranse;

    public ParticipantProba(Long idJucator, Long idProba, int puncteStranse) {
        this.idJucator = idJucator;
        this.idProba = idProba;
        this.puncteStranse = puncteStranse;
    }
    public Long getIdJucator() {
        return this.idJucator;
    }
    public void setIdJucator(Long idJucator) {
        this.idJucator = idJucator;
    }
    public Long getIdProba() {
        return this.idProba;
    }
    public void setIdProba(Long idProba) {
        this.idProba = idProba;
    }
    public Integer getPuncteStranse() {
        return this.puncteStranse;
    }
    public void setPuncteStranse(int puncteStranse) {
        this.puncteStranse = puncteStranse;
    }
    @Override
    public String toString() {
        return "ParticipantProba{ " +
                "id = " + id + '\'' +
                "id jucator = " + idJucator + '\'' +
                "id proba = " + idProba + '\'' +
                "puncte stranse = " + puncteStranse + '\'' +
                "}";
    }
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ParticipantProba that)) return false;
        return getIdJucator().equals(that.getIdJucator()) &&
                getIdProba().equals(that.getIdProba()) &&
                getPuncteStranse().equals(that.getPuncteStranse());
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
