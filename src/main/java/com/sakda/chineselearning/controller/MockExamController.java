package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.ExamHistoryDTO;
import com.sakda.chineselearning.dto.ExamResultDTO;
import com.sakda.chineselearning.dto.StartExamDTO;
import com.sakda.chineselearning.dto.SubmitExamDTO;
import com.sakda.chineselearning.entity.MockExam;
import com.sakda.chineselearning.service.MockExamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
        name = "Mock Exam API",
        description = "Student mock exam API"
)
@RestController
@RequestMapping("/api/v1/mock-exams")
@RequiredArgsConstructor
public class MockExamController {

    private final MockExamService mockExamService;

    @Operation(summary = "Get all mock exams")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MockExam>>> getAllExams() {

        List<MockExam> exams = mockExamService.getAllExams();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Mock exams retrieved successfully",
                        exams
                )
        );
    }

    @Operation(summary = "Start mock exam")
    @PostMapping("/{id}/start")
    public ResponseEntity<ApiResponse<StartExamDTO>> startExam(
            @PathVariable Long id
    ) {

        StartExamDTO exam = mockExamService.startExam(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Mock exam started successfully",
                        exam
                )
        );
    }

    @Operation(summary = "Submit mock exam")
    @PostMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<ExamResultDTO>> submitExam(@PathVariable Long id, @RequestBody SubmitExamDTO dto) {
    	
    	ExamResultDTO result = mockExamService.submitExam(id, dto);
    	
    	return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Mock exam submitted successfully",
                        result
                )
        );
    }
    
    @Operation(summary = "Get mock exam history")
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<ExamHistoryDTO>>> getExamHistory() {

        List<ExamHistoryDTO> history = mockExamService.getExamHistory();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Mock exam history retrieved successfully",
                        history
                )
        );
    }
}