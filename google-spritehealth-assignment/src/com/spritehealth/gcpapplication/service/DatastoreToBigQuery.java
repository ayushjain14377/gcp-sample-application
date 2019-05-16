package com.spritehealth.gcpapplication.service;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
/**
 * Servlet implementation class DatastoreToBigQuery
 */
@WebServlet("/DatastoreToBigQuery")
public class DatastoreToBigQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BigQuery bigquery;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DatastoreToBigQuery() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		bigquery = BigQueryOptions.getDefaultInstance().getService();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}


}
