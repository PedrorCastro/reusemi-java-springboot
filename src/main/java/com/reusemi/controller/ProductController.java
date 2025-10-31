package com.reusemi.controller;

import com.reusemi.entity.Category;
import com.reusemi.entity.Product;
import com.reusemi.repo.CategoryRepository;
import com.reusemi.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.time.LocalDateTime;
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
    // No método showProductForm:
    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findByActiveTrueOrderByNameAsc());
        return "product-form";
    }
    // Salvar produto
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Upload da imagem
            if (!imageFile.isEmpty()) {
                String imageName = uploadImageFile(imageFile);
                product.setImageUrl(imageName);
            }

            productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Redireciona para a página inicial em vez da lista de produtos
        return "redirect:/";
    }

    // Upload de imagem (endpoint público)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = uploadImageFile(file);
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro no upload: " + e.getMessage());
        }
    }

    // Método PRIVADO para upload (auxiliar interno)
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

    // =========================================================================
    // NOVAS ROTAS PARA OS ANÚNCIOS EM DESTAQUE
    // =========================================================================

    // Página do anúncio "Troco por bike" - Smartphone Samsung Galaxy S20
    @GetMapping("/s20")
    public String samsungS20Detail(Model model) {
        // Busca o produto do banco ou cria dados de exemplo
        Product product = productRepository.findById(1L)
                .orElse(createSamsungS20Product());
        model.addAttribute("product", product);
        model.addAttribute("seller", createCarlosSeller());
        return "products/s20";
    }

    // Página do anúncio "Aceito propostas" - Sofá 3 lugares
    @GetMapping("/sofa")
    public String sofaDetail(Model model) {
        Product product = productRepository.findById(2L)
                .orElse(createSofaProduct());
        model.addAttribute("product", product);
        model.addAttribute("seller", createAnaSeller());
        return "products/sofa";
    }

    // Página do anúncio "Procuro tênis" - Bicicleta Caloi
    @GetMapping("/bicicleta-caloi")
    public String bikeDetail(Model model) {
        Product product = productRepository.findById(3L)
                .orElse(createBikeProduct());
        model.addAttribute("product", product);
        model.addAttribute("seller", createPedroSeller());
        return "products/bike";
    }

    // Página do anúncio "Busco celular" - Notebook Dell
    @GetMapping("/notebook-dell")
    public String notebookDetail(Model model) {
        Product product = productRepository.findById(4L)
                .orElse(createNotebookProduct());
        model.addAttribute("product", product);
        model.addAttribute("seller", createMariaSeller());
        return "products/detail-notebook";
    }

    // Rota dinâmica para produtos por ID
    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        return productRepository.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    // Aqui você poderia buscar o vendedor real do banco
                    model.addAttribute("seller", createGenericSeller());
                    return "products/detail";
                })
                .orElse("redirect:/products");
    }

    @PostMapping("/products/save")
    public String saveProduct(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam Double price,
                              @RequestParam Long categoryId,
                              @RequestParam(required = false) Double discount,
                              @RequestParam(required = false) Boolean featured,
                              @RequestParam String condicao,
                              @RequestParam String tipoNegociacao,
                              @RequestParam String localizacao,
                              @RequestParam String contato,
                              RedirectAttributes redirectAttributes) {

        try {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setDiscount(discount != null ? discount : 0.0);
            product.setFeatured(featured != null ? featured : false);
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
            product.setCategory(category);

            productRepository.save(product);

            return "redirect:/products?success=true";

        } catch (Exception e) {
            return "redirect:/products/new?error=true";
        }
    }

    // =========================================================================
    // MÉTODOS AUXILIARES PARA DADOS DE EXEMPLO
    // =========================================================================

    private Product createSamsungS20Product() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone Samsung Galaxy S20");
        product.setDescription("Smartphone Samsung Galaxy S20 em ótimo estado de conservação. Equipamento com 128GB de armazenamento, tela de 6.2 polegadas, câmera tripla de 64MP + 12MP + 12MP. Inclui carregador original e capa protetora.");
        product.setImageUrl("samsung-s20.jpg");
        // Adicione outros campos conforme sua entidade Product
        return product;
    }

    private Product createSofaProduct() {
        Product product = new Product();
        product.setId(2L);
        product.setName("Sofá 3 lugares em ótimo estado");
        product.setDescription("Sofá 3 lugares em ótimo estado de conservação, quase novo. Estofado em tecido antimanchas de alta qualidade, cor cinza. Estrutura em madeira maciça, muito resistente.");
        product.setImageUrl("sofa-3-lugares.jpg");
        return product;
    }

    private Product createBikeProduct() {
        Product product = new Product();
        product.setId(3L);
        product.setName("Bicicleta Caloi Aro 29");
        product.setDescription("Bicicleta Caloi Aro 29, perfeita para exercícios e trilhas leves. Em excelente estado de conservação, com pouquíssimo uso.");
        product.setImageUrl("bike-caloi.jpg");
        return product;
    }

    private Product createNotebookProduct() {
        Product product = new Product();
        product.setId(4L);
        product.setName("Notebook Dell Inspiron 17");
        product.setDescription("Notebook Dell Inspiron 17 com 16GB RAM, SSD 512GB. Equipamento em perfeito estado, pouco uso.");
        product.setImageUrl("notebook-dell.jpg");
        return product;
    }

    // Classes DTO para informações adicionais (vendedor)
    private Seller createCarlosSeller() {
        return new Seller("Carlos Silva", 2023, 15, 4.5, "(11) 98765-4321", "carlos.silva@email.com");
    }

    private Seller createAnaSeller() {
        return new Seller("Ana Oliveira", 2022, 8, 5.0, "(21) 99876-5432", "ana.oliveira@email.com");
    }

    private Seller createPedroSeller() {
        return new Seller("Pedro Santos", 2024, 3, 4.0, "(31) 97654-3210", "pedro.santos@email.com");
    }

    private Seller createMariaSeller() {
        return new Seller("Maria Costa", 2023, 12, 4.8, "(61) 96543-2109", "maria.costa@email.com");
    }

    private Seller createGenericSeller() {
        return new Seller("Vendedor Reusemi", 2023, 10, 4.5, "(00) 00000-0000", "vendedor@reusemi.com");
    }

    // Classe DTO para informações do vendedor
    public static class Seller {
        private String name;
        private int memberSince;
        private int activeAds;
        private double rating;
        private String phone;
        private String email;

        public Seller() {}

        public Seller(String name, int memberSince, int activeAds, double rating, String phone, String email) {
            this.name = name;
            this.memberSince = memberSince;
            this.activeAds = activeAds;
            this.rating = rating;
            this.phone = phone;
            this.email = email;
        }

        // Getters e Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getMemberSince() { return memberSince; }
        public void setMemberSince(int memberSince) { this.memberSince = memberSince; }

        public int getActiveAds() { return activeAds; }
        public void setActiveAds(int activeAds) { this.activeAds = activeAds; }

        public double getRating() { return rating; }
        public void setRating(double rating) { this.rating = rating; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    @Autowired
    private CategoryRepository categoryRepository;


}