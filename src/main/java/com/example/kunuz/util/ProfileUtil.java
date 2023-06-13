package com.example.kunuz.util;

import com.example.kunuz.dto.RegistrationDTO;
import com.example.kunuz.exp.*;


public class ProfileUtil {


    public static void check(RegistrationDTO dto) {
        if (dto.getPhone() == null || dto.getPassword() == null || dto.getName() == null || dto.getSurname() == null) {
            throw new ArgumentNullException("Ma'lumotlarni barhcasini kiriting!");
        }

        if (!dto.getPhone().matches("[9]{1}[98]{1}[0-9]{10}")) {
            throw new InCorrectPhoneNumberException("Raqam togri kritilmagan");
        }


        if (dto.getPassword().length() < 7) {
            throw new IncorrectPasswordException("Password kamida 8 ta belgidan iborat bolishi lozim");
        }

        if (dto.getName().length() < 3) {
            throw new IncorrectArgumentValueSizeException("Name kamida 4 ta harfdan iborat bolishi kereak");
        }

        if (dto.getSurname().length() < 3) {
            throw new IncorrectArgumentValueSizeException("Surname kamida 4 ta harfdan iborat bolishi kereak");
        }

    }
}
