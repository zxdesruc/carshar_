package com.example.caruser;

import com.example.caruser.domain.CarUser;
import com.example.caruser.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @Autowired
    public UserRepository carUserRepository;

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String FIO, @RequestParam String password, Model model) {
        if (FIO.equals("admin") && password.equals("admin")) {
            return new ModelAndView("add");
        }
        List<CarUser> carUserList = carUserRepository.findByFIO(FIO);
        if (carUserList.isEmpty()) {
            model.addAttribute("error", "Invalid FIO");
            return new ModelAndView("auth");
        } else if (!carUserList.get(0).checkPassword(password)) {
            model.addAttribute("error", "Invalid password");
            return new ModelAndView("auth");
        } else {
            CarUser carUser = carUserList.get(0);
            model.addAttribute("carUser",carUser);
            return new ModelAndView("user");
        }
    }

    @GetMapping("/")
    public ModelAndView menu(Model model) {
        return new ModelAndView("auth");
    }

    @GetMapping("/result")
    public ModelAndView result(Model model) {
        return new ModelAndView("result");
    }

    @GetMapping("/allUsers")
    public ModelAndView show(Model model) {
        Iterable<CarUser> carUsers = carUserRepository.findAll();
        model.addAttribute("carUsers", carUsers);
        return new ModelAndView("allUsers");
    }

    @GetMapping("/add")
    public ModelAndView add(Model model) {
        return new ModelAndView("add");
    }

    @PostMapping("/add")
    public ModelAndView addPost(@RequestParam String FIO, @RequestParam int age, @RequestParam int experience, @RequestParam String rides, @RequestParam String violation, @RequestParam String password, Model model) {

        CarUser carUser = new CarUser(FIO, age, experience, rides, violation, password);
        carUserRepository.save(carUser);
        return new ModelAndView("redirect:/allUsers");
    }

    @PostMapping("/allUsers/{id}")
    public ModelAndView delete(@PathVariable(value = "id") long id, Model model) {
        CarUser carUser = carUserRepository.findById(id).orElseThrow();
        carUserRepository.delete(carUser);
        return new ModelAndView("redirect:/allUsers");
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(required = false) String FIO, @RequestParam(required = false) String age, Model model) {
        if (FIO == null && age == null) {
            return new ModelAndView("search");
        }

        List<CarUser> carUsers;
        if (age.equals("")) {
            carUsers = carUserRepository.findByFIO(FIO);
            if (carUsers.isEmpty()) {
                model.addAttribute("error", "User " + FIO + " not found");
                return new ModelAndView("search");
            } else model.addAttribute("error", "");
        } else {
            List<CarUser> users = carUserRepository.findByFIO(FIO);
            if (users.isEmpty()) {
                model.addAttribute("error", "User " + FIO + " not found");
                return new ModelAndView("search");
            } else model.addAttribute("error", "");
            carUsers = users.stream()
                    .filter(user -> age.equals(String.valueOf(user.getAge())))
                    .collect(Collectors.toList());
            if (carUsers.isEmpty()) {
                model.addAttribute("error", "User " + FIO + ", " + age + " years not found");
                return new ModelAndView("search");
            } else model.addAttribute("error", "");
        }
        model.addAttribute("carUsers", carUsers);
        return new ModelAndView("search");
    }
}


