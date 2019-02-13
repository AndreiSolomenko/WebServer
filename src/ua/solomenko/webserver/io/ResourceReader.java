package ua.solomenko.webserver.io;

import ua.solomenko.webserver.exception.InternalErrorException;
import ua.solomenko.webserver.exception.NotFoundException;

import java.io.*;

public class ResourceReader {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private String webAppPath;

    public String readContent(String uri) {
        File file = new File(webAppPath, uri);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(LINE_SEPARATOR);
            }
            return content.toString();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            throw new NotFoundException();
        }catch (IOException e){
            e.printStackTrace();
            throw new InternalErrorException();
        }
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }
}
