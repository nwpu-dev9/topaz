package org.dev9.topaz.api.controller;

import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.common.dao.AbstractQuery;
import org.dev9.topaz.common.dao.query.UserQuery;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class SearchController {

    @Resource
    private UserRepository userRepository;

    @PostMapping("/user/search")
    @ResponseBody
    public ResponseEntity<RESTfulResponse<List<User>>> searchUser(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String profile,
            @RequestParam(required = false) String logicTypeString
    ){
        RESTfulResponse<List<User>> response=null;
        List<User> users=null;

        if (null != userId){
            users = new ArrayList<>();
            users.add(userRepository.findById(userId).orElse(null));
        }

        if (null == users){
            UserQuery userQuery=new UserQuery(){{
                setCombineLogicType(LogicType.AND);
                setNameLike(name);
                setPhoneNumberLike(phoneNumber);
                setProfileLike(profile);
            }};

            if ("OR".equals(logicTypeString))
                userQuery.setCombineLogicType(AbstractQuery.LogicType.OR);

            users=userRepository.findAll(userQuery.toSpec(), Sort.by("signupTime"));
        }

        if (null == users)
            response=RESTfulResponse.fail("no such user");

        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);

        response=RESTfulResponse.ok();
        response.setData(users);
        return ResponseEntity.ok(response);
    }
}
