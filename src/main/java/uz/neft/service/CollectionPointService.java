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
            CollectionPoint collectionPoint = new CollectionPoint();
            collectionPoint.setName(dto.getName());

            Optional<Uppg> byIdCollection = uppgRepository.findById(dto.getUppgDto().getId());
            collectionPoint.setUppg(byIdCollection.get());

            CollectionPoint save = collectionPointRepository.save(collectionPoint);
            return converter.apiSuccess("Saved collection point", save);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error creating collection point");
        }
    }

    public ApiResponse edit(CollectionPointDto dto) {
        try {
            CollectionPoint editingCollectionPoint = new CollectionPoint();
            Optional<CollectionPoint> byId = collectionPointRepository.findById(dto.getId());
            if (byId.isPresent()) editingCollectionPoint = byId.get();
            editingCollectionPoint.setName(dto.getName());

            Optional<Uppg> byIdCollection = uppgRepository.findById(dto.getUppgDto().getId());
            editingCollectionPoint.setUppg(byIdCollection.get());

            CollectionPoint editedCollectionPoint = collectionPointRepository.save(editingCollectionPoint);
            return converter.apiSuccess("Edited Mining System", editedCollectionPoint);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error editing collection point");
        }
    }

    public ApiResponse delete(Integer id) {
        try {
            if (id != null) {
                Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
                if (byId.isPresent()) {
                    collectionPointRepository.deleteById(id);
                    return converter.apiSuccess("Deleted collection point");
                } else {
                    return converter.apiError("Collection point not found");
                }
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
            return converter.apiError("Error in finding collection point", e);
        }
    }

    public ApiResponse findById(Integer id) {
        try {
            if (id != null) {
                Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
                if (byId.isPresent()) {
                    Optional<CollectionPoint> byId1 = collectionPointRepository.findById(id);
                    return converter.apiSuccess(byId1.get());
                } else {
                    return converter.apiError("CollectionPoint not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding collectionPoint", e);
        }

    }
}
