package com.example.secTest.article;

import com.example.secTest.user.SiteUser;
import com.example.secTest.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList", articleList);
        return "article_list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create() {
        return "article_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.articleService.create(title, content,siteUser);
        return "redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    public String detail (Model model, @PathVariable("id")Integer id){
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article",article);
        return "article_detail";
    }
}
