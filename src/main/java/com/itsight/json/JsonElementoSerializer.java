/*
package com.itsight.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.itsight.domain.jsonb.ElementoPlantilla;

import java.io.IOException;
import java.util.List;

public class JsonElementoSerializer extends JsonSerializer<List<ElementoPlantilla>> {

    @Override
    public void serialize(List<ElementoPlantilla> value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeStartArray();
        for (ElementoPlantilla model : value) {
            jgen.writeStartObject();
            jgen.writeObjectField("elementos", model);
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }
}
*/
