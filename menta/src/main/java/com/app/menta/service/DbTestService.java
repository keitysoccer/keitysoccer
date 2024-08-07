package com.app.menta.service;

import com.app.menta.dao.NameInfoDao;
import com.app.menta.dto.*;
import com.app.menta.entity.NameInfo;
import com.app.menta.dao.ResultsDao;
import com.app.menta.entity.Results;
import com.app.menta.dao.AbilityDao;
import com.app.menta.entity.Ability;
import com.app.menta.dao.PostureDao;
import com.app.menta.entity.Posture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * DB接続試験用
 *
 */
@Slf4j
@Service
public class DbTestService {

    @Autowired
    private NameInfoDao nameInfoDao;
    @Autowired
    private ResultsDao resultsDao;
    @Autowired
    private AbilityDao abilityDao;
    @Autowired
    private PostureDao postureDao;
    /**
     * DB接続試験用
     *
     * @param request 要求電文
     */
    @Transactional(value = "datasourcemanager",rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public DbResponseDto excute(DbRequestDto request) {

        DbResponseDto dbResponseDto = new DbResponseDto();

        try {
            List<NameInfo> nameInfos = new ArrayList<>();
            List<Results> results = new ArrayList<>();
            List<Ability> abilitys = new ArrayList<>();
            List<Posture> postures = new ArrayList<>();

            // IDが連携されている場合はIDで検索。そうでない場合は全件取得
            if (request.getId() != null) {
                nameInfos.add(nameInfoDao.selectById(request.getId()));
                results.add(resultsDao.selectById(request.getId()));
                abilitys.add(abilityDao.selectById(request.getId()));
                postures.add(postureDao.selectById(request.getId()));
            } else {
                nameInfos = nameInfoDao.selectAll();
                results = resultsDao.selectAll();
                abilitys = abilityDao.selectAll();
                postures = postureDao.selectAll();
            }

            // 結果コードと取得結果をセット
            dbResponseDto.setResultCode("0000");
            dbResponseDto.setNameInfo(nameInfos);
            dbResponseDto.setResults(results);
            dbResponseDto.setAbility(abilitys);
            dbResponseDto.setPosture(postures);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            // エラーの場合はエラーコードをセット
            dbResponseDto.setResultCode("9999");
        }

        return dbResponseDto;
    }
}
