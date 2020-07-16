package com.zmj.javalib.lesson19_javaio_okio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/16
 * Description :
 */
class Main {

    public static void main(String[] args){
        //javaIo1();

        javaIo2();


    }

    private static void javaIo1(){
        try{
            OutputStream outputStream = new FileOutputStream("./19_io.txt");
            outputStream.write('a');
            outputStream.write('b');

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void javaIo2() {
        try (InputStream inputStream = new FileInputStream("./19_io.txt")){
//            System.out.println((char) inputStream.read());
//            System.out.println((char) inputStream.read());

            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            System.out.println(bufferedReader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
