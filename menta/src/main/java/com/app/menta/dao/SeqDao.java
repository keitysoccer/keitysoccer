package com.app.menta.dao;
import com.app.menta.config.DomaInjectConfig;
import com.app.menta.entity.Results;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;


import java.util.List;

@Dao
@DomaInjectConfig
public interface SeqDao {

    @Select
    Integer selectSeq();
}

