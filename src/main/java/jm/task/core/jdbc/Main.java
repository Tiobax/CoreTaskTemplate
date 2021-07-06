package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        //Создание таблицы
        userService.createUsersTable();

        //Добавление 4 пользователей в таблицу
        userService.saveUser("Евгений", "Буко", (byte) 34);
        userService.saveUser("Генадий", "Агафьев", (byte) 44);
        userService.saveUser("Виталий", "Дубнов", (byte) 56);
        userService.saveUser("Александр", "Пушкин", (byte) 104);

        //Получение всех пользователей из таблицы
        List<User> list = userService.getAllUsers();
        System.out.println(list);

        //Удаление пользователя с индексом 2 из таблицы
        userService.removeUserById(2);

        //Получение всех пользователей из таблицы
        list = userService.getAllUsers();
        System.out.println("Все пользователи после удаления по индексу: " + list);

        //Очистка таблицы
        userService.cleanUsersTable();

        //Получение всех пользователей из таблицы
        list = userService.getAllUsers();
        System.out.println("После очистки таблицы: " + list);

        //Удаление таблицы
        userService.dropUsersTable();

        //Засрытие Connection
        Util.closeConnection();
    }
}
