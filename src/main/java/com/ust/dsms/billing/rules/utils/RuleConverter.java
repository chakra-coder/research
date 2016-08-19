package com.ust.dsms.billing.rules.utils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.ust.dsms.billing.expression.GreaterThan;

public class RuleConverter implements Converter {

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(GreaterThan.class);
    }

    @Override
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        
        GreaterThan greaterThan = (GreaterThan) value;
        writer.addAttribute("expression", greaterThan.getLeftOpernad());

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        // TODO Auto-generated method stub
        return null;
    }

}
