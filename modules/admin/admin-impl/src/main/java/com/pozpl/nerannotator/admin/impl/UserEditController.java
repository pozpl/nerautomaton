package com.pozpl.nerannotator.admin.impl;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/admin/user/edit")
@RolesAllowed("ROLE_ADMIN") //Only global admin deserves this
public class UserEditController {

    private final IUsersEditService usersEditService;

    @Autowired
    public UserEditController(IUsersEditService usersEditService) {
        this.usersEditService = usersEditService;
    }


    @GetMapping("/list")
    public PageDto<UserEditDto> list(@RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
                                     final UserIntDto admin) {
        try{
            return this.usersEditService.list(page, admin);
        }catch (NerServiceException e){
            throw new NerWebException(e);
        }
    }

    @GetMapping("/get")
    public UserEditDto get(@RequestParam(name = "id") final Long id,
                           final UserIntDto admin) {
        try{
            return usersEditService.get(id, admin);
        }catch (NerServiceException e){
            throw new NerWebException(e);
        }
    }

    @PostMapping("/save")
    public UserEditResultDto save(@RequestBody final UserEditDto editDto,
                                  final UserIntDto admin) {
        try{
            return usersEditService.save(editDto, admin);
        }catch (NerServiceException e){
            throw new NerWebException(e);
        }
    }

    @DeleteMapping("/remove")
    public boolean remove(@RequestParam(name="id") final Long id,
                          final UserIntDto admin) {
        try{
            return usersEditService.remove(id, admin);
        }catch (NerServiceException e){
            throw new NerWebException(e);
        }
    }

}
