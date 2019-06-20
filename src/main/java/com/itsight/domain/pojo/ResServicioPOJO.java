package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ResServicioPOJO<T> implements Serializable {

    private Integer empresaTrainerId;

    private List<T> servicios;
}
