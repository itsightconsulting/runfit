package com.itsight.generic;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.pojo.AwsStresPOJO;
import com.itsight.util.Enums;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

import static com.itsight.util.Enums.FileExt.JPEG;

public interface BaseService<T, V> {

    T save(T entity);

    T update(T entity);

    T findOne(V id);

    T findOneWithFT(V id);

    void delete(V id);

    List<Integer> findIdsByFlagActivo(boolean flagActivo);

    List<T> findAll();

    List<T> findByNombre(String nombre);

    List<T> findByNombreContainingIgnoreCase(String nombre);

    List<T> findByDescripcionContainingIgnoreCase(String descripcion);

    List<T> findByFlagActivo(boolean flagActivo);

    List<T> findByFlagEliminado(boolean flagEliminado);

    List<T> findByIdsIn(List<V> ids);

    List<T> listarPorFiltro(String comodin, String estado, String fk);

    String registrar(T entity, String wildcard) throws CustomValidationException;

    String actualizar(T entity, String wildcard) throws CustomValidationException;

    void actualizarFlagActivoById(V id, boolean flagActivo);

    default String subirFile(MultipartFile file, Integer id, String uuid, String extension) throws CustomValidationException {
        return null;
    }

    default String subirFiles(MultipartFile[] files, Integer id, String uuid, String extension) throws CustomValidationException {
        return null;
    }

    default boolean uploadImageToAws3(MultipartFile file, AwsStresPOJO credentials, final Logger logger) {
        if (!file.isEmpty()) {
            String extension;
            if(credentials.getExtension() != null && !credentials.getExtension().equals("")){
                extension = credentials.getExtension();
            //}
            //if(file.getOriginalFilename().equals("blob")){
            //    extension = "." + credentials.getExtension();//Al enum JPEG='.jpg' se le hace un substring a partir de 1
            } else {
                extension = "."+file.getContentType().split("/")[1];
            }

            try {
                String objectName = credentials.getUuid()+extension;
                String fullPath = credentials.getPrefix()+objectName;

                AmazonS3 amazonS3 = AmazonS3ClientBuilder
                        .standard()
                        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAccessKeyId(), credentials.getSecretKey())))
                        .withRegion(credentials.getRegion())
                        .build();

                int maxUploadThreads = 5;

                TransferManager tm = TransferManagerBuilder
                        .standard()
                        .withS3Client(amazonS3)
                        .withMultipartUploadThreshold((long) (5 * 1024 * 1024))
                        .withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
                        .build();

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                /*metadata.setContentDisposition("attachment");*/

                PutObjectRequest request = new PutObjectRequest(
                    credentials.getBucket(),
                    fullPath,
                    file.getInputStream(),
                    metadata).withCannedAcl(CannedAccessControlList.PublicRead);

                Upload upload = tm.upload(request);
                upload.waitForCompletion();
                /*S3Object obj = amazonS3.getObject(new GetObjectRequest(credentials.getBucket(),fullPath));
                System.out.println(obj.getObjectMetadata().getVersionId());*/
                return true;
            } catch (IllegalStateException e) {
                logger.warn(e.getMessage());
            } catch (AmazonClientException e) {
                logger.warn(e.getMessage());
            } catch (InterruptedException e) {
                logger.warn(e.getMessage());
            } catch (IOException e){
                logger.warn(e.getMessage());
            }
        }
        return false;
    }

    default boolean uploadMultipleToAws3(MultipartFile[] files, AwsStresPOJO credentials, final Logger logger) {
        AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAccessKeyId(), credentials.getSecretKey())))
                .withRegion(credentials.getRegion())
                .build();

        int maxUploadThreads = 5;

        TransferManager tm = TransferManagerBuilder
                .standard()
                .withS3Client(amazonS3)
                .withMultipartUploadThreshold((long) (5 * 1024 * 1024))
                .withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
                .build();
        String[] uuids = credentials.getUuid().split("\\|");
        for(int i=0; i<files.length;i++){
            MultipartFile file = files[i];
            if (!file.isEmpty()) {
                String extension;

                if(file.getOriginalFilename().equals("blob")){//Aquí es necesario mantener el else file.getContentType() ya que mediante este metodo se podrían subir diferentes tipos de archivos(imgs,pdfs,docs)
                    extension = credentials.getExtension();
                }else{
                    if(file.getContentType().startsWith("image")){
                        extension = JPEG.get();
                    }else{
                        extension = "."+file.getContentType().split("/")[1];
                    }
                }
                String objectName = uuids[i]+extension;
                String fullPath = credentials.getPrefix()+objectName;
                try {
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(file.getSize());

                    PutObjectRequest request = new PutObjectRequest(
                            credentials.getBucket(),
                            fullPath,
                            file.getInputStream(),
                            metadata).withCannedAcl(CannedAccessControlList.PublicRead);

                    Upload upload = tm.upload(request);
                    upload.waitForCompletion();
                } catch (IllegalStateException e) {
                    logger.warn("Path file: "+fullPath);
                    logger.warn(e.getMessage());
                } catch (AmazonClientException e) {
                    logger.warn("Path file: "+fullPath);
                    logger.warn(e.getMessage());
                } catch (InterruptedException e) {
                    logger.warn("Path file: "+fullPath);
                    logger.warn(e.getMessage());
                } catch (IOException e){
                    logger.warn("Path file: "+fullPath);
                    logger.warn(e.getMessage());
                }

            }
        }
        return true;
    }
}
