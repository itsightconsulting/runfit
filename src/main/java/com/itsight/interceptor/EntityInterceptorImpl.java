package com.itsight.interceptor;

import com.itsight.domain.base.AuditingEntity;
import com.itsight.service.DateTimeRetriever;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

public class EntityInterceptorImpl extends EmptyInterceptor implements EntityInterceptor {
    /**
     *
     */
    private static final long serialVersionUID = 8160823652337870429L;
    @Autowired
    DateTimeRetriever dateTimeRetriever;


    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof AuditingEntity) {

            for (int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];

                if (propertyName.equals("creador")) {
                    /*Optional<Authentication> optSc =  Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
                    if(optSc.isPresent()){*/
                        state[i] = SecurityContextHolder.getContext().getAuthentication().getName();
                    /*}else{
                        state[i] = "InitialSeeder";
                    }*/
                } else if (propertyName.equals("fechaCreacion")) {
                    state[i] = currentTime();
                }
            }
        }
        return true;
    }

    private Date currentTime() {
        if (dateTimeRetriever == null) return new Timestamp(System.currentTimeMillis());
        return dateTimeRetriever.currentTime();
    }


    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
         if (entity instanceof AuditingEntity) {
            for (int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];

                if (propertyName.equals("modificadoPor")) {
                    currentState[i] = SecurityContextHolder.getContext().getAuthentication().getName();
                } else if (propertyName.equals("fechaModificacion")) {
                    currentState[i] = currentTime();
                }
            }
        }
        return true;
    }
}
