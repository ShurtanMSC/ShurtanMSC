package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.GasCompositionDto;
import uz.neft.dto.MiningSystemDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.variables.GasComposition;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.GasCompositionRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GasCompositionService {

    private final GasCompositionRepository compositionRepository;
    private final Converter converter;

    @Autowired
    public GasCompositionService(GasCompositionRepository compositionRepository, Converter converter) {
        this.compositionRepository = compositionRepository;
        this.converter = converter;
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

            GasComposition save = compositionRepository.save(composition);
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
            Optional<GasComposition> byId = compositionRepository.findById(dto.getId());
            if (byId.isPresent()) {
                composition = byId.get();
                composition.setName(dto.getName());
                composition.setCriticalPressure(dto.getCriticalPressure());
                composition.setCriticalTemperature(dto.getCriticalTemperature());
                composition = compositionRepository.save(composition);

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
                Optional<GasComposition> byId = compositionRepository.findById(id);
                if (byId.isPresent()) {
                    compositionRepository.deleteById(id);
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
            List<GasComposition> all = compositionRepository.findAll();
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
                Optional<GasComposition> byId = compositionRepository.findById(id);
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
}
