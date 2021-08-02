package uz.neft.dto.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionPointAndActionsDto {

    private Integer id;

    private String name;

    // Bosim  - Рсп
    private double pressure;

    // Tempratura
    private int temperature;

    // Rasxod - Расход, м³/ч
    private double expand;


//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modified;


}
