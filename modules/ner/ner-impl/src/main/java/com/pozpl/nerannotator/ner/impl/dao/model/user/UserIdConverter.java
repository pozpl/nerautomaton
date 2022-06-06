package com.pozpl.nerannotator.ner.impl.dao.model.user;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserIdConverter implements AttributeConverter<UserId, Long> {
    @Override
    public Long convertToDatabaseColumn(final UserId userId) {
        if(userId == null){
            return null;
        }
        final Long rawId = userId.getId();
        if (rawId == null){
            return rawId;
        }

        if(rawId < 0){
            throw new RuntimeException("Negative number supplied as User id: " + rawId);
        }
        return rawId;
    }

    @Override
    public UserId convertToEntityAttribute(Long dbData) {
        if (dbData == null) return null;

        return UserId.of(dbData);
    }
}