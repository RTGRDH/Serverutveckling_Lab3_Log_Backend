package Log_Backend.demo.bo;

import Log_Backend.demo.db.LogDB;

import java.util.ArrayList;

public class Log {
    private int id;
    private String title;
    private String content;
    private User user;

    public Log(int _id, String _title, String _content, User _user) {
        this.id = _id;
        this.title = _title;
        this.content = _content;
        this.user = _user;
    }

    public static ArrayList<Log> getAllLogs() {
        return LogDB.getAllLogs();
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static boolean createLog(String title, String content, UsersEntity currUser) {
        return LogDB.createLog(title, content, currUser);
    }

    public static ArrayList<Log> getLogs(UsersEntity currentUser) {
        return LogDB.getLogs(currentUser);
    }
    public static ArrayList<Log> getOtherLogs(String currentUser) { return LogDB.getOtherLogs(currentUser); }
}
