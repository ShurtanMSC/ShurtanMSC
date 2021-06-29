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
            MiningSystem miningSystem = new MiningSystem();
            miningSystem.setName(dto.getName());
            MiningSystem save = miningSystemRepository.save(miningSystem);
            return converter.apiSuccess("Saved Mining System", save);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error Creating MiningSystem");
        }
    }

    public ApiResponse edit(MiningSystemDto dto) {
        try {
            MiningSystem editingMiningSys = new MiningSystem();
            Optional<MiningSystem> byId = miningSystemRepository.findById(dto.getId());
            if (byId.isPresent()) editingMiningSys = byId.get();
            editingMiningSys.setName(dto.getName());

            MiningSystem editedMiningSys = miningSystemRepository.save(editingMiningSys);
            return converter.apiSuccess("Edited Mining System", editedMiningSys);
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
                    return converter.apiSuccess("Deleted mining system");
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
            return converter.apiError("Error in finding user", e);
        }
    }

    public ApiResponse findById(Integer id) {
        try {
            if (id != null) {
                Optional<MiningSystem> byId = miningSystemRepository.findById(id);
                if (byId.isPresent()) {
                    Optional<MiningSystem> byId1 = miningSystemRepository.findById(id);
                    return converter.apiSuccess(byId1.get());
                } else {
                    return converter.apiError("Mining system not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding mining sytem", e);
        }
    }


}
