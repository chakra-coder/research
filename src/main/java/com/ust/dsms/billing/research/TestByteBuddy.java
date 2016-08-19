package com.ust.dsms.billing.research;

import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

import java.lang.reflect.Method;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

public class TestByteBuddy {
  
  private static final String FORMAT_DOUBLE ="[+-]*([0-9]*[.])?[0-9]+";
  private static final String FORMAT_LONG ="[+-]*([0-9]*)+";
  private static final String FORMAT_DATE ="[0-9-]+";

  public static void main(String[] args) throws IllegalAccessException, Exception {

/*    System.out.println("12.33".matches(FORMAT_DOUBLE));
    System.out.println("-12.33".matches(FORMAT_DOUBLE));
    System.out.println(".33".matches(FORMAT_DOUBLE));
    System.out.println("1233".matches(FORMAT_LONG));
    System.out.println("-1233".matches(FORMAT_LONG));
    System.out.println("12.33".matches(FORMAT_LONG));*/
      
      TestByteBuddy buddy=new TestByteBuddy();
      buddy.test();
   
    
  }

  private void test() throws Exception, IllegalAccessException {
      
    Object newClass = new ByteBuddy()
    .subclass(Object.class)
    .name("example.Type")
    .method(named("foo")).intercept(FixedValue.value("Two!"))
    .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("Three!"))
    .make()
    .load(getClass().getClassLoader())
    .getLoaded()
    .newInstance();
    
    Method[] methods = newClass.getClass().getMethods();
    for (Method method : methods) {
      System.out.println(method.getName());  
    }
  }
   
  
  
}

