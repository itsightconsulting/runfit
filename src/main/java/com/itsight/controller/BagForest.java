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

package com.itsight.controller;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.CategoriaEjercicio;
import com.itsight.domain.GrupoVideo;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BagForest - BagForest
 *
 * @author Vlad Mihalcea
 */
@Entity
public class BagForest implements Identifiable {

    public static EntityVisitor<BagForest, Identifiable> ENTITY_VISITOR = new EntityVisitor<BagForest, Identifiable>(BagForest.class) {
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "forest", orphanRemoval = true)
    private List<CategoriaEjercicio> trees = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "forest", orphanRemoval = true)
    private List<GrupoVideo> treesGb = new ArrayList<>();

    public BagForest(){}

    public BagForest(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public List<CategoriaEjercicio> getTrees() {
        return trees;
    }

    public void setTrees(List<CategoriaEjercicio> trees) {
        this.trees = trees;
    }

    public void setId(int id){
        this.id = id;
    }

    public List<GrupoVideo> getTreesGb() {
        return treesGb;
    }

    public void setTreesGb(List<GrupoVideo> treesGb) {
        this.treesGb = treesGb;
    }
}
