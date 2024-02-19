package com.fastcampus.projectboardadmin.controller;

import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fastcampus.projectboardadmin.dto.response.UserAccountResponse;
import com.fastcampus.projectboardadmin.service.UserAccountManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/management/user-accounts")
@Controller
public class UserAccountManagementController {

    private final UserAccountManagementService userAccountManagementService;

    @GetMapping
    public String userAccounts(
            ModelMap model,
            HttpServletRequest httpServletRequest
    ) {
        List<UserAccountResponse> response = userAccountManagementService.getUserAccounts().stream().map(UserAccountResponse::from).toList();
        model.addAttribute("userAccounts",response);
        model.addAttribute("request",httpServletRequest);

        return "management/user-accounts";
    }

    @ResponseBody
    @GetMapping("/{userId}")
    public UserAccountResponse userAccount( @PathVariable String userId ) {
        return UserAccountResponse.from(userAccountManagementService.getUserAccount(userId));
    }

    @PostMapping("/{userId}")
    public String deleteUserAccount( @PathVariable String userId ) {
        userAccountManagementService.deleteUserAccount(userId);

        return "redirect:/management/user-accounts";
    }
}
