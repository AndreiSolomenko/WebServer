package ua.solomenko.webserver.server;

import ua.solomenko.webserver.entity.Request;
import ua.solomenko.webserver.exception.BadRequestException;
import ua.solomenko.webserver.exception.InternalErrorException;
import ua.solomenko.webserver.exception.NotFoundException;
import ua.solomenko.webserver.io.ResourceReader;
import ua.solomenko.webserver.util.RequestParser;
import ua.solomenko.webserver.util.ResponseWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

public class RequestHandler {

    private BufferedReader reader;
    private BufferedWriter writer;
    private String webAppPath;

    public void handle() {
        // write response
        ResponseWriter responseWriter = new ResponseWriter(writer);

        try {
            // parse request
            RequestParser requestParser = new RequestParser();
            Request request = requestParser.parseRequest(reader);

            // read resource
            ResourceReader resourceReader = new ResourceReader();
            resourceReader.setWebAppPath(webAppPath);
            String content = resourceReader.readContent(request.getUri());

            // write content
            responseWriter.writeSuccessContent(content);
        } catch (BadRequestException e) {
            e.printStackTrace();
            responseWriter.writeBadRequestResponse();
        } catch (NotFoundException e) {
            e.printStackTrace();
            responseWriter.writeNotFoundResponse();
        } catch (Exception e) {
            e.printStackTrace();
            responseWriter.writeInternalServerError();
        }
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }
}