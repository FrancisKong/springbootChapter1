package com.linfengda.sb.chapter1.common.proxy.jdk;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-12-24 15:21
 */
public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println("call doSomething()");
    }
}
