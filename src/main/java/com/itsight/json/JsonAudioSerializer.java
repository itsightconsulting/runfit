package com.itsight.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.itsight.domain.Audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonAudioSerializer extends StdSerializer<List<Audio>> {


    public JsonAudioSerializer() {
        this(null);
    }

    public JsonAudioSerializer(Class<List<Audio>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Audio> audios,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException {

        List<Audio> lstAudios = new ArrayList<>();
        for (Audio audio : audios) {
            Audio au = new Audio();
            au.setId(audio.getId());
            au.setNombre(audio.getNombre());
            au.setRutaWeb(audio.getRutaWeb());
            au.setDuracion(audio.getDuracion());
            lstAudios.add(au);
        }
        generator.writeObject(lstAudios);
    }
}
