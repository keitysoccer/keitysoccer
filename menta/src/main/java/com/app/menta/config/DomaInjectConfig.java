package com.app.menta.config;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * DBConfigをDaoクラスにAutowireするアノテーション
 */
@AnnotateWith(annotations = {
        // クラスに@Componentを設定する
        @Annotation(target = AnnotationTarget.CLASS,type = Component.class),
        // クラスのコンストラクタにConFingを@Autowiredする
        @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class),
        // クラスのコンストラクタに設定するConfigにQualifierを指定する
        @Annotation(target = AnnotationTarget.CONSTRUCTOR_PARAMETER, type = Qualifier.class,elements = "\"" + "dbConfig"+"\"")
})

public @interface DomaInjectConfig {
}
