package com.app.menta.dao;

import com.app.menta.config.DomaInjectConfig;
import com.app.menta.entity.NameInfo;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import java.util.List;

@Dao
@DomaInjectConfig
public interface NameInfoDao {

    @Select
    List<NameInfo> selectAll();

    @Select
    NameInfo selectById(String id);


}
