/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itsight.domain.dto;

import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;

import java.util.ArrayList;
import java.util.List;

/**
 * BagForest - BagForest
 *
 * @author Vlad Mihalcea
 */

public class BagForestDTO implements Identifiable {

    public static EntityVisitor<BagForestDTO, Identifiable> ENTITY_VISITOR = new EntityVisitor<BagForestDTO, Identifiable>(BagForestDTO.class) {
    };

    private Integer id;

    private List<GrupoVideoDTO> treesGb = new ArrayList<>();

    public BagForestDTO(){}

    public BagForestDTO(Integer id){
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public List<GrupoVideoDTO> getTreesGb() {
        return treesGb;
    }

    public void setTreesGb(List<GrupoVideoDTO> treesGb) {
        this.treesGb = treesGb;
    }
}
