package com.app.menta.dao;
import com.app.menta.config.DomaInjectConfig;
import com.app.menta.entity.Results;
import org.seasar.doma.*;

import java.util.List;

@Dao
@DomaInjectConfig
public interface ResultsDao{

    @Select
    List<Results> select(String results);
    @Select
    List<Results> selectAll();

    @Select
    Results selectById(String id);

    /** INSERT用Results resultsに値を詰めると自動でINSERT文を発行してくれる
     *  sqlFile = trueをつけると自前で書いたSQL文も実行できる
     *  その場合はresources配下にSQLを用意し、それを実行する(SQLがないとエラーになる)*/
    @Insert
    int insert(Results results);

    /** updateしたい場合は以下のアノテーションを作りメソッド作成
     * sqlFile = trueはresources配下にSQLを用意し、それを実行する(SQLがないとエラーになる) */
    @Update(sqlFile = true)
    int updateTitle(Results results);

    @Update
    int update(Results results);

    @Delete(sqlFile = true)
    int deleteByTitle(String title);
}
