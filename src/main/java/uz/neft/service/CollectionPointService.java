package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.CollectionPointDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.UppgRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectionPointService {

    private final CollectionPointRepository collectionPointRepository;
    private final Converter converter;
    private final UppgRepository uppgRepository;

    @Autowired
    public CollectionPointService(CollectionPointRepository collectionPointRepository, Converter converter, UppgRepository uppgRepository) {
        this.collectionPointRepository = collectionPointRepository;
        this.converter = converter;
        this.uppgRepository = uppgRepository;
    }

    public ApiResponse save(CollectionPointDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError("id shouldn't be sent");
            CollectionPoint collectionPoint = new CollectionPoint();

            Optional<Uppg> byIdUppg = uppgRepository.findById(dto.getUppgDto().getId());
            if (byIdUppg.isPresent()) {
                collectionPoint.setName(dto.getName());
                collectionPoint.setUppg(byIdUppg.get());
                CollectionPoint save = collectionPointRepository.save(collectionPoint);
                CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(save);
                return converter.apiSuccess("Collection point saved", collectionPointDto);
            }
            return converter.apiError("Uppg not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error creating collection point");
        }
    }

    public ApiResponse edit(CollectionPointDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError("Id is null");
            CollectionPoint editCollectionPoint;
            Optional<CollectionPoint> byId = collectionPointRepository.findById(dto.getId());
            if (byId.isPresent()) {
                Optional<Uppg> byIdUppg = uppgRepository.findById(dto.getUppgDto().getId());
                if (byIdUppg.isPresent()) {
                    editCollectionPoint = byId.get();
                    editCollectionPoint.setName(dto.getName());
                    editCollectionPoint.setUppg(byIdUppg.get());
                    CollectionPoint save = collectionPointRepository.save(editCollectionPoint);
                    CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(save);
                    return converter.apiSuccess("Edited Mining System", collectionPointDto);
                }
                return converter.apiError("Uppg not found");
            }
            return converter.apiError("Collection not found!");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error edit collection point");
        }
    }

    public ApiResponse delete(Integer id) {
        try {
            if (id != null) {
                Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
                if (byId.isPresent()) {
                    collectionPointRepository.deleteById(id);
                    return converter.apiSuccess("Collection point deleted");
                }
                return converter.apiError("Collection point not found");
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in deleting collection point", e);
        }
    }

    public ApiResponse findAll() {
        try {
            List<CollectionPoint> all = collectionPointRepository.findAll();
            List<CollectionPointDto> collect = all.stream().map(converter::collectionPointToCollectionPointDto).collect(Collectors.toList());

            return converter.apiSuccess(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in fetching all collection points", e);
        }
    }

    public ApiResponse findById(Integer id) {
        try {
            if (id != null) {
                Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
                if (byId.isPresent()) {
                    CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(byId.get());
                    return converter.apiSuccess(collectionPointDto);
                }
                return converter.apiError("CollectionPoint not found");
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding collectionPoint", e);
        }

    }
}
