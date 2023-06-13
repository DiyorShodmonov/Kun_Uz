package com.example.kunuz.util;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exp.ForbiddenException;

public class RoleUtil {

    public static void checkRoleToAdmin(JwtDTO jwtDTO) {
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new ForbiddenException("Method not allowed");
        }
    }

}
