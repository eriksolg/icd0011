package jdbc2;

import com.fasterxml.jackson.databind.ObjectMapper;
import util.FileUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/employees")
public class EmployeeServlet extends HttpServlet {
    EmployeeDao employeeDao;
    @Override
    public void init() throws ServletException {
        employeeDao = (EmployeeDao) getServletContext().getAttribute("employeeDao");
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        DataTableParams input = readParameters(request);

        System.out.println("Parameters from DataTables ajax query: "  + input);

        int orderColumnNr = input.getOrderColumnNr();
        int pageLength = input.getPageLength();
        int pageStart = input.getPageStart();
        String searchString = input.getSearchString();
        String orderDirection = input.getOrderDirection();

        System.out.println(searchString);

        List<Object> results = employeeDao.queryAllEmployees(orderColumnNr + 1, orderDirection, pageLength, pageStart, searchString);
        List<Employee> employeeList = (List<Employee>) results.get(0);
        int recordsFiltered = (int) results.get(1);

        ResponseData responseData = new ResponseData(employeeList, input.getToken(), recordsFiltered);

        new ObjectMapper().writeValue(response.getOutputStream(), responseData);
    }

    @Override
    public void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        String resultJson = FileUtil.readStream(request.getInputStream());

        System.out.println("got POST request with: " +
                resultJson);

        Employee employee = new ObjectMapper().readValue(resultJson, Employee.class);
        employeeDao.insertEmployee(employee);
    }

    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        System.out.println("got PUT request with: " +
                FileUtil.readStream(request.getInputStream()));

    }

    @Override
    public void doDelete(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        System.out.println("got delete request for id " +
                id);
        try {
            employeeDao.deleteEmployee(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private DataTableParams readParameters(HttpServletRequest request) {
        DataTableParams params = new DataTableParams();
        params.setOrderColumnNr(parseInt(request.getParameter("order[0][column]"), 0));
        params.setToken(parseInt(request.getParameter("draw"), 0));
        params.setPageStart(parseInt(request.getParameter("start"), 0));
        params.setPageLength(parseInt(request.getParameter("length"), 0));
        params.setSearchString(request.getParameter("search[value]"));
        params.setOrderDirection(request.getParameter("order[0][dir]"));

        return params;
    }

    private Integer parseInt(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
