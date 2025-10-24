package com.reusemi.controller;

import com.reusemi.entity.Usuario;
import com.reusemi.repo.UsuarioRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class ExportController {

    private final UsuarioRepository usuarioRepository;

    public ExportController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/exportar-usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try (XSSFWorkbook wb = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = wb.createSheet("Usuários");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Nome");
            header.createCell(2).setCellValue("Email");
            header.createCell(3).setCellValue("Nível de Acesso");

            int rowIdx = 1;
            for (Usuario u : usuarios) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(u.getId());
                row.createCell(1).setCellValue(u.getNome());
                row.createCell(2).setCellValue(u.getEmail());
                row.createCell(3).setCellValue(u.getNivel());
            }

            wb.write(out);
            byte[] bytes = out.toByteArray();

            String filename = "usuarios_REUSEMI.xlsx";
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(bytes);


        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("Erro ao exportar usuários: " + e.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    }
}
