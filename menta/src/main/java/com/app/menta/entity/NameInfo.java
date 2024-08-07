package com.app.menta.entity;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "NAME_INFO")
@Data
public class NameInfo {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VAL")
    private String val;

    @Column(name = "NUM")
    private Long num;


}
