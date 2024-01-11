package client.rest;

import client.communication.Communication;
import client.configuration.MyConfig;
import client.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);
        User newUser = new User(3L,"James", "Brown", (byte)9);

        communication.getAllUsers();
        String firstPart = communication.addUser(newUser);
        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        String secondPart = communication.editUser(newUser);
        String thirdPart = communication.deleteUser(3L);

        String result = firstPart.concat(secondPart).concat(thirdPart);
        System.out.println(result);
        System.out.println(result.length());
    }
}
