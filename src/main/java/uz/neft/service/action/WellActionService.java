package uz.neft.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.WellDto;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.User;
import uz.neft.entity.Well;
import uz.neft.entity.action.WellAction;
import uz.neft.entity.variables.GasComposition;
import uz.neft.entity.variables.MiningSystemGasComposition;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.GasCompositionRepository;
import uz.neft.repository.MiningSystemGasCompositionRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.WellRepository;
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

    @Autowired
    public WellActionService(WellActionRepository wellActionRepository, WellRepository wellRepository, Converter converter, MiningSystemRepository miningSystemRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository, Calculator calculator) {
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
        this.converter = converter;
        this.miningSystemRepository = miningSystemRepository;
        this.gasCompositionRepository = gasCompositionRepository;
        this.miningSystemMiningSystemGasCompositionRepository = miningSystemMiningSystemGasCompositionRepository;
        this.calculator = calculator;
    }

    /**
     * Manually
     **/
    public ApiResponse addManually(User user, WellActionDto dto) {

        Optional<Well> well = wellRepository.findById(dto.getWellId());
        if (user == null) return converter.apiError();
        if (!well.isPresent()) return converter.apiError("Quduq topilmadi!");

        Integer miningSystemId = well.get().getCollectionPoint().getUppg().getMiningSystem().getId();


        /**
         * List miningSystemGasCompositions
         **/
        List<MiningSystemGasComposition> miningSystemGasCompositions = miningSystemMiningSystemGasCompositionRepository.findAllByMiningSystem(miningSystemId);

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

        // MUAMMO
        double roGas = 0;
        double roAir = 0;

        /**
         * Относительная плотность газа ( ρ_отн )
         **/
        double Ro_otn = Calculator.relativeDensity(roGas, roAir);

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
        double C = 0;

        /**
         * D_well - Средний дебит скважин месторождения Шуртан ( D_СКВ )
         **/
        double D_well = Calculator.averageProductionRate(C, P_u, delta, Ro_otn, Z, T_u);

        try {
            WellAction wellAction = WellAction
                    .builder()
                    .user(user)
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
            return converter.apiError();
        }
    }
}
