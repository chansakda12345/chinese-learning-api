package com.sakda.chineselearning.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sakda.chineselearning.dto.AdminStudentProgressDTO;
import com.sakda.chineselearning.dto.AdminUserDTO;

public interface AdminUserService {

    Page<AdminUserDTO> getUsers(Pageable pageable);
    
    Page<AdminUserDTO> searchUsers(String keyword, Pageable pageable);
    
    AdminUserDTO enableUser(Long userId);
    
    AdminUserDTO disableUser(Long userId);
    
    AdminStudentProgressDTO getStudentProgress(Long userId);

}