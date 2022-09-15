package enity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "STUDENT")
public class Student extends Person implements Serializable {

	@Column(name = "CLASS_NAME")
	private String className;

	@Column(name = "MAJOR")
	private String major;

	@Column(name = "AVERAGE_MARK")
	private double averageMax = -1;

	public Student(int id, String fullName, Date birthday, String hometown, String gender, String className,
			String major, double averageMax) {
		super(id, fullName, birthday, hometown, gender);
		this.className = className;
		this.major = major;
		this.averageMax = averageMax;
	}

	public boolean kiemTrToanThuocTinh() {
		if (this.getFullName() != null || this.getBirthday() != null || this.className != null) {
			return true;
		}

		if (this.getHometown() != null || this.getGender() != null || this.major != null) {
			return true;
		}
		if(this.averageMax >  -1) {
			return true;
		}
		return false;
	}
}
