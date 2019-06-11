package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryParamsDTO implements Serializable {

    private int limit;
    private int offset;
    private String order;
}
