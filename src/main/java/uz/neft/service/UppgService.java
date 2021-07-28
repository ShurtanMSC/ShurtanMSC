package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.UppgDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UppgService {

    private final UppgRepository uppgRepository;
    private final Converter converter;
    private final MiningSystemRepository miningSystemRepository;

    @Autowired
    public UppgService(UppgRepository uppgRepository, Converter converter, MiningSystemRepository miningSystemRepository) {
        this.uppgRepository = uppgRepository;
        this.converter = converter;
        this.miningSystemRepository = miningSystemRepository;
    }

    public ApiResponse save(UppgDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError("id shouldn't be sent");
            Uppg uppg = new Uppg();
            Optional<MiningSystem> byIdMining = miningSystemRepository.findById(dto.getMiningSystem().getId());
            if (byIdMining.isPresent()) {
                uppg.setName(dto.getName());
                uppg.setMiningSystem(byIdMining.get());
                Uppg save = uppgRepository.save(uppg);
                UppgDto uppgDto = converter.uppgToUppgDto(save);
                return converter.apiSuccess("Uppg saved", uppgDto);
            }
            return converter.apiError("Mining system not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error creating uppg");
        }
    }

    public ApiResponse edit(UppgDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError("Id null");

            Uppg editUppg;
            Optional<Uppg> byId = uppgRepository.findById(dto.getId());
            if (byId.isPresent()) {
                Optional<MiningSystem> byIdMining = miningSystemRepository.findById(dto.getMiningSystem().getId());
                if (byIdMining.isPresent()) {
                    editUppg = byId.get();
                    editUppg.setName(dto.getName());
                    editUppg.setMiningSystem(byIdMining.get());
                    Uppg editedUppg = uppgRepository.save(editUppg);
                    return converter.apiSuccess("Edited Mining System", editedUppg);
                }
                return converter.apiError("Mining system not found");
            }
            return converter.apiError("Uppg not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error edit uppg");
        }
    }

    public ApiResponse delete(Integer id) {
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

    public ApiResponse findAll() {
        try {
            List<Uppg> all = uppgRepository.findAll();
            List<UppgDto> collect = all.stream().map(converter::uppgToUppgDto).collect(Collectors.toList());

            return converter.apiSuccess(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in fetching all uppgs", e);
        }

    }

    public ApiResponse findById(Integer id) {
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
