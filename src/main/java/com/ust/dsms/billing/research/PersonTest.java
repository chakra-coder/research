package com.ust.dsms.billing.research;

import com.thoughtworks.xstream.XStream;

public class PersonTest {

        public static void main(String[] args) {
            
                Double a = new Double("10.5");
                Double b = new Double("10.5");
                
                System.out.println(a.equals(b));
            
                Person person = new Person();
                person.setName("Guilherme");
                person.setWork("Programer");
                
                XStream xstream = new XStream();

                XStream xStream = new XStream();
                xStream.registerConverter(new PersonConverter());
                xStream.alias("person", Person.class);
                
                
                
                System.out.println(xStream.toXML(person));
                
//                person=(Person) xStream.fromXML("<person> <Programer>Guilherme</Programer></person>");
//                System.out.println(person);
        }

}