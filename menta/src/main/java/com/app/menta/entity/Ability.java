package com.app.menta.entity;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
@Entity
@Table(name = "ABILITY")
@Data
public class Ability {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name="POSITION")
    private int position;

    @Column(name="ability")
    private String ability;
}
