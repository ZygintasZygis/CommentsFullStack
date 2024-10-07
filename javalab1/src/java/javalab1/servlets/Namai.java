/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package javalab1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author zygis
 */
@WebServlet(name = "Namai", urlPatterns = {"/"})
public class Namai extends HttpServlet {
    
    private javalab1.beans.Message msg = new javalab1.beans.Message();
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    @Resource
    private UserTransaction utx;

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
        
        EntityManager em = null;
        
        try (PrintWriter out = response.getWriter()) {
            
            em = emf.createEntityManager(); //sukuriamas naujas CRUD operaciju valdymo objektas
            List<javalab1.entities.Message> messages = em.createQuery("Select m from Message m").getResultList();
            request.setAttribute("msg", this.msg);
            request.setAttribute("msg_list", messages);
            request.getRequestDispatcher("namai.jsp").forward(request,response);
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Namai</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Namai at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
        }catch (Exception ex){
            throw new ServletException(ex);
        } finally{
            if(em != null){
                em.close();
            }
        }
    }
    
 @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");

    if ("delete".equals(action)) {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            deleteMessageById(id); // Ištrina pranešimą pagal ID
        }
    } else if ("markDeleted".equals(action)) {
        String name = request.getParameter("name");

        if (name != null && !name.isEmpty()) {
            EntityManager em = null;
            try {
               System.out.println("Vardas: " + name );
               utx.begin();
               System.out.println("Tranzakcija pradeta.");
               
               em = emf.createEntityManager();
               System.out.println("EntityManager sukurtas.");
               
               Query query = em.createQuery(
               "Update Message m SET m.message = 'Istrintas' WHERE m.name = :name");
               int updateCount = query.setParameter("name", name).executeUpdate();
               
               System.out.println("atnaujintu pranesimu kiekis: " + updateCount);
               utx.commit();
               System.out.println("Tranzakcija baigta.");

            } catch (Exception ex) {
                if (utx != null) {
                    try {
                        utx.rollback();
                    } catch (Exception rollbackEx) {
                        rollbackEx.printStackTrace();
                        throw new ServletException(rollbackEx);
                    }
                }

                // Loguojame klaidos žinutę ir „stack trace“
                ex.printStackTrace();
                throw new ServletException(ex);

            } finally {
                if (em != null) {
                    em.close();
                }
            }
        }
    }
    else
    {
        String l_name = request.getParameter("name");
        String l_comment = request.getParameter("message");
        
        if (l_name != null && l_comment != null && !l_comment.isEmpty())
        {
            EntityManager em = null;
            try{
                javalab1.entities.Message e_msg = new javalab1.entities.Message();
                e_msg.setMessage(l_comment);
                e_msg.setName(l_name);
                e_msg.setTime(new Date());
                
                utx.begin();
                em = emf.createEntityManager();
                em.persist(e_msg);
                utx.commit();
                
            }catch (Exception ex){
                if (utx != null){
                    try{
                        utx.rollback();
                    }catch (Exception rollback){
                        rollback.printStackTrace();
                        throw new ServletException(rollback);
                    }
                }
                ex.printStackTrace();
                throw new ServletException(ex);
            }finally{
                if (em != null){
                    em.close();
                }
            }
            
        }
    }

    // Po veiksmo, nukreipiame atgal į `processRequest`, kad perduotume naujus duomenis į JSP
    processRequest(request, response);
}



  

// Funkcija, kuri ištrina pranešimą pagal ID
private void deleteMessageById(int id) throws ServletException {
    EntityManager em = null;
    try {
        utx.begin();
        em = emf.createEntityManager();
        javalab1.entities.Message message = em.find(javalab1.entities.Message.class, id);
        if (message != null) {
            em.remove(message);  // Ištriname pranešimą
        }
        utx.commit();
    } catch (Exception ex) {
        if (utx != null) {
            try {
                utx.rollback();
            } catch (Exception rollbackEx) {
                throw new ServletException(rollbackEx);
            }
        }
        throw new ServletException(ex);
    } finally {
        if (em != null) {
            em.close();
        }
    }
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
