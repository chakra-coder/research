package com.ust.dsms.billing.research;

import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class MyClassAliasingMapper extends MapperWrapper {

  public MyClassAliasingMapper(Mapper wrapped) {
    super(wrapped);
  }
  
  @Override
  public Class realClass(String elementName) {
      return super.realClass(elementName);
  }

  @Override
  public String serializedClass(Class type) {
      return super.serializedClass(type);
  }

}
