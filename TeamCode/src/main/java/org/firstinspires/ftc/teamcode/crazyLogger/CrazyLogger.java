package org.firstinspires.ftc.teamcode.crazyLogger;

import java.net.ServerSocket;

import fi.iki.elonen.NanoHTTPD;

public class CrazyLogger {
    NanoHTTPD httpd;
    public CrazyLogger() {
        httpd = new NanoHTTPD(3000) {
            @Override
            public Response serve(IHTTPSession session) {
                if (session.getUri().endsWith("/ws")) {
                    // It's a web socket connection request
                    //new Response()
                } else {
                }
                return newFixedLengthResponse("oops");
            }
        };
    }
}
