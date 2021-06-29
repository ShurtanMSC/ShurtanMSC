package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.WellDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Well;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.WellRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WellService {

    private final WellRepository wellRepository;
    private final Converter converter;
    private final CollectionPointRepository collectionPointRepository;

    @Autowired
    public WellService(WellRepository wellRepository, Converter converter, CollectionPointRepository collectionPointRepository) {
        this.wellRepository = wellRepository;
        this.converter = converter;
        this.collectionPointRepository = collectionPointRepository;
    }

    public ApiResponse save(WellDto dto) {
        try {
            Well well = new Well();
            well.setNumber(dto.getNumber());

            Optional<CollectionPoint> byIdMining = collectionPointRepository.findById(dto.getCollectionPointDto().getId());
            well.setCollectionPoint(byIdMining.get());

            Well save = wellRepository.save(well);
            return converter.apiSuccess("Saved well", save);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error creating well");
        }

    }

    public ApiResponse edit(WellDto dto) {
        try {
            Well editingWell = new Well();
            Optional<Well> byId = wellRepository.findById(dto.getId());
            if (byId.isPresent()) editingWell = byId.get();
            editingWell.setNumber(dto.getNumber());

            Optional<CollectionPoint> byIdMining = collectionPointRepository.findById(dto.getCollectionPointDto().getId());
            editingWell.setCollectionPoint(byIdMining.get());

            Well editedWell = wellRepository.save(editingWell);
            return converter.apiSuccess("Edited well", editedWell);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error editing well");
        }

    }

    public ApiResponse delete(Integer id) {
        try {
            if (id != null) {
                Optional<Well> byId = wellRepository.findById(id);
                if (byId.isPresent()) {
                    wellRepository.deleteById(id);
                    return converter.apiSuccess("Deleted well");
                } else {
                    return converter.apiError("Well not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in deleting well", e);
        }

    }

    public ApiResponse findAll() {
        try {
            List<Well> all = wellRepository.findAll();
            List<WellDto> collect = all.stream().map(converter::wellToWellDto).collect(Collectors.toList());

            return converter.apiSuccess(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding well", e);
        }
    }

    public ApiResponse findById(Integer id) {
        try {
            if (id != null) {
                Optional<Well> byId = wellRepository.findById(id);
                if (byId.isPresent()) {
                    Optional<Well> byId1 = wellRepository.findById(id);
                    return converter.apiSuccess(byId1.get());
                } else {
                    return converter.apiError("Well not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding well", e);
        }

    }
}
