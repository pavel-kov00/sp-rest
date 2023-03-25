//package ru.kata.spring.boot_security.demo.utils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.format.Formatter;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//@Component
//public class StringToRolesConverter implements Formatter<List<Role>> {
////        implements Converter<String, List<Role>> {
////    @Override
////    public List<Role> convert(String source) {
////        List<Role> roles = new ArrayList<>();
////        for (String role : source.split(",")) {
////            roles.add(new Role(role));
////        }
////        return roles;
////    }
//
//    @Autowired
//    RoleServiceImpl roleService;
//
//    @Override
//    public List<Role> parse(String text, Locale locale) throws ParseException {
//        List<Role> listRole = new ArrayList<>();
//        System.out.println("text from metod parse: " + text);
//        System.out.println(text + "         ***************         ****************         ");
//        listRole.add(roleService.getRolebyId(Integer.parseInt(text)));
//        if (listRole == null) {
//            throw new ParseException("Role not found: " + text, 0);
//        }
//        return listRole;
//    }
//
//    @Override
//    public String print(List<Role> object, Locale locale) {
//        return object.toString();
//    }
//}
