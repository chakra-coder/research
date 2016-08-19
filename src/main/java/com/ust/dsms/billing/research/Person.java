package com.ust.dsms.billing.research;

public class Person {

  private String name;
  private String work;

  public String getName() {
          return name;
  }

  public void setName(String name) {
          this.name = name;
  }

  public String getWork() {
    return work;
  }

  public void setWork(String work) {
    this.work = work;
  }

  @Override
  public String toString() {
    return "Person [name=" + name + ", work=" + work + "]";
  }

}