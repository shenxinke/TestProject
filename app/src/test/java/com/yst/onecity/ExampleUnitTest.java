package com.yst.onecity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        String str="http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180611163627918471636.jpg?width=300&height=400@@3421";
        String[] split = str.split("@@");
        System.out.println(split[0]);
        System.out.println(split[1]);
    }
}