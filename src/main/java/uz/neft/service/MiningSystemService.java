package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.MiningSystemDto;
import uz.neft.dto.UserDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Role;
import uz.neft.entity.User;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.utils.Converter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MiningSystemService {

    private final MiningSystemRepository miningSystemRepository;
    private final Converter converter;

    @Autowired
    public MiningSystemService(MiningSystemRepository miningSystemRepository, Converter converter) {
        this.miningSystemRepository = miningSystemRepository;
        this.converter = converter;
    }

    public ApiResponse save(MiningSystemDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError("id shouldn't be sent");
            MiningSystem miningSystem = new MiningSystem();
            miningSystem.setName(dto.getName());
            MiningSystem save = miningSystemRepository.save(miningSystem);
            MiningSystemDto miningSystemDto = converter.miningSysToMiningSysDto(save);
            return converter.apiSuccess("Mining system added", miningSystemDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error Creating MiningSystem");
        }
    }

    public ApiResponse edit(MiningSystemDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError("id is null");

            MiningSystem editMiningSys;
            Optional<MiningSystem> byId = miningSystemRepository.findById(dto.getId());
            if (byId.isPresent()) {
                editMiningSys = byId.get();
                editMiningSys.setName(dto.getName());
                editMiningSys = miningSystemRepository.save(editMiningSys);
                MiningSystemDto miningSystemDto = converter.miningSysToMiningSysDto(editMiningSys);
                return converter.apiSuccess("Mining system edited", miningSystemDto);
            }
            return converter.apiError("Mining system not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error editing mining system");
        }
    }

    public ApiResponse delete(Integer id) {
        try {
            if (id != null) {
                Optional<MiningSystem> byId = miningSystemRepository.findById(id);
                if (byId.isPresent()) {
                    miningSystemRepository.deleteById(id);
                    return converter.apiSuccess("Mining system deleted ");
                } else {
                    return converter.apiError("Mining system not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in deleting mining system", e);
        }
    }

    public ApiResponse findAll() {
        try {
            List<MiningSystem> all = miningSystemRepository.findAll();
            List<MiningSystemDto> collect = all.stream().map(converter::miningSysToMiningSysDto).collect(Collectors.toList());
            return converter.apiSuccess(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in fetching mining systems", e);
        }
    }

    public ApiResponse findById(Integer id) {
        try {
            if (id != null) {
                Optional<MiningSystem> byId = miningSystemRepository.findById(id);
                if (byId.isPresent()) {
                    MiningSystemDto miningSystemDto = converter.miningSysToMiningSysDto(byId.get());
                    return converter.apiSuccess(miningSystemDto);
                } else {
                    return converter.apiError("Mining system not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding mining system", e);
        }
    }


}
