package uz.neft.dto.special;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionPointAndWells {
    private Integer collectionPointId;
    private double temperature;
    private double pressure;
    private List<WellActionLite> wellList;
}
