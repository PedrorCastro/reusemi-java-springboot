package com.reusemi.controller;

import com.reusemi.entity.Product;
import com.reusemi.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Página principal de produtos
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    // Formulário para adicionar produto
    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    // Salvar produto
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Upload da imagem
            if (!imageFile.isEmpty()) {
                String imageName = uploadImageFile(imageFile); // Mudei o nome do método
                product.setImageUrl(imageName);
            }

            productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    // Upload de imagem (endpoint público)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = uploadImageFile(file); // Reutiliza o método interno
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro no upload: " + e.getMessage());
        }
    }

    // Método PRIVADO para upload (auxiliar interno) - Mudei o nome
    private String uploadImageFile(MultipartFile file) throws Exception {
        // Criar diretório se não existir
        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Gerar nome único
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir.getAbsolutePath() + File.separator + fileName);
        file.transferTo(dest);

        return fileName;
    }

    // Método para deletar produto (opcional)
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}