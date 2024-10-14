package com.gugulethugillz.auctionsystem.controllers;

import com.gugulethugillz.auctionsystem.common.enums.UserRole;
import com.gugulethugillz.auctionsystem.forms.PersonForm;
import com.gugulethugillz.auctionsystem.model.Admin;
import com.gugulethugillz.auctionsystem.model.Person;
import com.gugulethugillz.auctionsystem.service.ifaces.AssetService;
import com.gugulethugillz.auctionsystem.service.ifaces.CategoryService;
import com.gugulethugillz.auctionsystem.service.ifaces.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final CategoryService categoryService;
    private final AssetService assetService;

    @GetMapping
    public String indexPage(@AuthenticationPrincipal Person person, Model model) {
        if(person instanceof Admin) {
            return "redirect:/admin";
        }
        return "redirect:/assets";
    }



    @GetMapping("login")
    public String loginPage(Model model) {
        return configureSignInOrUpPage(model, new PersonForm(), null);
    }

    @GetMapping("logout")
    public String logoutPage(Model model) {
        return "logout";
    }

    @GetMapping("login/error")
    public String loginErrorPage(Model model) {
        return configureSignInOrUpPage(model, new PersonForm(), "Bad Credentials");
    }


    @PostMapping("register")
    public String registerPerson(@Valid PersonForm personForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        log.info("Got sign up request");

        @NotNull(message = "Person role is required") UserRole personRole = personForm.getUserRole();
        if (personRole == null) {
            model.addAttribute("action", "register");
            redirectAttributes.addFlashAttribute("error", "Could not create person");
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            return configureSignInOrUpPage(model, personForm, null);
        }

        final Person person = personForm.getPerson(passwordEncoder);
        person.setCreatedBy("SELF");
        person.setLastUpdatedBy("SELF");

        final Optional<Person> personOptional = personService.createPerson(person);
        log.info("Creation response successful? => {}", personOptional.isPresent());
        if (personOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("info", "Registered successfully, please sign in");
            return "redirect:/";
        } else {
            return configureSignInOrUpPage(model, personForm, "Could not register person, try again later");
        }
    }

    private String configureSignInOrUpPage(Model model, PersonForm personForm, String errors) {
        model.addAttribute("errors", errors);
        model.addAttribute("personForm", personForm);
        model.addAttribute("personRoles", Arrays.asList(UserRole.values()));
        model.addAttribute("assets", assetService.findAllAssets());
        return "sign_in_up";
    }

}
