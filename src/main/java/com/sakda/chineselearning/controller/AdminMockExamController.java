package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sakda.chineselearning.dto.AddQuestionToExamDTO;
import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.entity.MockExam;
import com.sakda.chineselearning.service.MockExamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
        name = "Admin Mock Exam API",
        description = "Admin mock exam management"
)
@RestController
@RequestMapping("/api/v1/admin/mock-exams")
@RequiredArgsConstructor
public class AdminMockExamController {

    private final MockExamService mockExamService;

    @Operation(summary = "Create admin mock exam")
    @PostMapping
    public ResponseEntity<ApiResponse<MockExam>> createExam(
            @RequestBody MockExam mockExam
    ) {

        MockExam exam = mockExamService.createExam(mockExam);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin mock exam created successfully",
                        exam
                )
        );
    }

    @Operation(summary = "Get admin mock exams")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MockExam>>> getAllExams() {

        List<MockExam> exams = mockExamService.getAllExams();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin mock exams retrieved successfully",
                        exams
                )
        );
    }

    @Operation(summary = "Add question to admin mock exam")
    @PostMapping("/{id}/questions")
    public ResponseEntity<ApiResponse<Void>> addQuestionToExam(
            @PathVariable Long id,
            @RequestBody AddQuestionToExamDTO dto
    ) {

        mockExamService.addQuestionToExam(id, dto);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Question added to admin mock exam successfully",
                        null
                )
        );
    }
    
    @Operation(summary = "Get admin mock exam by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MockExam>> getExamById(@PathVariable Long id) {
    	
    	return ResponseEntity.ok(
    			new ApiResponse<>(
    					true,
    					"Admin mock exam retrieved successfully",
    					mockExamService.getExamById(id)
    					)
    			);
    }
    
    @Operation(summary = "Update admin mock exam")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MockExam>> updateExam(
    		@PathVariable Long id, 
    		@RequestBody MockExam mockExam) {
    	
    	return ResponseEntity.ok(
    			new ApiResponse<>(
    					true,
    					"Admin mock exam updated successfully",
    					mockExamService.updateExam(id, mockExam)
    					)
    			);
    }
    
    @Operation(summary = "Update passing score")
    @PutMapping("/{id}/passing-score")
    public ResponseEntity<ApiResponse<MockExam>> updatePassingScore(
            @PathVariable Long id,
            @RequestParam Integer passingScore
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Passing score updated successfully",
                        mockExamService.updatePassingScore(id, passingScore)
                )
        );
    }
}