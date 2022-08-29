package com.tzh.newspring;

@Component("autowiredField")
public class AutowiredField {
    public void print() {
        System.out.println(this.getClass() + " used autowiredfield");
    }
}
