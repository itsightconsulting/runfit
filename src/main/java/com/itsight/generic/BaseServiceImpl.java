package com.itsight.generic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("${aws.auths.file}")
public abstract class BaseServiceImpl<T> {

    @Value("${amazon.aws.s3.access.key}")
    protected String aws3accessKey;

    @Value("${amazon.aws.s3.secret.key}")
    protected String aws3secretKey;

    @Value("${amazon.aws.s3.prf.img.region}")
    protected String aws3region;

    @Value("${amazon.aws.s3.prf.img.bucket}")
    protected String aws3bucket;

    @Value("${amazon.aws.s3.md.rutina.media.bucket}")
    protected String aws3RoutineBucket;


    @Value("${amazon.aws.s3.tr.post.bucket}")
    protected String aws3PostBucket;

    protected T     repository;

    public BaseServiceImpl(T repository) {
        // TODO Auto-generated constructor stub
        this.repository = repository;
    }
}
