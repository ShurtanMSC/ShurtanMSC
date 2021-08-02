package uz.neft.service.action;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.User;
import uz.neft.entity.Well;
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


    public WellActionService(WellActionRepository wellActionRepository, MiningSystemConstantRepository miningSystemConstantRepository, WellRepository wellRepository, Converter converter, MiningSystemRepository miningSystemRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository, Calculator calculator, ConstantRepository constantRepository, UserRepository userRepository) {
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
        this.converter = converter;
        this.miningSystemMiningSystemGasCompositionRepository = miningSystemMiningSystemGasCompositionRepository;
        this.miningSystemConstantRepository = miningSystemConstantRepository;
        this.constantRepository = constantRepository;
        this.userRepository = userRepository;
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
