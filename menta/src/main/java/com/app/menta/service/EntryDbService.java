package com.app.menta.service;

import com.app.menta.dao.AbilityDao;
import com.app.menta.dao.PostureDao;
import com.app.menta.dao.ResultsDao;
import com.app.menta.dao.SeqDao;
import com.app.menta.dto.MandalaDataDto;
import com.app.menta.dto.MandalaElementDto;
import com.app.menta.dto.MandalaRequestDto;
import com.app.menta.entity.Ability;
import com.app.menta.entity.Posture;
import com.app.menta.entity.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DB登録用のサービス
 *
 */
@Slf4j
@Service
public class EntryDbService {

    @Autowired
    private ResultsDao resultsDao;

    @Autowired
    private AbilityDao abilityDao;

    @Autowired
    private PostureDao postureDao;

    @Autowired
    private SeqDao seqDao;



    /** マンダラチャート中央のINDEX値 */
    private static final String CENTER_INDEX = "5";
    /** 達成フラグ:未達 */
    private static final int ACHIEVE_NOT = 0;


    /**
     * DB登録用
     *
     * @param request マンダラチャートの入力値
     */
    @Transactional(value = "datasourcemanager",rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void excute(MandalaRequestDto request) {

        // IDが既に振られている場合はINSERTそうでない場合はUPDATE
        boolean insFlg = request.getId() == null;
        // シーケンス番号を取得 初回はDBより、２度目以降は既に登録済みのものを使用
        Integer seq = request.getId() == null ? seqDao.selectSeq():Integer.valueOf(request.getId());
        // マンダラチャートのオブジェクトリストを全てループ
        for(MandalaElementDto element : request.getElements()){
            // マンダラチャートの中心の3×3の場合
            if(element.getPrntRowNumber().equals(CENTER_INDEX)){
                // 子要素に対して処理
                element.getDataList().forEach(data ->{
                    if(data.getRowNumber().equals(CENTER_INDEX)){
                        // 子要素の中心はタイトルとして登録
                        entryResults(insFlg, seq, data);
                    }else{
                        // 中心以外は子要素の中心はABILITYとして登録
                        entryAbilty(insFlg, seq, data);
                    }
                });
            }else{
                // マンダラチャートの中心の3×3以外の場合postureに登録
                entryPosture(insFlg, seq, element);
            }
        }

    }

    /**
     * タイトル登録
     * @param insFlg INSERTorUPDATEフラグ
     * @param seq シーケンス番号
     * @param data 登録データ
     */
    private void entryResults(boolean insFlg, Integer seq, MandalaDataDto data) {
        // 子要素の中心はタイトルとして登録
        Results results = new Results();
        results.setId(seq);
        results.setResults(data.getInputValue());
        // 初回はINSERT、それ以外はUPDATE
        if(insFlg){
            resultsDao.insert(results);
        }else{
            int num = resultsDao.update(results);
            if(num == 0){
                // ゴミデータ時に登録されないので、登録
                resultsDao.insert(results);
            }
        }
    }

    /**
     * ABILITY登録
     * @param insFlg INSERTorUPDATEフラグ
     * @param seq シーケンス番号
     * @param data 登録データ
     */
    private void entryAbilty(boolean insFlg, Integer seq, MandalaDataDto data) {

        // DBに登録
        Ability ability= new Ability();
        ability.setId(seq);
        // Positionは振られているnumberを設定
        ability.setPosition(Integer.valueOf(data.getRowNumber()));
        ability.setAbility(data.getInputValue());
        // 初回はINSERT、それ以外はUPDATE
        if(insFlg){
            abilityDao.insert(ability);
        }else{
            int num = abilityDao.update(ability);
            if(num == 0){
                // ゴミデータ時に登録されないので、登録
                abilityDao.insert(ability);
            }
        }
    }

    /**
     * Posture登録
     * @param insFlg INSERTorUPDATEフラグ
     * @param seq シーケンス番号
     * @param element 登録データ要素
     */
    private void entryPosture(boolean insFlg, Integer seq, MandalaElementDto element) {
        element.getDataList().forEach(data ->{
            //子要素中心はABILITYなので登録しない
            if(!data.getRowNumber().equals(CENTER_INDEX)){
                Posture posture = new Posture();
                posture.setId(seq);
                posture.setPosition(Integer.valueOf(element.getPrntRowNumber()));
                posture.setPosture_position(Integer.valueOf(data.getRowNumber()));
                posture.setPosture(data.getInputValue());
                posture.setAchieve_flg(ACHIEVE_NOT);
                // 初回はINSERT、それ以外はUPDATE
                if(insFlg){
                    postureDao.insert(posture) ;
                }else{
                    int num = postureDao.update(posture);
                    log.info("num " + num);
                    if(num == 0){
                        // ゴミデータ時に登録されないので、登録
                        postureDao.insert(posture);
                    }
                }
            }
        });
    }

    @Transactional(value = "datasourcemanager",rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void delete(String title) {
        resultsDao.deleteByTitle(title);
    }
}
