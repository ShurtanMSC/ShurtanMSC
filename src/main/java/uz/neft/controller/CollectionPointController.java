package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.action.CollectionPointActionService;

@RestController
@RequestMapping("api/collection_point")
public class CollectionPointController {

    @Autowired
    CollectionPointActionService collectionPointActionService;

    /**
     * Manually
     **/


    @PostMapping("manually/add/action")
    public HttpEntity<?> addAction(@CurrentUser User user,
                                   @RequestBody CollectionPointActionDto dto) {
        return collectionPointActionService.addManually(user, dto);
    }


    /** Auto **/

}
