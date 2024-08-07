package com.app.menta.dto;

import com.app.menta.entity.NameInfo;
import com.app.menta.entity.Results;
import com.app.menta.entity.Ability;
import com.app.menta.entity.Posture;
import lombok.Data;

import java.util.List;

@Data
public class DbResponseDto {

    /** DB情報 */
    private List<NameInfo> nameInfo;
    private List<Results> Results;
    private List<Ability> Ability;
    private List<Posture> Posture;
    /** 結果コード */
    private String resultCode;

}