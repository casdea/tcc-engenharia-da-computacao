package br.com.alura.gerenciador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/oi")
public class OiMundoServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter writer = resp.getWriter();
		
		writer.println("<html>");
		writer.println("<body>");		
		writer.println("MEU PRIMEIRO SERVLET");
		writer.println("</body>");
		writer.println("</html>");
	}
	
	
	public static void main(String[] args) {
	   
	}
}
