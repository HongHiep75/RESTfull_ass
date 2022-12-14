package api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import enity.Student;
import service.StudentService;
import utils.HttpUtil;

/**
 * Servlet implementation class apiStudentHappyBirthday
 */
@WebServlet(urlPatterns = { "/api-Student/HappyBirthday" })
public class apiStudentHappyBirthday extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public apiStudentHappyBirthday() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("APPLICATION/JSON");
		ObjectMapper mapper = new ObjectMapper();
		List<Student> lists = new StudentService().getHappyBirthday();
		if(lists.isEmpty()) {
			mapper.writeValue(response.getOutputStream(), "Không sinh viên sinh nhật trong hôm nay");
			return;
		}
		mapper.writeValue(response.getOutputStream(), lists);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
