package com.example.web.app.dao;

import com.example.web.app.dao.model.TestReport;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Tester implements InitializingBean {

    private Logger log = Logger.getLogger(getClass().getName());

    private int ID = 1;
    private int MAXID = 0;
    static final String dbPath = "jdbc:postgresql://192.168.56.101:5432/hornsandhooves";    // поменять
    static final String USER = "USER_Test";    // поменть
    static final String PASS = "67sfda7ew";  // поменять

    @Override
    public void afterPropertiesSet() throws Exception {
        initDb();
    }

    /*  Инициализация */
    public void initDb() {
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
                System.out.println("Подключение "+ USER + " прошло успешно!");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.WARNING, "База для " + USER + "не подключена", ex);
            ex.printStackTrace();
        }
    }

    /*
    * Сделать получение материала на тест
    * Сделать получение модели на тест
    * Сделать изменение статуса модели
    * Сделать изменение статуса материала
    * сделать получение кол-ва того и другого для информации
    * is_allowed = 0 - не протест, 1 - гуд, 2 - недоработки\брак
    * */

    public TestReport getModelToTest() {
        String query = "SELECT * FROM testerreport WHERE is_tested='0' LIMIT 1";
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            resultSet.next();
            Main main = new Main();
            TestReport tr = new TestReport();
            tr.setId(resultSet.getInt("id"));
            tr.setHuman_id(resultSet.getInt("human_id"));
            tr.setIs_tested(resultSet.getInt("is_tested"));
            tr.setDate_report(resultSet.getDate("date_report"));
            tr.setNSP(main.getNSP(tr.getId()));
            tr.setMaterial(resultSet.getString("material"));
            tr.setUrlMaket(resultSet.getString("urlmaket"));
            return tr;
    } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new TestReport();
        }
    }

    /*Изменить статус чего либо
    * На вход подаётся статус изменения, id, и текст*/
    public Boolean changeStatus(int is_tested_id, int id, String reviem) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE testerreport SET is_tested=").append(is_tested_id).append(", reviem='")
                .append(reviem).append( "' WHERE id= ").append(id);
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return false;
        }
    }
    public Boolean createNewModelToTest(int id, String urlmaket,  String material) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        StringBuilder query = new StringBuilder();
        query.append("insert into testerreport (human_id, is_tested, urlmaket, material, date_report) values (")
                .append(id).append(",'").append("0").append("','").append(urlmaket).append("','").append(material).append("','").append(timeStamp).append("');");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка");
            return false;
        }
    }
}
