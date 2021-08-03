package uz.neft.service.action;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.CollectionPointDto;
import uz.neft.dto.WellDto;
import uz.neft.dto.action.CollectionPointAndActionsDto;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.*;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.WellAction;
import uz.neft.entity.variables.*;
import uz.neft.repository.*;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.repository.constants.ConstantRepository;
import uz.neft.repository.constants.MiningSystemConstantRepository;
import uz.neft.service.Calculator;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WellActionService {
    private final WellActionRepository wellActionRepository;
    private final WellRepository wellRepository;
    private final Converter converter;
    private final MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository;
    private final MiningSystemConstantRepository miningSystemConstantRepository;
    private final ConstantRepository constantRepository;
    private final UserRepository userRepository;
    private final CollectionPointRepository collectionPointRepository;


    public WellActionService(WellActionRepository wellActionRepository, MiningSystemConstantRepository miningSystemConstantRepository, WellRepository wellRepository, Converter converter, MiningSystemRepository miningSystemRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository, Calculator calculator, ConstantRepository constantRepository, UserRepository userRepository, CollectionPointRepository collectionPointRepository) {
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
        this.converter = converter;
        this.miningSystemMiningSystemGasCompositionRepository = miningSystemMiningSystemGasCompositionRepository;
        this.miningSystemConstantRepository = miningSystemConstantRepository;
        this.constantRepository = constantRepository;
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
    }


    /**
     * Manually
     **/
    public ResponseEntity<?> addManually(User user, WellActionDto dto) {

        Optional<Well> well = wellRepository.findById(dto.getWellId());
//        if (user == null) return converter.apiError();
        if (!well.isPresent()) return converter.apiError404("Well not found!");

        Integer miningSystemId = well.get().getCollectionPoint().getUppg().getMiningSystem().getId();
        MiningSystem miningSystem = well.get().getCollectionPoint().getUppg().getMiningSystem();


        /**
         * List miningSystemGasCompositions
         **/
        List<MiningSystemGasComposition> miningSystemGasCompositions = miningSystemMiningSystemGasCompositionRepository.findAllByMiningSystem(miningSystem);

        /**
         * List molarFractions
         **/
        List<Double> molarFractions = miningSystemGasCompositions.stream().map(MiningSystemGasComposition::getMolarFraction).collect(Collectors.toList());

        /**
         * List criticalPressure
         **/
        List<Double> criticalPressure = miningSystemGasCompositions.stream().map(MiningSystemGasComposition::getGasComposition).map(GasComposition::getCriticalPressure).collect(Collectors.toList());

        /**
         * List criticalTemperature
         **/
        List<Double> criticalTemperature = miningSystemGasCompositions.stream().map(MiningSystemGasComposition::getGasComposition).map(GasComposition::getCriticalTemperature).collect(Collectors.toList());

        /**
         * Р_ПКР
         **/
        double P_pkr = Calculator.pseudoCriticalPressureOrTemperature(molarFractions, criticalPressure);

        /**
         * T_ПКР
         **/
        double T_pkr = Calculator.pseudoCriticalPressureOrTemperature(molarFractions, criticalTemperature);

        /**
         * Asosiy kiritiladigan bosim P_u
         **/
        double P_u = dto.getPressure();

        /**
         * Asosiy kiritiladigan temperatura T_u
         **/
        double T_u = dto.getTemperature() + 273;

        /**
         * P_pr
         **/
        double P_pr = Calculator.reducedPressure(P_u, P_pkr);

        /**
         * T_pr
         **/
        double T_pr = Calculator.reducedPressure(T_u, T_pkr);

        /**
         * roGas   -   ρ_газа – плотность газа при стандартных условиях (standart
         * sharoitda gaz zichligi), kg/m3
         **/

        Constant constRoGas = constantRepository.findByName(ConstantNameEnums.RO_GAS);
        MiningSystemConstant roGas = miningSystemConstantRepository.findByMiningSystemAndConstant(miningSystem, constRoGas);

        /**
         * roAir  -  ρ_возд – плотность воздуха при стандартных условиях (standart
         * sharoitda havo zichligi), kg/m3
         **/
        Constant constRoAir = constantRepository.findByName(ConstantNameEnums.RO_AIR);
        MiningSystemConstant roAir = miningSystemConstantRepository.findByMiningSystemAndConstant(miningSystem, constRoAir);

        /**
         * Относительная плотность газа ( ρ_отн )
         **/
        double Ro_otn = Calculator.relativeDensity(roGas.getValue(), roAir.getValue());

        /**
         * Z - Коэффициент сверхсжимаемости газа
         **/
        double Z = Calculator.superCompressFactor(P_u, T_u, Ro_otn);

        /**
         * delta - Поправочный коэффициент, зависящий от приведенных
         * давления и температуры (δ)
         **/
        double delta = Calculator.correctionFactor_P_T(T_pr, P_pr);

        //MUAMMO
        double C = 88;

        /**
         * D_well - Средний дебит скважин месторождения Шуртан ( D_СКВ )
         **/
        double D_well = Calculator.averageProductionRate(C, P_u, delta, Ro_otn, Z, T_u);


        try {
            WellAction wellAction = WellAction
                    .builder()
                    .user(userRepository.findById(1).get())
                    .temperature(dto.getTemperature())
                    .pressure(dto.getPressure())
                    .status(dto.getStatus())
                    .rpl(dto.getRpl())
                    .well(well.get())
                    .expend(D_well)
                    .build();

            WellAction save = wellActionRepository.save(wellAction);

            WellActionDto wellActionDto = converter.wellActionToWellActionDto(save);

            return converter.apiSuccess201(wellActionDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError404();
        }
    }

    public HttpEntity<?> getWells() {
        try {
            List<Well> all = wellRepository.findAll();

            List<WellDto> wellDtos = all.stream().map(converter::wellToWellDto).collect(Collectors.toList());

            return converter.apiSuccess200(wellDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching all wells");
        }
    }

    public HttpEntity<?> getWellsWithAction() {
        try {
            List<Well> all = wellRepository.findAll();

            return wellActionDtos(all);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching collection points with actions");
        }
    }

    public HttpEntity<?> getWellsWithActionByCollectionPoint(Integer id) {
        try {
            Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("collection point not found");

            List<Well> wells = wellRepository.findAllByCollectionPoint(byId.get());

            return wellActionDtos(wells);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching wells with actions by collection point ");
        }
    }

    public HttpEntity<?> getByCollectionPoint(Integer id) {
        try {
            Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("collection point not found");

            List<Well> wells = wellRepository.findAllByCollectionPoint(byId.get());

            List<WellDto> collect = wells.stream().map(converter::wellToWellDto).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching wells by collection point ");
        }
    }

    public HttpEntity<?> getWell(Integer id) {
        try {
            Optional<Well> byId = wellRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("well not found");
            WellDto dto = converter.wellToWellDto(byId.get());
            return converter.apiSuccess200(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching well by id");
        }
    }

    public HttpEntity<?> getWellWithAction(Integer id) {
        try {
            Optional<Well> byId = wellRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("well not found");

            Optional<WellAction> firstByWell = wellActionRepository.findFirstByWell(byId.get());
            if (!firstByWell.isPresent()) return converter.apiError404("well action not found");

            WellActionDto dto = converter.wellActionToWellActionDto(firstByWell.get());

            return converter.apiSuccess200(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching collection point by id");
        }
    }


    // helper method
    private HttpEntity<?> wellActionDtos(List<Well> wells) {
        List<WellActionDto> collect = wells.stream().map(item -> {
            Optional<Well> byId1 = wellRepository.findById(item.getId());
            Optional<WellAction> firstByWell = wellActionRepository.findFirstByWell(byId1.get());
            return converter.wellActionToWellActionDto(firstByWell.get());
        }).collect(Collectors.toList());

        return converter.apiSuccess200(collect);
    }

    /** Auto **/

    //..... from MODBUS
    //... coming soon


//    public ResponseEntity<?> test(Integer id){
//        try {
//            Optional<Well> well = wellRepository.findById(id);
//            if (!well.isPresent()) return converter.apiError404();
//            Optional<WellAction> first = wellActionRepository.findFirstByWell(well.get());
//
//            if (first.isPresent()){
//                return converter.apiSuccess200(converter.wellAndWellActionToJson(well.get(), first.get()));
//            }
//            return converter.apiError409();
//        }catch (Exception e){
//            e.printStackTrace();
//            return converter.apiError409();
//        }
//
//    }
}
