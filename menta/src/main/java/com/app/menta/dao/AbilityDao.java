package com.app.menta.dao;
import com.app.menta.config.DomaInjectConfig;
import com.app.menta.entity.Ability;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import java.util.List;

@Dao
@DomaInjectConfig
public interface AbilityDao{

    @Select
    List<Ability> select(String ability);

    @Select
    List<Ability> selectAll();

    @Select
    Ability selectById(String id);

    @Insert
    int insert(Ability ability);

    @Update
    int update(Ability ability);

}
