package net.parksy.j8;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardOpenOption.*;

public class CodeJenerator {
    private final TemplateEngine templateEngine;
    private final Path sourceDirectoryPath;
    private final Path destinationDirectoryPath;

    public CodeJenerator(Path sourceDirectoryPath, Path destinationDirectoryPath) {

        this.sourceDirectoryPath = sourceDirectoryPath;
        this.destinationDirectoryPath = destinationDirectoryPath;
        this.templateEngine = setupTemplateEngine(sourceDirectoryPath);

    }

    private TemplateEngine setupTemplateEngine(Path sourceDirectoryPath) {
        TemplateEngine templateEngine = new TemplateEngine();

        // Note: we need two FileTemplateResolvers
        // one that is able to deal with absolute path template names like 'D:/data/dev/blog/index'
        // and one that is able to resolve relative path template names like '_layouts/main-layout'
        templateEngine.addTemplateResolver(newTemplateResolver(sourceDirectoryPath.toAbsolutePath()));
        templateEngine.addTemplateResolver(newTemplateResolver());
        templateEngine.addDialect(new LayoutDialect(new EnhancedGroupingStrategy()));
        return templateEngine;
    }

    private TemplateResolver newTemplateResolver() {
        return newTemplateResolver(null);
    }

    private TemplateResolver newTemplateResolver(Path prefix) {
        TemplateResolver templateResolver = new FileTemplateResolver();

        // Instead of 'HTML5' this template mode allows void elements such as meta to have no closing tags
        templateResolver.setTemplateMode("LEGACYHTML5");
        templateResolver.setPrefix(prefix != null ? prefix.toString() + "/" : "");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    private TemplateEngine createTemplateEngine(TemplateMode templateMode) {
        TemplateEngine templateEngine = new TemplateEngine();
        StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(templateMode);
        templateEngine.addTemplateResolver(templateResolver);

        return templateEngine;
    }

    public void generate() throws IOException {

        buildFile( this.sourceDirectoryPath, this.destinationDirectoryPath);
    }

    private void buildFile(File htmlFile, Path targetDir) throws IOException {

        //DJP:TODO Maybe when looping?
        // Clear Thymeleaf's template cache
//        templateEngine.clearTemplateCache();

        // Ensure targetDir exists
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        try {
            List<String> lines = Collections.singletonList(
                    templateEngine.process(htmlFile.toString().replace(".html", ""), getBaseTemplateContext()));
            // Write to file
            Path destinationPath = targetDir.resolve(htmlFile.getName());
            try {
                Files.write(destinationPath, lines, StandardCharsets.UTF_8, CREATE, WRITE, TRUNCATE_EXISTING);
            } catch (IOException e) {
                System.out.println("Failed to write generated document to" + destinationPath);
            }
        } catch (RuntimeException ex) {
            System.out.println(("Thymeleaf failed to process '"+htmlFile+"'. Reason: '"+ex.getMessage()+"'");
        }
    }

    private Context getBaseTemplateContext() {
        Context context = new Context();
        context.setVariable("year", LocalDateTime.now().get(ChronoField.YEAR));
        return context;
    }

}
