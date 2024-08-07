package com.app.menta.entity;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;


@Entity
@Table(name = "RESULTS")
@Data
public class Results {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name="results")
    private String results;
}
