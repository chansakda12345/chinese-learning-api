package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.AddQuestionToExamDTO;
import com.sakda.chineselearning.dto.ExamHistoryDTO;
import com.sakda.chineselearning.dto.ExamResultDTO;
import com.sakda.chineselearning.dto.StartExamDTO;
import com.sakda.chineselearning.dto.SubmitExamDTO;
import com.sakda.chineselearning.entity.MockExam;

public interface MockExamService {

    MockExam createExam(MockExam mockExam);

    List<MockExam> getAllExams();

    void addQuestionToExam(
            Long examId,
            AddQuestionToExamDTO dto
    );

    StartExamDTO startExam(Long examId);

    ExamResultDTO submitExam(
            Long examId,
            SubmitExamDTO submitExamDTO
    );

    List<ExamHistoryDTO> getExamHistory();
    
    MockExam getExamById(Long id);
    
    MockExam updateExam(Long id, MockExam mockExam);
    
    MockExam updatePassingScore(Long id, Integer passingScore);
    
    void deleteExam(Long id);
    
}