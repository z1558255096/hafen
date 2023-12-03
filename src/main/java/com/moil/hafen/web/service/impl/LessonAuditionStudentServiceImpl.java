package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.LessonAuditionStudentDao;
import com.moil.hafen.web.domain.LessonAuditionStudent;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.LessonAuditionStudentService;
import com.moil.hafen.web.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LessonAuditionStudentServiceImpl extends ServiceImpl<LessonAuditionStudentDao, LessonAuditionStudent> implements LessonAuditionStudentService {
    @Resource
    private StudentService studentService;
    @Override
    public IPage<LessonAuditionStudent> getPage(QueryRequest request, LessonAuditionStudent lessonAuditionStudent) {
        Page<LessonAuditionStudent> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        QueryWrapper<LessonAuditionStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t1.status",lessonAuditionStudent.getStatus()).eq("audition_id", lessonAuditionStudent.getAuditionId());
        return this.baseMapper.getPage(page, queryWrapper);
    }

    @Override
    public void reservation(Integer auditionId, List<Integer> studentIds) {
        for (Integer studentId : studentIds) {
            int count = this.count(new LambdaQueryWrapper<LessonAuditionStudent>().eq(LessonAuditionStudent::getAuditionId, auditionId).eq(LessonAuditionStudent::getStudentId, studentId));
            if(count==0){
                Student student = studentService.getById(studentId);
                LessonAuditionStudent lessonAuditionStudent = new LessonAuditionStudent(auditionId, studentId, student.getCustomerId(), 0, null, new Date(), new Date());
                this.save(lessonAuditionStudent);
            }else {
                this.update(new LambdaUpdateWrapper<LessonAuditionStudent>().set(LessonAuditionStudent::getStatus,0)
                        .set(LessonAuditionStudent::getModifyTime,new Date()).set(LessonAuditionStudent::getCancelTime,null)
                        .set(LessonAuditionStudent::getCancelPerson, null)
                        .eq(LessonAuditionStudent::getAuditionId, auditionId)
                        .eq(LessonAuditionStudent::getStudentId, studentId));
            }
        }
    }

    @Override
    public void cancel(Integer auditionId, List<Integer> studentIds) {
        for (Integer studentId : studentIds) {
            Student student = studentService.getById(studentId);
            this.update(new LambdaUpdateWrapper<LessonAuditionStudent>().set(LessonAuditionStudent::getStatus,1)
                    .set(LessonAuditionStudent::getModifyTime,new Date()).set(LessonAuditionStudent::getCancelTime,new Date())
                    .set(LessonAuditionStudent::getCancelPerson, student.getMobileOwner())
                    .eq(LessonAuditionStudent::getAuditionId, auditionId)
                    .eq(LessonAuditionStudent::getStudentId, studentId));
        }
    }

    @Override
    public List<Student> getStudentList(String auditionId, int status) {

        return this.baseMapper.getStudentList(auditionId,status);
    }

    @Override
    public List<LessonAuditionStudent> getAuditionList(String auditionId) {
        return this.baseMapper.getAuditionList(auditionId,0);
    }
}
