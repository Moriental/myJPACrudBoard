package myCrudBoard.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class baseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    public String getFormattedCreatedAt() {
        return created_at.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getFormattedUpdatedAt() {
        return updated_at.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
