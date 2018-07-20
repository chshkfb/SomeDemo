package com.dlsmartercity.getclassdemo;

/**
 * Time:2018/6/21 10:13
 * Description:This is TestCreateHero
 * Author:ZhangXiWei
 */
public class TestCreateHero {

    public static void main(String[] args) {
        TestCreateHero facrty = new TestCreateHero();
        hero iroman = facrty.createHero("IronMan");
        iroman.attach();
    }

    //通过接口实现,但是接口如果需要用到new关键字,这时候耦合问题又会出现
    public hero createHero(String name) {
        if ((name).equals("IronMan")) {
            return new IronMan();
        }
        if ((name).equals("Hulk")) {
            return new Hulk();
        }
        return null;
    }

    interface hero {
        public void attach();
    }

    class IronMan implements hero {

        @Override
        public void attach() {
            System.out.println("Laser Light");

        }
    }

    class Hulk implements hero {

        @Override
        public void attach() {
            System.out.println("fist");

        }
    }

}
