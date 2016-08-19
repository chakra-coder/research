package com.ust.dsms.billing.rules.utils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.ust.dsms.billing.expression.Rule;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapToAttributesConverter implements Converter {

    public MapToAttributesConverter() {
    }

    @Override
    public boolean canConvert(Class type) {
        return Map.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Map<String, Rule> map =  (Map<String, Rule>) source;
        for (Entry<String, Rule> entry : map.entrySet()) {
            writer.addAttribute(entry.getKey(), entry.getValue().toString());
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Map<String, Rule> map = new HashMap<>();
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            String key = reader.getAttributeName(i);
        }
        return map;
    }
}