package uz.neft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.dto.MiningSystemDto;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "mining_system")
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
