package com.tlv8.system.help;

public class UserRequest
{
  private String space;
  private String controller;
  private String operation;

  public UserRequest(String space, String controller, String operation)
  {
    this.space = space;
    this.controller = controller;
    this.operation = operation;
  }

  public String getSpace()
  {
    return this.space;
  }

  public void setSpace(String space)
  {
    this.space = space;
  }

  public String getController()
  {
    return this.controller;
  }

  public void setController(String controller)
  {
    this.controller = controller;
  }

  public String getOperation()
  {
    return this.operation;
  }

  public void setOperation(String operation)
  {
    this.operation = operation;
  }

  public String getPackage()
  {
    return this.space + "." + this.controller;
  }

  public String getKey()
  {
    return this.space + "." + this.controller + "." + this.operation;
  }

  public String getControllerPackage()
  {
    return this.space + "." + this.controller + "Controller";
  }

  public String getBeanPackage(String pack)
  {
    return (pack != null ? pack : new StringBuilder().append(this.space).append(".").append(this.controller).toString()) + "Bean";
  }

  public String toString()
  {
    return "/" + this.space + "/" + this.controller + "/" + this.operation;
  }
}
