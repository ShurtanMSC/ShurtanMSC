package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.ConstantDto;
import uz.neft.entity.variables.Constant;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.ConstantRepository;
import uz.neft.utils.Converter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConstantService {

    private final ConstantRepository constantRepository;
    private final Converter converter;

    @Autowired
    public ConstantService(ConstantRepository constantRepository, Converter converter) {
        this.constantRepository = constantRepository;
        this.converter = converter;
    }

    public ApiResponse save(ConstantDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError("id shouldn't be sent");
            if (dto.getName() == null) return converter.apiError("name is null");

            Constant constant = new Constant();
            constant.setName(dto.getName());
            constant.setDescription(dto.getDescription());

            Constant save = constantRepository.save(constant);
            ConstantDto constantDto = converter.constantToConstantDto(save);

            return converter.apiSuccess("Constant added", constantDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error Creating Constant");
        }
    }

    public ApiResponse edit(ConstantDto dto) {
        try {
            Constant constant;
            Optional<Constant> byId = constantRepository.findById(dto.getId());
            if (byId.isPresent()) {
                constant = byId.get();
                constant.setName(dto.getName());
                constant.setDescription(dto.getDescription());
                constant = constantRepository.save(constant);

                ConstantDto gasCompositionDto = converter.constantToConstantDto(constant);
                return converter.apiSuccess("Constant edited", gasCompositionDto);
            }
            return converter.apiError("Constant not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error editing Constant");
        }
    }

    public ApiResponse delete(Integer id) {
        try {
            if (id != null) {
                Optional<Constant> byId = constantRepository.findById(id);
                if (byId.isPresent()) {
                    constantRepository.deleteById(id);
                    return converter.apiSuccess("Constant deleted ");
                }
                return converter.apiError("Constant not found");
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in deleting Constant", e);
        }
    }

    public ApiResponse findAll() {
        try {
            List<Constant> all = constantRepository.findAll();
            List<ConstantDto> collect = all.stream().map(converter::constantToConstantDto).collect(Collectors.toList());
            return converter.apiSuccess(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in fetching Constants", e);
        }
    }

    public ApiResponse findById(Integer id) {
        try {
            if (id != null) {
                Optional<Constant> byId = constantRepository.findById(id);
                if (byId.isPresent()) {
                    ConstantDto gasCompositionDto = converter.constantToConstantDto(byId.get());
                    return converter.apiSuccess(gasCompositionDto);
                } else {
                    return converter.apiError("Constant not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding Constant", e);
        }
    }
}
