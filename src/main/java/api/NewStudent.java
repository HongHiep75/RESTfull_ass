package api;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import enity.Mess;
import enity.Student;
import service.StudentService;
import utils.HttpUtil;

//http://localhost:8081/QuanLySinhVienREST/api-Student
/**
 * Servlet implementation class NewStudent
 */
@WebServlet(urlPatterns = { "/api-Student" })
public class NewStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("APPLICATION/JSON");
		ObjectMapper mapper = new ObjectMapper();
		Student student = HttpUtil.of(request.getReader()).toModel(Student.class);
		if(student == null || !student.kiemTrToanThuocTinh()) {
			mapper.writeValue(response.getOutputStream(), "Nhập ít nhất một thông tin để tìm sinh viên");
			return;
		}
		List<Student> lists = new StudentService().getLists(student);
		if(lists.isEmpty()) {
			mapper.writeValue(response.getOutputStream(), "Không tồn tại sinh viên trong hệ thống");
			return;
		}
		mapper.writeValue(response.getOutputStream(), lists);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("APPLICATION/JSON");
		Mess mess = new Mess();
		ObjectMapper mapper = new ObjectMapper();
		Student student = HttpUtil.of(request.getReader()).toModel(Student.class);
		if (student == null) {
			mess.add("kiem tra lai dinh dang du lieu ");
			mapper.writeValue(response.getOutputStream(), mess);
			return;
		} else {
			mess = new StudentService().save(student);
			mapper.writeValue(response.getOutputStream(), mess);
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("APPLICATION/JSON");
		Mess mess = new Mess();
		ObjectMapper mapper = new ObjectMapper();
		Student student = HttpUtil.of(request.getReader()).toModel(Student.class);
		if (student == null) {
			mess.add("kiem tra lai dinh dang du lieu ");
			mapper.writeValue(response.getOutputStream(), mess);
			return;
		} else {
			mess = new StudentService().update(student);
			mapper.writeValue(response.getOutputStream(), mess);
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("APPLICATION/JSON");
		Mess mess = new Mess();
		ObjectMapper mapper = new ObjectMapper();
		Student student = HttpUtil.of(request.getReader()).toModel(Student.class);
		if (student == null) {
			mess.add("kiem tra lai dinh dang du lieu ");
			mapper.writeValue(response.getOutputStream(), mess);
			return;
		} else {
			mess = new StudentService().delete(student.getId());
			mapper.writeValue(response.getOutputStream(), mess);
		}
	}

}
