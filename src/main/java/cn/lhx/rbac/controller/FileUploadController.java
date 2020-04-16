package cn.lhx.rbac.controller;

import cn.lhx.rbac.base.JsonResult;
import cn.lhx.rbac.entity.Employee;
import cn.lhx.rbac.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author lee549
 * @date 2020/4/11 13:45
 */
@Controller
public class FileUploadController {
  @Resource EmployeeService employeeService;

  @RequestMapping("/upload")
  @ResponseBody
  public JsonResult<Object> upload(MultipartFile file) throws IOException {
    // 设置随机前缀名
    String fileName = UUID.randomUUID().toString();
    // 获取源文件名
    String originalFilename = file.getOriginalFilename();
    // 获取后缀
    String extension = originalFilename.substring(originalFilename.indexOf("."));
    // 拼接文件名
    String uniqueFileName = fileName + extension;
    // 2.文件上传类型
    String contentType = file.getContentType();

    if ("image/jpeg".equals(contentType)
        || "image/png".equals(contentType)
        || "image/jpg".equals(contentType)) {
      // 查询当前用户,设置新路径
      Subject subject = SecurityUtils.getSubject();
      Employee employee = (Employee) subject.getPrincipal();
      employee.setPicture(
          File.separator + "file" + File.separator + "images" + File.separator + uniqueFileName);
      // 更新路径
      System.out.println("当前用户：" + employee.getPicture());
      QueryWrapper<Employee> qw = new QueryWrapper<>();
      qw.eq("id", employee.getId());
      employeeService.update(employee, qw);
      // 上传
      file.transferTo(new File("f:" + File.separator + "images" + File.separator + uniqueFileName));
      System.out.println("f:" + File.separator + "images" + File.separator + uniqueFileName);
    }

    return JsonResult.success(uniqueFileName);
  }
  @RequestMapping("/upload/mail")
  @ResponseBody
  public JsonResult<Object> uploadMail(MultipartFile file) throws IOException {
    // 设置随机前缀名
    String fileName = UUID.randomUUID().toString();
    // 获取源文件名
    String originalFilename = file.getOriginalFilename();
    // 获取后缀
    String extension = originalFilename.substring(originalFilename.indexOf("."));
    // 拼接文件名
    String uniqueFileName = fileName + extension;
    // 2.文件上传类型
    String contentType = file.getContentType();

    if ("image/jpeg".equals(contentType)
            || "image/png".equals(contentType)
            || "image/jpg".equals(contentType)) {
      // 上传
      file.transferTo(new File("f:" + File.separator + "images" + File.separator + uniqueFileName));
      System.out.println("f:" + File.separator + "images" + File.separator + uniqueFileName);
    }
    HashMap<Object, Object> map = new HashMap<>(2);
    map.put("src", "\\file\\images\\"+uniqueFileName);
    map.put("title", uniqueFileName);
    return JsonResult.success(map,0);
  }

  @ResponseBody
  @GetMapping("/getPicture")
  public JsonResult<Object> getPicture() {
    //从session中获取
    Subject subject = SecurityUtils.getSubject();
    Employee employee = (Employee) subject.getPrincipal();
    //查询数据库不然有缓存
    Employee emp = employeeService.selectByName(employee.getName());
    System.out.println("picture:" + emp.getPicture());
    return JsonResult.success(emp.getPicture());
  }


}
