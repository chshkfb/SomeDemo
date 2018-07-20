package com.dlsmartercity.getclassdemo;

/**
 * Time:2018/6/21 10:05
 * Description:This is TestNewCreateHero
 * Author:ZhangXiWei
 */
public class TestNewCreateHero {

    public static void create(String name) {
        TestNewCreateHero facrty = new TestNewCreateHero();
        Hero hero = facrty.createHero(name);
        hero.attack();
    }

    //其实反射的初衷不是方便你去创建一个对象,而是让你在写代码的时候可以更加灵活,
    // 降低耦合,提高代码的自适应能力.
    public Hero createHero(String name) {
        try {
            Class<?> cls = Class.forName(name);
            Hero hero = (Hero) cls.newInstance();
            return hero;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    class IroMan implements Hero {

        @Override
        public void attack() {
            System.out.println("Laser Light");

        }
    }

    class Hulk implements Hero {

        @Override
        public void attack() {
            System.out.println("Fist");

        }
    }

    interface Hero {
        public void attack();
    }

}
