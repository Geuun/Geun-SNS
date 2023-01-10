package com.dev.geunsns.apps.user.controller;

import com.dev.geunsns.apps.user.data.dto.request.UserRoleChangeRequest;
import com.dev.geunsns.apps.user.data.dto.response.UserRoleChangeResponse;
import com.dev.geunsns.apps.user.service.UserService;
import com.dev.geunsns.global.exception.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "User Role Change API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserChangeRoleController {

    private final UserService userService;

    @ApiOperation(value = "User Role 변경기능", notes = "변경할 User의 ID와 변경할 Role을 입력합니다.")
    @PostMapping("{id}/roles")
    public Response changeRole(@PathVariable Long id,
                               @RequestBody UserRoleChangeRequest roleChangeRequest) {

        UserRoleChangeResponse userRoleChangeResponse = userService.changeUserRole(id, roleChangeRequest);

        return Response.success(userRoleChangeResponse);
    }

    @ApiOperation(value = "User Role 변경기능 (For Super Admin", notes = "변경할 User의 ID와 변경할 Role을 입력합니다.")
    @PostMapping("{id}/roles/admins")
    public Response changeRoleForSuperAdmin(@PathVariable Long id,
                                            @RequestBody UserRoleChangeRequest roleChangeRequest) {

        UserRoleChangeResponse userRoleChangeResponse = userService.changeRoleForSuperAdmin(id, roleChangeRequest);

        return Response.success(userRoleChangeResponse);
    }
}
