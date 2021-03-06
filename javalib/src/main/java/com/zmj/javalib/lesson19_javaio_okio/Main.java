package com.zmj.javalib.lesson19_javaio_okio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.Okio;
import okio.Sink;
import okio.Source;


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
        //javaIo5();

//        nio1();
//        nio2();


//        okio1();
        okio2();

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


    private static void nio1(){
        try {
            RandomAccessFile file = new RandomAccessFile("./19_io.txt","r");
            FileChannel channel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer);
//            byteBuffer.limit(byteBuffer.position());
//            byteBuffer.position(0);
            byteBuffer.flip();

            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            byteBuffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void nio2(){
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8099));
//            serverSocketChannel.configureBlocking(false);   //配置非阻塞
//            Selector selector = Selector.open();
//            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (socketChannel.read(byteBuffer) != -1){
                byteBuffer.flip();
                //System.out.println("输出前：" + Charset.defaultCharset().decode(byteBuffer));
                socketChannel.write(byteBuffer);
                //System.out.println("输出后：" + Charset.defaultCharset().decode(byteBuffer));
                byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void okio1(){
        try(Source source = Okio.source(new File("./19_io.txt"))
            //ource source = Okio.buffer(Okio.source(new File("./19_io.txt")))
        ) {
            Buffer buffer = new Buffer();
            source.read(buffer,1024);

            //System.out.println(buffer.readUtf8());
            System.out.println(buffer.readUtf8Line());
            System.out.println(buffer.readUtf8Line());
        } catch (Exception e) {
            e.printStackTrace();
        }


        //写入数据用Sink
        try(Sink sink = Okio.sink(new File("./19_io.txt"))){
            Buffer buffer = new Buffer();

            buffer.writeUtf8("写入数据:\n");
            buffer.writeUtf8("hello OkIo");
            sink.write(buffer,buffer.size());

            //也可以写入ByteArray或InputStream数据
            byte[] byteArrayInputStream = "njiwndiewdineifvb".getBytes();
            buffer.write(byteArrayInputStream);

            InputStream inputStream = new FileInputStream(new File("./19_io.txt"));
            buffer.readFrom(inputStream);
            sink.write(buffer,buffer.size());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void okio2(){

        //java的输出io
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./19_io.txt"))){
            //objectOutputStream.writeUTF("abc");
            objectOutputStream.writeBoolean(true);
            //objectOutputStream.writeChar('0');
            //objectOutputStream.writeObject(new Throwable("Throwable"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //java结合Okio的Buffer进行io操作
        //即Okio与java传统io的对接
        Buffer buffer = new Buffer();
        try (//可以看做往buffer中写入东西，OKio的Bufffer可以被看作外部对象
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(buffer.outputStream())){
            objectOutputStream.writeUTF("abc");
            objectOutputStream.writeBoolean(true);
            objectOutputStream.writeChar('0');
            objectOutputStream.flush();     //一定要记得flush（），不然还没写入，后面是读不到数据，报错的

            ObjectInputStream objectInputStream = new ObjectInputStream(buffer.inputStream());
            System.out.println(objectInputStream.readUTF());
            System.out.println(objectInputStream.readBoolean());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
