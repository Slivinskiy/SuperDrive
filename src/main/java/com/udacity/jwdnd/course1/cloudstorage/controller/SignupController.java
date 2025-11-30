package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller handling user signup operations.
 */
@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {
    
    // View names
    private static final String SIGNUP_VIEW = "signup";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    
    // Model attribute keys
    private static final String ATTR_SIGNUP_ERROR = "signupError";
    private static final String ATTR_SIGNUP_SUCCESS = "signupSuccess";
    
    // Error messages
    private static final String ERROR_USERNAME_EXISTS = "The username already exists. Please choose a different username.";
    private static final String ERROR_SIGNUP_FAILED = "An error occurred during signup. Please try again.";
    private static final String ERROR_INVALID_INPUT = "Please fill in all required fields.";
    
    private final UserService userService;

    public SignupController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the signup form.
     *
     * @param user the user object for form binding
     * @param model the model to add attributes
     * @return the signup view name
     */
    @GetMapping
    public String showSignupForm(final User user, final Model model) {
        model.addAttribute("user", user);
        return SIGNUP_VIEW;
    }

    /**
     * Processes user signup request.
     *
     * @param user the user data from the form
     * @param redirectAttributes attributes for redirect scenario
     * @param model the model for error handling
     * @return redirect to login on success, or signup view on error
     */
    @PostMapping
    public String registerUser(@ModelAttribute final User user, final RedirectAttributes redirectAttributes, final Model model) {
        log.info("Signup attempt for username: {}", user.getUsername());
        
        // Validate input
        if (!isValidUserInput(user)) {
            log.warn("Invalid signup input for username: {}", user.getUsername());
            model.addAttribute(ATTR_SIGNUP_ERROR, ERROR_INVALID_INPUT);
            return SIGNUP_VIEW;
        }
        
        // Check username availability
        if (!userService.isUsernameAvailable(user.getUsername())) {
            log.warn("Username already exists: {}", user.getUsername());
            model.addAttribute(ATTR_SIGNUP_ERROR, ERROR_USERNAME_EXISTS);
            return SIGNUP_VIEW;
        }
        
        // Create user
        final var result = userService.createUser(user);
        if (result <= 0) {
            log.error("Failed to create user: {}", user.getUsername());
            model.addAttribute(ATTR_SIGNUP_ERROR, ERROR_SIGNUP_FAILED);
            return SIGNUP_VIEW;
        }
        
        // Success - redirect to login with success message
        log.info("User successfully created: {}", user.getUsername());
        redirectAttributes.addFlashAttribute(ATTR_SIGNUP_SUCCESS, true);
        return REDIRECT_LOGIN;
    }
    
    /**
     * Validates user input fields.
     * @param user the user object to validate
     * @return true if all required fields are present and valid
     */
    private boolean isValidUserInput(final User user) {
        return user != null
                && StringUtils.hasText(user.getUsername())
                && StringUtils.hasText(user.getPassword())
                && StringUtils.hasText(user.getFirstName())
                && StringUtils.hasText(user.getLastName());
    }
}




