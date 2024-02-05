package com.fastcampus.projectboardadmin.domain.converter;

import com.fastcampus.projectboardadmin.domain.constant.RoleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 이 클래스는 attributeConverter를 상속받아 구현한 구현체임.
 * implements를 통해 데이터베이스 컬럼과 Java 오브젝트 간의 컨버팅을 수행하는 컨버터다 라는 걸 알려준거고
 * 그것만 알려줘서는 jpa 구현체가 찾아내질 못하니 `@Converter`어노테이션을 붙여서 찾아낼 수 있게끔 만듦
 */
@Converter
public class RoleTypesConverter implements AttributeConverter<Set<RoleType>, String> {
    private static final String DELIMITER = ",";
    @Override
    public String convertToDatabaseColumn(Set<RoleType> attribute) {
        return attribute.stream().map(RoleType::name).sorted().collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<RoleType> convertToEntityAttribute(String dbData) {
        //Collectors.toUnmodifiableSet()을 사용하지 않은 이유는 데이터 update를 고려하여 변경할 수 있는 데이터로 세팅하기 위함
        return Arrays.stream(dbData.split(DELIMITER)).map(RoleType::valueOf).collect(Collectors.toSet());
    }
}
