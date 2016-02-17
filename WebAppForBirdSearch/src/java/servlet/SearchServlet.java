package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SearchModel;
/* 
 * @author Yi
 *
 * This is the Control part of MVC.
 * The welcome-file in the deployment descriptor (web.xml) points
 * to this servlet. So it is also the starting place for the web
 * application.
 *
 * The servlet is acting as the controller.
 * There are two views - prompt.jsp and result.jsp.
 * It decides between the two by determining if there is a search parameter or
 * not. If there is no parameter, then it uses the prompt.jsp view, as a 
 * starting place. If there is a search parameter, then it searches for a 
 * picture and uses the result.jsp view.
 * The model is provided by SearchModel.
 */

public class SearchServlet extends HttpServlet {

    private SearchModel sModel= null;
    //This is the target url string.
    private static final String url = "http://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/";;
    
    @Override
    public void init() {
        //Initialize the model and for later use.
        sModel = new SearchModel();
    }
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String search = request.getParameter("types");
        
         // determine what type of device our user is mobile client or desktop client
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        
        String nextView;
        if (search != null) {
            //Use the proper model and client type to do search.
            List rets = sModel.doBirdSearch(search, mobile);
            if (!rets.isEmpty()) {
            request.setAttribute("rets", rets);
            request.setAttribute("birdType", search);
            nextView = "result.jsp";
            } else {
                nextView = "prompt.jsp";
            }
        } else {
            nextView = "prompt.jsp";
        }
        
        //Forwar to next jsp view.
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
