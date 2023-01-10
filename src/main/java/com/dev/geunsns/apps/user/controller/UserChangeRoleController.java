package com.dev.geunsns.apps.user.controller;

import com.dev.geunsns.apps.user.data.dto.request.UserRoleChangeRequest;
import com.dev.geunsns.apps.user.data.dto.response.UserRoleChangeResponse;
import com.dev.geunsns.apps.user.service.UserService;
import com.dev.geunsns.global.exception.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserChangeRoleController {

    private final UserService userService;

    @PostMapping("{id}/roles")
    public Response changeRole(@PathVariable Long id, @RequestBody UserRoleChangeRequest roleChangeRequest) {

        UserRoleChangeResponse userRoleChangeResponse = userService.changeUserRole(id, roleChangeRequest);

        return Response.success(userRoleChangeResponse);
    }

    @PostMapping("{id}/roles/admins")
    public Response changeRoleForSuperAdmin(@PathVariable Long id, @RequestBody UserRoleChangeRequest roleChangeRequest) {

        UserRoleChangeResponse userRoleChangeResponse = userService.changeRoleForSuperAdmin(id, roleChangeRequest);

        return Response.success(userRoleChangeResponse);
    }
}
