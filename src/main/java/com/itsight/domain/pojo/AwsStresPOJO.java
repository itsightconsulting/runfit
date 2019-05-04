package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AwsStresPOJO {
    private String accessKeyId;
    private String secretKey;
    private String region;
    private String bucket;
    private String prefix;
    private String uuid;

}
