package com.itsight.constants;

import com.itsight.domain.dto.*;
import com.itsight.util.ClassId;
import com.itsight.util.EntityGraphBuilder;
import com.itsight.util.EntityVisitor;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ThatsWhyWePlay {

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        /*String x = String.valueOf(new Date().getTime());
        System.out.println(x);
        String x1 = Base64.getUrlEncoder().encodeToString(x.getBytes());
        byte[] decodedBytes = Base64.getDecoder().decode(x1);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);*/

        List<VideoDTO> lstVideo = new ArrayList<>();
        VideoDTO video = new VideoDTO(1);
        video.setNombre("Video demo con id 1");

        SubCategoriaVideoDTO subCatVideo = new SubCategoriaVideoDTO(2);
        subCatVideo.setNombre("Sub cat video demo con id 2");
        subCatVideo.setVideos(lstVideo);
        video.setSubCatVideo(subCatVideo);

        CategoriaVideoDTO catVid = new CategoriaVideoDTO(3);
        catVid.setNombre("Cat video demo con id 3");
        subCatVideo.setCategoriaVideo(catVid);

        GrupoVideoDTO gruVid = new GrupoVideoDTO(4);
        gruVid.setNombre("Grupo video demo con id 4");
        catVid.setGrupoVideo(gruVid);

        BagForestDTO bagFor = new BagForestDTO(2);
        gruVid.setForest(bagFor);

        lstVideo.add(video);


        VideoDTO video1 = new VideoDTO(11);
        video1.setNombre("Video demo con id 11");

        SubCategoriaVideoDTO subCatVideo1 = new SubCategoriaVideoDTO(22);
        subCatVideo1.setNombre("Sub cat video demo con id 22");
        subCatVideo1.setVideos(new ArrayList<>());
        video1.setSubCatVideo(subCatVideo1);

        CategoriaVideoDTO catVid1 = new CategoriaVideoDTO(33);
        catVid1.setNombre("Cat video demo con id 33");
        subCatVideo1.setCategoriaVideo(catVid1);

        gruVid = new GrupoVideoDTO(44);
        gruVid.setNombre("Grupo video demo con id 44");
        catVid1.setGrupoVideo(gruVid);

        gruVid.setForest(bagFor);

        lstVideo.add(video1);

        /** **/
        VideoDTO video2 = new VideoDTO(111);
        video2.setNombre("Video demo con id 111");
        video2.setSubCatVideo(subCatVideo1);

        lstVideo.add(video2);

        lstVideo.forEach(v->{
            System.out.println(v.toString());
        });

        BagForestDTO forest = reconstructForest(lstVideo, 2);
        System.out.println(forest.getTreesGb().toString());

    }

    protected static BagForestDTO reconstructForest(List<VideoDTO> leaves, Integer forestId) {
        EntityGraphBuilder entityGraphBuilder = new EntityGraphBuilder(new EntityVisitor[]{
                GrupoVideoDTO.ENTITY_VISITOR, CategoriaVideoDTO.ENTITY_VISITOR, SubCategoriaVideoDTO.ENTITY_VISITOR, VideoDTO.ENTITY_VISITOR, BagForestDTO.ENTITY_VISITOR
        }).build(leaves);
        ClassId<BagForestDTO> forestClassId = new ClassId<>(BagForestDTO.class, forestId);
        return entityGraphBuilder.getEntityContext().getObject(forestClassId);
    }
}
