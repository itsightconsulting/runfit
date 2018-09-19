package com.itsight.service.impl;

import com.itsight.service.DateTimeRetriever;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by josediaz on 28/10/2016.
 */
@Service
public class DateTimeRetrieverImpl implements DateTimeRetriever {

    @Override
    public Date currentTime() {
        return new Timestamp(System.currentTimeMillis());
    }
}

