package com.itsight.domain.pojo;

import java.io.Serializable;
import java.util.List;

public class MetricaVelPOJO implements Serializable {

    private String dist;

    private String[] ind;

    public MetricaVelPOJO() { }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String[] getInd() {
        return ind;
    }

    public void setInd(String[] ind) {
        this.ind = ind;
    }
}
