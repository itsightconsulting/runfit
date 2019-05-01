package com.itsight.constants;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.itsight.domain.dto.*;
import com.itsight.util.ClassId;
import com.itsight.util.EntityGraphBuilder;
import com.itsight.util.EntityVisitor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThatsWhyWePlay {

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        /*String x = String.valueOf(new Date().getTime());
        System.out.println(x);
        String x1 = Base64.getUrlEncoder().encodeToString(x.getBytes());
        byte[] decodedBytes = Base64.getDecoder().decode(x1);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);*/

        /*List<VideoDTO> lstVideo = new ArrayList<>();
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

        *//** **//*
        VideoDTO video2 = new VideoDTO(111);
        video2.setNombre("Video demo con id 111");
        video2.setSubCatVideo(subCatVideo1);

        lstVideo.add(video2);

        lstVideo.forEach(v->{
            System.out.println(v.toString());
        });

        BagForestDTO forest = reconstructForest(lstVideo, 2);
        System.out.println(forest.getTreesGb().toString());*/
        /*String name = "A";
        String lName = "B";
        String nick = "C";

        String x = String.format("Contact {name=%s, lastName=%s, nickName=%s}", name, lName, nick);
        System.out.println(x);*/

        Date start = new Date();
        System.out.println(start.getTime());
        String clientRegion = "sa-east-1";
        String bucketName = "dev-runfit";
        String objectKey = "3528a426-60c7-43b5-82af-2bb6d1df955d.zip";

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAS37G4KTWCUSHASND", "LlGNF/xUZhb3jpBytfzyILhxqWnZpKIvxLeS7zTP");

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();

            // Set the presigned URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 10;
            //expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            System.out.println("Pre-Signed URL: " + url.toString());
            Date end = new Date();
            System.out.println(end.getTime());
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }

    }

    protected static BagForestDTO reconstructForest(List<VideoDTO> leaves, Integer forestId) {
        EntityGraphBuilder entityGraphBuilder = new EntityGraphBuilder(new EntityVisitor[]{
                GrupoVideoDTO.ENTITY_VISITOR, CategoriaVideoDTO.ENTITY_VISITOR, SubCategoriaVideoDTO.ENTITY_VISITOR, VideoDTO.ENTITY_VISITOR, BagForestDTO.ENTITY_VISITOR
        }).build(leaves);
        ClassId<BagForestDTO> forestClassId = new ClassId<>(BagForestDTO.class, forestId);
        return entityGraphBuilder.getEntityContext().getObject(forestClassId);
    }
}
