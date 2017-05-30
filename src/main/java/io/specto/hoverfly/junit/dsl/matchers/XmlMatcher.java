package io.specto.hoverfly.junit.dsl.matchers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.specto.hoverfly.junit.core.model.FieldMatcher;
import io.specto.hoverfly.junit.dsl.HoverflyDslException;

import java.io.IOException;

public class XmlMatcher implements RequestFieldMatcher {

    private static final XmlMapper XML_MAPPER = new XmlMapper();
    private String pattern;
    private FieldMatcher fieldMatcher;

    private XmlMatcher(String pattern) {
        this.pattern = pattern;
        this.fieldMatcher = new FieldMatcher.Builder().xmlMatch(pattern).build();
    }


    @Override
    public FieldMatcher getFieldMatcher() {
        return fieldMatcher;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    static RequestFieldMatcher createFromString(String value) {
        validateXml(value);
        return new XmlMatcher(value);
    }

    private static void validateXml(String value) {
        try {
            XML_MAPPER.readTree(value);
        } catch (IOException e) {
            throw new HoverflyDslException("Fail to create JsonMatcher from invalid Xml string: " + value);
        }
    }
}
