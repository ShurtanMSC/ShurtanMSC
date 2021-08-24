package uz.neft.entity;

import lombok.*;
import uz.neft.entity.enums.WellCategory;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "well")
public class Well extends AbsEntityInteger {

    @NotNull
    private Integer number;

    @ManyToOne
    private CollectionPoint collectionPoint;

    private Timestamp commissioningDate;
    private Timestamp drillingStartDate;

    private String horizon;
    private double altitude;
    private double depth;
    private double x;
    private double y;
    @Enumerated(EnumType.STRING)
    private WellCategory category;
}
