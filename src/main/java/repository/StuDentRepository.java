package repository;


import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;
import chuc.nang.chung.CRUD;
import chuc.nang.chung.StudentInterface;
import enity.Mess;
import enity.Student;
import utils.HibernateUtil;

public class StuDentRepository implements CRUD<Student>,StudentInterface {
	static Logger logger = Logger.getLogger(StuDentRepository.class);

	private static final SessionFactory FACTORY = HibernateUtil.getSessionFactory();

	@Override
	public List<Student> getLists(Student t) {
		if(t == null) {
			return new LinkedList<Student>();
		}
		List<Student> lists = new LinkedList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Student> query = builder.createQuery(Student.class);
		Root<Student> root = query.from(Student.class);
        // tao cau dieu kien where ...and...and
		query.where(builder.and(checkName(builder, "fullName", root, t.getFullName()),
				kiemTraDungChuoi(builder, "className", root, t.getClassName()),
				kiemTraDungChuoi(builder, "hometown", root, t.getHometown()),
				kiemTraDungChuoi(builder, "major", root, t.getMajor()),
				kiemTraDungChuoi(builder, "gender", root, t.getGender()),
				kiemDiem(builder, "averageMax", root, t.getAverageMax()),
				kiemDate(builder, "birthday", root, t.getBirthday())

		));
		
		Query q = session.createQuery(query);
		lists = q.getResultList();
		session.close();
		return lists;
	}

	private Predicate checkName(CriteriaBuilder builder, String fullName, Root<Student> root, String name) {
		if (name == null) {
			// dieu kien luon dung khi query
			return builder.isNotNull(root.get("id"));
		}
		Expression<String> upper = builder.lower(root.get(fullName));
		return builder.like(upper, "%" + name.toLowerCase() + "%");
	}

	private Predicate kiemTraDungChuoi(CriteriaBuilder builder, String nameVal, Root<Student> root, String val) {
		if (val == null) {
			return builder.isNotNull(root.get("id"));
		}
		return builder.like(root.get(nameVal), "%" + val + "%");
	}

	private Predicate kiemDiem(CriteriaBuilder builder, String nameVal, Root<Student> root, double val) {
		if (val < 0) {
			return builder.isNotNull(root.get("id"));
		}
		return builder.between(root.get(nameVal), val - 2, val + 2);
	}

	private Predicate kiemDate(CriteriaBuilder builder, String nameVal, Root<Student> root, Date val) {
		if (val == null) {
			return builder.isNotNull(root.get("id"));
		}
		return builder.between(root.get(nameVal), tangGiamNgay(val, -2), tangGiamNgay(val, 2));
	}

	private Date tangGiamNgay(Date val, int ngay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(val);
		cal.add(Calendar.DATE, ngay);
		return cal.getTime();
	}



	@Override
	public Mess save(Student t) {
		if(t == null) {
		Mess mess =	new Mess();
		mess.add("Thêm thông tin sinh viên");
			return mess;
		}
		Mess mess = new Mess();
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.save(t);
			session.getTransaction().commit();
			mess.add("Thanh cong");
			return mess;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			;
			e.printStackTrace();
			logger.error(e);
		} catch (Exception e) {
			session.getTransaction().rollback();
			;
			e.printStackTrace();
			logger.error(e);
		} finally {
			session.close();
		}
		mess.add("Them khong thanh cong");
		return mess;
	}

	@Override
	public Mess update(Student t) {
		if(t == null) {
			Mess mess =	new Mess();
			mess.add("Thêm thông tin sinh viên");
				return mess;
			}
		Mess mess = new Mess();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		try {
			session.update(t);
			session.getTransaction().commit();
			mess.add("Thanh cong");
			return mess;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			;
			logger.error(e);
		} catch (Exception e) {
			session.getTransaction().rollback();
			;
			logger.error(e);
		} finally {
			session.close();
		}
		mess.add("Khong thanh cong, sinh viên không tồn tại trong hệ thống ");
		return mess;
	}

	@Override
	public Mess delete(int id) {
		Mess mess = new Mess();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		try {
			Student student = session.get(Student.class, id);
			if (student == null) {
				mess.add("Id không tồn tại");
				return mess;
			}
			session.delete(student);
			session.getTransaction().commit();
			mess.add("Thanh cong");
			return mess;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			;
			e.printStackTrace();
			logger.error(e);
		} catch (Exception e) {
			session.getTransaction().rollback();
			;
			e.printStackTrace();
			logger.error(e);
		} finally {
			session.close();
		}
		mess.add("khong thanh cong");
		return mess;
	}

	@Override
	public List<Student> getHappyBirthday() {
		List<Student> lists = new LinkedList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();

		String sql = "FROM STUDENT where to_char(BIRTHDAY,'dd') = to_char(sysdate,'dd')";
		Query q = session.createQuery(sql, Student.class);
		try {
			lists = q.getResultList();
		} catch (JDBCConnectionException e) {
			System.out.println("Ket noi database khong thanh cong");
            logger.error(e);
		} catch (SQLGrammarException e) {
			System.out.println("Loi cu phap sql");
			logger.error(e);
		} catch (HibernateException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			session.close();
		}
		return lists;
	}

}
