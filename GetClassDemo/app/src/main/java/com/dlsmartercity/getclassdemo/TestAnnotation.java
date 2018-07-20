package com.dlsmartercity.getclassdemo;

/**
 * Time:2018/6/26 13:26
 * Description:This is TestAnnotation
 * Author:ZhangXiWei
 */
public class TestAnnotation {

    public @interface TestOne{
        public int id() default 1;
    }

    @TestOne(id = 2)
    public class TestOneDemo{
        public void t(){
        }
    }

    public @interface TestTwo{

    }

    @TestTwo
    public class TestTwoDemo{

    }

}
