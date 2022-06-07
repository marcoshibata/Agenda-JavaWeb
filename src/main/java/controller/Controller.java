package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = {"/Controller","/main","/insert","/select","/update"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DAO dao = new DAO();  
    JavaBeans contato = new JavaBeans();
   
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if(action.equals("/main")) {
			contatos(request,response);
		}else if(action.equals("/insert")) {
			novoContato(request, response);
		}else if(action.equals("/select")) {
			listarContato(request, response);
		}else if(action.equals("/update")) {
			editarContato(request, response);
		}else {
		response.sendRedirect("index.html");
	}
  }
	//Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Criando um novo objeto que ira receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		//encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos",lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		
	}
	//Novo contatos
		protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			
			//setar as variaveis JavaBeans
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
			//invocar o metodo inserirContato passando o objeto contato
			dao.inserirContato(contato);
			//redirecionar para o documento agenda.jsp
			response.sendRedirect("main");
		}
	
		//Listar contato
		protected void listarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			//recebimenton do id co contato que sera editado
			String idcon = request.getParameter("idcon");
			//setar a variavel JavaBeans
			contato.setIdcon(idcon);
			//executar o metodo selecionarContato
			dao.selecionarContato(contato);
			//setar os atributos do fomulario com o conteudo JavaBeans
			request.setAttribute("idcon", contato.getIdcon());
			request.setAttribute("nome", contato.getNome());
			request.setAttribute("fone", contato.getFone());
			request.setAttribute("email", contato.getEmail());
			//Encaminhar ao documento editar.jsp
			RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
			rd.forward(request, response);
			
		}
		//Editar Contato
		protected void editarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//setar as veriaveis JavaBeans
			contato.setIdcon(request.getParameter("idcon"));
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
			//executar o metodo alterar contato
			dao.alterarContato(contato);
			//redirecionar para documento agenda.jsp (atualizando as alyteracoes)
			response.sendRedirect("main");
			
		}
}
