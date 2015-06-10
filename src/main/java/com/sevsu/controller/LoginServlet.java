package com.sevsu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevsu.dao.UserDao;
import com.sevsu.model.User;
import com.sevsu.util.Credentials;
import com.sevsu.util.PasswordHash;
import com.sevsu.util.UserDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //  Set response type to JSON
        response.setContentType("application/json");

        BufferedReader br = null;
        Credentials userCredentials = null;
        // initiate jackson mapper
        ObjectMapper mapper = new ObjectMapper();
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String json = br.readLine();

            // 3. Convert received JSON to String
            userCredentials = mapper.readValue(json, Credentials.class);
        } catch(IOException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String exceptionString = "Some shit happened while trying to get user input from JSON file!!!!!" + e.toString();
            mapper.writeValue(response.getOutputStream(), exceptionString);
        } finally {
            if(userCredentials == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                String exceptionString = "Some shit happened while trying to get user input from JSON file!!!!!" + "userCredentials == null";
                mapper.writeValue(response.getOutputStream(), exceptionString);
            }
        }

        // validate user сredentials

        // check if user with inputted e-mail exist in DB
        boolean userExist = false;
        User user = null;
        UserDao userDao = new UserDao();
        if(userCredentials != null) {
            user = userDao.getUserByLogin(userCredentials.getLogin());
            if(user != null) {
                userExist = true;
            }

        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String exceptionString = "Some shit happened while trying to get user input from JSON file!!!!!" + "userCredentials == null";
            mapper.writeValue(response.getOutputStream(), exceptionString);
        }

        if( ! userExist) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String exceptionString = "User with login: " + userCredentials.getLogin() + " doesn't exist! if( ! userExist)";
            mapper.writeValue(response.getOutputStream(), exceptionString);
        }


        // check if password is correct
        boolean passwordIsCorrect = PasswordHash.validatePassword(userCredentials.getPassword(), user.getPasswordMd5(), user.getSaltForPassword());

        if(passwordIsCorrect) {
            // add cookie
            //response.addCookie() .cookie("userEmailSurveyApp", validatedUserEmail, 60*60*24*30, true);

            // set session attribute

            UserDetails userDetails = new UserDetails(user.getId(), user.getLogin(), user.getRole().getName());
            HttpSession session = request.getSession();
            session.setAttribute("user", userDetails);

            String responseString = "Success";
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getOutputStream(), responseString);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
            String exceptionString = "User with login: " + userCredentials.getLogin() + " doesn't exist! if(passwordIsCorrect)";
            mapper.writeValue(response.getOutputStream(), exceptionString);
        }
    }
}
