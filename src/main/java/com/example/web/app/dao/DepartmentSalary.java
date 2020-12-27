package com.example.web.app.dao;

import com.example.web.app.api.request.ByNSPRequest;
import com.example.web.app.dao.model.Department;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DepartmentSalary implements InitializingBean {

    private Logger log = Logger.getLogger(getClass().getName());

    private int ID = 1;
    private int MAXID = 0;
    static final String dbPath = "jdbc:postgresql://192.168.56.101:5432/hornsandhooves";    // поменять
    static final String USER = "USER_Department";    // поменть
    static final String PASS = "as2df9fd8sdm";  // поменять

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

    public Boolean createNewDep(int id) {
        StringBuilder query = new StringBuilder();
        query.append("insert into department (human_id, department_name, position_name) values (")
                .append(id).append(",'").append("Цех").append("','").append("Подмастерье").append("'),(")
                .append(id).append(",'").append("Цех").append("','").append("Подмастерье").append("');");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка при создании новой РАБоты для человека");
            return false;
        }
    }

    public StringBuilder getInfoAboutHuman(int id) {
        getMaxId();
        if (id == 0) { ID--; id = ID;
            if (ID == 0) { ID = 1; id = 1; }
        } else if (id == 1) { ID++; id = ID;
        } else if (id == -1) { id = 1; ID = 1; }
        if (ID == 0) { ID = 1; id = 1; }
        if (id >= MAXID) { id = MAXID; ID = MAXID; }
        String query = "SELECT * FROM department WHERE human_id=" + id;
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            StringBuilder bigFloppa = new StringBuilder();
            bigFloppa.append("{");
            int range = 97;
            while (resultSet.next()) {
                bigFloppa.append("\"").append((char) range).append("id\":").append((resultSet.getInt("id"))).append(",")
                        .append("\"").append((char) range).append("human_id\":").append(resultSet.getInt("human_id")).append(",")
                        .append("\"").append((char) range).append("department_name\":").append("\"").append(resultSet.getString("department_name")).append("\",")
                        .append("\"").append((char) range).append("position_name\":").append("\"").append(resultSet.getString("position_name")).append("\",");
                range++;
            }
            bigFloppa.deleteCharAt(bigFloppa.length()-1);
            bigFloppa.append("}");
            return bigFloppa;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос. Получение информации о человеке");
            return new StringBuilder();
        }
    }

    public StringBuilder searchByNSP(ByNSPRequest NSP) {
        Main main = new Main();
        int id_human =  main.getIdFromDB(NSP);
        StringBuilder query = new StringBuilder();
        Integer id = 0;
        query.append("SELECT * FROM department WHERE human_id=").append(id_human).append(";");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.valueOf(query));
            StringBuilder bigFloppa = new StringBuilder();
            bigFloppa.append("{").append("\"id\":").append(id_human).append(",");
            int range = 97;
            while (resultSet.next()) {
                bigFloppa.append("\"").append((char) range).append("id\":").append((resultSet.getInt("id"))).append(",")
                        .append("\"").append((char) range).append("human_id\":").append(resultSet.getInt("human_id")).append(",")
                        .append("\"").append((char) range).append("department_name\":").append("\"").append(resultSet.getString("department_name")).append("\",")
                        .append("\"").append((char) range).append("position_name\":").append("\"").append(resultSet.getString("position_name")).append("\",")
                        .append("\"").append((char) range).append("money\":").append("\"").append(resultSet.getString("salary")).append("\",");
                range++;
            }
            bigFloppa.deleteCharAt(bigFloppa.length()-1);
            bigFloppa.append("}");
            return bigFloppa;
        } catch (SQLException ex ) {
            log.log(Level.WARNING, "Не удалось выполнить запрос. Поиск по ФИО из БД");
            return new StringBuilder();
        }
    }

    // Нужно сделать так, что бы мона было поменять зп у той профы, которую я выберу
    public Boolean changePosAndPay(int id_human, int id_work, String dep, String pos, String pay) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE department SET department_name='").append(dep).append("', position_name='").append(pos)
                .append("', salary='").append(pay).append("' WHERE id=").append(id_work).append(";");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            String role = "";
            if (dep.equals("Фин. и Юр. отдел")) {
                role = "USER_Department";
            } else if (dep.equals("Отдел контроля качества")) {
                role = "USER_Test";
            } else if (dep.equals("Цех")) {
                role = "USER_Workshop";
            } else if (dep.equals("Отдел работы с персоналом")) {
                role = "USER_Employee";
            } else if (dep.equals("Худ. совет")) {
                role = "USER_Design";
            } else if (dep.equals("Склад")) {
                role = "USER_Storage";
            } else {
                return true;
            }
            Main main = new Main();
            String login = main.getLoginFromBD(id_human);
            query.setLength(0);
            query.append("INSERT INTO roles(login, role_name) VALUES ('").append(login).append("','").append(role).append("');");
            Connection conn2 = DriverManager.getConnection(dbPath, USER, PASS);
            PreparedStatement stat2 = conn2.prepareStatement(String.valueOf(query));
            stat2.execute();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка выполнения запроса. Изменение ЗП и Должности");
            return false;
        }
    }
    public void getMaxId() {
        String query = ("SELECT max(id) FROM department;");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            resultSet.next();
            MAXID = resultSet.getInt("max");;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос");
        }
    }
}
