package com.innercircle.yeonwoo_onboarding.domain.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SurveyItemOptionIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String query = String.format("SELECT MAX(CAST(SUBSTRING(id, 9) AS INTEGER)) FROM SurveyItemOption WHERE id LIKE '%s%%'", datePrefix);
        
        Integer maxId = (Integer) session.createQuery(query,Integer.class).uniqueResult();
        int nextId = (maxId == null) ? 1 : maxId + 1;
        
        return String.format("%s%05d", datePrefix, nextId);
    }
}