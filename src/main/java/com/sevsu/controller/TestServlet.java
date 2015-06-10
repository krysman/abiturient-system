package com.sevsu.controller;

import com.sevsu.dao.EnrolleeDao;
import com.sevsu.dao.RoleDaoImpl;
import com.sevsu.dao.SpecializationDao;
import com.sevsu.dao.UserDao;
import com.sevsu.model.Enrollee;
import com.sevsu.model.Role;
import com.sevsu.model.Specialization;
import com.sevsu.model.User;
import com.sevsu.util.PasswordHash;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

public class TestServlet extends HttpServlet {

    /*public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. get received JSON data from request
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null) {
            json = br.readLine();
        }

        // 2. initiate jackson mapper
        ObjectMapper mapper = new ObjectMapper();

        // 3. Convert received JSON to Article
        String userInputString = mapper.readValue(json, String.class);

        LexicalAnalyzer analyzer = new LexicalAnalyzer(userInputString);
        String exceptionString = analyzer.getExceptionString();

        List<String> parsedLexemes = new ArrayList<>();
        if(exceptionString == null) {
            List<Lexeme> lexemes = analyzer.getLexemes();
            for(Lexeme lexeme : lexemes) {
                parsedLexemes.add(lexeme.toString());
            }
        } else {
            parsedLexemes.add(exceptionString);
        }

        // 4. Set response type to JSON
        response.setContentType("application/json");

        // 5. Add article to List<Article>
        //articles.add(article);

        // 6. Send List<Article> as JSON to client
        mapper.writeValue(response.getOutputStream(), parsedLexemes);

    }*/

    private static final String QUERY_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, email VARCHAR(100))";
    private static final String QUERY_CREATE_USER_TABLE_DATA = "INSERT INTO users (email) VALUES ('foo@bar.com'), ('bar@foo.com'), ('foobar@bf.com')";

    private StringBuilder log = new StringBuilder();

    private Connection getConnection() throws SQLException {

        log.append("getConnection: start");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            log.append(e.toString());
        }

        // JDBC driver name and database URL
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");

        String dbUrl = "jdbc:mysql://"+host+":"+port+"/abiturient";

        //String JDBC_DRIVER = "com.mysql.jdbc.Driver";

        //  Database credentials
        String username = "adminkuhEdFI";
        String password = "rFNmaPZZhjRS";
        log.append("getConnection: end");
        return DriverManager.getConnection(dbUrl, username, password);
    }

    private void createUserTable(){

        log.append("createUserTable: start");
        Connection connection = null;
        try {
            connection = getConnection();

            Statement stmt = connection.createStatement();

            if(stmt != null) {
                log.append("createUserTable: statement != null");
                stmt.executeUpdate(QUERY_CREATE_USER_TABLE);
            }

        } catch(SQLException e) {
            log.append("createUserTable: ");
            log.append(e.toString());

            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        log.append("createUserTable: end");
    }

    private void dropUserTable(){

        log.append("dropUserTable: start");
        Connection connection = null;
        try {
            connection = getConnection();

            Statement stmt = connection.createStatement();

            if(stmt != null) {
                log.append("dropUserTable: statement != null");
                stmt.executeUpdate("DROP TABLE IF EXISTS users");
            }

        } catch(SQLException e) {
            log.append("dropUserTable: ");
            log.append(e.toString());

            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        log.append("dropUserTable: end");
    }

    public void createSomeDataInDb(){

        log.append("createSomeDataInDb: start");

        Random rand = new Random();
        Connection connection = null;
        try {
            connection = getConnection();
            for(int i = 0; i < 500000; i++) {
                log.append("createSomeDataInDb: ");
                Statement stmt = connection.createStatement();
                if(stmt != null) {
                    stmt.executeUpdate("INSERT INTO users (email) VALUES ('foo@bar.com" + rand.nextInt(33000000) + "'), ('bar@foo.com"  + rand.nextInt(33000000) + "'))");
                    log.append(i);
                }
            }

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        log.append("createSomeDataInDb: end");
    }

    private List<String> readAllUsers(){
        Connection connection = null;
        List<String> result = new ArrayList<String>(1000000);
        try {
            connection = getConnection();

            Statement stmt = connection.createStatement();

            if(stmt != null) {
                ResultSet resultSet = stmt.executeQuery("SELECT * FROM users");


                while(resultSet.next()) {
                    StringBuilder row = new StringBuilder();
                    row.append("id: ");
                    row.append(resultSet.getInt("id"));
                    row.append(" e-mail: ");
                    row.append(resultSet.getString("email"));
                    result.add(row.toString());
                    log.append(1);
                }
            }
        } catch(SQLException e) {

        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        SpecializationDao specializationDao = new SpecializationDao();
        Specialization specialization1 = null;
        Specialization specialization2 = null;
            specialization1 = new Specialization();
            specialization1.setCipher("001001");
            specialization1.setDepartment("CS");
            specialization1.setInstitute("IT");

            specialization2 = new Specialization();
            specialization2.setCipher("001002");
            specialization2.setDepartment("Math");
            specialization2.setInstitute("IT");

            specializationDao.saveSpecialization(specialization1);
            specializationDao.saveSpecialization(specialization2);







        List<Specialization> specializations = specializationDao.getAllSpecializations();

        Random random = new Random();

        Enrollee enrollee1 = new Enrollee();
        enrollee1.setFirstName("Ivan" + random.nextInt(1000));
        enrollee1.setMiddleName("Dffs3");
        enrollee1.setLastName("Saprykin");
        enrollee1.setAddress("sffsdf, 3wfv, 34");
        enrollee1.setAppliedAcademicDegree("master's");
        enrollee1.setBirthDate(LocalDateTime.now());
        enrollee1.setEducationForm("day");
        enrollee1.setPreviousEducation("school #32");
        enrollee1.setPreviousEducationYear(LocalDateTime.now().minusYears(1).minusMonths(6));

        HashSet<Specialization> set = new HashSet<>();
        set.add(specialization1);
        enrollee1.setSpecializations(set);



        Enrollee enrollee2 = new Enrollee();
        enrollee2.setFirstName("Alex" + random.nextInt(1000));
        enrollee2.setSpecializations(new HashSet<Specialization>());
        Enrollee enrollee3 = new Enrollee();
        enrollee3.setFirstName("Anton" + random.nextInt(1000));
        enrollee3.setSpecializations(set);
        //enrollee3.setSpecializations(specializations);
        Enrollee enrollee4 = new Enrollee();
        enrollee4.setFirstName("Alexdfdfdfdf" + random.nextInt(1000));

        HashSet<Specialization> set2 = new HashSet<>();
        set2.add(specialization2);
        enrollee4.setSpecializations(set2);

        EnrolleeDao enrolleeDao = new EnrolleeDao();

        List<Enrollee> allEnrollees = enrolleeDao.getAllEnrollee();
        if(! allEnrollees.isEmpty()) {
            for(Enrollee enrollee : allEnrollees) {
                enrolleeDao.deleteEnrollee(enrollee);
            }
        }


        enrolleeDao.saveEnrollee(enrollee1);
        enrolleeDao.saveEnrollee(enrollee2);
        enrolleeDao.saveEnrollee(enrollee3);
        enrolleeDao.saveEnrollee(enrollee4);


        Enrollee enrolleeFromDb = enrolleeDao.getEnrolleeById(2);
        enrolleeFromDb.getSpecializations().add(specializations.get(0));
        enrolleeFromDb.getSpecializations().add(specializations.get(1));
        enrolleeDao.updateEnrollee(enrolleeFromDb);

//        createUserTable();
//        createSomeDataInDb();

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        out.println("specializations.size = " + specializations.size());
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");

        for(Specialization specialization : specializations) {
            out.println(specialization);
            out.println("<br>");
            out.println("<br>");
            out.println("<br>");
        }


        List<Enrollee> enrollees = enrolleeDao.getAllEnrollee();
        out.println("enrollees.size = " + enrollees.size());
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");

        for(Enrollee enrollee : enrollees) {
            out.println(enrollee);
            out.println("<br>");
            out.println("<br>");
            out.println("<br>");
        }


        Role role1 = new Role();
        role1.setName("Admin");

        Role role2 = new Role();
        role2.setName("User");
        RoleDaoImpl roleDao = new RoleDaoImpl();
        roleDao.saveRole(role1);
        roleDao.saveRole(role2);



        User user1 = new User();
        user1.setLogin("login1");
        PasswordHash.createNewSalt();
        user1.setPasswordMd5(PasswordHash.createHash("password"));
        user1.setSaltForPassword(PasswordHash.currentSalt);
        user1.setRole(role1);


        User user2 = new User();
        user2.setLogin("login2");
        PasswordHash.createNewSalt();
        user2.setSaltForPassword(PasswordHash.currentSalt);
        user2.setPasswordMd5(PasswordHash.createHash("1234"));
        user2.setRole(role2);

        User user3 = new User();
        user3.setLogin("login3");
        PasswordHash.createNewSalt();
        user3.setPasswordMd5(PasswordHash.createHash("qwerty"));
        user3.setSaltForPassword(PasswordHash.currentSalt);
        user3.setRole(role2);


        UserDao userDao = new UserDao();
        userDao.saveUser(user1);
        userDao.saveUser(user2);
        userDao.saveUser(user3);

        List<User> users = userDao.getAllUsers();
        out.println("users.size = " + users.size());
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");

        for(User user : users) {
            out.println(user);
            out.println("<br>");
            out.println("<br>");
            out.println("<br>");
        }
        //out.println(log.toString());

        User userFromBdByLogin = userDao.getUserByLogin("login2");
        out.println(userFromBdByLogin);

        out.println("end//////");
    }

}
