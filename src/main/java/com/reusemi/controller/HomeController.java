package com.reusemi.controller;

<<<<<<< HEAD
import com.reusemi.entity.Product;
import com.reusemi.entity.Category;
import com.reusemi.repo.ProductRepository;
import com.reusemi.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
=======
>>>>>>> 56605cd3e29d058c1166042c73bc3ea6cd7d8064
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

<<<<<<< HEAD
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(Model model) {
        // Verificar se o usuário está autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("username", authentication.getName());
        } else {
            model.addAttribute("isAuthenticated", false);
        }

        // Buscar produtos recentes (últimos 8 produtos)
        List<Product> recentProducts = productRepository.findTop8ByOrderByIdDesc();
        model.addAttribute("recentProducts", recentProducts);

        // Buscar produtos em destaque
        List<Product> featuredProducts = productRepository.findByFeaturedTrue();
        model.addAttribute("featuredProducts", featuredProducts);

        // Buscar categorias ativas
        List<Category> categories = categoryRepository.findByActiveTrueOrderByNameAsc();
        model.addAttribute("categories", categories);

=======
@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        // Verifica se o usuário está autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null &&
                authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser");

        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            model.addAttribute("username", authentication.getName());
        }

>>>>>>> 56605cd3e29d058c1166042c73bc3ea6cd7d8064
        return "index";
    }
}