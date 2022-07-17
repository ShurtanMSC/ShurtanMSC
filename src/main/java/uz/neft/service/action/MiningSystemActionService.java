package uz.neft.service.action;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.MiningSystemDto;
import uz.neft.dto.action.MiningSystemActionDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.dto.action.UppgActionDto;
import uz.neft.entity.ForecastGas;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.action.MiningSystemAction;
import uz.neft.entity.action.UppgAction;
import uz.neft.repository.*;
import uz.neft.repository.action.MiningSystemActionRepository;
import uz.neft.repository.constants.ConstantRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.repository.constants.MiningSystemConstantRepository;
import uz.neft.service.Calculator;
import uz.neft.utils.Converter;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
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
    private final Logger logger;


    public MiningSystemActionService(MiningSystemActionRepository miningSystemActionRepository, MiningSystemConstantRepository miningSystemConstantRepository, Converter converter, MiningSystemRepository miningSystemRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository, Calculator calculator, ConstantRepository constantRepository, UserRepository userRepository, CollectionPointRepository collectionPointRepository, MiningSystemActionRepository findAllByCollectionPoint, MiningSystemRepository miningSystemRepository1, ForecastGasRepository forecastGasRepository, Logger logger) {
        this.miningSystemRepository = miningSystemRepository;
        this.converter = converter;
        this.miningSystemMiningSystemGasCompositionRepository = miningSystemMiningSystemGasCompositionRepository;
        this.miningSystemConstantRepository = miningSystemConstantRepository;
        this.constantRepository = constantRepository;
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.miningSystemActionRepository = miningSystemActionRepository;
        this.forecastGasRepository = forecastGasRepository;
        this.logger = logger;
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
            logger.error(e.toString());
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
            logger.error(e.toString());
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
            logger.error(e.toString());
            return converter.apiError409();
        }
    }


    public HttpEntity<?> allWithActionsByMiningSystem(Integer id,Optional<Integer> page,Optional<Integer> pageSize, Optional<String> sortBy) {
        try {
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
//            List<MiningSystemAction> miningSystemActions =miningSystemActionRepository.findAllByMiningSystemOrderByCreatedAtDesc(miningSystem.get());

//            Stream<MiningSystemActionDto> miningSystemActionDtoStream = miningSystemActions.stream().map(converter::miningsystemActionToMiningSystemActionDto);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");

            Pageable pg = PageRequest.of(page.orElse(0), pageSize.orElse(10), Sort.Direction.DESC, sortBy.orElse("createdAt"));

            Page<MiningSystemAction> miningSystemActions = miningSystemActionRepository.findAllByMiningSystemOrderByCreatedAtDesc(miningSystem.get(), pg);

            Stream<MiningSystemActionDto> MiningSystemActionDto = miningSystemActions.stream().map(converter::miningsystemActionToMiningSystemActionDto);

            return converter.apiSuccess200(MiningSystemActionDto,miningSystemActions.getTotalElements(),miningSystemActions.getTotalPages(),miningSystemActions.getNumber());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
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
            logger.error(e.toString());
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
//    logger.error(e.toString());
//            return converter.apiError409();
//        }
//
//    }

    public void setAllAction(List<UppgAction> uppgActions, MiningSystem miningSystem) {
        Optional<MiningSystemAction> lastAction = miningSystemActionRepository.findFirstByMiningSystemOrderByCreatedAtDesc(miningSystem);
        MiningSystemAction miningSystemAction = MiningSystemAction
                .builder()
                .expend(uppgActions.stream().mapToDouble(UppgAction::getExpend).sum())
                .miningSystem(miningSystem)
                .planThisYear(lastAction.map(MiningSystemAction::getPlanThisYear).orElse(0.0))
                .planThisMonth(lastAction.map(MiningSystemAction::getPlanThisMonth).orElse(0.0))
//                .todayExpend(uppgAction1.getTodayExpend() + uppgAction2.getTodayExpend())
                .todayExpend(uppgActions.stream().mapToDouble(UppgAction::getTodayExpend).sum())
//                .yesterdayExpend(uppgAction1.getYesterdayExpend() + uppgAction2.getYesterdayExpend())
                .yesterdayExpend(uppgActions.stream().mapToDouble(UppgAction::getYesterdayExpend).sum())
//                .thisMonthExpend(uppgAction1.getThisMonthExpend() + uppgAction2.getThisMonthExpend())
                .thisMonthExpend(uppgActions.stream().mapToDouble(UppgAction::getThisMonthExpend).sum())
//                .lastMonthExpend(uppgAction1.getLastMonthExpend() + uppgAction2.getLastMonthExpend())
                .lastMonthExpend(uppgActions.stream().mapToDouble(UppgAction::getLastMonthExpend).sum())
                .build();
        miningSystemAction = miningSystemActionRepository.save(miningSystemAction);

        Date date = new Date();

        Optional<ForecastGas> forecastGasNow = forecastGasRepository.findByMiningSystemAndYearAndMonth(miningSystem, date.getYear(), Month.of(date.getMonth()+1));
        if (forecastGasNow.isPresent()) {
            forecastGasNow.get().setAmount(miningSystemAction.getThisMonthExpend());
            forecastGasRepository.save(forecastGasNow.get());
        }
    }
}
