package com.app.menta.dao;
import com.app.menta.config.DomaInjectConfig;
import com.app.menta.entity.Ability;
import com.app.menta.entity.Posture;
import com.app.menta.entity.Results;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import java.util.List;

@Dao
@DomaInjectConfig
public interface PostureDao{

    @Select
    List<Posture> selectAll();

    @Select
    Posture selectById(String id);

    /**
     *  親要素含めて全て取得する(表示位置もセット)
     *  子要素中心のデータについては双方登録されていない為、双方セットされるようにUNION
     *  @param id タイトルID
     *  */
    @Select
    List<Posture> selectAllDataById(String id);

    @Insert
    int insert(Posture posture);

    @Update
    int update(Posture posture);


}
