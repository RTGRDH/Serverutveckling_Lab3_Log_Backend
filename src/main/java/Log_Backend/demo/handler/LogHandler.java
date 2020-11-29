package Log_Backend.demo.handler;

import Log_Backend.demo.bo.Log;
import Log_Backend.demo.bo.UsersEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LogHandler {
    public static boolean createLog(String title, String content, String currUser) throws ProtocolException {
        UsersEntity currentUser = getUser(currUser);
        if(Log.createLog(title, content, currentUser)){
            return true;
        }
        return false;
    }

    /**
     * Gets a persons personal logs and returns them as a list
     * @param currUser
     * @return
     */
    public static ArrayList<Log> getLogs(String currUser) throws ProtocolException {
        UsersEntity currentUser = getUser(currUser);
        ArrayList<Log> logs = Log.getLogs(currentUser);
        return logs;
    }

    /**
     * Gets all other persons logs and removes the current users log from displaying.
     * Returns a list of logs
     * @param currentUser
     * @return
     */
    public static List<Log> getOtherLogs(String currentUser) {
        return Log.getOtherLogs(currentUser);
    }

    private static UsersEntity getUser(String name) throws ProtocolException {
        HttpURLConnection connection = null;
        try {
            //Koppla till users container
            connection = (HttpURLConnection) new URL("http://user-backend:8002/getUser?name=" + name).openConnection();

            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();


            if (responseCode == 200) {
                ObjectMapper mapper = new ObjectMapper();
                UsersEntity user = mapper.readValue(connection.getInputStream(), UsersEntity.class);
                System.out.println("Got user: " + user.getUsername() + " from API call");
                return user;
            }
            // an error happened
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

}
