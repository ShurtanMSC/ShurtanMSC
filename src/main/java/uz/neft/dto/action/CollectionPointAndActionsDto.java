package uz.neft.dto.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.neft.dto.CollectionPointDto;
import uz.neft.entity.CollectionPoint;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionPointAndActionsDto {

    private CollectionPointDto collectionPointDto;
    private CollectionPointActionDto collectionPointActionDto;

//    private Integer CPointid;
//    private Long id;
//
//    private String name;
//
//    // Bosim  - Рсп
//    private double pressure;
//
//    // Tempratura
//    private int temperature;
//
//    // Rasxod - Расход, м³/ч
//    private double expand;


//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modified;


}
