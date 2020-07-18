package com.zmj.javalib.lesson19_javaio_okio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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

        //javaIo2();

        //javaIo3();
        //javaIo4();
        javaIo5();

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
        //将需要回收的资源放在try（）中，不必再finally{}中进行释放了
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

    private static void javaIo3(){
        try(OutputStream outputStream = new FileOutputStream("./19_io.txt");
            Writer writer = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(writer)){

            bufferedWriter.write('a');
            bufferedWriter.write('b');
//            bufferedWriter.flush();     //将当前数据写入文件     载文件关闭的时候会自动执行flush功能

        }catch (Exception e){

        }
    }

    private static void javaIo4(){
        try(InputStream inputStream = new FileInputStream("./19_io.txt");
            OutputStream outputStream = new FileOutputStream("./19_newIo.txt")){
            byte[] data = new byte[1024];
            int len;
            while ((len = inputStream.read(data)) != -1){
                outputStream.write(data,0,len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 简单的基于tcp层的socket通信
     * 此方法是服务端
     *
     * 户端需要打开dos窗口执行  telnet localhost 8099  (注意端口号钱前不能加：，否则失败)
     */
    private static void javaIo5(){
        try {
            ServerSocket serverSocket = new ServerSocket(8099);
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.ISO_8859_1));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.ISO_8859_1));
            String data;
            while ((data = reader.readLine()) != null){
                writer.write("server:");
                writer.write(data);
                writer.write("\n");
                writer.flush();     //将写完的数据冲出去，否则有可能处于缓冲区，而客户端收不到数据
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
