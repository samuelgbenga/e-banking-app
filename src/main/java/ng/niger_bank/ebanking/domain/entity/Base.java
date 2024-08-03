package ng.niger_bank.ebanking.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@EnableJpaAuditing
@Getter
@Setter
public abstract class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime dateTimeCreated;


    @LastModifiedBy
    private LocalDateTime dateTimeModified;


    // just overriding the object.equal method
    @Override
    public boolean equals(Object obj) {
       if(this == obj) return true;
       if(obj == null || getClass() != obj.getClass()) return false;

       Base that = (Base) obj;
        return Objects.equals(id, that.id);
    }

    @PrePersist
    @PreUpdate
    public void prePersist(){
        if(dateTimeCreated == null){
            dateTimeCreated = LocalDateTime.now();
        }
        dateTimeModified = LocalDateTime.now();
    }

    @Override
    public int hashCode(){
        return id != null ? id.hashCode() : 0;
    }
}
