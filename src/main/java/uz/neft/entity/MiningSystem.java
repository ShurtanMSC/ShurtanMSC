package uz.neft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.neft.dto.MiningSystemDto;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "mining_system")
//@Audited
//@EntityListeners(AuditingEntityListener.class)
public class MiningSystem extends AbsEntityInteger {

    @NotNull
    private String name;

    @PrePersist
    public void onPrePersist() {
        System.out.println("INSERT");
    }

    @PreUpdate
    public void onPreUpdate() {
        System.out.println("UPDATE");
    }

    @PreRemove
    public void onPreRemove() {
        System.out.println("DELETE");
    }

    public MiningSystemDto toDto(){
        return MiningSystemDto
                .builder()
                .Id(getId())
                .name(name)
                .build();
    }

}
