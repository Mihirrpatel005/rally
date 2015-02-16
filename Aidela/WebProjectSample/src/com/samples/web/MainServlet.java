package com.samples.web;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samples.web.Models.Library;

/**
 * Servlet implementation class SampleServlet
 */
@WebServlet("/SampleServlet")
public class MainServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("category") != null) {
			// Category view request
			request.setAttribute("category", Library.getInstance().getCategoryWithName(request.getParameter("category")));
			request.getRequestDispatcher("/JSP/category.jsp").forward(request, response);
		} else if (request.getParameter("bookid") != null) {
			// Book view request
			request.setAttribute("book", Library.getInstance().getBookWithISBN(request.getParameter("bookid")));
			request.getRequestDispatcher("/JSP/book.jsp").forward(request, response);
		} else {
			// Default request
			request.setAttribute("categoryArrayList", Library.getInstance().getCategories());
			request.getRequestDispatcher("/JSP/home.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
