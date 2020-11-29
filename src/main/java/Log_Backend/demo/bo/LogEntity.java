package Log_Backend.demo.bo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "log", schema = "server_db")
public class LogEntity {
    private int id;
    private String title;
    private String content;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 45)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 45)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "userId") //var "id" förut
    private Log_Backend.demo.bo.UsersEntity userId;

    public void setUserId(Log_Backend.demo.bo.UsersEntity user){ this.userId = user; }
    public Log_Backend.demo.bo.UsersEntity getUserId(){ return this.userId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntity logEntity = (LogEntity) o;
        return id == logEntity.id &&
                Objects.equals(title, logEntity.title) &&
                Objects.equals(content, logEntity.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content);
    }
}
