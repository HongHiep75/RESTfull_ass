package enity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "STUDENS")
public class Test implements Serializable {
    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "BIR")
	private Date kiemTra;
}
