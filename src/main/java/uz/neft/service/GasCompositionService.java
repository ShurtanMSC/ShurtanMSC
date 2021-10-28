package uz.neft.service;
//lord

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.CollectionPointDto;
import uz.neft.dto.GasCompositionDto;
import uz.neft.dto.MiningSystemGasCompositionDto;
import uz.neft.dto.UppgDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.variables.GasComposition;
import uz.neft.entity.variables.MiningSystemGasComposition;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.GasCompositionRepository;
import uz.neft.repository.MiningSystemGasCompositionRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GasCompositionService {

    private final GasCompositionRepository gasCompositionRepository;
    private final Converter converter;
    private final MiningSystemGasCompositionRepository miningSystemGasCompositionRepository;
    private final MiningSystemRepository miningSystemRepository;

    @Autowired
    public GasCompositionService(GasCompositionRepository gasCompositionRepository, Converter converter, MiningSystemGasCompositionRepository miningSystemGasCompositionRepository, MiningSystemRepository miningSystemRepository) {
        this.gasCompositionRepository = gasCompositionRepository;
        this.converter = converter;
        this.miningSystemGasCompositionRepository = miningSystemGasCompositionRepository;
        this.miningSystemRepository = miningSystemRepository;
    }

    public ResponseEntity<?> save(GasCompositionDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (dto.getName() == null) return converter.apiError400("name is null");
            if (dto.getCriticalPressure() == null) return converter.apiError400("CriticalPressure is null");
            if (dto.getCriticalTemperature() == null) return converter.apiError400("CriticalTemperature is null");

            GasComposition composition = new GasComposition();
            composition.setName(dto.getName());
            composition.setCriticalPressure(dto.getCriticalPressure());
            composition.setCriticalTemperature(dto.getCriticalTemperature());

            GasComposition save = gasCompositionRepository.save(composition);
            GasCompositionDto gasCompositionDto = converter.gasCompositionToGasCompositionDto(save);

            return converter.apiSuccess201("Gas Composition added", gasCompositionDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error Creating Gas Composition");
        }
    }

    public ResponseEntity<?> edit(GasCompositionDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");

            GasComposition composition;
            Optional<GasComposition> byId = gasCompositionRepository.findById(dto.getId());
            if (byId.isPresent()) {
                composition = byId.get();
                composition.setName(dto.getName());
                composition.setCriticalPressure(dto.getCriticalPressure());
                composition.setCriticalTemperature(dto.getCriticalTemperature());
                composition = gasCompositionRepository.save(composition);

                GasCompositionDto gasCompositionDto = converter.gasCompositionToGasCompositionDto(composition);
                return converter.apiSuccess202("Gas Composition edited", gasCompositionDto);
            }
            return converter.apiError404("Gas Composition not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error editing Gas Composition");
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            if (id != null) {
                Optional<GasComposition> byId = gasCompositionRepository.findById(id);
                if (byId.isPresent()) {
                    gasCompositionRepository.deleteById(id);
                    return converter.apiSuccess200("Gas Composition deleted ");
                }
                return converter.apiError404("Gas Composition not found");
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in deleting Gas Composition", e);
        }
    }

    public ResponseEntity<?> findAll() {
        try {
            List<GasComposition> all = gasCompositionRepository.findAll();
            List<GasCompositionDto> collect = all.stream().map(converter::gasCompositionToGasCompositionDto).collect(Collectors.toList());
            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching Gas Compositions", e);
        }
    }

    public ResponseEntity<?> findById(Integer id) {
        try {
            if (id == null) return converter.apiError400("Id null");

            Optional<GasComposition> byId = gasCompositionRepository.findById(id);
            if (byId.isPresent()) {
                GasCompositionDto gasCompositionDto = converter.gasCompositionToGasCompositionDto(byId.get());
                return converter.apiSuccess200(gasCompositionDto);
            } else {
                return converter.apiError404("Gas Composition not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding Gas Composition", e);
        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //MiningSystemGasComposition Service methods for molar fractions

    // save method MiningSystemGasComposition ( MSGC )
    public ResponseEntity<?> saveMSGC(MiningSystemGasCompositionDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (dto.getMiningSystemId() == null) return converter.apiError400("MiningSystemId is null");
            if (dto.getGasCompositionId() == null) return converter.apiError400("GasCompositionId is null");
            if (dto.getMolarFraction() == null) return converter.apiError400("MolarFraction is null");

            MiningSystemGasComposition miningSystemGasComposition = new MiningSystemGasComposition();

            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMiningSystemId());
            Optional<GasComposition> gasComposition = gasCompositionRepository.findById(dto.getGasCompositionId());

            if (!miningSystem.isPresent()) return converter.apiError404("There is no Mining System such as ID");
            if (!gasComposition.isPresent()) return converter.apiError404("There is no Gas Component such as ID");

            Optional<MiningSystemGasComposition> firstFirstByMiningSystemAndGasComposition = miningSystemGasCompositionRepository.findFirstByMiningSystemAndGasComposition(miningSystem.get(), gasComposition.get());
            if (firstFirstByMiningSystemAndGasComposition.isPresent()) return converter.apiError409("This Molar Fraction Already exist");

            miningSystemGasComposition.setMiningSystem(miningSystem.get());
            miningSystemGasComposition.setGasComposition(gasComposition.get());
            miningSystemGasComposition.setMolarFraction(dto.getMolarFraction());

            MiningSystemGasComposition save = miningSystemGasCompositionRepository.save(miningSystemGasComposition);
            MiningSystemGasCompositionDto miningSystemGasCompositionDto = converter.miningSystemGasCompositionToMiningSystemGasCompositionDto(save);

            return converter.apiSuccess201("Molar Fraction saved in MiningSystem_GasComposition Entity", miningSystemGasCompositionDto);
        } catch (
                Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error saving Molar Fraction in MiningSystem_GasComposition table");
        }
    }

    public ResponseEntity<?> editMSGC(MiningSystemGasCompositionDto dto) {
        try {
//            if (dto.getId() == null) return converter.apiError("id is null");
//            if (dto.getMiningSystemId() == null) return converter.apiError("MiningSystemId is null");
//            if (dto.getGasCompositionId() == null) return converter.apiError("GasCompositionId is null");
            if (dto.getMolarFraction() == null) return converter.apiError400("MolarFraction is null");

            MiningSystemGasComposition editingMiningSystemGasComposition;

            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMiningSystemId());
            Optional<GasComposition> gasComposition = gasCompositionRepository.findById(dto.getGasCompositionId());

            if (!miningSystem.isPresent()) return converter.apiError404("There is no Mining System such as ID");
            if (!gasComposition.isPresent()) return converter.apiError404("There is no Gas Component such as ID");

            Optional<MiningSystemGasComposition> byIdMiningSysGasComposition =
                    miningSystemGasCompositionRepository.findByIdAndMiningSystemAndGasComposition(dto.getId(), miningSystem.get(), gasComposition.get());

            if (!byIdMiningSysGasComposition.isPresent())
                return converter.apiError404("There is no MiningSys_GasComposition ");

            byIdMiningSysGasComposition.get().setMolarFraction(dto.getMolarFraction());

            MiningSystemGasComposition save = miningSystemGasCompositionRepository.save(byIdMiningSysGasComposition.get());
            MiningSystemGasCompositionDto miningSystemGasCompositionDto = converter.miningSystemGasCompositionToMiningSystemGasCompositionDto(save);

            return converter.apiSuccess200("Molar Fraction edited ", miningSystemGasCompositionDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Editing Molar Fraction is ERROR in MiningSystem_GasComposition, catch");
        }
    }

    public ResponseEntity<?> deleteMSGC(Integer id) {
        try {
            if (id == null) return converter.apiError400("Id is null");

            Optional<MiningSystemGasComposition> byId = miningSystemGasCompositionRepository.findById(id);
            if (byId.isPresent()) {
                miningSystemGasCompositionRepository.deleteById(id);
                return converter.apiSuccess200("MiningSystem_GasComposition deleted");
            }
            return converter.apiError404("MiningSystem_GasComposition not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Deleting Molar Fraction is ERROR in MiningSystem_GasComposition, catch", e);
        }
    }

    public ResponseEntity<?> findAllMSGCs() {
        try {
            List<MiningSystem> miningSystems = miningSystemRepository.findAll();
            List<ObjectWithActionsDto> dtoList=new ArrayList<>();

            for (MiningSystem miningSystem : miningSystems) {
                List<MiningSystemGasComposition> allByMiningSystem = miningSystemGasCompositionRepository.findAllByMiningSystem(miningSystem);
                List<MiningSystemGasCompositionDto> collect = allByMiningSystem.stream().map(converter::miningSystemGasCompositionToMiningSystemGasCompositionDto).collect(Collectors.toList());
                dtoList.add(new ObjectWithActionsDto(converter.miningSysToMiningSysDto(miningSystem), collect));
            }
//            miningSystemGasCompositionRepository.findAllByMiningSystem();
//            List<MiningSystemGasComposition> all = miningSystemGasCompositionRepository.findAll();
//            List<MiningSystemGasCompositionDto> collect = all.stream().map(converter::miningSystemGasCompositionToMiningSystemGasCompositionDto).collect(Collectors.toList());

            return converter.apiSuccess200(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching all Molar Fractions in MiningSystem_GasComposition table", e);
        }
    }


    public ResponseEntity<?> findAllMSGCsByMiningSystem(Integer mining_system_id){
        try {
            if (mining_system_id==null) return converter.apiError400("Mining System id is null!");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(mining_system_id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found!");

            List<GasComposition> gasCompositionsList=new ArrayList<>();
            List<MiningSystemGasComposition> all = miningSystemGasCompositionRepository.findAllByMiningSystemOrderByGasCompositionId(miningSystem.get());
            List<MiningSystemGasCompositionDto> collect = all.stream().map(converter::miningSystemGasCompositionToMiningSystemGasCompositionDto).collect(Collectors.toList());
            List<ObjectWithActionsDto> objectWithActionsDtos=new ArrayList<>();
            for (int i = 0; i <collect.size() ; i++) {
                objectWithActionsDtos.add(new ObjectWithActionsDto(all.get(i).getGasComposition(),collect.get(i)));
            }
            return converter.apiSuccess200(objectWithActionsDtos);


        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> findByIdMSGC(Integer id) {
        try {
            if (id == null) return converter.apiError400("Id null");
            Optional<MiningSystemGasComposition> byId = miningSystemGasCompositionRepository.findById(id);
            if (byId.isPresent()) {
                MiningSystemGasCompositionDto dto = converter.miningSystemGasCompositionToMiningSystemGasCompositionDto(byId.get());
                return converter.apiSuccess200(dto);
            }
            return converter.apiError404("Molar Fraction not found in MiningSystem_GasComposition table");

        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding Molar Fraction in MiningSystem_GasComposition table", e);
        }
    }

//    public ResponseEntity<?> saveAllMSGCByMiningSystem(List<MiningSystemGasCompositionDto> dtoList) {
//        try {
//
//            miningSystemGasCompositionRepository.findByIdAndMiningSystemAndGasComposition()
//            List<MiningSystemGasComposition> compositionList=new ArrayList<>();
//
//            for (MiningSystemGasCompositionDto dto : dtoList) {
//                if (dto.getMiningSystemId()==null) return converter.apiError400("Mining system id is null");
//                if (dto.getGasCompositionId()==null) return converter.apiError400("Gas composition id is null");
//                if (dto.getMolarFraction()==null) return converter.apiError400("Gas composition id is null");
//                Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMiningSystemId());
//                Optional<GasComposition> gasComposition = gasCompositionRepository.findById(dto.getGasCompositionId());
//                if (!miningSystem.isPresent()) return converter.apiError404("Mining System not found");
//                if (!gasComposition.isPresent()) return converter.apiError404("Gas composition not found");
//
//
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return converter.apiError409();
//        }
//    }
}
