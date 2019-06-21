/**
 *
 */
package org.simon.pascal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author nsimonin1
 *
 */
@Controller
public class MainController {
	private static final String USERNAME="username";

	@GetMapping("/")
	public String index(HttpServletRequest request, Model model) {
		final String username = (String) request.getSession().getAttribute(USERNAME);

		if (username == null || username.isEmpty()) {
			return "redirect:/login";
		}
		model.addAttribute(USERNAME, username);

		return "chat";
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username) {
		username = username.trim();

		if (username.isEmpty()) {
			return "login";
		}
		request.getSession().setAttribute(USERNAME, username);

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession(true).invalidate();

		return "redirect:/login";
	}
}
