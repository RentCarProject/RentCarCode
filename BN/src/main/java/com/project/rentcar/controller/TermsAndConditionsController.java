package com.project.rentcar.controller;

import com.project.rentcar.domain.dto.TermsAndConditionsDto;
import com.project.rentcar.domain.service.TermsAndConditionsService;
import com.project.rentcar.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TermsAndConditionsController {

    private final TermsAndConditionsService termsAndConditionsService;
    private final UserService userService;

    @Autowired
    public TermsAndConditionsController(TermsAndConditionsService termsAndConditionsService, UserService userService) {
        this.termsAndConditionsService = termsAndConditionsService;
        this.userService = userService;
    }

    // 동의 항목 페이지 보여주기
    @GetMapping("/terms")
    public String showTermsPage(Model model) {
        List<TermsAndConditionsDto> terms = termsAndConditionsService.getAllTermsAndConditions();
        model.addAttribute("terms", terms);
        return "terms"; // terms.html
    }

    @PostMapping("/terms/agree")
    public String agreeToTerms(@RequestParam(value = "termIds", required = false) List<Long> termIds, Model model) {
        // 동의 항목이 하나도 선택되지 않은 경우
        if (termIds == null || termIds.isEmpty()) {
            model.addAttribute("error", "모든 동의 항목을 체크해 주세요.");
            List<TermsAndConditionsDto> terms = termsAndConditionsService.getAllTermsAndConditions();
            model.addAttribute("terms", terms);
            return "terms"; // 동의 페이지로 돌아가기
        }

        // 사용자가 동의한 항목들을 처리
        for (Long termId : termIds) {
            termsAndConditionsService.agreeToTerms(termId, true);  // 동의 처리
        }

        return "redirect:/signup"; // 회원가입 페이지로 리디렉션
    }

}
