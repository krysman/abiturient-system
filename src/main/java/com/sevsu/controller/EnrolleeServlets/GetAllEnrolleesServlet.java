package com.sevsu.controller.EnrolleeServlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevsu.dao.EnrolleeDao;
import com.sevsu.model.Enrollee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class GetAllEnrolleesServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // set response type to JSON
        response.setContentType("application/json");

        EnrolleeDao enrolleeDao = new EnrolleeDao();
        List<Enrollee> allEnrollees = enrolleeDao.getAllEnrollee();

        //  initiate jackson mapper
        ObjectMapper mapper = new ObjectMapper();

        // send List<Enrollee> as JSON to client
        mapper.writeValue(response.getOutputStream(), allEnrollees);
    }
}
