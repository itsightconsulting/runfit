package com.itsight.domain.jsonb;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PostDetalle implements Serializable {

    private Integer cliId;

    private String nomFull;

    private boolean flgLiked;

    private boolean flgFav;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date feCreac;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date feModif;

    public PostDetalle() {
    }

    public PostDetalle(Integer cliId, String nomFull, boolean flgLiked, boolean flgFav, Date feCreac, Date feModif) {
        this.cliId = cliId;
        this.nomFull = nomFull;
        this.flgLiked = flgLiked;
        this.flgFav = flgFav;
        this.feCreac = feCreac;
        this.feModif = feModif;
    }
}
