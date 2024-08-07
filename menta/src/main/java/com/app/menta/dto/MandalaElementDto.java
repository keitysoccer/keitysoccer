package com.app.menta.dto;

import lombok.Data;

import java.util.List;

/**
 * MandalaElementDto

 * */
@Data
public class MandalaElementDto {

    /** rowNumber */
    private List<MandalaDataDto> dataList;

    private String prntRowNumber;

}

