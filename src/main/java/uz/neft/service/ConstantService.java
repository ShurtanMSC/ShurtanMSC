package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.ConstantDto;
import uz.neft.entity.variables.Constant;
import uz.neft.repository.constants.ConstantRepository;
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

    public ResponseEntity<?> save(ConstantDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (dto.getName() == null) return converter.apiError400("name is null");

            Constant constant = new Constant();
            constant.setName(dto.getName());
            constant.setDescription(dto.getDescription());

            Constant save = constantRepository.save(constant);
            ConstantDto constantDto = converter.constantToConstantDto(save);

            return converter.apiSuccess201("Constant added", constantDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error Creating Constant");
        }
    }

    public ResponseEntity<?> edit(ConstantDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");

            Constant constant;
            Optional<Constant> byId = constantRepository.findById(dto.getId());
            if (byId.isPresent()) {
                constant = byId.get();
                constant.setName(dto.getName());
                constant.setDescription(dto.getDescription());
                constant = constantRepository.save(constant);

                ConstantDto gasCompositionDto = converter.constantToConstantDto(constant);
                return converter.apiSuccess202("Constant edited", gasCompositionDto);
            }
            return converter.apiError404("Constant not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error editing Constant");
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            if (id != null) {
                Optional<Constant> byId = constantRepository.findById(id);
                if (byId.isPresent()) {
                    constantRepository.deleteById(id);
                    return converter.apiSuccess200("Constant deleted ");
                }
                return converter.apiError404("Constant not found");
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in deleting Constant", e);
        }
    }

    public ResponseEntity<?> findAll() {
        try {
            List<Constant> all = constantRepository.findAll();
            List<ConstantDto> collect = all.stream().map(converter::constantToConstantDto).collect(Collectors.toList());
            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching Constants", e);
        }
    }

    public ResponseEntity<?> findById(Integer id) {
        try {
            if (id != null) {
                Optional<Constant> byId = constantRepository.findById(id);
                if (byId.isPresent()) {
                    ConstantDto gasCompositionDto = converter.constantToConstantDto(byId.get());
                    return converter.apiSuccess200(gasCompositionDto);
                } else {
                    return converter.apiError404("Constant not found");
                }
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding Constant", e);
        }
    }
}
