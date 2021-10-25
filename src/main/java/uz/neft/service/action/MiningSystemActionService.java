package uz.neft.service.action;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.MiningSystemDto;
import uz.neft.dto.action.MiningSystemActionDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.action.MiningSystemAction;
import uz.neft.repository.*;
import uz.neft.repository.action.MiningSystemActionRepository;
import uz.neft.repository.constants.ConstantRepository;
import uz.neft.repository.constants.MiningSystemConstantRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.service.Calculator;
import uz.neft.utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MiningSystemActionService {
    private final MiningSystemRepository miningSystemRepository;
    private final Converter converter;
    private final MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository;
    private final MiningSystemConstantRepository miningSystemConstantRepository;
    private final ConstantRepository constantRepository;
    private final UserRepository userRepository;
    private final CollectionPointRepository collectionPointRepository;
    private final MiningSystemActionRepository miningSystemActionRepository;
    private final ForecastGasRepository forecastGasRepository;


    public MiningSystemActionService(MiningSystemActionRepository miningSystemActionRepository, MiningSystemConstantRepository miningSystemConstantRepository, Converter converter, MiningSystemRepository miningSystemRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository, Calculator calculator, ConstantRepository constantRepository, UserRepository userRepository, CollectionPointRepository collectionPointRepository, MiningSystemActionRepository findAllByCollectionPoint, MiningSystemRepository miningSystemRepository1, ForecastGasRepository forecastGasRepository) {
        this.miningSystemRepository = miningSystemRepository;
        this.converter = converter;
        this.miningSystemMiningSystemGasCompositionRepository = miningSystemMiningSystemGasCompositionRepository;
        this.miningSystemConstantRepository = miningSystemConstantRepository;
        this.constantRepository = constantRepository;
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.miningSystemActionRepository = miningSystemActionRepository;
        this.forecastGasRepository = forecastGasRepository;
    }


    public ResponseEntity<?> findByIdWithAction(Integer id) {
        try {
            if (id == null) return converter.apiError400("Mining system id is null!");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent())
                return converter.apiError404("No mining system was found with id " + id + "!");
            Optional<MiningSystemAction> action = miningSystemActionRepository.findFirstByMiningSystemOrderByCreatedAtDesc(miningSystem.get());
            return converter.apiSuccess200(new ObjectWithActionsDto(miningSystem.get(), action));
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> allWithActions() {
        try {
            List<MiningSystem> miningSystemList = miningSystemRepository.findAll();
            List<ObjectWithActionsDto> list = new ArrayList<>();
            miningSystemList.forEach(m -> list.add(new ObjectWithActionsDto(m, miningSystemActionRepository.findFirstByMiningSystemOrderByCreatedAtDesc(m))));
            return converter.apiSuccess200(list);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> allWithActionsByMiningSystem(Integer id) {
        try {
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            List<MiningSystemAction> miningSystemActions =miningSystemActionRepository.findAllByMiningSystemOrderByCreatedAtDesc(miningSystem.get());

            Stream<MiningSystemActionDto> miningSystemActionDtoStream = miningSystemActions.stream().map(converter::miningsystemActionToMiningSystemActionDto);

            return converter.apiSuccess200(miningSystemActionDtoStream);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
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
