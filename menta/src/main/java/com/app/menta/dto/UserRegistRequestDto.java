package com.app.menta.dto;

import lombok.Data;

/**
 * RequestDto
 *
 * */
@Data
public class UserRegistRequestDto {

    /** ID */
    private String username;

    /** 名前 */
    private String password;

    private String email;

}

