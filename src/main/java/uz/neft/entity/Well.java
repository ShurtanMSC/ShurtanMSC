package uz.neft.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import uz.neft.entity.enums.WellCategory;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    public Well(@NotNull Integer number, CollectionPoint collectionPoint) {
        this.number = number;
        this.collectionPoint = collectionPoint;
    }

    @Column(columnDefinition = "date")
    @DateTimeFormat(pattern="dd.MM.yyyy")
    private Date commissioningDate;


    @Column(columnDefinition = "date")
    @DateTimeFormat(pattern="dd.MM.yyyy")
    private Date drillingStartDate;

    private String horizon;
    private double altitude;
    private double depth;
    private double x;
    private double y;
    @Enumerated(EnumType.STRING)
    private WellCategory category;
    private double c;
}
