package com.example3.clientserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class ClientServer {
    private static final int SERVER_PORT = 11011; // 포트 -> .conf 파일에서 받을 수 있게 리팩토링
    private final String host;
    private final int port;

    private Channel serverChannel;
    private EventLoopGroup eventLoopGroup;

    public ClientServer(String host, int port) {
        this.host = host;
        this.port = port;
    }
    // connect 연결
    public void connect() throws InterruptedException{
        try {
        eventLoopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("client")); // 스레드의 갯수, 이름 설정

        Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup);

        bootstrap.channel(NioSocketChannel.class); // 채널 등록
        bootstrap.remoteAddress(new InetSocketAddress(host, port)); // 호스트와 포트 등록
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //핸들러 등록
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast(new LineBasedFrameDecoder(65535)); //decoder ??
                pipeline.addLast(new StringDecoder());
                pipeline.addLast(new StringEncoder());
                pipeline.addLast(new ClientHandler());

            }
        });
        // future를 사용하는 방법?? 알아보기 하는 역할 등등
        serverChannel = bootstrap.connect().sync().channel();

        } finally {
            
        }


    }

    // 실행
    private void start() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        String message;
        ChannelFuture future;

        while (true) {
            // 사용자 입력
            message = scanner.nextLine();

            // 서버로 전송
            future = serverChannel.writeAndFlush(message.concat("\n"));

            if ("quit".equals(message)) {
                serverChannel.closeFuture().sync();
                break;
            }
        }
        // 종료되기 전 모든 메시지가 flush 될때까지 기다림
        if( future != null) {
            future.sync();
        }
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception{
        ClientServer client = new ClientServer("127.0.0.1", SERVER_PORT); // client 객체 생성

        try {
            client.connect(); // 연결
            client.start(); // 실행

        } finally {
            client.close();
        }

    }
}
