package com.app.menta.dto;

import lombok.Data;

import java.util.List;

/**
 * MandalaRequestDto
 *
 * */
@Data
public class MandalaRequestDto {

    /** elements */
    private List<MandalaElementDto> elements;
    private String id;

}

