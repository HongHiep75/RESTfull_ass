package service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import chuc.nang.chung.CRUD;
import chuc.nang.chung.StudentInterface;
import enity.Mess;
import enity.Student;
import repository.StuDentRepository;

public class StudentService implements CRUD<Student>,StudentInterface {
	private StuDentRepository stuDentRepository = new StuDentRepository();

	@Override
	public Mess save(Student t) {
		Mess mess = this.validate(t);
		if (mess.getMesses().isEmpty()) {
			return stuDentRepository.save(t);
		}
		return mess;
	}

	@Override
	public Mess update(Student t) {
		Mess mess = this.validate(t);
		if (mess.getMesses().isEmpty()) {
			return stuDentRepository.update(t);
		}
		return mess;
	}

	@Override
	public Mess delete(int id) {
		return stuDentRepository.delete(id);
	}

	public Mess validate(Student t) {
		Pattern checkName = Pattern.compile("[a-zA-z]{1,50}$");
		Pattern checkString = Pattern.compile("[a-zA-Z0-9]");
		Mess mess = new Mess();

		if (t.getFullName() == null || !checkName.matcher(t.getFullName()).find()) {
			mess.add("Tên bị để trống, hoặc lớn hơn 50 ký tự");
		}
		if (t.getClassName() == null || !checkString.matcher(t.getClassName()).find()) {
			mess.add("Tên lớp không hợp lệ");
		}
		if (t.getHometown() == null || !checkString.matcher(t.getHometown()).find()) {
			mess.add("Địa chỉ  không hợp lệ");
		}
		if (t.getMajor() == null || !checkString.matcher(t.getMajor()).find()) {
			mess.add("Tên khoa không hợp lệ");
		}
		String gender = t.getGender();
		if (!(gender.equals("Nam") || gender.equals("Nữ") || gender.equals("Khác"))) {
			mess.add("Giới tính không hợp lệ (Nam,Nữ,Khác)");
		}
		t.setAverageMax(Math.round(t.getAverageMax() * 100) / 100.0);
		if (t.getAverageMax() < 0 || t.getAverageMax() > 10) {
			mess.add("Điểm  không hợp lệ");
		}
		if (t.getBirthday() == null) {
			mess.add("Birthday khong duoc de trong,Birthday dinh dang dd/mm/yyy");
		} else {
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			Calendar namSinh = Calendar.getInstance();
			namSinh.setTime(t.getBirthday());
			int tuoi = calendar.get(Calendar.YEAR) - namSinh.get(Calendar.YEAR);
			System.out.println(t.getBirthday().toString());
			if (tuoi < 2 || tuoi > 150) {
				mess.add("Tuổi không hợp lệ");
			}
		}
		// kiem tra tuoi nay nam hien tai tru nam sinh

		return mess;

	}

	@Override
	public List<Student> getLists(Student t) {
		return stuDentRepository.getLists(t);
	}

	@Override
	public List<Student> getHappyBirthday() {
		// TODO Auto-generated method stub
		return stuDentRepository.getHappyBirthday();
	}

}