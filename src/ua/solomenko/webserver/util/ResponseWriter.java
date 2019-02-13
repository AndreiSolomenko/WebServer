package ua.solomenko.webserver.util;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {
    private static final String LINE_END = "\n";
    private BufferedWriter writer;

    public ResponseWriter (BufferedWriter writer){
        this.writer = writer;
    }

    public void writeSuccessContent(String content){
        try {
            writer.write("HTTP/1.1 200 OK");
            writer.write(LINE_END);
            writer.write(LINE_END);
            writer.write(content);
            writer.flush();
        }catch (IOException e){
            writeInternalServerError();
        }
    }

    public void writeBadRequestResponse(){
        try{
            this.writer.write("HTTP/1.1 400 Bad Request");
            this.writer.flush();
        }catch (IOException e){
            writeInternalServerError();
        }
    }

    public void writeNotFoundResponse(){
        try{
            writer.write("HTTP/1.1 404 Not Found");
            writer.flush();
        }catch (IOException e){
            writeInternalServerError();
        }
    }

    public void writeInternalServerError(){
        try {
            writer.write("HTTP/1.1 500 Internal Server Error");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}