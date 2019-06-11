package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResPaginationDTO<T> implements Serializable {

    private List<T> rows;

    private int total;

    public ResPaginationDTO(List<T> rows, int total) {
        this.rows = rows;
        this.total = total;
    }
}
