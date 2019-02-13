package ua.solomenko.webserver.util;

import ua.solomenko.webserver.entity.HttpMethod;
import ua.solomenko.webserver.entity.Request;
import ua.solomenko.webserver.exception.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public Request parseRequest(BufferedReader reader){

        try {
            Request request = new Request();
            String requestLine = reader.readLine();
            injectUriAndMethod(request, requestLine);
            injectHeaders(request, reader);
            return request;
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException();
        }
    }

    void injectUriAndMethod(Request request, String requestLine){
        String[] splitRequestLine = requestLine.split(" ");
        request.setMethod(HttpMethod.getByName(splitRequestLine[0]));
        request.setUri(splitRequestLine[1]);
    }

    void injectHeaders(Request request, BufferedReader reader) throws IOException{
        Map <String, String> headers = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null){
            if(line.isEmpty()){
                break;
            }
            String[] splitedHeader = line.split(": ");
            headers.put(splitedHeader[0], splitedHeader[1]);
        }
        request.setHeaders(headers);
    }
}
