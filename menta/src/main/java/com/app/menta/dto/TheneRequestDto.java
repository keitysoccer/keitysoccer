package com.app.menta.dto;

import lombok.Data;

/**
 * RequestDto
 *
 * */
@Data
public class TheneRequestDto {

    /** ID */
    private String themeId;

    /** 名前 */
    private String themeName;
    /** タイトル */
    private String themeTitle;

    private String themeGrid;
}

