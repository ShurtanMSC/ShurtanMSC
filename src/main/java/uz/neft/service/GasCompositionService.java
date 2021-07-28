package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.CollectionPointDto;
import uz.neft.dto.GasCompositionDto;
import uz.neft.dto.MiningSystemGasCompositionDto;
import uz.neft.dto.UppgDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.variables.GasComposition;
import uz.neft.entity.variables.MiningSystemGasComposition;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.GasCompositionRepository;
import uz.neft.repository.MiningSystemGasCompositionRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.utils.Converter;

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

    public ApiResponse save(GasCompositionDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError("id shouldn't be sent");
            if (dto.getName() == null) return converter.apiError("name is null");
            if (dto.getCriticalPressure() == null) return converter.apiError("CriticalPressure is null");
            if (dto.getCriticalTemperature() == null) return converter.apiError("CriticalTemperature is null");

            GasComposition composition = new GasComposition();
            composition.setName(dto.getName());
            composition.setCriticalPressure(dto.getCriticalPressure());
            composition.setCriticalTemperature(dto.getCriticalTemperature());

            GasComposition save = gasCompositionRepository.save(composition);
            GasCompositionDto gasCompositionDto = converter.gasCompositionToGasCompositionDto(save);

            return converter.apiSuccess("Gas Composition added", gasCompositionDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error Creating Gas Composition");
        }
    }

    public ApiResponse edit(GasCompositionDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError("id is null");

            GasComposition composition;
            Optional<GasComposition> byId = gasCompositionRepository.findById(dto.getId());
            if (byId.isPresent()) {
                composition = byId.get();
                composition.setName(dto.getName());
                composition.setCriticalPressure(dto.getCriticalPressure());
                composition.setCriticalTemperature(dto.getCriticalTemperature());
                composition = gasCompositionRepository.save(composition);

                GasCompositionDto gasCompositionDto = converter.gasCompositionToGasCompositionDto(composition);
                return converter.apiSuccess("Gas Composition edited", gasCompositionDto);
            }
            return converter.apiError("Gas Composition not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error editing Gas Composition");
        }
    }

    public ApiResponse delete(Integer id) {
        try {
            if (id != null) {
                Optional<GasComposition> byId = gasCompositionRepository.findById(id);
                if (byId.isPresent()) {
                    gasCompositionRepository.deleteById(id);
                    return converter.apiSuccess("Gas Composition deleted ");
                }
                return converter.apiError("Gas Composition not found");
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in deleting Gas Composition", e);
        }
    }

    public ApiResponse findAll() {
        try {
            List<GasComposition> all = gasCompositionRepository.findAll();
            List<GasCompositionDto> collect = all.stream().map(converter::gasCompositionToGasCompositionDto).collect(Collectors.toList());
            return converter.apiSuccess(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in fetching Gas Compositions", e);
        }
    }

    public ApiResponse findById(Integer id) {
        try {
            if (id != null) {
                Optional<GasComposition> byId = gasCompositionRepository.findById(id);
                if (byId.isPresent()) {
                    GasCompositionDto gasCompositionDto = converter.gasCompositionToGasCompositionDto(byId.get());
                    return converter.apiSuccess(gasCompositionDto);
                } else {
                    return converter.apiError("Gas Composition not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding Gas Composition", e);
        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //MiningSystemGasComposition Service methods for molar fractions

    // save method MiningSystemGasComposition ( MSGC )
    public ApiResponse saveMSGC(MiningSystemGasCompositionDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError("id shouldn't be sent");
            if (dto.getMiningSystemId() == null) return converter.apiError("MiningSystemId is null");
            if (dto.getGasCompositionId() == null) return converter.apiError("GasCompositionId is null");
            if (dto.getMolarFraction() == null) return converter.apiError("MolarFraction is null");

            MiningSystemGasComposition miningSystemGasComposition = new MiningSystemGasComposition();

            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMiningSystemId());
            Optional<GasComposition> gasComposition = gasCompositionRepository.findById(dto.getGasCompositionId());

            if (!miningSystem.isPresent()) return converter.apiError("There is no Mining System such as ID");
            if (!gasComposition.isPresent()) return converter.apiError("There is no Gas Component such as ID");

            miningSystemGasComposition.setMiningSystem(miningSystem.get());
            miningSystemGasComposition.setGasComposition(gasComposition.get());
            miningSystemGasComposition.setMolarFraction(dto.getMolarFraction());

            MiningSystemGasComposition save = miningSystemGasCompositionRepository.save(miningSystemGasComposition);
            MiningSystemGasCompositionDto miningSystemGasCompositionDto = converter.miningSystemGasCompositionToMiningSystemGasCompositionDto(save);

            return converter.apiSuccess("Molar Fraction saved in MiningSystemGasComposition Entity", miningSystemGasCompositionDto);
        } catch (
                Exception e) {
            e.printStackTrace();
            return converter.apiError("Error creating Molar Fraction ");
        }
    }

    public ApiResponse editMSGC(MiningSystemGasCompositionDto dto) {
        try {
//            if (dto.getId() == null) return converter.apiError("id is null");
//            if (dto.getMiningSystemId() == null) return converter.apiError("MiningSystemId is null");
//            if (dto.getGasCompositionId() == null) return converter.apiError("GasCompositionId is null");
            if (dto.getMolarFraction() == null) return converter.apiError("MolarFraction is null");

            MiningSystemGasComposition editingMiningSystemGasComposition;

            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMiningSystemId());
            Optional<GasComposition> gasComposition = gasCompositionRepository.findById(dto.getGasCompositionId());

            if (!miningSystem.isPresent()) return converter.apiError("There is no Mining System such as ID");
            if (!gasComposition.isPresent()) return converter.apiError("There is no Gas Component such as ID");

            Optional<MiningSystemGasComposition> byIdMiningSysGasComposition =
                    miningSystemGasCompositionRepository.findByIdAndMiningSystemAndGasComposition(dto.getId(), miningSystem.get(), gasComposition.get());

            if (!byIdMiningSysGasComposition.isPresent())
                return converter.apiError("There is no MiningSysGasComposition ");

            byIdMiningSysGasComposition.get().setMolarFraction(dto.getMolarFraction());

            MiningSystemGasComposition save = miningSystemGasCompositionRepository.save(byIdMiningSysGasComposition.get());
            MiningSystemGasCompositionDto miningSystemGasCompositionDto = converter.miningSystemGasCompositionToMiningSystemGasCompositionDto(save);

            return converter.apiSuccess("Molar Fraction edited ", miningSystemGasCompositionDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error edit uppg");
        }

    }

    public ApiResponse deleteMSGC(Integer id) {
        try {
            if (id != null) {
                Optional<Uppg> byId = uppgRepository.findById(id);
                if (byId.isPresent()) {
                    uppgRepository.deleteById(id);
                    return converter.apiSuccess("Uppg deleted");
                } else {
                    return converter.apiError("Mining system not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in deleting uppg", e);
        }
    }

    public ApiResponse findAllMSGCs() {
        try {
            List<Uppg> all = uppgRepository.findAll();
            List<UppgDto> collect = all.stream().map(converter::uppgToUppgDto).collect(Collectors.toList());

            return converter.apiSuccess(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in fetching all uppgs", e);
        }
    }

    public ApiResponse findByIdMSGC(Integer id) {
        try {
            if (id != null) {
                Optional<Uppg> byId = uppgRepository.findById(id);
                if (byId.isPresent()) {
                    UppgDto uppgDto = converter.uppgToUppgDto(byId.get());
                    return converter.apiSuccess(uppgDto);
                } else {
                    return converter.apiError("Uppg not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding uppg", e);
        }
    }
}
