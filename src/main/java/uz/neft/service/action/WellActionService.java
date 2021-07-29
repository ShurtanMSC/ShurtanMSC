package uz.neft.service.action;

import org.springframework.stereotype.Service;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.User;
import uz.neft.entity.Well;
import uz.neft.entity.action.WellAction;
import uz.neft.entity.variables.*;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.*;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.service.Calculator;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WellActionService {
    private WellActionRepository wellActionRepository;
    private WellRepository wellRepository;
    private Converter converter;
    private MiningSystemRepository miningSystemRepository;
    private GasCompositionRepository gasCompositionRepository;
    private MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository;
    private Calculator calculator;
    private MiningSystemConstantRepository miningSystemConstantRepository;
    private ConstantRepository constantRepository;
    private UserRepository userRepository;


    public WellActionService(WellActionRepository wellActionRepository, MiningSystemConstantRepository miningSystemConstantRepository, WellRepository wellRepository, Converter converter, MiningSystemRepository miningSystemRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository, Calculator calculator, ConstantRepository constantRepository, UserRepository userRepository) {
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
        this.converter = converter;
        this.miningSystemRepository = miningSystemRepository;
        this.gasCompositionRepository = gasCompositionRepository;
        this.miningSystemMiningSystemGasCompositionRepository = miningSystemMiningSystemGasCompositionRepository;
        this.calculator = calculator;
        this.miningSystemConstantRepository = miningSystemConstantRepository;
        this.constantRepository = constantRepository;
        this.userRepository = userRepository;
    }

    /**
     * Manually
     **/
    public ApiResponse addManually(User user, WellActionDto dto) {

        Optional<Well> well = wellRepository.findById(dto.getWellId());
//        if (user == null) return converter.apiError();
        if (!well.isPresent()) return converter.apiError("Quduq topilmadi!");

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
        double C = 0.088;

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
            return converter.apiSuccess(save);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError();
        }
    }
}
