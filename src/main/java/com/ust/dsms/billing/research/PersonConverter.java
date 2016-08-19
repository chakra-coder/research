package com.ust.dsms.billing.research;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PersonConverter implements Converter {

        public boolean canConvert(Class clazz) {
                return clazz.equals(Person.class);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
                Person person = (Person) value;
                writer.startNode("fullname");
                writer.setValue(person.getName());
                writer.endNode();
        }

        public Object unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context) {
                Person person = new Person();
                reader.moveDown();
                person.setName(reader.getValue());
                reader.moveUp();
                return person;
        }

}