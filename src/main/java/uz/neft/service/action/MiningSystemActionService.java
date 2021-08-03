package uz.neft.service.action;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.MiningSystemDto;
import uz.neft.dto.action.MiningSystemActionDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.action.MiningSystemAction;
import uz.neft.repository.*;
import uz.neft.repository.action.MiningSystemActionRepository;
import uz.neft.repository.constants.ConstantRepository;
import uz.neft.repository.constants.MiningSystemConstantRepository;
import uz.neft.service.Calculator;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MiningSystemActionService {
    private final MiningSystemActionRepository wellActionRepository;
    private final MiningSystemRepository miningSystemRepository;
    private final Converter converter;
    private final MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository;
    private final MiningSystemConstantRepository miningSystemConstantRepository;
    private final ConstantRepository constantRepository;
    private final UserRepository userRepository;
    private final CollectionPointRepository collectionPointRepository;
    private final MiningSystemActionRepository findAllByCollectionPoint;


    public MiningSystemActionService(MiningSystemActionRepository wellActionRepository, MiningSystemConstantRepository miningSystemConstantRepository, Converter converter, MiningSystemRepository miningSystemRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository, Calculator calculator, ConstantRepository constantRepository, UserRepository userRepository, CollectionPointRepository collectionPointRepository, MiningSystemActionRepository findAllByCollectionPoint, MiningSystemRepository miningSystemRepository1) {
        this.wellActionRepository = wellActionRepository;
        this.miningSystemRepository = miningSystemRepository;
        this.converter = converter;
        this.miningSystemMiningSystemGasCompositionRepository = miningSystemMiningSystemGasCompositionRepository;
        this.miningSystemConstantRepository = miningSystemConstantRepository;
        this.constantRepository = constantRepository;
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.findAllByCollectionPoint = findAllByCollectionPoint;

    }


    /**
     * Manually
     **/


    public HttpEntity<?> getMiningSystems() {
        try {
            List<MiningSystem> all = miningSystemRepository.findAll();

            List<MiningSystemDto> wellDtos = all.stream().map(converter::miningSysToMiningSysDto).collect(Collectors.toList());

            return converter.apiSuccess200(wellDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching all wells");
        }
    }

    /** Auto **/

    //..... from MODBUS
    //... coming soon


//    public ResponseEntity<?> test(Integer id){
//        try {
//            Optional<MiningSystem> well = miningSystemRepository.findById(id);
//            if (!well.isPresent()) return converter.apiError404();
//            Optional<MiningSystemAction> first = wellActionRepository.findFirstByMiningSystem(well.get());
//
//            if (first.isPresent()){
//                return converter.apiSuccess200(converter.wellAndMiningSystemActionToJson(well.get(), first.get()));
//            }
//            return converter.apiError409();
//        }catch (Exception e){
//            e.printStackTrace();
//            return converter.apiError409();
//        }
//
//    }
}
