package com.app.menta.entity;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
@Entity
@Table(name = "POSTURE")
@Data
public class Posture {
    @Id
    @Column(name = "ID")
    private int id;
    @Id
    @Column(name="POSITION")
    private int position;
    @Id
    @Column(name="POSTURE_POSITION")
    private int posture_position;

    @Column(name="POSTURE")
    private String posture;

    @Column(name="ACHIEVE_FLG")
    private int achieve_flg;
}
